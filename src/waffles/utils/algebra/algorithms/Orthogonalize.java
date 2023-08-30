package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code Orthogonalize} interface defines an algorithm for matrix orthogonalization.
 *
 * @author Waffles
 * @since 27 Apr 2020
 * @version 1.0
 */
public interface Orthogonalize
{		
	/**
	 * Computes the nearest orthogonal matrix in the {@code Orthogonalize}.
	 * 
	 * @return  a nearest orthogonal matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix NearestOrthogonal();
}