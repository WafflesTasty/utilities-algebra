package waffles.utils.alg.linear.solvers.factor.uv;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.Tridiagonal;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerHessenberg;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperHessenberg;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.factor.UVFactor;
import waffles.utils.alg.utilities.matrix.Householder;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code UVHessenberg} algorithm performs hessenberg factorization using
 * {@code HouseHolder} transformations. This method decomposes a {@code Square}
 * matrix {@code M = UHV* = UHU*}, where U, V are (reduced) {@code Orthogonal}
 * matrices, and H is a {@code Hessenberg} matrix.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see UVFactor
 */
public class UVHessenberg implements UVFactor
{
	/**
	 * The {@code Hints} interface defines hints for an {@code UVHessenberg}.
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
		 * This determines which Hessenberg algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(UpperHessenberg.Type()))
				return State.TRIVIAL;
			if(Matrix().allows(Square.Type(), 0))
				return State.VALID;
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
		 * An invalid matrix can not be used.
		 */
		INVALID,
		/**
		 * A hessenberg matrix is not decomposed at all.
		 */
		TRIVIAL,
		/**
		 * A square matrix is valid for factorization.
		 */
		VALID;
	}
	
	
	private Hints hints;
	private Matrix u, h;
		
	/**
	 * Creates a new {@code UVHessenberg}.
	 * 
	 * @param h  solver hints
	 */
	public UVHessenberg(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code UVHessenberg}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public UVHessenberg(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}

	/**
	 * Returns the H matrix of the {@code UVFactor}.
	 * <ul>
	 * 	<li>If M was {@code Tall}, H will be {@code UpperHessenberg}.</li>
	 *  <li>If M was {@code Wide}, H will be {@code LowerHessenberg}.</li>
	 * </ul>
	 * 
	 * @return  a hessenberg matrix
	 * 
	 * 
	 * @see LowerHessenberg
	 * @see UpperHessenberg
	 * @see Matrix
	 */
	public Matrix H()
	{
		if(h == null)
			h = factor();
		return h;
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
			h = factor();
		return u;
	}
	
	@Override
	public Matrix V()
	{
		if(u == null)
			h = factor();
		return u;
	}

	
	Matrix factorSquare()
	{
		int r1 = h.Rows();
		int c1 = h.Columns();

		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// If the subdiagonal is not finished...
			if(k + 2 < r1)
			{
				// Create the column reflection normal.
				Vector uk = h.Column(k);
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
					h = uhh.times(h).times(vhh);
					u = u.times(uhh);
				}
			}
		}

		u.setOperator(Orthogonal.Type());
		if(h.is(Symmetric.Type()))
			h.setOperator(Tridiagonal.Type());
		else
			h.setOperator(UpperHessenberg.Type());

		return h;
	}
	
	Matrix factor()
	{
		if(h == null)
		{
			h = Hints().Matrix();
			int c1 = h.Columns();
			int r1 = h.Rows();
			
			u = Matrices.identity(r1);
			u.setOperator(Identity.Type());

			switch(Hints().State())
			{
			case VALID:
				h = factorSquare(); break;
			case INVALID:
			case TRIVIAL:
			default:
				break;
			}
		}

		return h;
	}
}