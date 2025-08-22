package waffles.utils.alg.linear.measure.tensor;

import waffles.utils.alg.linear.measure.tensor.ops.TensorAbs;
import waffles.utils.alg.linear.measure.tensor.ops.TensorCopy;
import waffles.utils.alg.linear.measure.tensor.ops.TensorEquality;
import waffles.utils.alg.linear.measure.tensor.ops.TensorQualify;
import waffles.utils.alg.linear.measure.tensor.ops.TensorResize;
import waffles.utils.alg.linear.measure.tensor.ops.angular.TensorAddition;
import waffles.utils.alg.linear.measure.tensor.ops.angular.TensorDotProduct;
import waffles.utils.alg.linear.measure.tensor.ops.angular.TensorHadamard;
import waffles.utils.alg.linear.measure.tensor.ops.angular.TensorScalar;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.patterns.operator.Operator;
import waffles.utils.tools.primitives.Floats;

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
 * @version 1.1
 * 
 *
 * @see Operator
 * @see Tensor
 */
public interface TensorOps extends Operator<Tensor>
{		
	@Deprecated
	public default boolean allows(Tensor t, int ulps)
	{
		return Allows(Floats.EPSILON * ulps).result();
	}
	
	
	/**
	 * Returns the abstract type of the {@code TensorOps}.
	 * </br> The result of this method can be passed to a
	 * {@code Tensor} object to verify compatibility.
	 * 
	 * @return  a type operator
	 */
	public static TensorOps Type()
	{
		return () -> null;
	}
	
	
	/**
	 * Returns a tensor copy {@code Operation}.
	 * 
	 * @return  a copy operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> Copy()
	{
		return new TensorCopy<>(Operable());
	}
	
	/**
	 * Returns an absolute tensor {@code Operation}.
	 * 
	 * @return  an absolute operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> Absolute()
	{
		return new TensorAbs<>(Operable());
	}
	
	/**
	 * Returns a tensor type allows {@code Operation}.
	 * 
	 * @param e  an error margin
	 * @return  an allows operation
	 * 
	 * 
	 * @see Operation
	 */
	public default Operation<Boolean> Allows(float e)
	{
		return new TensorQualify(Operable(), e);
	}
	
	/**
	 * Returns a tensor equality {@code Operation}.
	 * 
	 * @param t  a comparable tensor
	 * @param e  an error margin
	 * @return   an equality operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Boolean> Equality(Tensor t, float e)
	{
		return new TensorEquality(Operable(), t, e);
	}
	
	/**
	 * Returns a tensor resize {@code Operation}.
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
	 * Returns a tensor Hadamard product {@code Operation}.
	 * 
	 * @param t  a tensor to multiply
	 * @return  a hadamard product operation
	 * 
	 * 
	 * @see Operation
	 * @see Tensor
	 */
	public default Operation<Tensor> Hadamard(Tensor t)
	{
		return new TensorHadamard(Operable(), t);
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

				
	@Override
	public default TensorOps instance(Tensor t)
	{
		return () -> t;
	}

	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof TensorOps;
	}
}