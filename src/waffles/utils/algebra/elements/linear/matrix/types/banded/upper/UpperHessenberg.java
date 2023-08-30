package waffles.utils.algebra.elements.linear.matrix.types.banded.upper;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A {@code UpperHessenberg} matrix is assumed to have zeroes everywhere below the
 * first subdiagonal. This is equivalent to having a lower band of 1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Banded
 */
public interface UpperHessenberg extends Banded
{
	/**
	 * Returns the abstract type of the {@code UpperHessenberg} operator.
	 * 
	 * @return  a type operator
	 */
	public static UpperHessenberg Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default UpperHessenberg instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof UpperHessenberg;
	}
	
	@Override
	public default int LowerBand()
	{
		return 1;
	}
}