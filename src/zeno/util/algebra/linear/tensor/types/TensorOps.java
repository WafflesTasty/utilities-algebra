package zeno.util.algebra.linear.tensor.types;

import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.functions.TensorAddition;
import zeno.util.algebra.linear.tensor.functions.TensorDotProduct;
import zeno.util.algebra.linear.tensor.functions.TensorEquality;
import zeno.util.tools.patterns.properties.operable.Operation;
import zeno.util.tools.patterns.properties.operable.Operator;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code TensorOps} implements {@code Operations} for tensors.
 * Depending on the layout and contents of tensors, most methods have a variety
 * of ways to be implemented, each providing their own benefits and drawbacks.
 * The {@code TensorOps} interface serves as a indicator to which benefits
 * or drawbacks the underlying tensor may provide.
 * 
 * Be aware that operators only serve as indicators to a tensor's layout. They don't
 * actually have any capability to enforce it. Making sure a tensor's contents corresponds
 * to its operator type is a task left to the developer. Failing to do so may lead to
 * unexpected results when requesting results of various algorithms.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Operator
 * @see Tensor
 */
public interface TensorOps extends Operator<Tensor>
{		
	/**
	 * Returns the abstract type of the {@code TensorOps}.
	 * 
	 * @return  a type operator
	 */
	public static TensorOps Type()
	{
		return () ->
		{
			return null;
		};
	}
	

	@Override
	public default TensorOps instance(Tensor t)
	{
		return () ->
		{
			return t;
		};
	}

	
	/**
	 * Returns a tensor dot product {@code Operation}.
	 * 
	 * @param t  a tensor to multiply with
	 * @return  a dot product operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 * @see Float
	 */
	public default Operation<Float> dotproduct(Tensor t)
	{
		return new TensorDotProduct(Operable(), t);
	}
	
	/**
	 * Returns an approximate tensor equality {@code Operation}.
	 * 
	 * @param t  a tensor to compare
	 * @param ulps  an approximate error margin
	 * @return  an equality operation
	 *
	 * 
	 * @see Operation
	 * @see Boolean
	 * @see Tensor
	 */
	public default Operation<Boolean> equality(Tensor t, int ulps)
	{
		return new TensorEquality(Operable(), t, ulps);
	}
			
	/**
	 * Returns a tensor addition {@code Operation}.
	 * 
	 * @param t  a tensor to add
	 * @return  a sum operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> addition(Tensor t)
	{
		return new TensorAddition<>(Operable(), t);
	}
	
	
	/**
	 * Computes a scalar in the {@code TensorOps}.
	 * 
	 * @param val  a value to multiply
	 * @return  a tensor scalar
	 * 
	 * 
	 * @see Tensor
	 */
	public default Tensor multiply(float val)
	{
		Tensor source = Operable();
		
		Tensor result = source.copy();
		for(int i = 0; i < source.Size(); i++)
		{
			float v = source.Data().get(i);
			result.Data().set(i, val * v);
		}
		
		return result;
	}

	/**
	 * Computes a trace in the {@code TensorOps}.
	 * 
	 * @return  a tensor trace
	 */
	public default float trace()
	{
		Tensor source = Operable();
		
		int size = Integers.min(source.Dimensions());
		int order = source.Order();
		
		double result = 0d;
		for(int i = 0; i < size; i++)
		{
			int[] coords = new int[order];
			for(int k = 0; k < order; k++)
			{
				coords[k] = i;
			}

			result += source.get(coords);
		}
		
		return (float) result;
	}
}