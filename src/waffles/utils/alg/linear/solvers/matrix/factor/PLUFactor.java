package waffles.utils.alg.linear.solvers.matrix.factor;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.solvers.matrix.MatrixSolver;

/**
 * A {@code PLUFactor} defines an algorithm that performs PLU decomposition.
 * This decomposition takes the form {@code M = PLU} where P is a permutation
 * matrix, L a lower triangular matrix, and U an upper triangular matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface PLUFactor extends LUFactor
{	
	/**
	 * Returns the P matrix of the {@code LUFactor}.
	 * 
	 * @return  a permutation matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
}