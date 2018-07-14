package zeno.util.algebra.linear.alg.lsquares;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.types.Orthogonal;
import zeno.util.algebra.attempt4.linear.types.banded.UpperTriangular;
import zeno.util.algebra.attempt4.linear.types.orthogonal.Identity;
import zeno.util.algebra.attempt4.linear.types.size.Square;
import zeno.util.algebra.attempt4.linear.types.size.Tall;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.linear.alg.LeastSquares;
import zeno.util.algebra.linear.alg.factor.FCTOrthogonal;
import zeno.util.algebra.linear.alg.solvers.SLVTriangular;
import zeno.util.algebra.linear.error.DimensionError;

/**
 * The {@code LSQHouseHolder} class solves least squares linear systems using {@code Householder triangulization}.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Householder} process simultaneously orthogonalizes all columns by applying reflections.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQHouseHolder implements FCTOrthogonal, LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix mat, c;
	private Matrix inv, q, r;
	private int iError;
	
	/**
	 * Creates a new {@code LSQHouseHolder}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public LSQHouseHolder(Matrix m)
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
	 * @see Matrix
	 */
	public LSQHouseHolder(Matrix m, int ulps)
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
			throw new DimensionError("Solving a least squares system requires compatible dimensions: ", mat, b);
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
	public void requestUpdate()
	{
		c = q = r = inv = null;
	}
	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
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

	
	private void houseHolder()
	{
		// Copy the target matrix.
		c = mat.copy();
		
		
		// Matrix dimensions.
		int mRows = c.Rows();
		int mCols = c.Columns();	
				
		// Create the orthogonal matrix Q.
		q = Matrices.identity(mRows, mRows);
		// Assign the type of matrix Q.
		q.setType(Identity.Type());
		
		
		// For each column in the target matrix...
		for(int j = 0; j < mCols; j++)
		{
			// Create the reflection normal.
			Vector vk = c.Column(j);
			for(int i = 0; i < j; i++)
			{
				vk.set(0f, i);
			}
			
			// Create the reflection matrix.
			Matrix vhh = Matrices.houseHolder(vk, j);
			// Reflect the target matrix.
			c = vhh.times(c);
			q = q.times(vhh);
		}
	}
	
	private void decompose()
	{
		// If the matrix is not tall...
		if(!mat.is(Tall.Type()))
		{
			// A QR factorization cannot be computed.
			throw new DimensionError("QR factorization requires a tall matrix: ", mat);
		}

		
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();		
		
		
		// If the matrix is upper triangular...
		if(mat.is(UpperTriangular.Type()))
		{
			// Skip the Householder method.
			q = Matrices.identity(rows, cols);
			r = c = mat.copy();
			return;
		}
		
		// If the matrix is orthogonal...
		if(mat.is(Orthogonal.Type()))
		{
			// Skip the Householder method.
			r = Matrices.identity(cols, cols);
			q = c = mat.copy();
			return;
		}
		
		
		// Otherwise, perform Householder's method.
		houseHolder();
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
		int qCols = q.Columns();
		int cCols = c.Columns();
		
		// If Q is not in reduced form...
		if(qCols != cCols)
		{
			// Reduce the orthogonal Q matrix.
			q = Matrices.subMatrix(q, 0, 0, qRows, cCols);
		}
		
		// Assign the type of matrix Q.
		if(q.is(Square.Type()))
		{
			q.setType(Orthogonal.Type());
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
			int cols = c.Columns();
			
			// Create the upper triangular matrix.
			r = Matrices.create(cols, cols);
			// Assign the type of matrix R.
			r.setType(UpperTriangular.Type());
			
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