package zeno.util.algebra.linear.matrix.operators.banded;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.banded.lower.LowerHessenberg;
import zeno.util.algebra.linear.matrix.operators.banded.upper.UpperHessenberg;

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
 * @param <M>  the type of the underlying matrix
 * @see LowerHessenberg
 * @see UpperHessenberg
 * @see Matrix
 */
public interface Tridiagonal<M extends Matrix> extends LowerHessenberg<M>, UpperHessenberg<M>
{
	/**
	 * Returns the abstract type of the {@code Tridiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Tridiagonal<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default Tridiagonal<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
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