package waffles.utils.alg.linear.measure.tensor.ops.angular;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorAddition} computes a sum {@code Tensor} the naive way.
 * The first given {@code Tensor} will be used for the iterative step,
 * which means its cost is determined by its non-zero value count.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.1
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class TensorAddition implements Operation<Tensor>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorAddition}.
	 * 
	 * @param t1  a  first tensor to add
	 * @param t2  a second tensor to add
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
	public Tensor result()
	{
		Tensor t3 = t1.copy();
		for(int[] crd : t2.Data().NZKeys())
		{
			float v1 = t1.get(crd);
			float v2 = t2.get(crd);
			
			t3.set(v1 + v2, crd);
		}
		
		return t3;
	}
	
	@Override
	public int cost()
	{
		int c1 = t1.Data().NZCount();
		int c2 = t2.Data().NZCount();
		
		if(!t1.isDestructible())
			return c1 + c2;
		return c2;
	}
}