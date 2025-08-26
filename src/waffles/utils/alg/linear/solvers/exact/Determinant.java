package waffles.utils.alg.linear.solvers.exact;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.solvers.Solver;

/**
 * A {@code Determinant} solver can compute a matrix determinant.
 * This algorithm will require a {@code Square} matrix.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Solver
 */
public interface Determinant extends Solver
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