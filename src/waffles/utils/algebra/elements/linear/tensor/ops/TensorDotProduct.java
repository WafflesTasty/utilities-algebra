package waffles.utils.algebra.elements.linear.tensor.ops;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorDotProduct} computes the dot product of two tensors the naive way.
 * The first given {@code Tensor} will be the one used for the iterative step, which
 * means the cost is given by its value count during iteration.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Float
 */
public class TensorDotProduct implements Operation<Float>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorDotProduct}.
	 * 
	 * @param t1  the  first tensor to multiply
	 * @param t2  the second tensor to multiply
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorDotProduct(Tensor t1, Tensor t2)
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public Float result()
	{
		double dot = 0d;
		for(int[] coord : t1.Data().NZKeys())
		{
			float v1 = t1.get(coord);
			float v2 = t2.get(coord);
			dot += v1 * v2;
		}
		
		return (float) dot;
	}
	
	@Override
	public int cost()
	{
		return 2 * t1.Data().NZCount() - 1;
	}
}