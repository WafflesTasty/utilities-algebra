package waffles.utils.alg.linear.solvers.matrix.square;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.solvers.matrix.MatrixSolver;
import waffles.utils.alg.utilities.errors.DimensionError;
import waffles.utils.algebra.elements.linear.Matrices;

/**
 * A {@code LeastSquares} algorithm solves linear least squares optimization problems.
 * Suppose {@code AX = B} is a linear system with A an {@code m x n} matrix, X an {@code n x k}
 * matrix, and B an {@code m x k} matrix. The least squares problem attempts to find such
 * a matrix X that it minimizes the regular euclidian 2-norm {@code |AX - B|}.
 * 
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see MatrixSolver
 */
public interface LeastSquares extends MatrixSolver
{
	/**
	 * An {@code Error} is thrown when a {@code LeastSquares} encounters
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
		private static final long serialVersionUID = -117440964367578670L;

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
			super("Solving a least squares system requires compatible dimensions", set);
		}
	}
	
		
	/**
	 * Computes a matrix solution in the {@code LeastSquares}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return   a matrix of unknowns
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract <M extends Matrix> M approx(M b);
	
	/**
	 * Checks solvability of a matrix in the {@code LeastSquares}.
	 * 
	 * @param b  a right-hand side matrix
	 * @return  {@code true} if the system can be solved
	 * 
	 * 
	 * @see Matrix
	 */
	public default boolean canApprox(Matrix b)
	{
		Matrix a = Hints().Matrix();
		if(a.Rows() != b.Rows())
		{
			throw new Error(a, b);
		}
		
		return true;
	}
	
	/**
	 * Computes a pseudoinverse in the {@code LeastSquares}.
	 * 
	 * @return  a pseudoinverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix pseudoinverse()
	{
		Matrix a = Hints().Matrix();
		int c1 = a.Columns();
		int r1 = a.Rows();
		
		Matrix b = Matrices.identity(r1);
		return approx(b);
	}
}
