package zeno.util.algebra.linear.matrix.operators.banded.lower;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.banded.Tridiagonal;

/**
 * The {@code LowerBidiagonal} interface defines an operator for lower bidiagonal matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere above the diagonal
 * and below the lower subdiagonal. This is equivalent to having a lower band of 1 and
 * and an upper band of 0.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see LowerTriangular
 * @see Tridiagonal
 * @see Matrix
 */
public interface LowerBidiagonal<M extends Matrix> extends Tridiagonal<M>, LowerTriangular<M>
{
	/**
	 * Returns the abstract type of the {@code LowerBidiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerBidiagonal<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerBidiagonal<M> instance(Tensor t)
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
		return 0;
	}
}