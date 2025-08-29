package waffles.utils.alg.linear.solvers.factor;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.linear.solvers.Solver;

/**
 * A {@code QSFactor} defines an algorithm that performs QS decomposition.
 * This decomposition will require a {@code Square} matrix, and takes the form
 * {@code M = QS} where Q is an {@code Orthogonal} matrix and {@code S} a
 * {@code Symmetric} matrix. The matrix Q is considered the nearest
 * orthogonal matrix to M.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Solver
 */
public interface QSFactor extends Solver
{	
	/**
	 * Returns the Q matrix of the {@code QSFactor}.
	 * 
	 * @return  an orthogonal matrix
	 * 
	 * 
	 * @see Orthogonal
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the S matrix of the {@code QSFactor}.
	 * 
	 * @return  a symmetric matrix
	 * 
	 * 
	 * @see Symmetric
	 * @see Matrix
	 */
	public abstract Matrix S();
}