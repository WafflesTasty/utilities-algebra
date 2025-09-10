package waffles.utils.alg.linear.solvers.matrix.exact;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.utilities.errors.DimensionError;
import waffles.utils.algebra.elements.linear.Matrices;

/**
 * A {@code LinearSystem} can solve exact systems of linear equations.
 * An exact linear system is given by AX = B, where A is some square
 * {@code n x n} matrix; and X, B both an {@code n x k} matrix.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see Determinant
 */
public interface LinearSystem extends Determinant
{
	/**
	 * An {@code Error} is thrown when a {@code LinearSystem} encounters
	 * a coefficient matrix with an incompatible row dimension.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see DimensionError
	 */
	public static class Error extends DimensionError
	{
		private static final long serialVersionUID = 6365307847457011514L;

		/**
		 * Creates a new {@code Error}.
		 * 
		 * @param set  a matrix set
		 * 
		 * 
		 * @see Matrix
		 */
		public Error(Matrix... set)
		{
			super("Solving a linear system requires compatible dimensions", set);
		}
	}
	
	
	/**
	 * Computes a matrix solution in the {@code LinearSystem}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return   a matrix of unknowns
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> M solve(M b);
	
	/**
	 * Checks solvability of a matrix in the {@code LinearSystem}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  {@code true} if the system can be solved
	 * 
	 * 
	 * @see Matrix
	 */
	public default boolean canSolve(Matrix b)
	{
		Matrix a = Hints().Matrix();
		if(a.Rows() != b.Rows())
		{
			throw new Error(a, b);
		}
		
		return true;
	}
	
	/**
	 * Computes an inverse matrix in the {@code LinearSystem}.
	 * 
	 * @return  an inverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix inverse()
	{
		int r = Hints().Matrix().Rows();
		Matrix id = Matrices.identity(r);
		id.setOperator(Identity.Type());
		return solve(id.destroy());
	}
}