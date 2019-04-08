package zeno.util.algebra.algorithms;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code LinearSolver} interface defines a solver for exact linear systems.
 * An exact linear system is given by AX = B, with A an {@code n x n} matrix; and X, B an {@code n x k} matrix.
 *
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface LinearSolver extends Adaptable
{		
	/**
	 * Computes the determinant in the {@code LinearSolver}.
	 * 
	 * @return  a matrix determinant
	 */
	public abstract float determinant();
	
	/**
	 * Checks invertibility of a matrix in the {@code LinearSolver}.
	 * 
	 * @return  {@code true} if the matrix is invertible
	 */
	public abstract boolean isInvertible();
	
	/**
	 * Checks if a matrix can be solved in the {@code LinearSolver}.
	 * 
	 * @param b  a matrix to check
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
	 * @return  a matrix of unknowns
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