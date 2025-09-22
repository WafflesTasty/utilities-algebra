package waffles.utils.alg.lin.solvers.matrix.factor;

import waffles.utils.alg.lin.measure.matrix.Matrix;

/**
 * A {@code PLUQFactor} defines an algorithm that performs PLUQ decomposition.
 * This decomposition takes the form {@code PMQ = LU} where P,Q are permutation
 * matrices, L a lower triangular matrix, and U an upper triangular matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see PLUFactor
 */
public interface PLUQFactor extends PLUFactor
{	
	/**
	 * Returns the Q matrix of the {@code PLUQFactor}.
	 * 
	 * @return  a permutation matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
}