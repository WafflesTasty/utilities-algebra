package waffles.utils.alg.linear.measure.tensor.ops;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.TensorOps;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorQualify} operation qualifies a {@code Tensor}.
 * This operation is used to check if a tensor is allowed
 * to operate through a {@code TensorOps} subtype.
 * 
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 */
public class TensorQualify implements Operation<Boolean>
{
	private float err;
	private Tensor t1;
	
	/**
	 * Creates a new {@code TensorQualify}.
	 * 
	 * @param t1   a tensor
	 * @param err  an error margin
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorQualify(Tensor t1, float err)
	{
		this.err = err;
		this.t1 = t1;
	}
	
	
	/**
	 * Returns the error margin.
	 * 
	 * @return  an error margin
	 */
	protected float Error()
	{
		return err;
	}

	@Override
	public Boolean result()
	{
		return t1.is(TensorOps.Type());
	}
	
	@Override
	public int cost()
	{
		return 0;
	}
}