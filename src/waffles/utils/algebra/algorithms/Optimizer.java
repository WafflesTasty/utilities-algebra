package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.lang.enums.Extreme;

/**
 * The {@code Optimizer} interface defines an algorithm that optimizes an objective function.
 *
 * @author Waffles
 * @since 27 Aug 2023
 * @version 1.0
 *
 *
 * @param <T>  a tensor result type
 * @see Tensor
 */
public interface Optimizer<T extends Tensor>
{
	/**
	 * Checks if the {@code Optimizer} is feasible.
	 * 
	 * @return  {@code true} if a solution exists
	 */
	public abstract boolean isFeasible();
	
	/**
	 * Optimizes the {@code Optimizer}.
	 * 
	 * @param ex  an optimal extreme
	 * @return    an optimal vector
	 * 
	 * 
	 * @see Extreme
	 */
	public default T optimize(Extreme ex)
	{
		if(ex == Extreme.MIN)
			return minimize();
		else
			return maximize();
	}
	
	/**
	 * Minimizes the {@code Optimizer}.
	 * 
	 * @return  a minimal vector
	 * 
	 * 
	 * @see Vector
	 */
	public default T minimize()
	{
		return optimize(Extreme.MIN);
	}
	
	/**
	 * Maximizes the {@code Optimizer}.
	 * 
	 * @return  a maximal vector
	 * 
	 * 
	 * @see Vector
	 */
	public default T maximize()
	{
		return optimize(Extreme.MAX);
	}
}