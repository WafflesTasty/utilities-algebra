package waffles.utils.alg.linear.solvers.factor.uv;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerBidiagonal;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperBidiagonal;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.exact.Determinant;
import waffles.utils.alg.linear.solvers.factor.UVFactor;
import waffles.utils.alg.utilities.matrix.Householder;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code UVBidiagonal} algorithm performs bidiagonal factorization using
 * {@code HouseHolder} transformations. This method decomposes a matrix
 * {@code M = UBV*}, where U, V are (reduced) {@code Orthogonal}
 * matrices, and B is a {@code Bidiagonal} matrix.
 * 
 * The algorithm used is a simplified version of the Golub-Kahan-Lanczos algorithm.
 * It alternates between zeroeing the components of a row and a column by applying
 * the appropriate Householder reflections on both sides of the matrix.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see <a href="https://www.netlib.org/utk/people/JackDongarra/etemplates/node198.html">Golub-Kahan-Lanczos</a>
 * @see Determinant
 * @see UVFactor
 */
public class UVBidiagonal implements UVFactor, Determinant
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LUCrout}.
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
		 * This determines which Crout's algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(UpperBidiagonal.Type()))
				return State.TRIVIAL;
			if(Matrix().allows(Tall.Type(), 0))
				return State.TALL;
			return State.WIDE;
		}

		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code UVBidiagonal} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * A bidiagonal matrix is not decomposed at all.
		 */
		TRIVIAL,
		/**
		 * A tall matrix is factorized the usual way.
		 */
		TALL,
		/**
		 * A wide matrix is factorized the transposed way.
		 */
		WIDE;
	}
	
	
	private Hints hints;
	private Float det, sgn;
	private Matrix u, b, v;
		
	/**
	 * Creates a new {@code UVBidiagonal}.
	 * 
	 * @param h  solver hints
	 */
	public UVBidiagonal(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code UVBidiagonal}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public UVBidiagonal(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}

	/**
	 * Returns the B matrix of the {@code UVFactor}.
	 * <ul>
	 * 	<li>If M was {@code Tall}, B will be {@code UpperBidiagonal}.</li>
	 *  <li>If M was {@code Wide}, B will be {@code LowerBidiagonal}.</li>
	 * </ul>
	 * 
	 * @return  a bidiagonal matrix
	 * 
	 * 
	 * @see LowerBidiagonal
	 * @see UpperBidiagonal
	 * @see Matrix
	 */
	public Matrix B()
	{
		if(b == null)
			b = factor();
		return b;
	}
	
		
	@Override
	public float determinant()
	{
		b = factor();
		if(det == null)
		{
			det = sgn;
			
			
			int r = b.Rows();
			int c = b.Columns();
			
			for(int k = 0; k < Integers.min(r, c); k++)
			{
				det *= b.get(k, k);
			}
		}

		return det;
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
			b = factor();
		return u;
	}
	
	@Override
	public Matrix V()
	{
		if(v == null)
			b = factor();
		return v;
	}

	
	Matrix factorTall()
	{
		int r1 = b.Rows();
		int c1 = b.Columns();

		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// If the subdiagonal is not finished...
			if(k + 1 < r1)
			{
				// Create the column reflection normal.
				Vector uk = b.Column(k);
				for(int i = 0; i < k; i++)
				{
					uk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(Hints().Error() < uk.normSqr())
				{
					// Create the column reflection matrix.
					Matrix uhh = Householder.reflect(uk, k+0);
					
					// Column reflect the target matrix.
					b = uhh.times(b);
					u = u.times(uhh);
					sgn = -sgn;
				}
			}
			
			// If the superdiagonal is not finished...
			if(k + 2 < c1)
			{
				// Create the row reflection normal.
				Vector vk = b.Row(k);
				for(int i = 0; i <= k; i++)
				{
					vk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(Hints().Error() < vk.normSqr())
				{
					// Create the row reflection matrix.
					Matrix vhh = Householder.reflect(vk, k+1);
					
					// Row reflect the target matrix.
					b = b.times(vhh);
					v = v.times(vhh);
					sgn = -sgn;
				}
			}
		}
		
		if(b.allows(Square.Type(), 0))
		{
			u.setOperator(Orthogonal.Type());
			b.setOperator(UpperBidiagonal.Type());
			v.setOperator(Orthogonal.Type());
			
			return b;
		}
		
		v.setOperator(Orthogonal.Type());
		if(!Hints().isReduced())
			u.setOperator(Orthogonal.Type());
		else
		{
			b = b.resize(c1, c1);
			b.setOperator(UpperBidiagonal.Type());
			u = u.resize(r1, c1);
		}

		return b;
	}
	
	Matrix factorWide()
	{
		int r1 = b.Rows();
		int c1 = b.Columns();

		// For every row/column in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			// If the superdiagonal is not finished...
			if(k + 1 < c1)
			{
				// Create the row reflection normal.
				Vector vk = b.Row(k);
				for(int i = 0; i < k; i++)
				{
					vk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(Hints().Error() < vk.normSqr())
				{
					// Create the row reflection matrix.
					Matrix vhh = Householder.reflect(vk, k+0);
					
					// Row reflect the target matrix.
					b = b.times(vhh);
					v = v.times(vhh);
					sgn = -sgn;
				}
			}
			
			// If the subdiagonal is not finished...
			if(k + 2 < r1)
			{
				// Create the column reflection normal.
				Vector uk = b.Column(k);
				for(int i = 0; i <= k; i++)
				{
					uk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(Hints().Error() < uk.normSqr())
				{
					// Create the column reflection matrix.
					Matrix uhh = Householder.reflect(uk, k+1);
					
					// Column reflect the target matrix.
					b = uhh.times(b);
					u = u.times(uhh);
					sgn = -sgn;
				}
			}
		}

		if(b.allows(Square.Type(), 0))
		{
			u.setOperator(Orthogonal.Type());
			b.setOperator(UpperBidiagonal.Type());
			v.setOperator(Orthogonal.Type());
			
			return b;
		}
		
		u.setOperator(Orthogonal.Type());
		if(!Hints().isReduced())
			v.setOperator(Orthogonal.Type());
		else
		{
			b = b.resize(r1, r1);
			b.setOperator(LowerBidiagonal.Type());
			v = v.resize(c1, r1);
		}
		
		return b;
	}
	
	Matrix factor()
	{
		if(b == null)
		{
			b = Hints().Matrix();
			int c1 = b.Columns();
			int r1 = b.Rows();
			
			u = Matrices.identity(r1);
			u.setOperator(Identity.Type());
			v = Matrices.identity(c1);
			v.setOperator(Identity.Type());
			sgn = 1f;
			
			switch(Hints().State())
			{
			case TALL:
				b = factorTall(); break;				
			case WIDE:
				b = factorWide(); break;
			case TRIVIAL:
			default:
				break;
			}
		}

		return b;
	}
}