package zeno.util.algebra.linear.algo.factor;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.tools.generic.properties.Updateable;

/**
 * The {@code FCTEigen} interface defines an algorithm that performs eigenvalue factorization.
 * Every diagonalizable matrix can be decomposed as {@code M = QEQ*} where Q is an orthogonal
 * matrix, and E is a diagonal matrix of eigenvalues.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * @see Updateable
 */
public interface FCTEigen extends Updateable
{
	/**
	 * Returns the diagonal matrix E from the {@code FCTEigen}.
	 * 
	 * @return  the diagonal matrix E
	 * @see Matrix
	 */
	public abstract Matrix E();
	
	/**
	 * Returns the orthogonal matrix Q from the {@code FCTEigen}.
	 * 
	 * @return  the orthogonal matrix Q
	 * @see Matrix
	 */
	public abstract Matrix Q();
}