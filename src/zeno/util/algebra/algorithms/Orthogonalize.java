package zeno.util.algebra.algorithms;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code Orthogonalize} interface provides a matrix orthogonalization method.
 *
 * @author Zeno
 * @since 27 Apr 2020
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface Orthogonalize extends Adaptable
{		
	/**
	 * Computes the nearest orthogonal matrix in the {@code Orthogonalize}.
	 * 
	 * @return  the nearest orthogonal
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix NearestOrthogonal();
}