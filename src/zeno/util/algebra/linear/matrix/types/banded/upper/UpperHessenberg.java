package zeno.util.algebra.linear.matrix.types.banded.upper;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.Banded;
import zeno.util.algebra.linear.tensor.Tensor;

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
 * @see Banded
 */
public interface UpperHessenberg extends Banded
{
	/**
	 * Returns the abstract type of the {@code UpperHessenberg Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperHessenberg Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default UpperHessenberg instance(Tensor t)
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
}