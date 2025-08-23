package waffles.utils.alg.linear.measure.matrix.types.banded.upper;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.tensor.Tensor;

/**
 * An {@code UpperTriangular} operator assumes a matrix to have zeroes everywhere
 * below the main diagonal. This is equivalent to having a lower band of 0.
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
	 * Returns the abstract {@code UpperTriangular} type.
	 * 
	 * @return  a type operator
	 */
	public static UpperTriangular Type()
	{
		return () -> null;
	}
	
	
	@Override
	public default UpperTriangular instance(Tensor t)
	{
		return () -> (Matrix) t;
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