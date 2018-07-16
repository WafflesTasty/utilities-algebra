package zeno.util.algebra.linear.matrix.types;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.dimensions.Wide;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Square} interface defines an operator for square matrices.
 * Matrices tagged with this operator are assumed to have as many
 * rows as they have columns.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Tall
 * @see Wide
 */
public interface Square extends Tall, Wide
{
	/**
	 * Returns the abstract type of the {@code Square Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Square Type()
	{
		return () ->
		{
			return null;
		};
	}

	
	@Override
	public default boolean matches(Tensor t, int ulps)
	{
		if(t instanceof Matrix)
		{
			int rows = ((Matrix) t).Rows();
			int cols = ((Matrix) t).Columns();
			return rows == cols;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Square;
	}
		
	@Override
	public default Square instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
}