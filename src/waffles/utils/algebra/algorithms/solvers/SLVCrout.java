package waffles.utils.algebra.algorithms.solvers;

import waffles.utils.algebra.algorithms.LinearSolver;
import waffles.utils.algebra.algorithms.NullSpace;
import waffles.utils.algebra.algorithms.factoring.FCTTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.Wide;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code SLVCrout} algorithm solves exact linear systems using {@code Crout's method}.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix {@code M = PLU},
 * where P is a permutation matrix, L a lower triangular matrix, and U an upper triangular matrix.
 * Note that non-square matrices can also be decomposed: one of L or U will not be square.
 * {@code Crout's method} is designed to leave the matrix U with a unit diagonal.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see FCTTriangular
 * @see LinearSolver
 * @see NullSpace
 */
public class SLVCrout implements FCTTriangular, LinearSolver, NullSpace
{
	private Float det;
	private Matrix p, l, u;
	private Matrix spc, cmp;
	private Matrix mat, inv;

	private boolean canInvert;
	private Matrix base;
		
	/**
	 * Creates a new {@code SLVCrout} algorithm.
	 * If used as a linear system solver, it requires
	 * a matrix marked as square. Otherwise, an
	 * exception is thrown by canSolve(b).
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVCrout(Matrix m)
	{
		base = m;
	}

	
	private void decompose()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
		
		// Extend the matrix with a permutation column.	
		mat = Matrices.resize(base, rows, cols+1);
		for(int i = 0; i < rows; i++)
		{
			mat.set(i, i, cols);
		}
				
				
		// If the base matrix is lower triangular...
		if(base.is(LowerTriangular.Type()))
		{
			// No decomposition is necessary.
			croutLess();
		}
		// If the matrix is upper triangular...
		else if(base.is(UpperTriangular.Type()))
		{
			// A simplified Crout's method is used.
			croutSimplified();
		}
		// Otherwise...
		else
		{
			// The standard Crout's method is used.
			croutMethod();
		}
	}
	
	private void swap(int i, int j)
	{
		// Swap the rows i and j.
		for(int k = 0; k < mat.Columns(); k++)
		{
			float cur = mat.get(i, k);
			mat.set(mat.get(j, k), i, k);
			mat.set(cur, j, k);
		}
	}

	private void croutSimplified()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
		
		// For each row in the matrix...
		for(int i = 0; i < rows; i++)
		{
			float diag = mat.get(i, i);
			// Divide the diagonal element.
			for(int j = i + 1; j < cols; j++)
			{
				float val = mat.get(i, j) / diag;
				mat.set(val, i, j);
			}
		}
		
		
		double val = 1d;
		// Compute the determinant.
		for(int i = 0; i < mat.Rows(); i++)
		{
			val *= mat.get(i, i);
		}
		
		canInvert = Doubles.isZero(val, rows);
		det = (float) val;
		return;
	}
	
	private void croutMethod()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
				
		
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
					sum += (double) mat.get(j, k) * mat.get(k, i);
				}

				sum =  mat.get(j, i) - sum;
				mat.set((float) sum, j, i);
				
				// Leave the largest value as the next pivot.
				if(Doubles.abs(sum) > vMax)
				{
					vMax = Doubles.abs(sum);
					iMax = j;
				}
			}
			
			
			// If the next pivot is zero...
			if(Floats.isZero((float) vMax, 3))
			{
				// The coefficient matrix is not invertible.
				canInvert = false;
				return;
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
					sum += (double) mat.get(i, t) * mat.get(t, j);
				}
				
				sum = (mat.get(i, j) - sum) / mat.get(i, i);
				mat.set((float) sum, i, j);
			}
			
			dVal *= mat.get(i, i);
		}
		
		// Finalize the determinant.
		canInvert = !Doubles.isZero(dVal, 9);
		det = (float) dVal;
	}

	private void croutLess()
	{
		// Define dimensions.
		int rows = mat.Rows();
		
		
		double val = 1d;
		// Compute the determinant.
		for(int i = 0; i < rows; i++)
		{
			val *= mat.get(i, i);
		}
		
		canInvert = Doubles.isZero(val, rows);
		det = (float) val;
		return;
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
			throw new Matrices.TypeError(Square.Type());
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
		return x;
	}
	
	@Override
	public Matrix ColComplement()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If no row space has been made yet...
		if(cmp == null)
		{
			// The column space is determined by L.
			SLVTriangular slv1 = new SLVTriangular(L());
			SLVTriangular slv2 = new SLVTriangular(U());
			
			cmp = slv1.ColComplement();
			cmp = slv2.inverse().times(cmp);
		}

		return cmp;
	}
	
	@Override
	public Matrix ColSpace()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If no column space has been made yet...
		if(spc == null)
		{
			// The column space is determined by L.
			SLVTriangular slv = new SLVTriangular(L());

			spc = slv.ColSpace();
			spc = P().times(spc);
		}

		return spc;
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
		
		return Matrices.identity(mat.Columns()-1);
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
								
			// Copy from the decomposed matrix.
			for(int j = 0; j < cols; j++)
			{
				for(int i = j; i < rows; i++)
				{
					l.set(mat.get(i, j), i, j);
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
								
			// Copy from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				for(int j = i; j < cols; j++)
				{
					if(i != j)
						u.set(mat.get(i, j), i, j);
					else
						u.set(1f, i, j);
				}
			}
		}
		
		return u;
	}
}