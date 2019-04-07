package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code FCTTriangular} interface defines an algorithm that performs PLUQ factorization.
 * Every matrix can be decomposed as {@code PMQ = LU} where P, Q are permutation matrices,
 * L is a lower triangular matrix, and U an upper triangular matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface FCTTriangular extends Adaptable
{
	/**
	 * Returns the permutation matrix P from the {@code FCTTriangular}.
	 * 
	 * @return  the permutation matrix P
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
	
	/**
	 * Returns the permutation matrix Q from the {@code FCTTriangular}.
	 * 
	 * @return  the permutation matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the lower triangular matrix L from the {@code FCTTriangular}.
	 * 
	 * @return  the lower triangular matrix L
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix L();
	
	/**
	 * Returns the upper triangular matrix U from the {@code FCTTriangular}.
	 * 
	 * @return  the upper triangular matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
}