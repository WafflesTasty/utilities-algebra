package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTPermutation} interface defines an algorithm with a permutation matrix.
 *
 * @author Waffles
 * @since Apr 7, 2019
 * @version 1.0
 */
public interface FCTPermutation
{
	/**
	 * Returns the {@code FCTPermutation} matrix P.
	 * 
	 * @return  a permutation matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
}