package waffles.utils.alg.lin.measure.tensor.ops;

import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Doubles;

/**
 * A {@code TensorEquality} operation performs {@code Tensor} comparison.
 * Every pair of values is evaluated to within a given error margin.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Boolean
 */
public class TensorEquality implements Operation<Boolean>
{
	private double err;
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorEquality}.
	 * 
	 * @param t1   a  first tensor
	 * @param t2   a second tensor
	 * @param err  an error margin
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorEquality(Tensor t1, Tensor t2, double err)
	{
		this.err = Doubles.abs(err);
		
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public Boolean result()
	{
		return t1.minus(t2).normSqr() < err;
	}
	
	@Override
	public int cost()
	{
		return 2 * t1.Size() + 1;
	}
}