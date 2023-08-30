package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTHessenberg} interface defines an algorithm that performs Hessenberg factorization.
 * Every square matrix can be decomposed as {@code M = Q*HQ} where Q is an orthogonal matrix,
 * and H a Hessenberg matrix. If M is symmetric, then H will be tridiagonal.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 */
public interface FCTHessenberg
{
	/**
	 * Returns a Hessenberg matrix H of the {@code FCTHessenberg}.
	 * 
	 * @return  a hessenberg matrix H
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix H();
	
	/**
	 * Returns an orthogonal matrix Q of the {@code FCTHessenberg}.
	 * 
	 * @return  an orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
}