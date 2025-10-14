package waffles.utils.alg.lin.solvers.matrix.square;

import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;

/**
 * A {@code Conditioned} algorithm computes the condition number of a matrix.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface Conditioned extends MatrixSolver
{	
	/**
	 * Computes a {@code Matrix} condition number.
	 * 
	 * @return  a condition number
	 */
	public abstract float condition();
}