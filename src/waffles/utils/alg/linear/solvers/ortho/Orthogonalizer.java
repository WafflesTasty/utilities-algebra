package waffles.utils.alg.linear.solvers.ortho;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.solvers.Solver;

/**
 * An {@code Orthogonalizer} can compute a nearest orthogonal matrix.
 *
 * @author Waffles
 * @since 27 Apr 2020
 * @version 1.0
 * 
 * 
 * @see Solver
 */
public interface Orthogonalizer extends Solver
{		
	/**
	 * Computes the nearest {@code Orthogonal} matrix.
	 * 
	 * @return  an orthogonal matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix orthogonalize();
}