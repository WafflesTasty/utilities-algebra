package waffles.utils.alg.lin.solvers.matrix.ranks.types;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.lin.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.solvers.matrix.exact.types.LUTriangular;
import waffles.utils.alg.lin.solvers.matrix.factor.LQRFactor;
import waffles.utils.alg.lin.solvers.matrix.factor.PLQRFactor;
import waffles.utils.alg.lin.solvers.matrix.ranks.RankReveal;
import waffles.utils.alg.lin.solvers.matrix.square.LeastSquares;
import waffles.utils.alg.utilities.matrix.Householder;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRHouseholder} algorithm computes rank through {@code Householder} triangulization.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see <a href="https://www.amazon.com/Numerical-Linear-Algebra-Optimization-Vol/dp/0201126494">Numerical Linear Algebra & Optimization - Volume 1, 1991</a>
 * @see LeastSquares
 * @see PLQRFactor
 * @see RankReveal
 */
public class RRHouseholder implements PLQRFactor, LeastSquares, RankReveal
{
	/**
	 * The {@code Hints} interface defines hints for an {@code RRHouseholder}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see LQRFactor
	 */
	@FunctionalInterface
	public static interface Hints extends LQRFactor.Hints
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * This determines which least squares algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(Orthogonal.Type()))
				return State.ORTHOGONAL;
			if(Matrix().allows(Tall.Type(), 0))
				return State.TALL;
			return State.WIDE;
		}


		@Override
		public default double Error()
		{
			return Doubles.pow(2, -24);
		}
	}
	
	/**
	 * The {@code TriangleHints} define hints for an {@code LUTriangular} solver.
	 *
	 * @author Waffles
	 * @since 02 Sep 2025
	 * @version 1.1
	 *
	 * 
	 * @see LUTriangular
	 */
	public class TriangleHints implements LUTriangular.Hints
	{	
		@Override
		public LUTriangular.State State()
		{
			if(Matrix().allows(Tall.Type(), 0))
				return LUTriangular.State.UPPER_TRIANGULAR;
			return LUTriangular.State.LOWER_TRIANGULAR;
		}
			
		@Override
		public boolean checkPivot()
		{
			return false;
		}
		
		@Override
		public Matrix Matrix()
		{
			Matrix m = Hints().Matrix();
			if(m.allows(Tall.Type(), 0))
				return R();
			return L();
		}
		
		@Override
		public double Error()
		{
			return Hints().Error();
		}
	}
			
	/**
	 * A {@code State} defines the types of {@code RRHouseholder} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * An orthogonal matrix is not decomposed.
		 */
		ORTHOGONAL,
		/**
		 * A tall matrix is factorized as QR.
		 */
		TALL,
		/**
		 * A wide matrix is factorized as LQ.
		 */
		WIDE;
	}
	
	
	private int rank;
	private Hints hints;
	private Matrix lt, rt;
	private Matrix m, p, q;
	private LUTriangular lut;
	
	/**
	 * Creates a new {@code LSHouseholder}.
	 * 
	 * @param h  solver hints
	 */
 	public RRHouseholder(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code LSHouseholder}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRHouseholder(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}
	
	
	LUTriangular TriangleSolver()
	{
		return new LUTriangular(new TriangleHints());
	}
	
	@Override
	public <M extends Matrix> M approx(M b)
	{
		if(lut == null)
		{
			lut = TriangleSolver();
		}
		

		if(m.allows(Tall.Type(), 0))
		{	
			Matrix x = Q().transpose().times(b);
			return (M) P().times(lut.solve(x));
		}

		Matrix x = lut.solve(P().times(b));
		return (M) Q().transpose().times(x);
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}
	
	@Override
	public Matrix P()
	{
		if(p == null)
			m = factor();
		return p;
	}
	
	@Override
	public Matrix L()
	{
		if(lt == null)
			m = factor();
		return lt;
	}
	
	@Override
	public Matrix Q()
	{
		if(q == null)
			m = factor();
		return q;
	}

	@Override
	public Matrix R()
	{
		if(rt == null)
			m = factor();
		return rt;
	}

	@Override
	public int rank()
	{
		if(m == null)
			m = factor();
		return rank;
	}
	
	
	
	int indexTall(int r)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		for(int c = 0; c < c1; c++)
		{
			if(p.get(r, c) > 0f)
			{
				return c;
			}
		}
		
		return -1;
	}
	
	int indexWide(int c)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		for(int r = 0; r < c1; r++)
		{
			if(p.get(r, c) > 0f)
			{
				return r;
			}
		}
		
		return -1;
	}
	
	
	void swapTall(int c, int d)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		for(int k = 0; k < r1; k++)
		{
			float vc = m.get(k, c);
			float vd = m.get(k, d);
			
			m.set(vd, k, c);
			m.set(vc, k, d);
			
			if(k < c1)
			{
				float pc = p.get(k, c);
				float pd = p.get(k, d);
				
				p.set(pd, k, c);
				p.set(pc, k, d);
			}
		}
	}
	
	void swapWide(int r, int s)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		for(int k = 0; k < c1; k++)
		{
			float vc = m.get(r, k);
			float vd = m.get(s, k);
			
			m.set(vd, r, k);
			m.set(vc, s, k);
			
			if(k < r1)
			{
				float pc = p.get(r, k);
				float pd = p.get(s, k);
				
				p.set(pd, r, k);
				p.set(pc, s, k);
			}
		}
	}
	
	
	Matrix factorTall()
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		double e = Hints().Error();
		
		float[] norms = new float[c1];
		for(int c = 0; c < c1; c++)
		{
			Vector v = m.Column(c);
			norms[c] = v.normSqr();
		}
		
		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			int pvt = k;
			// Create the column reflection normal.
			Vector uk = m.Column(k);
			for(int i = 0; i < k; i++)
			{
				uk.set(0f, i);
			}
			
			float norm = uk.normSqr();
			// For every remaining column...
			for(int j = k + 1; j < c1; j++)
			{
				// Consider it as a pivot.
				Vector vk = m.Column(j);
				for(int i = 0; i < k; i++)
				{
					vk.set(0f, i);
				}
				
				float nk = vk.normSqr();
				if(norm < nk)
				{
					uk = vk;
					norm = nk;
					pvt = j;
				}
			}
			
			int c = indexTall(k);
			// If a pivot was found...
			if(k < pvt)
			{
				// Swap the columns.
				swapTall(k, pvt);
			}
			
			double dif = (1f + norms[c]);
			// If the reflection is feasible...
			if(uk.normSqr() <= dif * dif * e)
				break;
			else			
			{
				// Create the column reflection normal.
				for(int i = 0; i < k; i++)
				{
					uk.set(0f, i);
				}
				
				// Create the column reflection matrix.
				Matrix vhh = Householder.reflect(uk, k);
				
				// Column reflect the target matrix.
				m = vhh.times(m);
				q = q.times(vhh);
				
				rank++;
			}
		}
		
		return reduceTall();
	}
	
	Matrix factorWide()
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		double e = Hints().Error();
		
		float[] norms = new float[r1];
		for(int r = 0; r < r1; r++)
		{
			Vector v = m.Row(r);
			norms[r] = v.normSqr();
		}
		
		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			int pvt = k;
			// Create the row reflection normal.
			Vector uk = m.Row(k);
			for(int i = 0; i < k; i++)
			{
				uk.set(0f, i);
			}
			
			float norm = uk.normSqr();
			// For every remaining row...
			for(int j = k + 1; j < r1; j++)
			{
				// Consider it as a pivot.
				Vector vk = m.Row(j);
				for(int i = 0; i < k; i++)
				{
					vk.set(0f, i);
				}
				
				float nk = vk.normSqr();
				if(norm < nk)
				{
					uk = vk;
					norm = nk;
					pvt = j;
				}
			}
			
			int r = indexWide(k);
			// If a pivot was found...
			if(k < pvt)
			{
				// Swap the rows.
				swapWide(k, pvt);
			}
						
			double dif = (1f + norms[r]);
			// If the reflection is feasible...
			if(uk.normSqr() <= dif * dif * e)
				break;
			else
			{
				// Create the row reflection normal.
				for(int i = 0; i < k; i++)
				{
					uk.set(0f, i);
				}
				
				// Create the row reflection matrix.
				Matrix vhh = Householder.reflect(uk, k);
				
				// Row reflect the target matrix.
				m = m.times(vhh);
				q = vhh.times(q);
				
				rank++;
			}
		}
		
		return reduceWide();
	}
	
	Matrix reduceTall()
	{
		rt = m;

		int r1 = m.Rows();
		int c1 = m.Columns();
		
		if(rt.allows(Square.Type(), 0))
		{
			rt.setOperator(UpperTriangular.Type());
			q.setOperator(Orthogonal.Type());

			return rt;
		}
		
		if(!Hints().isReduced())
			q.setOperator(Orthogonal.Type());
		else
		{
			rt = rt.resize(c1, c1);
			rt.setOperator(UpperTriangular.Type());
			q = q.resize(r1, c1);
		}

		return rt;
	}
	
	Matrix reduceWide()
	{
		lt = m;
		
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		if(lt.allows(Square.Type(), 0))
		{
			lt.setOperator(LowerTriangular.Type());
			q.setOperator(Orthogonal.Type());

			return lt;
		}
		
		if(!Hints().isReduced())
			q.setOperator(Orthogonal.Type());
		else
		{
			lt = lt.resize(r1, r1);
			lt.setOperator(LowerTriangular.Type());
			q = q.resize(r1, c1);
		}

		return lt;
	}
	
	Matrix factor()
	{
		if(m == null)
		{
			m = Hints().Matrix();
			int c1 = m.Columns();
			int r1 = m.Rows();

			switch(Hints().State())
			{
			case ORTHOGONAL:
			{
				q = m;
				
				lt = Matrices.identity(r1);
				rt = Matrices.identity(c1);
				 p = Matrices.identity(c1);
				 
				lt.setOperator(Identity.Type());
				rt.setOperator(Identity.Type());
				 p.setOperator(Identity.Type());
				
				rt = reduceTall();
				
				break;
			}
			case TALL:
			{
				rt = m.instance();
				
				 p = Matrices.identity(c1);
				 q = Matrices.identity(r1);
				lt = Matrices.identity(r1);
				
				lt.setOperator(Identity.Type());
				 q.setOperator(Identity.Type());
				
				rt = factorTall();
				
				break;
			}
			case WIDE:
			{
				lt = m.instance();
				
				 p = Matrices.identity(r1);
				 q = Matrices.identity(c1);
				rt = Matrices.identity(c1);
				
				 q.setOperator(Identity.Type());
				rt.setOperator(Identity.Type());
				
				lt = factorWide();
				
				break;
			}
			default:
				break;
			}
		}

		return m;
	}
}