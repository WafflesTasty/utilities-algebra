package waffles.utils.alg.lin.measure.matrix.types.banded.lower;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.banded.Tridiagonal;
import waffles.utils.alg.lin.measure.tensor.Tensor;

/**
 * A {@code LowerBidiagonal} operator assumes a matrix to have zeroes everywhere
 * above the diagonal and below the lower subdiagonal. This is equivalent
 * to having a lower band of 1 and an upper band of 0.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerTriangular
 * @see Tridiagonal
 */
public interface LowerBidiagonal extends Tridiagonal, LowerTriangular
{
	/**
	 * Returns the abstract {@code LowerBidiagonal} type.
	 * 
	 * @return  a type operator
	 */
	public static LowerBidiagonal Type()
	{
		return () -> null;
	}
	
	
	@Override
	public default LowerBidiagonal instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof LowerBidiagonal;
	}
	
	
	@Override
	public default int LowerBand()
	{
		return 1;
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}