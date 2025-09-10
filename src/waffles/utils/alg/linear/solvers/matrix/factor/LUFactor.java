package waffles.utils.alg.linear.solvers.matrix.factor;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.solvers.matrix.MatrixSolver;

/**
 * An {@code LUFactor} defines an algorithm that performs LU decomposition.
 * This decomposition takes the form {@code M = LU} where L is a lower
 * triangular matrix, and U an upper triangular matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface LUFactor extends MatrixSolver
{	
	/**
	 * Returns the L matrix of the {@code LUFactor}.
	 * 
	 * @return  a lower triangular matrix
	 * 
	 * 
	 * @see LowerTriangular
	 * @see Matrix
	 */
	public abstract Matrix L();
	
	/**
	 * Returns the U matrix of the {@code LUFactor}.
	 * 
	 * @return  an upper triangular matrix
	 * 
	 * 
	 * @see UpperTriangular
	 * @see Matrix
	 */
	public abstract Matrix U();
}