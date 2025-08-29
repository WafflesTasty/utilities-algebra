package waffles.utils.alg.linear.solvers.factor;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.solvers.Solver;

/**
 * An {@code UVFactor} defines an algorithm that performs UV decomposition.
 * This decomposition takes the form {@code M = UXV*} where U, V are
 * both (reduced) orthogonal matrices, and X is a matrix further
 * determined by the implementing algorithm.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Solver
 */
public interface UVFactor extends Solver
{	
	/**
	 * The {@code Hints} interface defines hints for an {@code UVFactor}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Solver
	 */
	@FunctionalInterface
	public static interface Hints extends Solver.Hints
	{
		/**
		 * Returns the reduced state of the {@code Hints}.
		 * 
		 * @return  a reduced state
		 */
		public default boolean isReduced()
		{
			return false;
		}
	}
	
	
	/**
	 * Returns the U matrix of the {@code UVFactor}.
	 * 
	 * @return  a (reduced) orthogonal matrix
	 * 
	 * 
	 * @see Orthogonal
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns the V matrix of the {@code UVFactor}.
	 * 
	 * @return  a (reduced) orthogonal matrix
	 * 
	 * 
	 * @see Orthogonal
	 * @see Matrix
	 */
	public abstract Matrix V();

	
	@Override
	public abstract Hints Hints();
}