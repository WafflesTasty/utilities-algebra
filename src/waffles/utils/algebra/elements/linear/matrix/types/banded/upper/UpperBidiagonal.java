package waffles.utils.algebra.elements.linear.matrix.types.banded.upper;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Tridiagonal;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * An {@code UpperBidiagonal} matrix is assumed to have zeroes everywhere below
 * the diagonal and above the upper subdiagonal. This is equivalent to
 * having a lower band of 0 and an upper band of 1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see UpperTriangular
 * @see Tridiagonal
 */
public interface UpperBidiagonal extends Tridiagonal, UpperTriangular
{
	/**
	 * Returns the abstract type of the {@code UpperBidiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperBidiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperBidiagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof UpperBidiagonal;
	}
	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}