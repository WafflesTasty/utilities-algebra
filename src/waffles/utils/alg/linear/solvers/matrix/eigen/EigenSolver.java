package waffles.utils.alg.linear.solvers.matrix.eigen;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.matrix.MatrixSolver;
import waffles.utils.alg.utilities.Algorithmic;
import waffles.utils.alg.utilities.matrix.Rayleigh;
import waffles.utils.tools.primitives.Doubles;

/**
 * An {@code EigenSolver} approximates eigen vectors of square matrices.
 * Per definition of an eigen vector, given a matrix {@code A} each eigenvector
 * {@code v} corresponds to a value {@code \u03bb} such that {@code Av = \u03bbv}.
 * 
 * @author Waffles
 * @since 26 Aug 2025
 * @version 1.1
 *
 * 
 * @see MatrixSolver
 */
public interface EigenSolver extends MatrixSolver
{
	/**
	 * The {@code Hints} interface defines hints for an {@code EigenSolver}.
	 *
	 * @author Waffles
	 * @since 26 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Algorithmic
	 * @see MatrixSolver
	 */
	public static interface Hints extends Algorithmic.Iterative, MatrixSolver.Hints
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * 
		 * @return  a solver state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().allows(Square.Type(), 0))
				return State.VALID;
			return State.INVALID;
		}
		
		
		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code EigenSolver} states.
	 *
	 * @author Waffles
	 * @since 26 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * An invalid matrix cannot be used.
		 */
		INVALID,
		/**
		 * A square matrix is valid.
		 */
		VALID;
	}

	
	/**
	 * Approximates an eigen pair in the {@code EigenSolver}.
	 * 
	 * @param v  an eigen vector
	 * @return  an eigen pair
	 * 
	 * 
	 * @see EigenPair
	 * @see Vector
	 */
	public default EigenPair approx(Vector v)
	{
		Matrix m = Hints().Matrix();
		float l = Rayleigh.coefficient(m, v);
		EigenPair p = new EigenPair(v, l);
		return approx(p);
	}
	
	/**
	 * Approximates an eigen pair in the {@code EigenSolver}.
	 * 
	 * @param v  a vector estimate
	 * @param l  a value estimate
	 * @return  an eigen pair
	 * 
	 * 
	 * @see EigenPair
	 */
	public default EigenPair approx(Vector v, float l)
	{
		return approx(new EigenPair(v, l));
	}
	
	/**
	 * Approximates an eigen pair in the {@code EigenSolver}.
	 * 
	 * @param est  an initial estimate
	 * @return  an eigen pair
	 * 
	 * 
	 * @see EigenPair
	 */
	public abstract EigenPair approx(EigenPair est);
	
	
	@Override
	public abstract Hints Hints();
}