package waffles.utils.alg.linear.solvers.matrix.square.types;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.linear.solvers.matrix.exact.types.LUCholesky;
import waffles.utils.alg.linear.solvers.matrix.exact.types.LUTriangular;
import waffles.utils.alg.linear.solvers.matrix.factor.LQRFactor;
import waffles.utils.alg.linear.solvers.matrix.square.LeastSquares;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;

/**
 * The {@code LSCholesky} algorithm solves least squares systems
 * with {@code Cholesky} factorization. This method decomposes a matrix
 * {@code M = QR} where Q is a (reduced) {@code Orthogonal} matrix, and R
 * an {@code UpperTriangular} matrix. The {@code Cholesky} process solves
 * the system through normal equations {@code M*MX = R*RX = M*B}.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see LeastSquares
 * @see LQRFactor
 */
public class LSCholesky implements LQRFactor, LeastSquares
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LSCholesky}.
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
	 * A {@code State} defines the types of {@code LSCholesky} algorithms.
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
	private LUCholesky luc;
	private Hints hints;
	
	/**
	 * Creates a new {@code LSCholesky}.
	 * 
	 * @param h  solver hints
	 */
	public LSCholesky(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code LSCholesky}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSCholesky(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}
	
		
	@Override
	public <M extends Matrix> M approx(M b)
	{
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
		Matrix t = m.transpose();
		Matrix n = t.times(m);
		
		n.setOperator(Symmetric.Type());
		luc = new LUCholesky(n);
		
		
		Matrix l = luc.L();
		Matrix u = luc.U();
		
		l.setOperator(LowerTriangular.Type());
		u.setOperator(UpperTriangular.Type());
		
		
		lut = new LUTriangular(l);
		q = lut.solve(t).transpose();
		lut = new LUTriangular(u);
		return u;
	}
	
	Matrix factorWide()
	{
		Matrix t = m.transpose();
		Matrix n = m.times(t);
		
		n.setOperator(Symmetric.Type());
		luc = new LUCholesky(n);
		
		
		Matrix l = luc.L();
		Matrix u = luc.U();
		
		l.setOperator(LowerTriangular.Type());
		u.setOperator(UpperTriangular.Type());

		
		lut = new LUTriangular(l);
		q = lut.solve(m);
		return l;
	}
	
	Matrix reduceTall()
	{
		rt = luc.U();

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