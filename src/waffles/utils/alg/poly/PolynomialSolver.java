package waffles.utils.alg.poly;

import waffles.utils.alg.lin.solvers.Solver;
import waffles.utils.tools.primitives.Doubles;

/**
 * The {@code PolynomialSolver} interface defines a base for a polynomial algorithm.
 *
 * @author Waffles
 * @since 24 Aug 2025
 * @version 1.1
 * 
 * 
 * @see Solver
 */
@FunctionalInterface
public interface PolynomialSolver extends Solver
{
	/**
	 * Defines the default error of a {@code PolynomialSolver}.
	 */
	public static final double DEF_ERROR = Doubles.pow(2, -32);
	
	/**
	 * The {@code Hints} interface defines hints for a {@code PolynomialSolver}.
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
		 * Returns the polynomial of the {@code Hints}.
		 * 
		 * @return  a base polynomial
		 * 
		 * 
		 * @see Polynomial
		 */
		public abstract Polynomial Polynomial();
		
		
		@Override
		public default double Error()
		{
			return DEF_ERROR;
		}
	}


	@Override
	public abstract Hints Hints();
}