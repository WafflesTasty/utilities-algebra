package zeno.util.algebra.linear.systems.slv;

import zeno.util.algebra.attempt4.linear.data.Type;
import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.systems.LinearSolver;
import zeno.util.tools.primitives.Doubles;
import zeno.util.tools.primitives.Floats;

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
 * @see LinearSolver
 */
public class SLVCholesky implements LinearSolver
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix c, r;
	private Matrix mat, inv;
	private int iError;
		
	/**
	 * Creates a new {@code SLVCholesky}.
	 * A symmetric matrix needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public SLVCholesky(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVCholesky}.
	 * A symmetric matrix needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public SLVCholesky(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}

	
	/**
	 * Returns the upper triangular matrix R from the {@code SLVCholesky}.
	 * 
	 * @return  the upper triangular matrix R
	 * @see Matrix
	 */
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Cholesky's method.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(r == null)
		{
			// Matrix dimensions.
			int rows = c.Rows();
			int cols = c.Columns();
			
			// Create the upper triangular matrix.
			r = Matrices.create(rows, cols);
						
			// Assign a default matrix type.
			if(Matrices.isSquare(r))
			{
				r.setType(Type.UPPER_TRIANGULAR);
			}
			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i; j < cols; j++)
				{
					r.set(c.get(i, j), i, j);
				}
			}
		}
		
		return r;
	}
	
	
	@Override
	public void requestUpdate()
	{
		c = r = inv = null;
	}
	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
	}
	
	@Override
	public Matrix solve(Matrix b)
	{		
		// Matrix dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// Check dimension compatibility.
		if(mRows != bRows)
		{
			throw new Matrices.DimensionException(mat, b);
		}
		
		// If no decomposition has been made yet...
		if(needsUpdate())
		{			
			// Perform Cholesky's method.
			decompose();
		}
		
		
		// Compute the result through substitution.
		Matrix x = b.copy();
		x = new SLVTriangular(R().transpose(), iError).solve(x);
		x = new SLVTriangular(R(), iError).solve(x);
		return x;
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
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// If the matrix is orthogonal...
			if(Matrices.isOrthogonal(mat, iError))
			{
				// Provide the inverse through transposition.
				inv = mat.transpose();
				inv.setType(Type.ORTHOGONAL);
				return inv;
			}
			
			// Compute the inverse through substitution.
			inv = solve(Matrices.identity(mat.Rows()));
			inv.setType(Type.SYMMETRIC);
		}
		
		return inv;
	}
	
	
	private void choleskySimplified()
	{
		// Matrix row count.
		int mRows = mat.Rows();
		
		double dVal = 1d;
		// For each row in the matrix...
		for(int i = 0; i < mRows; i++)
		{
			dVal *= c.get(i, i);
			// Take the square root of the diagonal.
			c.set(Floats.sqrt(c.get(i, i)), i, i);
		}
		
		det = (float) dVal;
	}
	
	private void choleskysMethod()
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
				
		double dVal = 1d;
		// For each row in the matrix...
		for(int k = 0; k < mRows; k++)
		{
			// If the diagonal element is negative...
			if(c.get(k, k) < 0)
			{
				// ...the matrix is not positive definite.
				throw new Matrices.PosDefException();
			}
			
			
			dVal *= c.get(k, k);
			// For each row below the diagonal...
			for(int i = k + 1; i < mRows; i++)
			{
				double val = (double) c.get(k, i) / c.get(k, k);
				
				// Eliminate a column of superdiagonal values.
				for(int j = i; j < mCols; j++)
				{
					c.set((float) (c.get(i, j) - c.get(k, j) * val), i, j);
				}
			}

			
			double vSqrt = Doubles.sqrt(c.get(k, k));
			// For each row below the diagonal...
			for(int i = k; i < mCols; i++)
			{
				// Divide the root of the diagonal element.
				c.set((float) (c.get(k, i) / vSqrt), k, i);
			}
		}
		
		det = (float) dVal;
	}
	
	private void decompose()
	{
		// Copy the target matrix.
		c = mat.copy();
		
		
		// If the matrix is diagonal...
		if(Matrices.isDiagonal(mat, iError))
		{
			// Perform the simplified crout method.
			choleskySimplified();
			return;
		}
		
		// If the matrix is symmetric...
		if(Matrices.isSymmetric(mat, iError))
		{
			// Perform the full crout method.
			choleskysMethod();
			return;
		}
		

		throw new Matrices.SymmetricException();
	}
}