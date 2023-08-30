package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTEigenVals} interface defines an algorithm that performs eigenvalue factorization.
 * Every diagonalizable matrix can be decomposed as {@code M = QEQ*} where Q is an orthogonal
 * matrix, and E is a diagonal matrix of eigenvalues.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 */
public interface FCTEigenVals
{
	/**
	 * Returns a diagonal matrix E of the {@code FCTEigenVals}.
	 * 
	 * @return  a diagonal matrix E
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix E();
	
	/**
	 * Returns a orthogonal matrix Q of the {@code FCTEigenVals}.
	 * 
	 * @return  a orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
}