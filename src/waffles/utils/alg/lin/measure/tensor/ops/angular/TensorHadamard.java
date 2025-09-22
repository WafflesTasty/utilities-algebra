package waffles.utils.alg.lin.measure.tensor.ops.angular;

import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorHadamard} computes an element-wise {@code Tensor} product the naive way.
 * The first given {@code Tensor} will be the one used for the iterative step,
 * which means the cost is determined by its non-zero value count.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.1
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class TensorHadamard implements Operation<Tensor>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorHadamard}.
	 * 
	 * @param t1  a  first tensor
	 * @param t2  a second tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorHadamard(Tensor t1, Tensor t2)
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public Tensor result()
	{
		Tensor t3 = t1.instance();
		for(int[] crds : t1.Data().NZKeys())
		{
			float v1 = t1.get(crds);
			float v2 = t2.get(crds);
			
			t3.set(v1 * v2, crds);
		}
		
		return t3;
	}
	
	@Override
	public int cost()
	{
		return 2 * t1.Data().NZCount();
	}
}