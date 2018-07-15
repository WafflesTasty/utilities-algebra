package zeno.util.algebra.linear.matrix.operators.banded.upper;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.banded.Tridiagonal;

/**
 * The {@code UpperBidiagonal} interface defines an operator for upper bidiagonal matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere below the diagonal
 * and above the upper subdiagonal. This is equivalent to having a lower band of 0 and
 * and an upper band of 1.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see UpperTriangular
 * @see Tridiagonal
 * @see Matrix
 */
public interface UpperBidiagonal<M extends Matrix> extends Tridiagonal<M>, UpperTriangular<M>
{
	/**
	 * Returns the abstract type of the {@code UpperBidiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperBidiagonal<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperBidiagonal<M> instance(Tensor t)
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
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}