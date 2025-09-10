package waffles.utils.alg.linear.solvers.matrix;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.solvers.Solver;
import waffles.utils.alg.utilities.Algorithmic;

/**
 * The {@code MatrixSolver} interface defines a base for a linear algorithm.
 *
 * @author Waffles
 * @since 24 Aug 2025
 * @version 1.1
 * 
 * 
 * @see Solver
 */
@FunctionalInterface
public interface MatrixSolver extends Solver
{
	/**
	 * The {@code Hints} interface defines hints for a {@code MatrixSolver}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Algorithmic
	 */
	public static interface Hints extends Solver.Hints
	{
		/**
		 * Returns the matrix of the {@code Hints}.
		 * 
		 * @return  a base matrix
		 * 
		 * 
		 * @see Matrix
		 */
		public abstract Matrix Matrix();
	}


	@Override
	public abstract Hints Hints();
}