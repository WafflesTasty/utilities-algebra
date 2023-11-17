package waffles.utils.algebra.elements.linear.tensor.ops;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorScalar} computes the scalar multiple of a tensor the naive way.
 * The cost is given by the tensor's data count.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class TensorScalar implements Operation<Tensor>
{
	private Tensor t;
	private float mul;
	
	/**
	 * Creates a new {@code TensorDotProduct}.
	 * 
	 * @param t    a tensor to multiply
	 * @param mul  a scalar multiple
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorScalar(Tensor t, float mul)
	{
		this.mul = mul;
		this.t = t;
	}
	

	@Override
	public Tensor result()
	{
		Tensor result = t.copy();
		for(int[] coord : t.Data().NZKeys())
		{
			float val = mul * t.get(coord);
			result.set(val, coord);
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		int c = t.Data().NZCount();

		if(!t.isDestructible())
			return 2 * c;
		return c;
	}
}