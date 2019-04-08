package zeno.util.algebra.algorithms.solvers;

import zeno.util.algebra.algorithms.LinearSolver;
import zeno.util.algebra.algorithms.factor.FCTCholesky;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.Diagonal;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.square.Symmetric;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Doubles;
import zeno.util.tools.Floats;

/**
 * The {@code SLVCholesky} class solves exact linear systems using {@code Cholesky's method}.
 * This method is a variant of {@code Gauss elimination} that takes advantage of symmetric matrices
 * to cut computation time roughly in half. It decomposes a matrix {@code M = R*R}, where R
 * is an upper triangular matrix.
 * 
 * @author Zeno
 * @since Jul 6, 2018
 * @version 1.0
 * 
 * 
 * @see LinearSolver
 * @see FCTCholesky
 */
public class SLVCholesky implements FCTCholesky, LinearSolver
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix inv, u;
	private Matrix mat, c;
	private boolean isInvertible;
	private int iError;
		
	/**
	 * Creates a new {@code SLVCholesky}.
	 * This algorithm requires a symmetric matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCholesky(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVCholesky}.
	 * This algorithm requires a symmetric matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCholesky(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
	@Override
	public <M extends Matrix> boolean canSolve(M b)
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires compatible dimensions: ", mat, b);
		}
		
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires a square matrix: ", mat);
		}
		
		
		// If no decomposition has been made yet...
		if(needsUpdate())
		{			
			// Perform Cholesky factorization.
			decompose();
		}
		
		// Solvability requires invertibility.
		return isInvertible();
	}

	@Override
	public <M extends Matrix> M solve(M b)
	{		
		// If the linear system cannot be solved...
		if(!canSolve(b))
		{
			// Throw an exception.
			throw new Matrices.InvertibleError(mat);
		}		
		
		// Compute the result through substitution.
		M x = (M) b.copy();
		x = new SLVTriangular(U().transpose(), iError).solve(x);
		x = new SLVTriangular(U(), iError).solve(x);
		return x;
	}
	
	
	@Override
	public void requestUpdate()
	{
		c = u = inv = null;
	}
	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
	}
		
	@Override
	public boolean isInvertible()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{			
			// Perform Gauss's method.
			decompose();
		}
		
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// Invertibility cannot be determined.
			return false;
		}
		
		return isInvertible;
	}
	
	@Override
	public float determinant()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Cholesky's method.
			decompose();
		}
		
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// A determinant cannot be calculated.
			throw new Tensors.DimensionError("Computing the determinant requires a square matrix: ", mat);
		}
		
		// If the matrix is not invertible...
		if(!isInvertible())
		{
			// It has zero determinant.
			return 0f;
		}
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// Compute the inverse through substitution.
			inv = solve(Matrices.identity(mat.Rows()));
			inv.setOperator(Symmetric.Type());
		}
		
		return inv;
	}
	
	
	private void choleskySimplified()
	{
		// Matrix row count.
		int rows = mat.Rows();
		
		double dVal = 1d;
		// For each row in the matrix...
		for(int i = 0; i < rows; i++)
		{
			dVal *= c.get(i, i);
			// Take the square root of the diagonal.
			c.set(Floats.sqrt(c.get(i, i)), i, i);
		}
		
		isInvertible = !Doubles.isZero(dVal, iError);
		det = (float) dVal;
	}
	
	private void choleskysMethod()
	{
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
				
		double dVal = 1d;
		// For each row in the matrix...
		for(int k = 0; k < rows; k++)
		{
			// If the diagonal element is negative...
			if(c.get(k, k) < 0)
			{
				// ...the matrix is not positive definite.
				throw new Matrices.PosDefiniteError(mat);
			}
			
			
			dVal *= c.get(k, k);
			// For each row below the diagonal...
			for(int i = k + 1; i < rows; i++)
			{
				double val = (double) c.get(k, i) / c.get(k, k);
				
				// Eliminate a column of superdiagonal values.
				for(int j = i; j < cols; j++)
				{
					c.set((float) (c.get(i, j) - c.get(k, j) * val), i, j);
				}
			}

			
			double vSqrt = Doubles.sqrt(c.get(k, k));
			// For each row below the diagonal...
			for(int i = k; i < cols; i++)
			{
				// Divide the root of the diagonal element.
				c.set((float) (c.get(k, i) / vSqrt), k, i);
			}
		}
		
		isInvertible = !Doubles.isZero(dVal, iError);
		det = (float) dVal;
	}
	
	private void decompose()
	{
		// Copy the target matrix.
		c = mat.copy();
		
		
		// If the matrix is diagonal...
		if(mat.is(Diagonal.Type(), iError))
		{
			// Perform the simplified Cholesky method.
			choleskySimplified();
			return;
		}
		
		// If the matrix is symmetric...
		if(mat.is(Symmetric.Type(), iError))
		{
			// Perform the full Cholesky method.
			choleskysMethod();
			return;
		}
		

		// Otherwise, Cholesky's method is not applicable.
		throw new Matrices.SymmetricError(mat);
	}

	
	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Cholesky factorization.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(u == null)
		{
			// Matrix dimensions.
			int rows = c.Rows();
			int cols = c.Columns();
			
			// Create the upper triangular matrix.
			u = Matrices.create(rows, cols);
			// Assign the type of the matrix U.
			u.setOperator(UpperTriangular.Type());
			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i; j < cols; j++)
				{
					u.set(c.get(i, j), i, j);
				}
			}
		}
		
		return u;
	}
}