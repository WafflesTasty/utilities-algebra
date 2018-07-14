package zeno.util.algebra.linear.error;

import zeno.util.algebra.attempt4.linear.mat.Matrix;

/**
 * The {@code PosDefiniteError} defines an error thrown when a matrix is not positive definite.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see RuntimeException
 */
public class PosDefiniteError extends RuntimeException
{
	private static final long serialVersionUID = -1165264740808510034L;

	/**
	 * Creates a new {@code PosDefiniteError}.
	 * 
	 * @param m  a target matrix
	 * @see Matrix
	 */
	public PosDefiniteError(Matrix m)
	{
		super("The matrix " + m + " is not positive definite.");
	}
}