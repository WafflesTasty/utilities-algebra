package zeno.util.algebra.linear.matrix.types.banded.lower;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.tensor.Tensor;

/**
 * The {@code LowerTriangular} interface defines an operator for lower triangular matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere above the
 * diagonal. This is equivalent to having an upper band of 0.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerHessenberg
 */
public interface LowerTriangular extends LowerHessenberg
{
	/**
	 * Returns the abstract type of the {@code LowerTriangular Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerTriangular Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerTriangular instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}
