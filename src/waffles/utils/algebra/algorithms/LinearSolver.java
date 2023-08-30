package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code LinearSolver} interface defines a solver for exact linear systems.
 * An exact linear system is given by AX = B, with A an {@code n x n} matrix; and X, B an {@code n x k} matrix.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Determinant
 */
public interface LinearSolver extends Determinant
{			
	/**
	 * Checks if a matrix can be solved in the {@code LinearSolver}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  {@code true} if the system can be solved
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> boolean canSolve(M b);
	
	/**
	 * Solves an exact linear system in the {@code LinearSolver}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return   a matrix of unknowns
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> M solve(M b);
	
	/**
	 * Computes the inverse matrix in the {@code LinearSolver}.
	 * 
	 * @return  an inverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix inverse();
}