package zeno.util.algebra.linear.error;

import zeno.util.algebra.attempt4.linear.mat.Matrix;

/**
 * The {@code TriangularError} defines an error thrown when a matrix is not triangular.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see RuntimeException
 */
public class TriangularError extends RuntimeException
{
	private static final long serialVersionUID = -1165264740808510034L;

	/**
	 * Creates a new {@code TriangularError}.
	 * 
	 * @param m  a target matrix
	 * @see Matrix
	 */
	public TriangularError(Matrix m)
	{
		super("The matrix " + m + " is not triangular.");
	}
}