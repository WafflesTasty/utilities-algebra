package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTOrthogonal} interface defines an algorithm that performs QR factorization.
 * Every matrix can be decomposed as {@code M = QR} where Q is a matrix with
 * orthonormal columns, and R an upper triangular matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 */
public interface FCTOrthogonal
{
	/**
	 * Returns a reduced orthogonal matrix Q from the {@code FCTOrthogonal}.
	 * 
	 * @return  a reduced orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns an upper triangular matrix R from the {@code FCTOrthogonal}.
	 * 
	 * @return  an upper triangular matrix R
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix R();
}