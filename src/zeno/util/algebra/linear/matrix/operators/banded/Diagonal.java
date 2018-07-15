package zeno.util.algebra.linear.matrix.operators.banded;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.operators.banded.lower.LowerBidiagonal;
import zeno.util.algebra.linear.matrix.operators.banded.upper.UpperBidiagonal;
import zeno.util.algebra.linear.matrix.operators.square.Symmetric;
import zeno.util.tools.patterns.Operator;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Diagonal} interface defines an operator for diagonal matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere except
 * for the diagonal. This is equivalent to having a lower and upper band of 0.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see LowerBidiagonal
 * @see UpperBidiagonal
 * @see Symmetric
 * @see Matrix
 */
public interface Diagonal<M extends Matrix> extends LowerBidiagonal<M>, UpperBidiagonal<M>, Symmetric<M>
{
	/**
	 * Returns the abstract type of the {@code Diagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Diagonal<?> Type()
	{
		return () ->
		{
			return null;
		};
	}

	
	@Override
	public default boolean matches(Operator<Tensor> type)
	{
		return LowerBidiagonal.super.matches(type);
	}
	
	@Override
	public default boolean matches(Tensor m, int ulps)
	{
		return LowerBidiagonal.super.matches(m, ulps);
	}

	@Override
	public default Diagonal<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}

	
	@Override
	public default M pseudoinverse()
	{
		return inverse();
	}
	
	@Override
	public default M inverse()
	{
		int rows = Operable().Rows();
		int cols = Operable().Columns();
		
		Matrix inv = Matrices.create(rows, cols);
		for(int i = 0; i < rows; i++)
		{
			float val = Operable().get(i, i);
			if(Floats.isZero(val, 3))
			{
				throw new Matrices.InvertibleError(Operable());
			}
			
			inv.set(1f / val, i, i);
		}
		
		return (M) inv;
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