package zeno.util.algebra.linear.matrix.operators.banded.upper;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;

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
 * @param <M>  the type of the underlying matrix
 * @see UpperHessenberg
 * @see Matrix
 */
public interface UpperTriangular<M extends Matrix> extends UpperHessenberg<M>
{
	/**
	 * Returns the abstract type of the {@code UpperTriangular Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperTriangular<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperTriangular<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
}
