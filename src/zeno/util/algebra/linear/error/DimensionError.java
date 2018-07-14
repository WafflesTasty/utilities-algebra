package zeno.util.algebra.linear.error;

import zeno.util.algebra.attempt4.linear.mat.Matrix;

/**
 * The {@code DimensionError} defines an error thrown when matrix dimensions are incompatible.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see RuntimeException
 */
public class DimensionError extends RuntimeException
{
	private static final long serialVersionUID = -2858384093246469008L;

	/**
	 * Creates a new {@code DimensionError}.
	 * 
	 * @param message  a message to append to the error
	 * @param m1  a  first target matrix
	 * @param m2  a second target matrix
	 * @see String
	 * @see Matrix
	 */
	public DimensionError(String message, Matrix m1, Matrix m2)
	{
		super(message + "( " + m1.Rows() + ", " + m1.Columns() + ") & ( " + m2.Rows() + ", " + m2.Columns() + ").");
	}
	
	/**
	 * Creates a new {@code DimensionError}.
	 * 
	 * @param message  a message to append to the error
	 * @param m  a target matrix
	 * @see String
	 * @see Matrix
	 */
	public DimensionError(String message, Matrix m)
	{
		super(message + "( " + m.Rows() + ", " + m.Columns() + ").");
	}
}