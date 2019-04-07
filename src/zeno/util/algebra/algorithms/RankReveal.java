package zeno.util.algebra.algorithms;

import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code RankReveal} interface defines an algorithm that approximates the rank of a matrix.
 *
 * @author Zeno
 * @since Apr 7, 2019
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface RankReveal extends Adaptable
{
	/**
	 * Returns the rank of the {@code RankReveal}.
	 * 
	 * @return  a matrix rank
	 */
	public abstract int rank();
}