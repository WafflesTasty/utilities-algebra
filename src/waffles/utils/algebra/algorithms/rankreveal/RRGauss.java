package waffles.utils.algebra.algorithms.rankreveal;

import waffles.utils.algebra.algorithms.LinearSolver;
import waffles.utils.algebra.algorithms.RankReveal;
import waffles.utils.algebra.algorithms.factoring.FCTTriangular;
import waffles.utils.algebra.algorithms.solvers.SLVTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.Wide;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRGauss} class solves exact linear systems using {@code Gauss's method} with complete pivoting.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix into {@code PMQ = LU},
 * where P, Q are permutation matrices, L a lower triangular matrix, and U an upper triangular matrix.
 * Note that non-square matrices can also be decomposed: one of L or U will not be square.
 * 
 * @author Waffles
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
	private Float det;
	private Integer rank;
	private Matrix mat, inv;
	private Matrix p, q, l, u;
	
	private boolean swapRows, swapCols;
	private boolean canInvert;
	private Matrix base;
		
	/**
	 * Creates a new {@code RRGauss}.
	 * If used as a linear system solver, it requires
	 * a matrix marked as square. Otherwise, an
	 * exception is thrown by canSolve(b).
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRGauss(Matrix m)
	{
		base = m;
	}

	
	private void decompose()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
				
		// Extend the matrix with a permutation column/row.	
		mat = Matrices.resize(base, rows + 1, cols + 1);
		for(int k = 0; k < Integers.max(rows, cols); k++)
		{
			if(k < rows) mat.set(k, k, cols);
			if(k < cols) mat.set(k, rows, k);
		}
			
		// Assume no swaps have occurred.
		swapRows = swapCols = false;
		// Assume matrix has a full rank.
		rank = Integers.min(rows, cols);
		canInvert = true;
		
		// Don't simplify to retain rank.
		// Decompose with Gauss's method.
		gaussMethod();
	}
	
	private void swapCols(int i, int j)
	{
		swapCols = true;
		
		// Swap the columns i and j.
		for(int k = 0; k < mat.Rows(); k++)
		{
			float cur = mat.get(k, i);
			mat.set(mat.get(k, j), k, i);
			mat.set(cur, k, j);
		}
	}
	
	private void swapRows(int i, int j)
	{
		swapRows = true;
		
		// Swap the rows i and j.
		for(int k = 0; k < mat.Columns(); k++)
		{
			float cur = mat.get(i, k);
			mat.set(mat.get(j, k), i, k);
			mat.set(cur, j, k);
		}
	}

	private void gaussMethod()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
				
		
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
					float val = mat.get(k, j);
					if(Floats.abs(val) > vMax)
					{
						vMax = Floats.abs(val);
						cMax = j; rMax = k;
					}
				}
			}
			
			
			// If the next pivot is zero...
			if(Floats.isZero(vMax, 3))
			{
				rank = i;
				
				// The coefficient matrix is not invertible.
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
				float div = mat.get(j, i) / mat.get(i, i);

				mat.set(div, j, i);
				// Eliminate a row of superdiagonal values.
				for(int k = i + 1; k < cols; k++)
				{
					float val = mat.get(j, k) - mat.get(j, i) * mat.get(i, k);
					mat.set(val, j, k);
				}
			}
			
			dVal *= mat.get(i, i);
		}
		
		// Finalize the determinant.
		if(Square.Type().allows(mat, 0))
		{
			det = (float) dVal;
			canInvert = true;
		}
	}
	
	
	@Override
	public <M extends Matrix> boolean canSolve(M b)
	{
		// Define dimensions.
		int mRows = base.Rows();
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
		
		M x = (M) P().times(b);
		// Solve through substitution.
		x = new SLVTriangular(L()).solve(x);
		x = new SLVTriangular(U()).solve(x);
		x = (M) Q().times(x);
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
		}
		
		return inv;
	}

	@Override
	public int rank()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		return rank;
	}
	
	
	@Override
	public Matrix P()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
				
		// If P has not been computed yet...
		if(p == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
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
				
				// Copy from the decomposed matrix.
				for(int i = 0; i < rows; i++)
				{
					int j = mat.get(i, cols).intValue();
					p.set(1f, i, j);
				}
			}
		}
		
		return p;
	}
	
	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If Q has not been computed yet...
		if(q == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
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
				
				// Copy from the decomposed matrix.
				for(int j = 0; j < cols; j++)
				{
					int i = mat.get(rows, j).intValue();
					q.set(1f, i, j);
				}
			}
		}
		
		return q;
	}

	@Override
	public Matrix L()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If L has not been computed yet...
		if(l == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
			// Create the lower triangular matrix L.
			if(rows <= cols)
			{
				// Create the triangular matrix L.
				l = Matrices.create(rows, rows);
				// Assign the type of the matrix L.
				l.setOperator(LowerTriangular.Type());
			}
			else
			{
				// Create the triangular matrix L.
				l = Matrices.create(rows, cols);
				// Assign the type of the matrix L.
				l.setOperator(Tall.Type());
			}
								
			// Copy from the decomposed matrix.
			for(int j = 0; j < cols; j++)
			{
				for(int i = j; i < rows; i++)
				{
					if(i != j)
						l.set(mat.get(i, j), i, j);
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
			
			if(rows >= cols)
			{
				// Create the triangular matrix U.
				u = Matrices.create(cols, cols);
				// Assign the type of the matrix U.
				u.setOperator(UpperTriangular.Type());
			}
			else
			{
				// Create the triangular matrix U.
				u = Matrices.create(rows, cols);
				// Assign the type of the matrix U.
				u.setOperator(Wide.Type());
			}
								
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