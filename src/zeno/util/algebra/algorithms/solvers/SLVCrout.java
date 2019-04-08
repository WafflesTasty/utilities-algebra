package zeno.util.algebra.algorithms.solvers;

import zeno.util.algebra.algorithms.LinearSolver;
import zeno.util.algebra.algorithms.factor.FCTTriangular;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.lower.LowerTriangular;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.dimensions.Wide;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Doubles;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code SLVCrout} class solves exact linear systems using {@code Crout's method}.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix {@code M = PLU},
 * where P is a permutation matrix, L a lower triangular matrix, and U an upper triangular matrix.
 * Note that non-square matrices can also be decomposed: one of L or U will not be square.
 * {@code Crout's method} is designed to leave the matrix U with a unit diagonal.
 * 
 * @author Zeno
 * @since Jul 6, 2018
 * @version 1.0
 * 
 * 
 * @see FCTTriangular
 * @see LinearSolver
 */
public class SLVCrout implements FCTTriangular, LinearSolver
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix mat, c;
	private Matrix inv, p, l, u;
	private boolean isInvertible;
	private int iError;
		
	/**
	 * Creates a new {@code SLVCrout}.
	 * If it is used as a linear system solver, this algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCrout(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVCrout}.
	 * If it is used as a linear system solver, this algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCrout(Matrix m, int ulps)
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
			// Perform Crout's method.
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
		M x = (M) P().times(b);
		x = new SLVTriangular(L(), iError).solve(x);
		x = new SLVTriangular(U(), iError).solve(x);
		return x;
	}
	
	
	private void swap(int i, int j)
	{
		// Swap the rows i and j.
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
		int rows = mat.Rows();
		int cols = mat.Columns();
		
		// For each row in the matrix...
		for(int i = 0; i < rows; i++)
		{
			float val = c.get(i, i);
			// Divide the diagonal element.
			for(int j = i + 1; j < cols; j++)
			{
				c.set(c.get(i, j) / val, i, j);
			}
		}
	}
	
	private void croutsMethod()
	{
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
				
		
		double dVal = 1d;
		// For each dimension in the matrix...
		for(int i = 0; i < Integers.min(rows, cols); i++)
		{
			double vMax = 0; int iMax = i;
			
			// Eliminate a column of subdiagonal values.
			for(int j = i; j < rows; j++)
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
				throw new Matrices.InvertibleError(mat);
			}
			
			// Pivot the next row.
			if(i != iMax)
			{
				swap(i, iMax);
				dVal = -dVal;
			}
			
			
			// Eliminate a row of superdiagonal values.
			for(int j = i + 1; j < cols; j++)
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
		if(mat.is(Square.Type()))
		{
			det = (float) dVal;
		}
	}

	private void decompose()
	{
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
		
		// Extend the matrix with a permutation column.	
		c = Matrices.resize(mat, rows, cols + 1);
		for(int i = 0; i < rows; i++)
		{
			c.set(i, i, cols);
		}
				
				
		// If the matrix is lower triangular...
		if(mat.is(LowerTriangular.Type(), iError))
		{
			double val = 1d;
			// Calculate the determinant.
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			
			isInvertible = Doubles.isZero(val, iError);
			det = (float) val;
			return;
		}
		
		// If the matrix is upper triangular...
		if(mat.is(UpperTriangular.Type(), iError))
		{
			// Perform the simplified Crout method.
			croutsSimplified();
			
			double val = 1d;
			// Calculate the determinant.
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			
			isInvertible = Doubles.isZero(val, iError);
			det = (float) val;
			return;
		}
		
		// Otherwise, perform Crout's method.
		croutsMethod();
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
			throw new Tensors.DimensionError("Invertibility requires a square matrix: ", mat);
		}
		
		return isInvertible;
	}
	
	@Override
	public float determinant()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Crout's method.
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
		}
		
		return inv;
	}
	
		
	@Override
	public Matrix P()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		// If P has not been computed yet...
		if(p == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// Create the permutation matrix P.
			p = Matrices.create(rows, rows);
			// Assign the type of the matrix P.
			p.setOperator(Orthogonal.Type());
			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				p.set(1f, i, (int) c.get(i, cols));
			}
		}
		
		return p;
	}
	
	@Override
	public Matrix Q()
	{
		return Matrices.identity(mat.Columns());
	}

	@Override
	public Matrix L()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		// If L has not been computed yet...
		if(l == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// Create the lower triangular matrix L.
			if(rows <= cols)
			{
				l = Matrices.create(rows, rows);
				// Assign the type of the matrix L.
				l.setOperator(LowerTriangular.Type());
			}
			else
			{
				l = Matrices.create(rows, cols);
				// Assign the type of the matrix L.
				l.setOperator(Tall.Type());
			}
								
			// Copy the elements from the decomposed matrix.
			for(int j = 0; j < cols; j++)
			{
				for(int i = j; i < rows; i++)
				{
					l.set(c.get(i, j), i, j);
				}
			}
		}
		
		return l;
	}

	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Crout's method.
			decompose();
		}
		
		// If U has not been computed yet...
		if(u == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// Create the upper triangular matrix U.
			if(rows >= cols)
			{	
				u = Matrices.create(cols, cols);
				// Assign the type of the matrix U.
				u.setOperator(UpperTriangular.Type());
			}
			else
			{
				u = Matrices.create(rows, cols);
				// Assign the type of the matrix U.
				u.setOperator(Wide.Type());
			}
								
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i; j < cols; j++)
				{
					if(i != j)
						u.set(c.get(i, j), i, j);
					else
						u.set(1f, i, j);
				}
			}
		}
		
		return u;
	}
}