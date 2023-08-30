package waffles.utils.algebra.elements.linear.matrix.types.banded.lower;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A {@code LowerHessenberg} matrix is assumed to have zeroes everywhere above the
 * first superdiagonal. This is equivalent to having an upper band of 1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Banded
 */
public interface LowerHessenberg extends Banded
{
	/**
	 * Returns the abstract type of the {@code LowerHessenberg} operator.
	 * 
	 * @return  a type operator
	 */
	public static LowerHessenberg Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default LowerHessenberg instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof LowerHessenberg;
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}