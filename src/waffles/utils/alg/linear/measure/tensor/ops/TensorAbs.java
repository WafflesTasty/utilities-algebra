package waffles.utils.alg.linear.measure.tensor.ops;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code TensorAbs} operation computes an absolute value {@code Tensor}.
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
public class TensorAbs<T extends Tensor> implements Operation<T>
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
	public TensorAbs(Tensor t1)
	{
		this.t1 = t1;
	}
	

	@Override
	public T result()
	{
		Tensor t2 = t1.instance();
		for(int[] crd : t1.Data().NZKeys())
		{
			float v1 = t1.get(crd);
			t2.set(Floats.abs(v1), crd);
		}

		return (T) t2;
	}
	
	@Override
	public int cost()
	{
		return t1.Data().NZCount();
	}
}