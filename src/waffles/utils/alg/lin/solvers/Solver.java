package waffles.utils.alg.lin.solvers;

import waffles.utils.alg.utilities.Algorithmic;
import waffles.utils.tools.primitives.Doubles;

/**
 * The {@code Solver} interface defines a base for an advanced algorithm.
 *
 * @author Waffles
 * @since 24 Aug 2025
 * @version 1.1
 */
@FunctionalInterface
public interface Solver
{
	/**
	 * Defines the default error margin of the {@code Hints}.
	 */
	public static final double DEF_ERROR = Doubles.pow(2, -8);
	
	/**
	 * The {@code Hints} interface defines hints for a {@code Solver}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Algorithmic
	 */
	public static interface Hints extends Algorithmic
	{
		@Override
		public default double Error()
		{
			return DEF_ERROR;
		}
	}
	
	
	/**
	 * Returns {@code Solver} hints.
	 * 
	 * @return  solver hints
	 * 
	 * 
	 * @see Hints
	 */
	public abstract Hints Hints();
}