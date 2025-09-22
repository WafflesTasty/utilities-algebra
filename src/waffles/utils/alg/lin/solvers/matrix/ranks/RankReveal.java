package waffles.utils.alg.lin.solvers.matrix.ranks;

import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;

/**
 * A {@code RankReveal} algorithm can compute the rank of a matrix.
 *
 * @author Waffles
 * @since Apr 7, 2019
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface RankReveal extends MatrixSolver
{
	/**
	 * Computes a {@code Matrix} rank.
	 * 
	 * @return  a matrix rank
	 */
	public abstract int rank();
}