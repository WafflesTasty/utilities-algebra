package waffles.utils.alg.lin.solvers.matrix.factor;

import waffles.utils.alg.lin.measure.matrix.Matrix;

/**
 * A {@code PLQRFactor} defines an algorithm that performs PLQR decomposition.
 * This decomposition takes the form {@code PM = LQR} for {@code Wide} matrices
 * and {@code MP = LQR} for {@code Tall} matrices. Here P is a permutation matrix,
 * Q a (reduced) {@code Orthogonal} matrix, L a {@code LowerTriangular} matrix
 * and R is an {@code UpperTriangular} matrix.
 * 
 * The triangular matrices take different forms depending on the base matrix.
 * <ul>
 *  <li>If M is {@code Tall}, then L is {@code Identity} and R is {@code Tall}.</li>
 *  <li>If M is {@code Wide}, then L is {@code Wide} and R is {@code Identity}.</li>
 * </ul>
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see LQRFactor
 */
public interface PLQRFactor extends LQRFactor
{	
	/**
	 * Returns the P matrix of the {@code PLQRFactor}.
	 * 
	 * @return  a permutation matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
}