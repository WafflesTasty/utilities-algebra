package zeno.util.algebra.linear.matrix.types.banded.upper;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.tensor.Tensor;

/**
 * The {@code UpperTriangular} interface defines an operator for upper triangular matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere below the
 * diagonal. This is equivalent to having a lower band of 0.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see UpperHessenberg
 */
public interface UpperTriangular extends UpperHessenberg
{
	/**
	 * Returns the abstract type of the {@code UpperTriangular Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperTriangular Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperTriangular instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
}
