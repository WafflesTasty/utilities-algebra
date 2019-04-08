package zeno.util.algebra.algorithms.rankreveal;

import zeno.util.algebra.algorithms.LinearSolver;
import zeno.util.algebra.algorithms.RankReveal;
import zeno.util.algebra.algorithms.factor.FCTTriangular;
import zeno.util.algebra.algorithms.solvers.SLVTriangular;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.lower.LowerTriangular;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.dimensions.Wide;
import zeno.util.algebra.linear.matrix.types.orthogonal.Identity;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code RRGauss} class solves exact linear systems using {@code Gauss's method} with complete pivoting.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix into {@code PMQ = LU},
 * where P, Q are permutation matrices, L a lower triangular matrix, and U an upper triangular matrix.
 * Note that non-square matrices can also be decomposed: one of L or U will not be square.
 * 
 * @author Zeno
 * @since Jul 6, 2018
 * @version 1.0
 * 
 * 
 * @see FCTTriangular
 * @see LinearSolver
 * @see RankReveal
 */
public class RRGauss implements FCTTriangular, LinearSolver, RankReveal
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Integer rank;
	private Matrix mat, c;
	private Matrix inv, p, q, l, u;
	private boolean swapRows, swapCols;
	private boolean canInvert;
	private int iError;
		
	/**
	 * Creates a new {@code RRGauss}.
	 * If it is used as a linear system solver, this algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRGauss(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code RRGauss}.
	 * If it is used as a linear system solver, this algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public RRGauss(Matrix m, int ulps)
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
		
		// Solvability depends on invertibility.
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
		x = (M) Q().times(x);
		return x;
	}
	
	
	private void swapCols(int i, int j)
	{
		swapCols = true;
		
		// Swap the columns i and j.
		for(int k = 0; k < c.Rows(); k++)
		{
			float cur = c.get(k, i);
			c.set(c.get(k, j), k, i);
			c.set(cur, k, j);
		}
	}
	
	private void swapRows(int i, int j)
	{
		swapRows = true;
		
		// Swap the rows i and j.
		for(int k = 0; k < c.Columns(); k++)
		{
			float cur = c.get(i, k);
			c.set(c.get(j, k), i, k);
			c.set(cur, j, k);
		}
	}

	private void gaussMethod()
	{
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
				
		
		double dVal = 1d;
		// For each dimension in the matrix...
		for(int i = 0; i < Integers.min(rows, cols); i++)
		{
			float vMax = 0; int cMax = i, rMax = i;
			
			// Check for a better pivot in remaining matrix.
			for(int j = i; j < cols; j++)
			{
				for(int k = i; k < rows; k++)
				{
					float val = c.get(k, j);
					if(Floats.abs(val) > vMax)
					{
						vMax = Floats.abs(val);
						cMax = j; rMax = k;
					}
				}
			}
			
			
			// If the next pivot is zero...
			if(Floats.isZero(vMax, iError))
			{
				// Finalize the rank.
				rank = i;
				// Finalize invertibility.
				canInvert = false;
				return;
			}
			
			// Pivot the next column.
			if(cMax != i)
			{
				swapCols(cMax, i);
				dVal = -dVal;
			}
			
			// Pivot the next row.
			if(rMax != i)
			{
				swapRows(rMax, i);
				dVal = -dVal;
			}	
						
			
			// For each row below the diagonal...
			for(int j = i + 1; j < rows; j++)
			{
				// Divide the leading element.
				c.set(c.get(j , i) / c.get(i, i), j, i);
				
				// Eliminate a row of superdiagonal values.
				for(int k = i + 1; k < cols; k++)
				{
					c.set(c.get(j, k) - c.get(j, i) * c.get(i, k),  j, k);
				}
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
				
		// Extend the matrix with a permutation column/row.	
		c = Matrices.resize(mat, rows + 1, cols + 1);
		for(int k = 0; k < Integers.max(rows, cols); k++)
		{
			if(k < rows) c.set(k, k, cols);
			if(k < cols) c.set(k, rows, k);
		}
			
		// Assume no swaps have occurred.
		swapRows = swapCols = false;
		// Assume matrix has a full rank.
		rank = Integers.min(rows, cols);
		canInvert = true;
		
		// Don't simplify to retain rank.
		// Perform Gauss's method.
		gaussMethod();
	}
	
	
	@Override
	public void requestUpdate()
	{
		c = p = q = l = u = inv = null;
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
		
		return canInvert;
	}
	
	@Override
	public float determinant()
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
	public int rank()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{			
			// Perform Gauss's method.
			decompose();
		}
		
		return rank;
	}
	
	
	@Override
	public Matrix P()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gauss's method.
			decompose();
		}
				
		// If P has not been computed yet...
		if(p == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// If no swaps have occurred...
			if(!swapRows)
			{
				// Create an identity matrix P.
				p = Matrices.identity(rows);
				// Assign the type of the matrix P.
				p.setOperator(Identity.Type());
			}
			else
			{
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
		}
		
		return p;
	}
	
	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gauss's method.
			decompose();
		}
		
		// If Q has not been computed yet...
		if(q == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// If no swaps have occurred...
			if(!swapCols)
			{
				// Create an identity matrix Q.
				q = Matrices.identity(cols);
				// Assign the type of the matrix Q.
				q.setOperator(Identity.Type());
			}
			else
			{
				// Create the permutation matrix Q.
				q = Matrices.create(cols, cols);
				// Assign the type of the matrix Q.
				q.setOperator(Orthogonal.Type());
				
				// Copy the elements from the decomposed matrix.
				for(int j = 0; j < cols; j++)
				{
					q.set(1f, (int) c.get(rows, j), j);
				}
			}
		}
		
		return q;
	}

	@Override
	public Matrix L()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gauss's method.
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
					if(i != j)
						l.set(c.get(i, j), i, j);
					else
						l.set(1f, i, j);
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
			// Perform Gauss's method.
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
					u.set(c.get(i, j), i, j);
				}
			}
		}
		
		return u;
	}

}