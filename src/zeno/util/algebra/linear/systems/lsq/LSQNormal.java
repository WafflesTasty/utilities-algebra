package zeno.util.algebra.linear.systems.lsq;

import zeno.util.algebra.attempt4.linear.data.Type;
import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.systems.LeastSquares;
import zeno.util.algebra.linear.systems.slv.SLVCholesky;
import zeno.util.algebra.linear.systems.slv.SLVTriangular;

/**
 * The {@code LSQNormal} class solves least squares linear systems using normal equations.
 * This method decomposes a matrix {@code M = QR} where Q is a matrix with orthonormal columns,
 * and R an upper triangular matrix. Normal equations solve a linear system M*MX = R*RX = M*B
 * through Cholesky factorization.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see LeastSquares
 */
public class LSQNormal implements LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix c, q, r;
	private Matrix mat, inv;
	private SLVCholesky slv;
	private int iError;
	
	/**
	 * Creates a new {@code LSQNormal}.
	 * A matrix with at least as many rows as columns needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public LSQNormal(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQNormal}.
	 * A matrix with at least as many rows as columns needs to be provided.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public LSQNormal(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
	
	/**
	 * Returns the reduced orthogonal matrix Q from the {@code LSQNormal}.
	 * 
	 * @return  the reduced orthogonal matrix Q
	 * @see Matrix
	 */
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform normal decomposition on the matrix.
			decompose();
		}
		
		// If Q hasn't been computed yet...
		if(q == null)
		{
			// Compute the reduced orthonormal matrix.
			q = new SLVTriangular(R()).inverse();
			q = mat.times(q);
		}
		
		return q;
	}
	
	/**
	 * Returns the upper triangular matrix R from the {@code LSQNormal}.
	 * 
	 * @return  the upper triangular matrix R
	 * @see Matrix
	 */
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Cholesky decomposition on the matrix.
			decompose();
		}
		
		// If R hasn't been computed yet...
		if(r == null)
		{
			// Compute the upper triangular matrix.
			r = slv.R();
		}
		
		return r;
	}
	
	
	@Override
	public void requestUpdate()
	{
		c = q = inv = null;
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
			// Perform Gram-Schmidt on the matrix.
			decompose();
		}
		
		// Compute the result through substitution.
		Matrix x = mat.transpose().times(b);
		x = slv.solve(x);
		return x;
	}
	
	@Override
	public Matrix pseudoinverse()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Cholesky decomposition on the matrix.
			decompose();
		}
		
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
	
	
	private void decompose()
	{
		// If the matrix is underdetermined...
		if(!Matrices.isTall(mat))
		{
			// Normal equations are not applicable.
			throw new Matrices.TallException();
		}
		
		
		// Matrix dimensions.
		int mRows = c.Rows();
		int mCols = c.Columns();		
		
		// If the matrix is upper triangular...
		if(Matrices.isUpperTriangular(mat, iError))
		{
			// Skip the normal method.
			q = Matrices.identity(mRows, mCols);
			r = c; return;
		}
		
		// If the matrix is orthogonal...
		if(Matrices.isOrthogonal(mat, iError))
		{
			// Skip the normal method.
			r = Matrices.identity(mCols, mCols);
			q = c; return;
		}
		
		
		// Create the symmetric product matrix.
		c = mat.transpose().times(mat);
		c.setType(Type.SYMMETRIC);
		
		// Initialize the Cholesky solver.
		slv = new SLVCholesky(c, iError);		
	}
}