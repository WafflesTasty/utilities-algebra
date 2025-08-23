package waffles.utils.alg.linear.measure.matrix.types.banded.upper;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Tridiagonal;
import waffles.utils.alg.linear.measure.tensor.Tensor;

/**
 * An {@code UpperBidiagonal} operator assumes a matrix to have zeroes everywhere
 * below the diagonal and above the upper subdiagonal. This is equivalent
 * to having a lower band of 0 and an upper band of 1.
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
	 * Returns the abstract {@code UpperBidiagonal} type.
	 * 
	 * @return  a type operator
	 */
	public static UpperBidiagonal Type()
	{
		return () -> null;
	}
	
	
	@Override
	public default UpperBidiagonal instance(Tensor t)
	{
		return () -> (Matrix) t;
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