package zeno.util.algebra.linear.tensor.functions;

import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Integers;
import zeno.util.tools.patterns.properties.operator.Operation;

/**
 * The {@code TensorAddition} class defines the standard tensor sum operation.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @param <T>  the type of the result
 * @see Operation
 * @see Tensor
 */
public class TensorAddition<T extends Tensor> implements Operation<T>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorAddition}.
	 * 
	 * @param t1  the  first tensor to add
	 * @param t2  the second tensor to add
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorAddition(Tensor t1, Tensor t2)
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public T result()
	{
		float[] v1 = t1.Values();
		float[] v2 = t2.Values();
		
		Tensor result = Tensors.create(t1.Dimensions());
		for(int i = 0; i < v1.length; i++)
		{
			result.Data().set(i, v1[i] + v2[i]);
		}
		
		return (T) result;
	}
	
	@Override
	public int cost()
	{
		if(Tensors.isomorph(t1, t2))
		{
			return t1.Size();
		}
		
		return Integers.MAX_VALUE;
	}
}