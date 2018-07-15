package zeno.util.algebra.linear.matrix.operators.banded.lower;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;

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
 * @param <M>  the type of the underlying matrix
 * @see LowerHessenberg
 * @see Matrix
 */
public interface LowerTriangular<M extends Matrix> extends LowerHessenberg<M>
{
	/**
	 * Returns the abstract type of the {@code LowerTriangular Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerTriangular<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerTriangular<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}
