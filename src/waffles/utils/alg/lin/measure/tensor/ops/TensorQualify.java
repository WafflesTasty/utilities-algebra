package waffles.utils.alg.lin.measure.tensor.ops;

import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.tensor.TensorOps;
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
	private double err;
	private TensorOps o1;
	
	/**
	 * Creates a new {@code TensorQualify}.
	 * 
	 * @param o1   a tensor operator
	 * @param err  an error margin
	 * 
	 * 
	 * @see TensorOps
	 */
	public TensorQualify(TensorOps o1, double err)
	{
		this.err = err;
		this.o1 = o1;
	}
	
	
	/**
	 * Returns the {@code TensorOps} operator.
	 * 
	 * @return  an operator
	 * 
	 * 
	 * @see TensorOps
	 */
	public TensorOps Operator()
	{
		return o1;
	}
	
	/**
	 * Returns the base {@code Tensor}.
	 * 
	 * @return  a tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public Tensor Tensor()
	{
		return o1.Operable();
	}
	
	/**
	 * Returns the error margin.
	 * 
	 * @return  an error margin
	 */
	public double Error()
	{
		return err;
	}


	@Override
	public Boolean result()
	{
		return Tensor().is(TensorOps.Type());
	}
	
	@Override
	public int cost()
	{
		return 0;
	}
}