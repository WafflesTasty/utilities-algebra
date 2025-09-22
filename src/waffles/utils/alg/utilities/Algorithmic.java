package waffles.utils.alg.utilities;

/**
 * The {@code Algorithmic} interface defines algorithmic hints.
 *
 * @author Waffles
 * @since 05 Aug 2025
 * @version 1.1
 */
@FunctionalInterface
public interface Algorithmic
{
	/**
	 * The {@code Hints.Iterative} interface defines iterative hints.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Algorithmic
	 */
	public static interface Iterative extends Algorithmic
	{
		/**
		 * Defines the default loops of the {@code Hints}.
		 */
		public static final int DEF_LOOPS = 10000;
			
		/**
		 * Returns the maximum loops of the {@code Hints}.
		 * 
		 * @return  a loop maximum
		 */
		public default int MaxLoops()
		{
			return DEF_LOOPS;
		}
	}


	/**
	 * Returns the error margin of the {@code Hints}.
	 * 
	 * @return  an error margin
	 */
	public abstract double Error();
}