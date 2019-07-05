package zeno.util.algebra.algorithms;

import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code Determinant} interface provides square linear systems with a determinant.
 * A square linear system is given by AX = B, with A an {@code n x n} matrix; and X, B an {@code n x k} matrix.
 *
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface Determinant extends Adaptable
{		
	/**
	 * Computes the determinant in the {@code Determinant}.
	 * 
	 * @return  a matrix determinant
	 */
	public abstract float determinant();
	
	/**
	 * Checks invertibility of a matrix in the {@code Determinant}.
	 * 
	 * @return  {@code true} if the matrix is invertible
	 */
	public abstract boolean isInvertible();
}