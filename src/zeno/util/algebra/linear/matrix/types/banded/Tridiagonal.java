package zeno.util.algebra.linear.matrix.types.banded;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.lower.LowerHessenberg;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperHessenberg;
import zeno.util.algebra.linear.tensor.Tensor;

/**
 * The {@code Tridiagonal} interface defines an operator for tridiagonal matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere except
 * for the diagonal and the two subdiagonals. This is equivalent to having a lower
 * and upper band of 1.
 * 
 * @author Zeno
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
	 * Returns the abstract type of the {@code Tridiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Tridiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default Tridiagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
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