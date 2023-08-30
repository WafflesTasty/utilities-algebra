package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.algorithms.Orthogonalize;
import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code FCTPolarized} interface defines an algorithm that performs polar factorization.
 * Every matrix can be decomposed as {@code M = QP} where Q is an orthogonal matrix
 * and P is a positive-semidefinite Hermitian matrix.
 *
 * @author Waffles
 * @since 27 Apr 2020
 * @version 1.0
 * 
 * 
 * @see Orthogonalize
 */
public interface FCTPolarized extends Orthogonalize
{
	/**
	 * Returns the orthogonal matrix Q of the {@code FCTPolarized}.
	 * 
	 * @return  the orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the Hermitian matrix P of the {@code FCTPolarized}.
	 * 
	 * @return  the hermitian matrix P
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
	
	
	@Override
	public default Matrix NearestOrthogonal()
	{
		return Q();
	}
}