package waffles.utils.algebra.elements.linear.matrix.types.banded.lower;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A {@code LowerTriangular} matrix is assumed to have zeroes everywhere above the
 * diagonal. This is equivalent to having an upper band of 0.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerHessenberg
 */
public interface LowerTriangular extends LowerHessenberg
{
	/**
	 * Returns the abstract type of the {@code LowerTriangular} operator.
	 * 
	 * @return  a type operator
	 */
	public static LowerTriangular Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerTriangular instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof LowerTriangular;
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}
