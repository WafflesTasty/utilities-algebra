package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTTriangular} interface defines an algorithm that performs PLUQ factorization.
 * Every matrix can be decomposed as {@code PMQ = LU} where P, Q are permutation matrices,
 * L is a lower triangular matrix, and U an upper triangular matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see FCTPermutation
 */
public interface FCTTriangular extends FCTPermutation
{	
	/**
	 * Returns a permutation matrix Q from the {@code FCTTriangular}.
	 * 
	 * @return  a permutation matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns a lower triangular matrix L from the {@code FCTTriangular}.
	 * 
	 * @return  a lower triangular matrix L
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix L();
	
	/**
	 * Returns a upper triangular matrix U from the {@code FCTTriangular}.
	 * 
	 * @return  a upper triangular matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
}