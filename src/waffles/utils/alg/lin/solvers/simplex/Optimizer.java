package waffles.utils.alg.lin.solvers.simplex;

import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;
import waffles.utils.lang.utilities.enums.Extreme;

/**
 * An {@code Optimizer} can optimize an objective function.
 *
 * @author Waffles
 * @since 27 Aug 2023
 * @version 1.0
 *
 *
 * @param <T>  a tensor type
 * @see MatrixSolver
 * @see Tensor
 */
public interface Optimizer<T extends Tensor> extends MatrixSolver
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