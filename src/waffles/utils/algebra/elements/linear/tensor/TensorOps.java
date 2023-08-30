package waffles.utils.algebra.elements.linear.tensor;

import waffles.utils.algebra.elements.linear.tensor.ops.TensorAddition;
import waffles.utils.algebra.elements.linear.tensor.ops.TensorDotProduct;
import waffles.utils.algebra.elements.linear.tensor.ops.TensorEquality;
import waffles.utils.algebra.elements.linear.tensor.ops.TensorResize;
import waffles.utils.algebra.elements.linear.tensor.ops.TensorScalar;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.patterns.operator.Operator;

/**
 * The {@code TensorOps} interface implements {@code Operations} for tensors.
 * Depending on the layout and contents of tensors, most methods have a variety
 * of ways to be implemented, each providing their own benefits and drawbacks.
 * Different subtypes of {@code TensorOps} can provide indicators to which
 * benefits or drawbacks the underlying tensor may provide.
 * 
 * Be aware that operators only serve as indicators to a tensor's layout. They don't
 * actually have any capability to enforce it. Making sure a tensor's contents corresponds
 * to its operator type is a task left to the developer. Failing to do so may lead to
 * unexpected results when requesting results of various algorithms.
 *
 * @author Waffles
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
	public default Operation<Tensor> Addition(Tensor t)
	{
		return new TensorAddition(Operable(), t);
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
	public default Operation<Float> DotProduct(Tensor t)
	{
		return new TensorDotProduct(Operable(), t);
	}	

	/**
	 * Returns a tensor approx. equality {@code Operation}.
	 * 
	 * @param t  a tensor to compare with
	 * @param iError  an error margin
	 * @return  an equality operation
	 * 
	 * 
	 * @see Operation
	 * @see Boolean
	 * @see Tensor
	 */
	public default Operation<Boolean> Equality(Tensor t, int iError)
	{
		return new TensorEquality(Operable(), t, iError);
	}
	
	/**
	 * Returns a scalar tensor product {@code Operation}.
	 * 
	 * @param val  a value to multiply
	 * @return  a tensor scalar
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> Multiply(float val)
	{
		return new TensorScalar(Operable(), val);
	}
	
	/**
	 * Returns a tensor dimension resizing {@code Operation}.
	 * 
	 * @param dims  a tensor dimension
	 * @return  a resize operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> Resize(int... dims)
	{
		return new TensorResize(Operable(), dims);
	}
	
	/**
	 * Checks if a {@code Tensor} allows this {@code Operation}.
	 * 
	 * @param t  a tensor to verify
	 * @param ulps  an error margin
	 * @return  {@code true} if the operation is allowed
	 */
	public default boolean allows(Tensor t, int ulps)
	{
		return matches(t);
	}
	
	
	@Override
	public default TensorOps instance(Tensor t)
	{
		return () ->
		{
			return t;
		};
	}
}