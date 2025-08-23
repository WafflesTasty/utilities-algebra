package waffles.utils.alg.linear.measure.matrix.types.banded;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerHessenberg;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperHessenberg;
import waffles.utils.alg.linear.measure.tensor.Tensor;

/**
 * A {@code Tridiagonal} operator assumes a matrix to have zeroes everywhere
 * except for the diagonal and the two subdiagonals. This is equivalent
 * to having both a lower and upper band of 1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerHessenberg
 * @see UpperHessenberg
 */
public interface Tridiagonal extends LowerHessenberg, UpperHessenberg
{
	/**
	 * Returns the abstract {@code Tridiagonal} type.
	 * 
	 * @return  a type operator
	 */
	public static Tridiagonal Type()
	{
		return () -> null;
	}
	
		
	@Override
	public default Tridiagonal instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Tridiagonal;
	}
	
	
	@Override
	public default int LowerBand()
	{
		return 1;
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}