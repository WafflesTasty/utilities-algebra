package zeno.util.algebra.linear.matrix.types.banded.upper;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.Tridiagonal;
import zeno.util.algebra.linear.tensor.Tensor;

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
 * @see UpperTriangular
 * @see Tridiagonal
 */
public interface UpperBidiagonal extends Tridiagonal, UpperTriangular
{
	/**
	 * Returns the abstract type of the {@code UpperBidiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static UpperBidiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default UpperBidiagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
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