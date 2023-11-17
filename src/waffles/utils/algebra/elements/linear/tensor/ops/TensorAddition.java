package waffles.utils.algebra.elements.linear.tensor.ops;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorAddition} computes the sum of two tensors the naive way.
 * The first given {@code Tensor} will be the one used for the iterative step, which
 * means the cost is given by its value count during iteration.
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
	public Tensor result()
	{
		Tensor t3 = Tensors.create(t1.Dimensions());
		for(int[] coord : t1.Data().Keys())
		{
			float v1 = t1.get(coord);
			float v2 = t2.get(coord);
			
			t3.set(v1 + v2, coord);
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