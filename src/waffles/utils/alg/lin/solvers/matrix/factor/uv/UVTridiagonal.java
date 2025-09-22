package waffles.utils.alg.lin.solvers.matrix.factor.uv;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.matrix.types.banded.Tridiagonal;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.lin.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.solvers.matrix.factor.UVFactor;
import waffles.utils.alg.utilities.matrix.Householder;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code UVTridiagonal} algorithm performs tridiagonal factorization using
 * {@code HouseHolder} transformations. This method decomposes a {@code Symmetric}
 * matrix {@code M = UTV* = UTU*}, where U, V are {@code Orthogonal} matrices,
 * and T is a {@code Tridiagonal} matrix.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see UVFactor
 */
public class UVTridiagonal implements UVFactor
{
	/**
	 * The {@code Hints} interface defines hints for an {@code UVTridiagonal}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see UVFactor
	 */
	@FunctionalInterface
	public static interface Hints extends UVFactor.Hints
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * This determines which Tridiagonal algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(Symmetric.Type()))
				return State.SYMMETRIC;
			if(Matrix().allows(Square.Type(), 0))
				return State.INDETERMINATE;
			return State.INVALID;
		}

		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code UVHessenberg} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * An invalid matrix cannot be used.
		 */
		INVALID,
		/**
		 * An indeterminate matrix is validated against other types.
		 */
		INDETERMINATE,
		/**
		 * A symmetric matrix is valid and can be used.
		 */
		SYMMETRIC;
	}
	
	
	private Hints hints;
	private State state;
	private Matrix u, t;
		
	/**
	 * Creates a new {@code UVTridiagonal}.
	 * 
	 * @param h  solver hints
	 */
	public UVTridiagonal(Hints h)
	{
		state = h.State();
		hints = h;
	}
	
	/**
	 * Creates a new {@code UVTridiagonal}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public UVTridiagonal(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}

	/**
	 * Returns the T matrix of the {@code UVTridiagonal}.
	 * 
	 * @return  a tridiagonal matrix
	 * 
	 * 
	 * @see Tridiagonal
	 * @see Matrix
	 */
	public Matrix T()
	{
		if(t == null)
			t = factor();
		return t;
	}
	

	@Override
	public Hints Hints()
	{
		return hints;
	}

	@Override
	public Matrix U()
	{
		if(u == null)
			t = factor();
		return u;
	}
	
	@Override
	public Matrix V()
	{
		if(u == null)
			t = factor();
		return u;
	}

	
	Matrix factorSymmetric()
	{
		int r1 = t.Rows();
		int c1 = t.Columns();

		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// If the subdiagonal is not finished...
			if(k + 2 < r1)
			{
				// Create the column reflection normal.
				Vector uk = t.Column(k);
				for(int i = 0; i <= k; i++)
				{
					uk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(Hints().Error() < uk.normSqr())
				{
					// Create the column reflection matrix.
					Matrix uhh = Householder.reflect(uk, k+1);
					Matrix vhh = uhh.transpose();
					
					// Column reflect the target matrix.
					t = uhh.times(t).times(vhh);
					u = u.times(uhh);
				}
			}
		}

		t.setOperator(Tridiagonal.Type());
		u.setOperator(Orthogonal.Type());
		
		return t;
	}
	
	Matrix factor()
	{
		if(t == null)
		{
			switch(Hints().State())
			{
			case SYMMETRIC:
			{
				t = Hints().Matrix();
				int c1 = t.Columns();
				int r1 = t.Rows();
				
				u = Matrices.identity(r1);
				u.setOperator(Identity.Type());
				t = factorSymmetric();
				break;
			}
			case INDETERMINATE:
			case INVALID:
			default:
				break;
			}
		}

		return t;
	}
	
	State State()
	{
		if(state == State.INDETERMINATE)
		{
			double e = Hints().Error();
			Matrix a = Hints().Matrix();
			if(a.allows(Symmetric.Type(), e))
				state = State.SYMMETRIC;
			else
				state = State.INVALID;
		}

		return state;
	}
}