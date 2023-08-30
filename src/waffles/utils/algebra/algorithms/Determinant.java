package waffles.utils.algebra.algorithms;

/**
 * The {@code Determinant} interface provides linear solvers with a determinant.
 * A square linear system is given by AX = B, with A an {@code n x n} matrix; and X, B an {@code n x k} matrix.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 */
public interface Determinant
{			
	/**
	 * Checks invertibility of a matrix.
	 * 
	 * @return  {@code true} if the matrix is invertible
	 */
	public abstract boolean canInvert();

	/**
	 * Computes the determinant.
	 * 
	 * @return  a matrix determinant
	 */
	public abstract float determinant();
}