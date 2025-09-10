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
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;

/**
 * The {@code LSGramSchmidt} algorithm solves least squares systems with
 * {@code Gram-Schmidt} orthogonalization. This method decomposes a matrix
 * {@code M = QR} where Q is a (reduced) {@code Orthogonal} matrix, and R
 * an {@code UpperTriangular} matrix. The {@code Gram-Schmidt} process
 * sequentially orthogonalizes each column of M in order.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see LeastSquares
 * @see LQRFactor
 */
public class LSGramSchmidt implements LQRFactor, LeastSquares
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LSGramSchmidt}.
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
	 * A {@code State} defines the types of {@code LSGramSchmidt} algorithms.
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
	 * Creates a new {@code LSGramSchmidt}.
	 * 
	 * @param h  solver hints
	 */
	public LSGramSchmidt(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code LSGramSchmidt}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSGramSchmidt(Matrix m)
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

		// For every column in the base matrix...
		for(int c = 0; c < c1; c++)
		{
			// Compute the vector norm.
			Vector vc = m.Column(c);
			float norm = vc.norm();
			rt.set(norm, c, c);

			// Normalize the vector.
			vc = vc.times(1f / norm);
			
			// For every row in the base matrix...
			for(int r = 0; r < r1; r++)
			{
				// Store the unit vector in Q.
				q.set(vc.get(r), r, c);
			}
			
			// For every remaining column...
			for(int d = c + 1; d < c1; d++)
			{
				// Compute the dot product.
				Vector vd = m.Column(d);
				float dot = vc.dot(vd);
				rt.set(dot, c, d);
				
				// Subtract the unit vector.
				vd = vd.minus(vc.times(dot));
				// Store the new vector in M.
				for(int r = 0; r < r1; r++)
				{
					m.set(vd.get(r), r, d);
				}
			}
		}
		
		return reduceTall();
	}
	
	Matrix factorWide()
	{
		int r1 = m.Rows();
		int c1 = m.Columns();

		// For every row in the base matrix...
		for(int r = 0; r < r1; r++)
		{
			// Compute the vector norm.
			Vector vr = m.Row(r);
			float norm = vr.norm();
			lt.set(norm, r, r);

			// Normalize the vector.
			vr = vr.times(1f / norm);
			
			// For every column in the base matrix...
			for(int c = 0; c < c1; c++)
			{
				// Store the unit vector in Q.
				q.set(vr.get(r), r, c);
			}
			
			// For every remaining row...
			for(int s = r + 1; s < r1; s++)
			{
				// Compute the dot product.
				Vector vs = rt.Row(s);
				float dot = vr.dot(vs);
				lt.set(dot, s, r);
				
				// Subtract the unit vector.
				vs = vs.minus(vr.times(dot));
				// Store the new vector in M.
				for(int c = 0; c < c1; c++)
				{
					m.set(vs.get(r), r, c);
				}
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
				rt = m;
				
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
				
				break;
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