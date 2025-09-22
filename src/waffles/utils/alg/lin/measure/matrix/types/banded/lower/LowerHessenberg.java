package waffles.utils.alg.lin.measure.matrix.types.banded.lower;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.banded.Banded;
import waffles.utils.alg.lin.measure.tensor.Tensor;

/**
 * The {@code LowerHessenberg} operator assumes a {@code Banded} matrix to have zeroes everywhere
 * above the first superdiagonal. This is equivalent to having an upper band of 1.
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
	 * Returns the abstract {@code LowerHessenberg} type.
	 * 
	 * @return  a type operator
	 */
	public static LowerHessenberg Type()
	{
		return () -> null;
	}


	@Override
	public default LowerHessenberg instance(Tensor t)
	{
		return () -> (Matrix) t;
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