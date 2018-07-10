package zeno.util.algebra.linear.systems.lsq;

import zeno.util.algebra.attempt4.linear.data.Type;
import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.linear.systems.LeastSquares;
import zeno.util.algebra.linear.systems.slv.SLVTriangular;

/**
 * The {@code LSQGramSchmidt} class solves least squares linear systems using {@code Gram-Schmidt orthogonalization}.
 * This method decomposes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Gram-Schmidt} process sequentially orthogonalizes each column of M in order.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see LeastSquares
 */
public class LSQGramSchmidt implements LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix c, q, r;
	private Matrix mat, inv;
	private int iError;
	
	/**
	 * Creates a new {@code LSQGramSchmidt}.
	 * A matrix with at least as many rows as columns needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public LSQGramSchmidt(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQGramSchmidt}.
	 * A matrix with at least as many rows as columns needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public LSQGramSchmidt(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
	
	/**
	 * Returns the reduced orthogonal matrix Q from the {@code LSQGramSchmidt}.
	 * 
	 * @return  the reduced orthogonal matrix Q
	 * @see Matrix
	 */
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gram-Schmidt on the matrix.
			decompose();
		}
		
		return q;
	}
	
	/**
	 * Returns the upper triangular matrix R from the {@code LSQGramSchmidt}.
	 * 
	 * @return  the upper triangular matrix R
	 * @see Matrix
	 */
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gram-Schmidt on the matrix.
			decompose();
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
		return c == null
			&& q == null
			&& r == null;
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
			// Perform Gram-Schmidt on the matrix.
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
	
		
	private void gramSchmidt()
	{
		// Matrix dimensions.
		int mRows = c.Rows();
		int mCols = c.Columns();
		
		// Create new QR-matrices.
		q = Matrices.create(mRows, mCols);
		r = Matrices.create(mCols, mCols);
		r.setType(Type.UPPER_TRIANGULAR);
		
		// For each column in the target matrix...
		for(int k = 0; k < mCols; k++)
		{
			// Normalize the column.
			Vector vk = c.Column(k);
			float norm = vk.norm();
			r.set(norm, k, k);
			
			
			vk = vk.times(1 / norm);
			// Store the unit vector.
			for(int i = 0; i < mRows; i++)
			{
				q.set(vk.get(i), i, k);
			}
			
			
			// For each row in the target matrix...
			for(int j = k + 1; j < mCols; j++)
			{
				Vector vj = c.Column(j);
				float dot = vk.dot(vj);
				r.set(dot, k, j);
				
				
				vj = vj.minus(vk.times(dot));
				// Subtract the remaining vectors.
				for(int i = 0; i < mRows; i++)
				{
					c.set(vj.get(i), i, j);
				}
			}
		}
	}
	
	private void decompose()
	{
		// If the matrix is underdetermined...
		if(!Matrices.isTall(mat))
		{
			// Gram-Schmidt is not applicable.
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
			// Skip the Gram-Schmidt method.
			q = Matrices.identity(mRows, mCols);
			r = c; return;
		}
		
		// If the matrix is orthogonal...
		if(Matrices.isOrthogonal(mat, iError))
		{
			// Skip the Gram-Schmidt method.
			r = Matrices.identity(mCols, mCols);
			q = c; return;
		}
		
		// Otherwise, perform Gram-Schmidt.
		gramSchmidt();
	}
}