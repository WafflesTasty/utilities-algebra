package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code FCTOrthogonal} interface defines an algorithm that performs QR factorization.
 * Every matrix can be decomposed as {@code M = QR} where Q is a matrix with
 * orthonormal columns, and R an upper triangular matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface FCTOrthogonal extends Adaptable
{
	/**
	 * Returns the reduced orthogonal matrix Q from the {@code FCTOrthogonal}.
	 * 
	 * @return  the reduced orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the upper triangular matrix R from the {@code FCTOrthogonal}.
	 * 
	 * @return  the upper triangular matrix R
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix R();
}