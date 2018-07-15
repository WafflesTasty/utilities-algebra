package zeno.util.algebra.linear.matrix.operators.banded.upper;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.square.Banded;

/**
 * The {@code UpperHessenberg} interface defines an operator for upper Hessenberg matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere below the
 * first subdiagonal. This is equivalent to having a lower band of 1.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see Banded
 * @see Matrix
 */
public interface UpperHessenberg<M extends Matrix> extends Banded<M>
{
	/**
	 * Returns the abstract type of the {@code UpperHessenberg Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperHessenberg<?> Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default UpperHessenberg<M> instance(Tensor t)
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
}