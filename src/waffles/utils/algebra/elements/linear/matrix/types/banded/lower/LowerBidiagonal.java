package waffles.utils.algebra.elements.linear.matrix.types.banded.lower;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Tridiagonal;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A {@code LowerBidiagonal} matrix is assumed to have zeroes everywhere above
 * the diagonal and below the lower subdiagonal. This is equivalent to
 * having a lower band of 1 and an upper band of 0.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerTriangular
 * @see Tridiagonal
 */
public interface LowerBidiagonal extends Tridiagonal, LowerTriangular
{
	/**
	 * Returns the abstract type of the {@code LowerBidiagonal} operator.
	 * 
	 * @return  a type operator
	 */
	public static LowerBidiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerBidiagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof LowerBidiagonal;
	}
	
	@Override
	public default int LowerBand()
	{
		return 1;
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}