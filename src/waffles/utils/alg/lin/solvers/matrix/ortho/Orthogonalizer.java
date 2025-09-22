package waffles.utils.alg.lin.solvers.matrix.ortho;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;

/**
 * An {@code Orthogonalizer} can compute a nearest orthogonal matrix.
 *
 * @author Waffles
 * @since 27 Apr 2020
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface Orthogonalizer extends MatrixSolver
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