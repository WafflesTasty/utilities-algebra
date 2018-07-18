package zeno.util.algebra.algorithms;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Correctable;

/**
 * The {@code LeastSquares} interface defines a solver for least squares linear systems.
 * A least squares linear system is given by AX = B, with A an {@code m x n} matrix; and X, B an {@code n x k} matrix.
 * A solution can only be found if the system is overdetermined i.e. {@code m >= n}.
 * 
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Correctable
 */
public interface LeastSquares extends Correctable
{
	/**
	 * Approximates a linear system in {@code LeastSquares}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  a matrix of unknowns
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> M approx(M b);
	
	/**
	 * Computes the pseudoinverse in the {@code LeastSquares}.
	 * 
	 * @return  a pseudoinverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix pseudoinverse();
}
