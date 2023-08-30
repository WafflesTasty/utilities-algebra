package waffles.utils.algebra.algorithms.rankreveal;

import waffles.utils.algebra.algorithms.LeastSquares;
import waffles.utils.algebra.algorithms.RankReveal;
import waffles.utils.algebra.algorithms.factoring.FCTOrthogonal;
import waffles.utils.algebra.algorithms.factoring.FCTPermutation;
import waffles.utils.algebra.algorithms.solvers.SLVTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.algebra.utilities.matrix.Householder;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRHouseHolder} class solves least squares linear systems using {@code Householder} triangulization.
 * This method factorizes a matrix {@code MP = QR} where P is a permutation matrix, Q is a matrix with orthonormal columns,
 * and R an upper triangular matrix. The {@code Householder} process simultaneously orthogonalizes all columns by
 * applying reflections. Column pivoting is applied to allow this method to reveal matrix rank.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see <a href="https://www.amazon.com/Numerical-Linear-Algebra-Optimization-Vol/dp/0201126494">Numerical Linear Algebra & Optimization - Volume 1, 1991</a>
 * @see FCTPermutation
 * @see FCTOrthogonal
 * @see LeastSquares
 * @see RankReveal
 */
public class RRHouseHolder implements FCTOrthogonal, FCTPermutation, LeastSquares, RankReveal
{
	private Integer rank;
	private Matrix p, q, r;
	private Matrix mat, inv;
	
	private boolean swapCols;
	private Matrix base;
	
	/**
	 * Creates a new {@code RRHouseHolder}.
	 * It requires a matrix marked as tall.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRHouseHolder(Matrix m)
	{
		base = m;
	}

	
	private void decompose()
	{		
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
		
		// Extend the matrix with a permutation row.	
		mat = Matrices.resize(base, rows+1, cols);
		for(int k = 0; k < cols; k++)
		{
			mat.set(k, rows, k);
		}
		
		// Assume no columns are swapped.
		swapCols = false;
		// Don't simplify to retain rank.
		// Decompose with Householder's method.
		houseHolder();
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
	
	private void houseHolder()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();	

		
		// Create the vector of norms.
		Vector norms = Vectors.create(cols);
		for(int k = 0; k < cols; k++)
		{
			Vector v = mat.Column(k);
			norms.set(v.norm(), k);
		}


		// Create the orthogonal matrix Q.
		q = Matrices.identity(rows+1);
		// Assign the type of matrix Q.
		q.setOperator(Identity.Type());
		
		
		rank = 0;
		// For each column in the target matrix...
		for(int j = 0; j < cols; j++)
		{
			// Find the least reduced column.
			float vMax = 0f; int iMax = j;
			for(int i = j; i < cols; i++)
			{
				// Create a subcolumn.
				Vector v = mat.Column(i);
				
				// Remove the permutation element.
				v.set(0f, rows);
				// Remove previous elements.
				for(int k = 0; k < j; k++)
				{
					v.set(0f, k);
				}
				
				// Check the norm size.
				float val = v.norm();
				if(Floats.abs(val) > vMax)
				{
					vMax = Floats.abs(val);
					iMax = i;
				}
			}
						
			// Pivot the next column.
			if(iMax != j)
			{
				swapCols(iMax, j);
			}
			
		
			int k = mat.get(rows, j).intValue();
			// If the pivot has become negligible...
			if(Floats.isZero(vMax, 1 + Integers.ceil(norms.get(k))))
			{
				// Stop the factorization.
				return;
			}
			
			
			// Create the reflection normal.
			Vector vk = mat.Column(j);
			for(int i = 0; i <= rows; i++)
			{
				if(i < j || i == rows)
				{
					vk.set(0f, i);
				}
			}
			
			// Create the reflection matrix.
			Matrix vhh = Householder.Matrix(vk, j);
			// Reflect the coefficient matrix.
			mat = vhh.times(mat);
			q = q.times(vhh);
			rank += 1;
		}
	}
	
	
	@Override
	public <M extends Matrix> boolean canApprox(M b)
	{
		// If the matrix is not tall...
		if(!Tall.Type().allows(base, 0))
		{
			// A QR factorization cannot be computed.
			throw new Matrices.TypeError(Tall.Type());
		}
		
		// Define dimensions.
		int mRows = base.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The least squares system cannot be solved.
			throw new Tensors.DimensionError("Solving a least squares system requires compatible dimensions: ", mat, b);
		}
		
		return true;
	}

	@Override
	public <M extends Matrix> M approx(M b)
	{		
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}

		
		// Solve through substitution.
		M x = (M) Q().transpose().times(b);
		x = new SLVTriangular(R()).solve(x);
		return x;
	}
	
	@Override
	public Matrix pseudoinverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// Compute the inverse through substitution.
			inv = approx(Matrices.identity(base.Rows()));
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
			if(!swapCols)
			{
				// Create an identity matrix P.
				p = Matrices.identity(cols);
				// Assign the type of the matrix P.
				p.setOperator(Identity.Type());
			}
			else
			{
				// Create the permutation matrix P.
				p = Matrices.create(cols, cols);
				// Assign the type of the matrix P.
				p.setOperator(Orthogonal.Type());
				
				// Copy from the decomposed matrix.
				for(int j = 0; j < cols; j++)
				{
					int i = mat.get(rows, j).intValue();
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
		
		// Define dimensions.
		int qRows = q.Rows();
		int mRows = base.Rows();
		int qCols = q.Columns();
		int mCols = base.Columns();
		
		// If Q is not in reduced form...
		if(qRows != mRows || qCols != mCols)
		{
			// Reduce the orthogonal Q matrix.
			q = Matrices.resize(q, mRows, mCols);
		}
		
		// Assign the type of matrix Q.
		if(Square.Type().allows(q, 0))
		{
			q.setOperator(Orthogonal.Type());
		}
		
		
		return q;
	}

	@Override
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(r == null)
		{
			// Define dimensions.
			int cols = mat.Columns();
			
			// Create the upper triangular matrix.
			r = Matrices.create(cols, cols);
			// Assign the type of the matrix R.
			r.setOperator(UpperTriangular.Type());
			
			// Copy from the decomposed matrix.
			for(int i = 0; i < cols; i++)
			{
				for(int j = i; j < cols; j++)
				{
					r.set(mat.get(i, j), i, j);
				}
			}			
		}
		
		return r;
	}
}