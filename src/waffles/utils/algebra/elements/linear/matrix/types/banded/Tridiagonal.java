package waffles.utils.algebra.elements.linear.matrix.types.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerHessenberg;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperHessenberg;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A {@code Tridiagonal} matrix is assumed to have zeroes everywhere except
 * for the diagonal and the two subdiagonals. This is equivalent to
 * having a lower and upper band of 1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerHessenberg
 * @see UpperHessenberg
 */
public interface Tridiagonal extends LowerHessenberg, UpperHessenberg
{
	/**
	 * Returns the abstract type of the {@code Tridiagonal} operator.
	 * 
	 * @return  a type operator
	 */
	public static Tridiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
		
	@Override
	public default Tridiagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Tridiagonal;
	}
	
	@Override
	public default int LowerBand()
	{
		return 1;
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}