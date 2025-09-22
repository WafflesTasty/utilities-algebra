package waffles.utils.alg.lin.measure.matrix.types.banded.lower;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.tensor.Tensor;

/**
 * The {@code LowerTriangular} operator assumes a {@code Banded} matrix to have zeroes everywhere
 * above the main diagonal. This is equivalent to having an upper band of 0.
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
	 * Returns the abstract {@code LowerTriangular} type.
	 * 
	 * @return  a type operator
	 */
	public static LowerTriangular Type()
	{
		return () -> null;
	}
	
	
	@Override
	public default LowerTriangular instance(Tensor t)
	{
		return () -> (Matrix) t;
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
