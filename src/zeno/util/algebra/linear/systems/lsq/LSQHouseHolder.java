package zeno.util.algebra.linear.systems.lsq;

import zeno.util.algebra.attempt4.linear.data.Type;
import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.attempt4.linear.vec.Vectors;
import zeno.util.algebra.linear.systems.LeastSquares;
import zeno.util.algebra.linear.systems.slv.SLVTriangular;

/**
 * The {@code LSQHouseHolder} class solves least squares linear systems using {@code Householder triangulization}.
 * This method decomposes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Householder} process simultaneously orthogonalizes all columns by applying orthogonal rotations.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see LeastSquares
 */
public class LSQHouseHolder implements LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix c, q, r;
	private Matrix mat, inv;
	private int iError;
	
	/**
	 * Creates a new {@code LSQHouseHolder}.
	 * A matrix with at least as many rows as columns needs to be provided.
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
	 * A matrix with at least as many rows as columns needs to be provided.
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
	
	
	/**
	 * Returns the reduced orthogonal matrix Q from the {@code LSQHouseHolder}.
	 * 
	 * @return  the reduced orthogonal matrix Q
	 * @see Matrix
	 */
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Householder decomposition on the matrix.
			decompose();
		}
		
		
		// Matrix dimensions.
		int qRows = q.Rows();
		int qCols = q.Columns();
		int cCols = c.Columns();
		
		// If Q is not in reduced form...
		if(qCols != cCols)
		{
			// Reduce the Q matrix.
			q = Matrices.subMatrix(q, 0, 0, qRows, cCols);
		}
		
				
		return q;
	}
	
	/**
	 * Returns the upper triangular matrix R from the {@code LSQHouseHolder}.
	 * 
	 * @return  the upper triangular matrix R
	 * @see Matrix
	 */
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Householder decomposition on the matrix.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(r == null)
		{
			// Matrix dimensions.
			int cols = c.Columns();
			
			// Create the upper triangular matrix.
			r = Matrices.create(cols, cols);
			r.setType(Type.UPPER_TRIANGULAR);

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
	public Matrix approx(Matrix b)
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
			// Perform Householder decomposition on the matrix.
			decompose();
		}

		
		// Compute the result through substitution.
		Matrix x = Q().transpose().times(b);
		x = new SLVTriangular(R(), iError).solve(x);
		return x;
	}
	
	@Override
	public Matrix pseudoinverse()
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
			inv = approx(Matrices.identity(mat.Rows()));
		}
		
		return inv;
	}

	
	private void householder()
	{
		// Matrix dimensions.
		int mRows = c.Rows();
		int mCols = c.Columns();	
				
		q = Matrices.identity(mRows, mRows);		
		for(int k = 0; k < mCols; k++)
		{
			Vector vk = Vectors.create(mRows - k);
			for(int i = k; i < mRows; i++)
			{
				vk.set(c.get(i, k), i - k);
			}

			Matrix hh = Matrices.houseHolder(vk, mRows);
			c = hh.transpose().times(c);
			q = q.times(hh);
		}
	}
	
	private void decompose()
	{
		// If the matrix is underdetermined...
		if(!Matrices.isTall(mat))
		{
			// Householder is not applicable.
			throw new Matrices.TallException();
		}
		
				
		// Copy the target matrix.
		c = mat.copy();
		
		// Matrix dimensions.
		int mRows = c.Rows();
		int mCols = c.Columns();		
		
		// If the matrix is upper triangular...
		if(Matrices.isUpperTriangular(mat, iError))
		{
			// Skip the Householder method.
			q = Matrices.identity(mRows, mCols);
			r = c; return;
		}
		
		// If the matrix is orthogonal...
		if(Matrices.isOrthogonal(mat, iError))
		{
			// Skip the Householder method.
			r = Matrices.identity(mCols, mCols);
			q = c; return;
		}
		
		
		// Otherwise, perform Householder.
		householder();
	}
}