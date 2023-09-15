package waffles.utils.algebra.elements.linear.vector;

import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.vector.ops.VectorExclude;
import waffles.utils.algebra.elements.linear.vector.ops.VectorNorm;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * The {@code VectorOps} interface implements {@code Operations} for vectors.
 * This extends {@code MatrixOps} with a few basic vector functionalities.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Tall
 */
public interface VectorOps extends Tall
{
	/**
	 * Returns the abstract type of the {@code VectorOps}.
	 * 
	 * @return  a type operator
	 */
	public static VectorOps Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public abstract Vector Operable();
	
	@Override
	public default VectorOps instance(Tensor t)
	{
		return () ->
		{
			return (Vector) t;
		};
	}
	
	@Override
	public default boolean allows(Tensor obj, int ulps)
	{
		if(!matches(obj))
		{
			return obj.Order() == 2	&& obj.Dimensions()[1] == 1;
		}
		
		return true;
	}
	
	@Override
	public default boolean matches(Tensor obj)
	{
		return obj.Operator() instanceof VectorOps;
	}
	
		
	/**
	 * Returns a vector exclude {@code Operation}.
	 * 
	 * @param i  an excluded index
	 * @return   a resized vector
	 * 
	 * 
	 * @see Operation
	 * @see Vector
	 */
	public default Operation<Vector> Exclude(int i)
	{
		return new VectorExclude(Operable(), i);
	}
	
	/**
	 * Returns a vector 1-norm {@code Operation}.
	 * 
	 * @return  a norm operation
	 * 
	 * 
	 * @see Operation
	 */
	public default Operation<Float> Norm1()
	{
		return new VectorNorm(Operable());
	}
}