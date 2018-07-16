package zeno.util.algebra.linear.matrix.types.banded.lower;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.Banded;
import zeno.util.algebra.linear.tensor.Tensor;

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
 * @see Banded
 */
public interface LowerHessenberg extends Banded
{
	/**
	 * Returns the abstract type of the {@code LowerHessenberg Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerHessenberg Type()
	{
		return () ->
		{
			return null;
		};
	}


	@Override
	public default LowerHessenberg instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default int UpperBand()
	{
		return 1;
	}
}