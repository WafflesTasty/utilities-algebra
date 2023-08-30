package waffles.utils.algebra.algorithms.solvers;

import waffles.utils.algebra.algorithms.LinearSolver;
import waffles.utils.algebra.algorithms.factoring.FCTCholesky;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code SLVCholesky} class solves exact linear systems using {@code Cholesky's method}.
 * This method is a variant of {@code Gauss elimination} that takes advantage of symmetric matrices
 * to cut computation time roughly in half. It decomposes a matrix {@code M = R*R}, where R
 * is an upper triangular matrix.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.0
 * 
 * 
 * @see LinearSolver
 * @see FCTCholesky
 */
public class SLVCholesky implements FCTCholesky, LinearSolver
{
	private Float det;
	private Matrix u, l;
	private Matrix mat, inv;
	
	private boolean canInvert;
	private Matrix base;
		
	/**
	 * Creates a new {@code SLVCholesky}.
	 * This algorithm requires a matrix marked
	 * as symmetric. Otherwise, canSolve(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCholesky(Matrix m)
	{
		base = m;
	}
	

	private void decompose()
	{
		// Copy the base matrix.
		mat = base.copy();
		
		
		// If the matrix is diagonal...
		if(base.is(Diagonal.Type()))
		{
			// Perform the simplified Cholesky method.
			choleskySimplified();
		}
		// If the matrix is symmetric...
		else if(base.is(Symmetric.Type()))
		{
			// Perform the full Cholesky method.
			choleskysMethod();
		}
	}
		
	private void choleskysMethod()
	{
		// Define dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
				
		
		double dVal = 1d;
		// For each row in the matrix...
		for(int k = 0; k < rows; k++)
		{
			// If the diagonal element is negative...
			if(mat.get(k, k) < 0)
			{
				// ...the matrix is not positive definite.
				throw new Matrices.PosDefiniteError(mat);
			}
			
			
			dVal *= mat.get(k, k);
			// For each row below the diagonal...
			for(int i = k + 1; i < rows; i++)
			{
				// Eliminate a column of superdiagonal values.
				double mul = (double) mat.get(k, i) / mat.get(k, k);
				for(int j = i; j < cols; j++)
				{
					double val = mat.get(i, j) - mat.get(k, j) * mul;
					mat.set((float) val, i, j);
				}
			}

			
			double vSqrt = Doubles.sqrt(mat.get(k, k));
			// For each row below the diagonal...
			for(int i = k; i < cols; i++)
			{
				// Divide the root of the diagonal element.
				double val = mat.get(k, i) / vSqrt;
				mat.set((float) val, k, i);
			}
		}
		
		det = (float) dVal;
		canInvert = !Floats.isZero(det, 3);
	}
	
	private void choleskySimplified()
	{
		// Define dimensions.
		int rows = base.Rows();
		
		
		double dVal = 1d;
		// For each row in the matrix...
		for(int i = 0; i < rows; i++)
		{
			dVal *= mat.get(i, i);
			
			// Take the square root of the diagonal.
			float sqrt = Floats.sqrt(mat.get(i, i));
			mat.set(sqrt, i, i);
		}
		
		det = (float) dVal;
		canInvert = !Floats.isZero(det, rows);
	}
	
	
	@Override
	public <M extends Matrix> boolean canSolve(M b)
	{
		// Define dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires compatible dimensions: ", mat, b);
		}
		
		// If the coefficient matrix is not square...
		if(!base.is(Square.Type()))
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires a square matrix: ", mat);
		}

		return canInvert();
	}

	@Override
	public <M extends Matrix> M solve(M b)
	{		
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		M x = (M) b.copy();
		// Solve through substitution.
		Matrix tpos = U().transpose();
		tpos.setOperator(LowerTriangular.Type());
		x = new SLVTriangular(tpos).solve(x);
		x = new SLVTriangular(U()).solve(x);
		return x;
	}
	

	@Override
	public boolean canInvert()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If the coefficient matrix is not square...
		if(!base.is(Square.Type()))
		{
			// It cannot define an inverse matrix.
			throw new Matrices.TypeError(Square.Type());
		}
		
		return canInvert;
	}
	
	@Override
	public float determinant()
	{
		if(canInvert())
			return det;
		return 0f;
	}
	
	@Override
	public Matrix inverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// Compute the inverse through substitution.
			inv = solve(Matrices.identity(base.Rows()));
			inv.setOperator(Symmetric.Type());
		}
		
		return inv;
	}

	
	@Override
	public Matrix L()
	{
		// If T has not been computed yet...
		if(l == null)
		{
			// Create the lower triangular matrix L.
			l = U().transpose();
			// Assign the type of the matrix L.
			l.setOperator(LowerTriangular.Type());
		}
		
		return l;
	}

	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If U has not been computed yet...
		if(u == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
			
			// Create the upper triangular matrix U.
			u = Matrices.create(rows, cols);
			// Assign the type of the matrix U.
			u.setOperator(UpperTriangular.Type());
			
			// Copy from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i; j < cols; j++)
				{
					u.set(mat.get(i, j), i, j);
				}
			}
		}
		
		return u;
	}
}