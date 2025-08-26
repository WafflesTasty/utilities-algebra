package waffles.utils.alg.utilities.errors;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.primitives.Array;

/**
 * A {@code DimensionError} is thrown when a {@code Tensor}
 * set has incompatible dimensions for any operation.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see RuntimeException
 */
public class DimensionError extends RuntimeException
{
	private static final long serialVersionUID = -2858384093246469008L;

	private static String parse(Tensor... set)
	{
		String s = "";
		for(Tensor t : set)
		{
			s += Array.parse.of(t.Dimensions());
		}
		
		return s;
	}
	
	
	/**
	 * Creates a new {@code DimensionError}.
	 * 
	 * @param msg  a prefix message
	 * @param set  a set of tensors
	 * 
	 * 
	 * @see Tensor
	 */
	public DimensionError(String msg, Tensor... set)
	{
		super(msg + ": " + parse(set) + ".");
	}
}