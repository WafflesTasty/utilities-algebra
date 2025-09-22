package waffles.utils.alg.lin.solvers.matrix.exact;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;

/**
 * A {@code Determinant} solver can compute a matrix determinant.
 * This algorithm will require a {@code Square} matrix.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface Determinant extends MatrixSolver
{			
	/**
	 * Computes a {@code Matrix} determinant.
	 * 
	 * @return  a matrix determinant
	 */
	public abstract float determinant();
	
	/**
	 * Check invertibility of a {@code Matrix}.
	 * 
	 * @return  {@code true} if invertible
	 */
	public default boolean canInvert()
	{
		double e = Hints().Error();
		Matrix a = Hints().Matrix();
		
		return a.allows(Square.Type(), 0)
			&& e < determinant();
	}
}