package waffles.utils.alg.linear.measure.tensor.ops.angular;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorScalar} computes a {@code Tensor} scalar product the naive way.
 * The cost of the operation is determined by its non-zero value count.
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
	private float s1;
	private Tensor t1;
	
	/**
	 * Creates a new {@code TensorDotProduct}.
	 * 
	 * @param t1  a tensor
	 * @param s1  a scalar
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorScalar(Tensor t1, float s1)
	{
		this.s1 = s1;
		this.t1 = t1;
	}
	

	@Override
	public Tensor result()
	{
		Tensor t2 = t1.copy();
		for(int[] crds : t1.Data().NZKeys())
		{
			float v1 = t1.get(crds);
			t2.set(s1 * v1, crds);
		}
		
		return t2;
	}
	
	@Override
	public int cost()
	{
		int c1 = t1.Data().NZCount();

		if(!t1.isDestructible())
			return 2 * c1;
		return c1;
	}
}