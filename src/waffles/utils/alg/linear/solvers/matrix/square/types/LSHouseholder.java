package waffles.utils.alg.linear.solvers.matrix.square.types;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.matrix.exact.types.LUTriangular;
import waffles.utils.alg.linear.solvers.matrix.factor.LQRFactor;
import waffles.utils.alg.linear.solvers.matrix.square.LeastSquares;
import waffles.utils.alg.utilities.matrix.Householder;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code LSHouseholder} algorithm solves least squares systems with
 * {@code Householder} triangulization. The {@code Householder} process
 * simultaneously orthogonalizes all columns by applying reflections.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see LeastSquares
 * @see LQRFactor
 */
public class LSHouseholder implements LQRFactor, LeastSquares
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LSHouseholder}.
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
			if(Matrix().is(UpperTriangular.Type()))
				return State.UPPER_TRIANGULAR;
			if(Matrix().is(Orthogonal.Type()))
				return State.ORTHOGONAL;
			if(Matrix().allows(Tall.Type(), 0))
				return State.TALL;
			return State.WIDE;
		}

		
		@Override
		public default boolean isReduced()
		{
			return true;
		}
		
		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code LSHouseholder} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * An upper triangular matrix is not decomposed.
		 */
		UPPER_TRIANGULAR,
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
	
	
	private Matrix m, q;
	private Matrix lt, rt;
	private LUTriangular lut;
	private Hints hints;
	
	/**
	 * Creates a new {@code LSHouseholder}.
	 * 
	 * @param h  solver hints
	 */
	public LSHouseholder(Hints h)
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
	public LSHouseholder(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}
	
	
	LUTriangular TriangularSolver()
	{
		if(m.allows(Tall.Type(), 0))
		{
			Matrix b = R();
			if(!b.allows(Square.Type(), 0))
			{
				int r = b.Rows();
				int c = b.Columns();
				b = b.resize(c, c);
			}
			
			b.setOperator(UpperTriangular.Type());
			return new LUTriangular(b);
		}
		
		Matrix b = L();
		if(!b.allows(Square.Type(), 0))
		{
			int r = b.Rows();
			int c = b.Columns();
			b = b.resize(r, r);
		}
		
		b.setOperator(LowerTriangular.Type());
		return new LUTriangular(b);
	}
	
	@Override
	public <M extends Matrix> M approx(M b)
	{
		if(lut == null)
		{
			lut = TriangularSolver();
		}
		
		Matrix q = Q().transpose();
		if(m.allows(Tall.Type(), 0))
		{
			// Solve through substitution.
			return (M) lut.solve(q.times(b));
		}
		
		// Solve through substitution.
		return (M) q.times(lut.solve(b));
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
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


	Matrix factorTall()
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// Create the column reflection normal.
			Vector uk = m.Column(k);
			for(int i = 0; i < k; i++)
			{
				uk.set(0f, i);
			}
			
			// If the reflection is feasible...
			if(Hints().Error() < uk.normSqr())
			{
				// Create the column reflection matrix.
				Matrix uhh = Householder.reflect(uk, k);
				
				// Column reflect the target matrix.
				m = uhh.times(m);
				q = q.times(uhh);
			}
		}
		
		return reduceTall();
	}
	
	Matrix factorWide()
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// Create the row reflection normal.
			Vector uk = m.Row(k);
			for(int i = 0; i < k; i++)
			{
				uk.set(0f, i);
			}
			
			// If the reflection is feasible...
			if(Hints().Error() < uk.normSqr())
			{
				// Create the row reflection matrix.
				Matrix uhh = Householder.reflect(uk, k);
				
				// Column reflect the target matrix.
				m = m.times(uhh);
				q = uhh.times(q);
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
			case UPPER_TRIANGULAR:
			{
				lt = Matrices.identity(r1);
				 q = Matrices.identity(r1);
				
				lt.setOperator(Identity.Type());
				 q.setOperator(Identity.Type());
				
				rt = reduceTall();
				
				break;
			}
			case ORTHOGONAL:
			{
				q = m;
				
				lt = Matrices.identity(r1);
				rt = Matrices.identity(c1);
				
				lt.setOperator(Identity.Type());
				rt.setOperator(Identity.Type());
				
				rt = reduceTall();
			}
			case TALL:
			{
				rt = m.instance();
				
				 q = Matrices.identity(r1);
				lt = Matrices.identity(r1);
				
				 q.setOperator(Identity.Type());
				lt.setOperator(Identity.Type());
				
				rt = factorTall();
				
				break;
			}
			case WIDE:
			{
				lt = m.instance();
				
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

		return q;
	}
}