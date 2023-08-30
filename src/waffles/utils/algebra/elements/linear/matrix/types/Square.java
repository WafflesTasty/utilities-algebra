package waffles.utils.algebra.elements.linear.matrix.types;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.square.SquareInverse;
import waffles.utils.algebra.elements.linear.matrix.ops.square.SquareTrace;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A matrix tagged with the {@code Tall} operator is assumed to have as many rows as columns.
 * 
 * @author Waffles
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
	 * Returns the abstract type of the {@code Square} operator.
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

	
	/**
	 * Returns a matrix trace {@code Operation}.
	 * 
	 * @return  a trace operation
	 * 
	 * 
	 * @see Operation
	 */
	public default Operation<Float> Trace()
	{
		return new SquareTrace(Operable());
	}
	
	/**
	 * Returns a matrix inverse {@code Operation}.
	 * 
	 * @return  an inverse operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<Matrix> Inverse()
	{
		return new SquareInverse(Operable());
	}
	
			
	@Override
 	public default Square instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean allows(Tensor t, int ulps)
	{
		if(matches(t))
		{
			return true;
		}
		
		
		if(t instanceof Matrix)
		{
			int rows = ((Matrix) t).Rows();
			int cols = ((Matrix) t).Columns();
			
			return cols == rows;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Square;
	}
}