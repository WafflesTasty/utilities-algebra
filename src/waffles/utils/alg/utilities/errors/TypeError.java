package waffles.utils.alg.utilities.errors;

import waffles.utils.alg.linear.measure.tensor.TensorOps;

/**
 * A {@code TypeError} is thrown when a {@code Tensor}
 * has an incompatible type operator for an algorithm.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see RuntimeException
 */
public class TypeError extends RuntimeException
{
	private static final long serialVersionUID = -8605947131270014198L;


	private static String parse(TensorOps... set)
	{
		String s = "";
		for(TensorOps ops : set)
		{
			if(s.isEmpty())
				s += "( ";
			else
				s += ", ";
			
			s += ops.getClass().getName();
		}
		
		return s;
	}
	
	
	/**
	 * Creates a new {@code TypeError}.
	 * 
	 * @param set  a set of operators
	 * 
	 * 
	 * @see TensorOps
	 */
	public TypeError(TensorOps... set)
	{
		super("A matrix did not have the right type " + parse(set) + ".");
	}
}