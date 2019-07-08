package zeno.util.algebra.linear.matrix.types.banded;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.lower.LowerBidiagonal;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperBidiagonal;
import zeno.util.algebra.linear.matrix.types.square.Symmetric;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.Floats;
import zeno.util.tools.patterns.properties.operator.Operator;

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
 * @see LowerBidiagonal
 * @see UpperBidiagonal
 * @see Symmetric
 */
public interface Diagonal extends LowerBidiagonal, UpperBidiagonal, Symmetric
{
	/**
	 * Returns the abstract type of the {@code Diagonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Diagonal Type()
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
	public default Diagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}

	
	@Override
	public default Matrix pseudoinverse()
	{
		int rows = Operable().Rows();
		int cols = Operable().Columns();
		
		Matrix inv = Matrices.create(rows, cols);
		for(int i = 0; i < rows; i++)
		{
			float val = Operable().get(i, i);
			if(!Floats.isZero(val, 3))
			{
				inv.set(1f / val, i, i);
			}
		}
		
		return inv;
	}
	
	@Override
	public default Matrix inverse()
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
		
		return inv;
	}

	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}