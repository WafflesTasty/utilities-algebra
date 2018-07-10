package zeno.util.algebra.linear.systems.slv;

import zeno.util.algebra.attempt4.linear.data.Type;
import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.systems.LinearSolver;
import zeno.util.tools.primitives.Doubles;
import zeno.util.tools.primitives.Floats;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code SLVCrout} class solves exact linear systems using {@code Crout's method}.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix {@code M = PLU},
 * where P is a permutation matrix, L a lower triangular matrix, and U an upper triangular matrix.
 * {@code Crout's method} is designed to leave the matrix U with a unit diagonal.
 * 
 * @author Zeno
 * @since Jul 6, 2018
 * @version 1.0
 * 
 * @see LinearSolver
 */
public class SLVCrout implements LinearSolver
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix mat, inv;
	private Matrix c, p, l, u;
	private int iError;
		
	/**
	 * Creates a new {@code SLVCrout}.
	 * If it is used as a linear system solver, a square matrix needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public SLVCrout(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVCrout}.
	 * If it is used as a linear system solver, a square matrix needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public SLVCrout(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
	
	/**
	 * Returns the permutation matrix P from the {@code SLVCrout}.
	 * 
	 * @return  the permutation matrix P
	 * @see Matrix
	 */
	public Matrix P()
	{
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		if(p == null)
		{
			// Matrix dimensions.
			int rows = c.Rows();
			int cols = c.Columns();
			
			// Create a square permutation matrix.
			p = Matrices.create(rows, rows);
			for(int i = 0; i < rows; i++)
			{
				p.set(1f, i, (int) c.get(i, cols - 1));
			}
		}
		
		return p;
	}

	/**
	 * Returns the lower triangular matrix L from the {@code SLVCrout}.
	 * 
	 * @return  the lower triangular matrix L
	 * @see Matrix
	 */
	public Matrix L()
	{
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		if(l == null)
		{
			// Matrix dimensions.
			int rows = c.Rows();
			int cols = c.Columns();
			
			// Create the lower triangular matrix.
			if(rows >= cols)
				l = Matrices.identity(rows, cols - 1);
			else
				l = Matrices.identity(rows, rows);
				
			// Assign a default matrix type.
			if(Matrices.isSquare(l))
			{
				l.setType(Type.LOWER_TRIANGULAR);
			}
				
			// Copy the elements from the decomposed matrix.
			for(int j = 0; j < cols - 1; j++)
			{
				for(int i = j; i < rows; i++)
				{
					l.set(c.get(i, j), i, j);
				}
			}
		}
		
		return l;
	}
	
	/**
	 * Returns the upper triangular matrix U from the {@code SLVCrout}.
	 * 
	 * @return  the upper triangular matrix U
	 * @see Matrix
	 */
	public Matrix U()
	{
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		if(u == null)
		{
			// Matrix dimensions.
			int rows = c.Rows();
			int cols = c.Columns();
			
			// Create the upper triangular matrix.
			if(rows >= cols)
				u = Matrices.identity(cols - 1, cols - 1);
			else
				u = Matrices.identity(rows, cols - 1);
				
			// Assign a default matrix type.
			if(Matrices.isSquare(u))
			{
				u.setType(Type.UPPER_TRIANGULAR);
			}
				
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i + 1; j < cols - 1; j++)
				{
					u.set(c.get(i, j), i, j);
				}
			}
		}
		
		return u;
	}
	
	
	
	@Override
	public void requestUpdate()
	{
		c = p = l = u = inv = null;
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
			// Check matrix dimensions.
			if(!Matrices.isSquare(mat))
			{
				// Non-square matrices are invalid for this operation.
				throw new Matrices.SquareException();
			}
			
			// Perform Crout's method.
			decompose();
		}
		
		// Compute the result through substitution.
		Matrix x = P().times(b);
		x = new SLVTriangular(L(), iError).solve(x);
		x = new SLVTriangular(U(), iError).solve(x);
		return x;
	}

	@Override
	public float determinant()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Check matrix dimensions.
			if(!Matrices.isSquare(mat))
			{
				// Non-square matrices don't have a determinant.
				throw new Matrices.SquareException();
			}
						
			// Perform Crout's method.
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
		}
		
		return inv;
	}
	
	
	private void swap(int i, int j)
	{
		for(int k = 0; k < c.Columns(); k++)
		{
			float cur = c.get(i, k);
			c.set(c.get(j, k), i, k);
			c.set(cur, j, k);
		}
	}

	private void croutsSimplified()
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
		
		// For each row in the matrix...
		for(int i = 0; i < mRows; i++)
		{
			float val = c.get(i, i);
			// Divide the diagonal element.
			for(int j = i + 1; j < mCols; j++)
			{
				c.set(c.get(i, j) / val, i, j);
			}
		}
	}
	
	private void croutsMethod()
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
				
		
		double dVal = 1d;
		// For each dimension in the matrix...
		for(int i = 0; i < Integers.min(mRows, mCols); i++)
		{
			double vMax = 0;
			int iMax = i;
			
			
			// Eliminate a column of subdiagonal values.
			for(int j = i; j < mRows; j++)
			{
				double sum = 0;
				for(int k = 0; k < i; k++)
				{
					sum += (double) c.get(j, k) * c.get(k, i);
				}

				sum =  c.get(j, i) - sum;
				c.set((float) sum, j, i);
				
				// Leave the largest value as the next pivot.
				if(Doubles.abs(sum) > vMax)
				{
					vMax = Doubles.abs(sum);
					iMax = j;
				}
			}
			
			
			// If the next pivot is zero...
			if(Floats.isZero((float) vMax, iError))
			{
				// ... the coëfficient matrix is not invertible.
				throw new Matrices.RegularException();
			}
			
			// Pivot the next row.
			if(i != iMax)
			{
				swap(i, iMax);
				dVal = -dVal;
			}
			
			
			// Eliminate a row of superdiagonal values.
			for(int j = i + 1; j < mCols; j++)
			{
				double sum = 0;
				for(int t = 0; t < i; t++)
				{
					sum += (double) c.get(i, t) * c.get(t, j);
				}
				
				sum = (c.get(i, j) - sum) / c.get(i, i);
				c.set((float) sum, i, j);
			}
			
			dVal *= c.get(i, i);
		}
		
		// Finalize the determinant.
		if(Matrices.isSquare(mat))
		{
			det = (float) dVal;
		}
	}

	private void decompose()
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
		
		// Extend the matrix with a permutation column.		
		c = new Matrix(mRows, mCols + 1);
		for(int i = 0; i < mRows; i++)
		{
			c.set(i, i, mCols);
			for(int j = 0; j < mCols; j++)
			{
				c.set(mat.get(i, j), i, j);
			}
		}
				
				
		// If the matrix is lower triangular...
		if(Matrices.isLowerTriangular(mat, iError))
		{
			double val = 1d;
			// Calculate the determinant.
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			det = (float) val;
			return;
		}
		
		// If the matrix is upper triangular...
		if(Matrices.isUpperTriangular(mat, iError))
		{
			// Perform the simplified crout method.
			croutsSimplified();
			
			double val = 1d;
			// Calculate the determinant.
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			det = (float) val;
			return;
		}
		
		// Otherwise, perform the full crout method.
		croutsMethod();
	}
}