package zeno.util.algebra.linear.systems;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.tools.generic.properties.Updateable;

/**
 * The {@code LinearSolver} interface defines a solver for exact linear systems.
 * An exact linear system is given by AX = B, with A an {@code n x n} matrix; and X, B an {@code n x k} matrix.
 *
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * @see Updateable
 */
public interface LinearSolver extends Updateable
{
	/**
	 * Computes the inverse matrix in the {@code LinearSolver}.
	 * 
	 * @return  an inverse matrix
	 * @see Matrix
	 */
	public abstract Matrix inverse();
	
	/**
	 * Solves an exact linear system in the {@code LinearSolver}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  a matrix of unknowns
	 * @see Matrix
	 */
	public abstract Matrix solve(Matrix b);
	
	/**
	 * Computes the determinant in the {@code LinearSolver}.
	 * 
	 * @return  a matrix determinant
	 */
	public abstract float determinant();
}