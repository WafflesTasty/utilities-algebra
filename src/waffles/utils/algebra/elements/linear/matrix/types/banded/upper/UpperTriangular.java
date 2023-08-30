package waffles.utils.algebra.elements.linear.matrix.types.banded.upper;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * An {@code UpperTriangular} matrix is assumed to have zeroes everywhere below the
 * diagonal. This is equivalent to having a lower band of 0.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see UpperHessenberg
 */
public interface UpperTriangular extends UpperHessenberg
{
	/**
	 * Returns the abstract type of the {@code UpperTriangular} operator.
	 * 
	 * @return  a type operator
	 */
	public static UpperTriangular Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperTriangular instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof UpperTriangular;
	}
	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
}