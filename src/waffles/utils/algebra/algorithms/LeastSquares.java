package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code LeastSquares} interface defines a solver for least squares linear systems.
 * A least squares linear system is given by AX = B, with A an {@code m x n} matrix; and X, B an {@code n x k} matrix.
 * A unique solution can only be found if the system is overdetermined i.e. {@code m >= n}.
 * 
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 */
public interface LeastSquares
{
	/**
	 * Checks if a matrix can be approximated in the {@code LeastSquares}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  {@code true} if the system can be approximated
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> boolean canApprox(M b);
	
	/**
	 * Approximates a linear system in {@code LeastSquares}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return   a matrix of unknowns
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> M approx(M b);
	
	/**
	 * Computes a pseudoinverse in the {@code LeastSquares}.
	 * 
	 * @return  a pseudoinverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix pseudoinverse();
}
