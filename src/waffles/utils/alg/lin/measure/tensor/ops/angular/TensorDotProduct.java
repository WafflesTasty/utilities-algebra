package waffles.utils.alg.lin.measure.tensor.ops.angular;

import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorDotProduct} computes a {@code Tensor} dot product the naive way.
 * The first given {@code Tensor} will be the one used for the iterative step,
 * which means the cost is determined by its non-zero value count.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 */
public class TensorDotProduct implements Operation<Float>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorDotProduct}.
	 * 
	 * @param t1  a  first tensor
	 * @param t2  a second tensor
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