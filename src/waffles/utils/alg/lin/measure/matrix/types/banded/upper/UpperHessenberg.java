package waffles.utils.alg.lin.measure.matrix.types.banded.upper;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.banded.Banded;
import waffles.utils.alg.lin.measure.tensor.Tensor;

/**
 * An {@code UpperHessenberg} operator assumes a matrix to have zeroes everywhere
 * below the first subdiagonal. This is equivalent to having a lower band of 1.
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
	 * Returns the abstract {@code UpperHessenberg} type.
	 * 
	 * @return  a type operator
	 */
	public static UpperHessenberg Type()
	{
		return () -> null;
	}


	@Override
	public default UpperHessenberg instance(Tensor t)
	{
		return () -> (Matrix) t;
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