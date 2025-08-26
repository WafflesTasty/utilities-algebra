package waffles.utils.alg.linear.measure.tensor.ops;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.TensorData;
import waffles.utils.algebra.elements.linear.Tensors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorCopy} operation copies a {@code Tensor}.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @param <T>  a tensor type
 * @see Operation
 * @see Tensor
 */
public class TensorCopy<T extends Tensor> implements Operation<T>
{
	private Tensor t1;
	
	/**
	 * Creates a new {@code TensorCopy}.
	 * 
	 * @param t1    a tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorCopy(Tensor t1)
	{
		this.t1 = t1;
	}
	

	@Override
	public T result()
	{
		TensorData data = t1.Data();
		if(!t1.isDestructible())
		{
			data = data.copy();
		}
		
		return Tensors.create(data);
	}
	
	@Override
	public int cost()
	{
		int c1 = t1.Data().NZCount();
		if(!t1.isDestructible())
			return c1;
		return 0;
	}
}