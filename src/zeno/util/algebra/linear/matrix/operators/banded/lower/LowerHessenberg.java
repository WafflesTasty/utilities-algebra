package zeno.util.algebra.linear.matrix.operators.banded.lower;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.square.Banded;

/**
 * The {@code LowerHessenberg} interface defines an operator for lower Hessenberg matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere above the
 * first superdiagonal. This is equivalent to having an upper band of 1.
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
public interface LowerHessenberg<M extends Matrix> extends Banded<M>
{
	/**
	 * Returns the abstract type of the {@code LowerHessenberg Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerHessenberg<?> Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default LowerHessenberg<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}