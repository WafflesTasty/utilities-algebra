package zeno.util.algebra.linear.matrix.types.banded.lower;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.Tridiagonal;
import zeno.util.algebra.linear.tensor.Tensor;

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
 * @see LowerTriangular
 * @see Tridiagonal
 */
public interface LowerBidiagonal extends Tridiagonal, LowerTriangular
{
	/**
	 * Returns the abstract type of the {@code LowerBidiagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static LowerBidiagonal Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default LowerBidiagonal instance(Tensor t)
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
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}