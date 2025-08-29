package waffles.utils.alg.linear.solvers.factor;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.solvers.Solver;

/**
 * An {@code LQRFactor} defines an algorithm that performs LQR decomposition.
 * This decomposition takes the form {@code M = LQR} where Q is a (reduced)
 * {@code Orthogonal} matrix, L is a {@code LowerTriangular} matrix,
 * and R is an {@code UpperTriangular} matrix.
 * 
 * The triangular matrices take different forms depending on the base matrix.
 * <ul>
 *  <li>If M is {@code Tall}, then L is {@code Identity} and R is {@code Tall}.</li>
 *  <li>If M is {@code Wide}, then L is {@code Wide} and R is {@code Identity}.</li>
 * </ul>
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Solver
 */
public interface LQRFactor extends Solver
{	
	/**
	 * The {@code Hints} interface defines hints for a {@code LQRFactor}.
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
	 * Returns the L matrix of the {@code LQRFactor}.
	 * 
	 * @return  a lower triangular matrix
	 * 
	 * 
	 * @see LowerTriangular
	 * @see Matrix
	 */
	public abstract Matrix L();
	
	/**
	 * Returns the Q matrix of the {@code LQRFactor}.
	 * 
	 * @return  a (reduced) orthogonal matrix
	 * 
	 * 
	 * @see Orthogonal
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the R matrix of the {@code LQRFactor}.
	 * 
	 * @return  an upper triangular matrix
	 * 
	 * 
	 * @see UpperTriangular
	 * @see Matrix
	 */
	public abstract Matrix R();

	
	@Override
	public abstract Hints Hints();
}