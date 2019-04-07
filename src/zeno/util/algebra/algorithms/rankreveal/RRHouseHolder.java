package zeno.util.algebra.algorithms.rankreveal;

import zeno.util.algebra.algorithms.LeastSquares;
import zeno.util.algebra.algorithms.Permutation;
import zeno.util.algebra.algorithms.RankReveal;
import zeno.util.algebra.algorithms.factor.FCTOrthogonal;
import zeno.util.algebra.algorithms.solvers.SLVTriangular;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.orthogonal.Identity;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code RRHouseHolder} class solves least squares linear systems using {@code Householder triangulization}.
 * This method factorizes a matrix {@code MP = QR} where P is a permutation matrix, Q is a matrix with orthonormal columns,
 * and R an upper triangular matrix. The {@code Householder} process simultaneously orthogonalizes all columns by
 * applying reflections. Column pivoting is applied to allow this method to reveal matrix rank.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see <a href="https://www.amazon.com/Numerical-Linear-Algebra-Optimization-Vol/dp/0201126494">Numerical Linear Algebra & Optimization - Volume 1, 1991</a>
 * @see FCTOrthogonal
 * @see LeastSquares
 * @see Permutation
 * @see RankReveal
 */
public class RRHouseHolder implements FCTOrthogonal, Permutation, LeastSquares, RankReveal
{
	private static final int ULPS = 3;
	
	
	private Matrix mat, c;
	private Matrix inv, p, q, r;
	private boolean swapCols;
	private Integer rank;
	private int iError;
	
	/**
	 * Creates a new {@code LSQHouseHolder}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRHouseHolder(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQHouseHolder}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public RRHouseHolder(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}

	@Override
	public <M extends Matrix> M approx(M b)
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The least squares system cannot be solved.
			throw new Tensors.DimensionError("Solving a least squares system requires compatible dimensions: ", mat, b);
		}
		
		
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Householder factorization.
			decompose();
		}

		
		// Compute the result through substitution.
		M x = (M) Q().transpose().times(b);
		x = new SLVTriangular(R(), iError).solve(x);
		return x;
	}
	
	
	@Override
	public Matrix pseudoinverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// Compute the inverse through substitution.
			inv = approx(Matrices.identity(mat.Rows()));
		}
		
		return inv;
	}
	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
	}

	@Override
	public void requestUpdate()
	{
		c = q = r = inv = null;
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
	
	private void houseHolder()
	{
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();	

		
		// Create the norm vector.
		Vector norms = Vectors.create(cols);
		for(int k = 0; k < cols; k++)
		{
			norms.set(mat.Column(k).norm(), k);
		}


		// Create the orthogonal matrix Q.
		q = Matrices.identity(rows + 1);
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
				Vector v = c.Column(i);
				
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
			
		
			int k = (int) c.get(rows, j);
			// If the pivot has become negligible...
			if(Floats.isZero(vMax, 1 + Integers.ceil(norms.get(k))))
			{
				// Stop the factorization.
				return;
			}
			
			
			// Create the reflection normal.
			Vector vk = c.Column(j);
			for(int i = 0; i <= rows; i++)
			{
				if(i < j || i == rows)
				{
					vk.set(0f, i);
				}
			}
			
			// Create the reflection matrix.
			Matrix vhh = Matrices.houseHolder(vk, j);
			// Otherwise, reflect the target matrix.
			c = vhh.times(c);
			q = q.times(vhh);
			rank += 1;
		}
	}
	
	private void decompose()
	{
		// If the matrix is not tall...
		if(!mat.is(Tall.Type()))
		{
			// A QR factorization cannot be computed.
			throw new Tensors.DimensionError("QR factorization requires a tall matrix: ", mat);
		}
		
		
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
		
		// Extend the matrix with a permutation row.	
		c = Matrices.resize(mat, rows + 1, cols);
		for(int k = 0; k < cols; k++)
		{
			c.set(k, rows, k);
		}
		
		// Assume no columns are swapped.
		swapCols = false;
		
		
		// Don't simplify to retain rank.
		// Perform Householder's method.
		houseHolder();
	}

	
	@Override
	public int rank()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Householder factorization.
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
			// Perform Householder factorization.
			decompose();
		}
		
		// If P has not been computed yet...
		if(p == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();

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
				
				// Copy the elements from the decomposed matrix.
				for(int j = 0; j < cols; j++)
				{
					p.set(1f, (int) c.get(rows, j), j);
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
			// Perform Householder factorization.
			decompose();
		}
		
		
		// Matrix dimensions.
		int qRows = q.Rows();
		int mRows = mat.Rows();
		int qCols = q.Columns();
		int mCols = mat.Columns();
		
		// If Q is not in reduced form...
		if(qRows != mRows || qCols != mCols)
		{
			// Reduce the orthogonal Q matrix.
			q = Matrices.resize(q, mRows, mCols);
		}
		
		// Assign the type of matrix Q.
		if(q.is(Square.Type()))
		{
			q.setOperator(Orthogonal.Type());
		}
		
		
		return q;
	}

	@Override
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Householder factorization.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(r == null)
		{
			// Matrix dimensions.
			int cols = mat.Columns();
			
			// Create the upper triangular matrix.
			r = Matrices.create(cols, cols);
			// Assign the type of matrix R.
			r.setOperator(UpperTriangular.Type());
			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < cols; i++)
			{
				for(int j = i; j < cols; j++)
				{
					r.set(c.get(i, j), i, j);
				}
			}			
		}
		
		return r;
	}
}