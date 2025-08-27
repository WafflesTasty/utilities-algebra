package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTBidiagonal} interface defines an algorithm that performs bidiagonal factorization.
 * Every matrix can be decomposed as {@code M = UBV*} where U is a matrix of orthogonal columns,
 * B is an upper bidiagonal matrix, and V is an orthogonal matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 */
public interface FCTBidiagonal
{
	/**
	 * Returns a bidiagonal matrix B of the {@code FCTBidiagonal}.
	 * 
	 * @return  a bidiagonal matrix B
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix B();
	
	/**
	 * Returns an (reduced) orthogonal matrix U of the {@code FCTBidiagonal}.
	 * 
	 * @return  an orthogonal matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns an orthogonal matrix V of the {@code FCTBidiagonal}.
	 * 
	 * @return  an orthogonal matrix V
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix V();
}