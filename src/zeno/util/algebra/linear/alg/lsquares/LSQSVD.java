package zeno.util.algebra.linear.alg.lsquares;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.types.Orthogonal;
import zeno.util.algebra.attempt4.linear.types.banded.Diagonal;
import zeno.util.algebra.attempt4.linear.types.size.Square;
import zeno.util.algebra.attempt4.linear.types.size.Tall;
import zeno.util.algebra.linear.alg.LeastSquares;
import zeno.util.algebra.linear.alg.factor.FCTSingular;
import zeno.util.algebra.linear.alg.factor.hh.FCTBidiagonalHH;
import zeno.util.algebra.linear.alg.solvers.SLVTriangular;
import zeno.util.algebra.linear.error.DimensionError;

/**
 * The {@code LSQSVD} class solves least squares linear systems using {@code SVD factorization}.
 * This method factorizes a matrix {@code M = UEV*} where U is a matrix with orthonormal columns,
 * E is a matrix of singular values, and V is an orthogonal matrix.
 * 
 * This algorithm is an implementation of the Demmel & Kahan zero-shift downward sweep.
 * During every iteration Givens iterations are applied to the subdiagonal from top to bottom. 
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see LeastSquares
 * @see FCTSingular
 */
public class LSQSVD implements FCTSingular, LeastSquares
{
	private static final int MAX_SWEEPS = 1000;
	private static final int ERROR_MULT = 4;
	private static final int ULPS = 3;
	
	
	private Matrix mat, inv;
	private Matrix c, e, u, v;
	private int iError;
	
	/**
	 * Creates a new {@code LSQSVD}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public LSQSVD(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQSVD}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public LSQSVD(Matrix m, int ulps)
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
			// Perform SVD factorization.
			decompose();
		}
		
		// Compute the result through substitution.
		Matrix x = U().transpose().times(b);
		x = new SLVTriangular(E()).solve(x);
		return (M) V().times(x);
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
		c = e = u = v = inv = null;
	}

	private void decompose()
	{
		// If the matrix is not tall...
		if(!mat.is(Tall.Type()))
		{
			// An SVD factorization cannot be computed.
			throw new DimensionError("SVD factorization requires a tall matrix: ", mat);
		}
		
		
		// Perform bidiagonal factorization on the matrix.
		FCTBidiagonalHH bih = new FCTBidiagonalHH(mat, iError);
		u = bih.U(); c = bih.B(); v = bih.V();
		
		
		int iCount = 0;
		// As long as the target matrix is not diagonalized...
		while(!c.is(Diagonal.Type(), iError * ERROR_MULT))
		{
			int size = c.Rows();
			// For every row/column in the target matrix...
			for(int i = 0; i < size - 1; i++)
			{
				// Create the right Givens matrix.
				Matrix rg = Matrices.rightGivens(c, i, i + 1);
				// Right rotate the target matrix.
				c = c.times(rg); v = v.times(rg);

				
				// Create the left Givens matrix.
				Matrix lg = Matrices.leftGivens(c, i, i + 1);
				// Left rotate the target matrix.
				c = lg.times(c); u = u.times(lg.transpose());
			}
			
			
			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_SWEEPS)
			{
				break;
			}
		}
	}
	

	@Override
	public Matrix E()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform SVD factorization.
			decompose();
		}

		
		// If matrix E hasn't been computed yet...
		if(e == null)
		{
			// Matrix dimensions.
			int cols = c.Columns();
						
			// Create the diagonal matrix.
			e = Matrices.create(cols, cols);
			// Assign the type of matrix E.
			e.setType(Diagonal.Type());
			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < cols; i++)
			{
				e.set(c.get(i, i), i, i);
			}	
		}
		
		return e;
	}

	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform SVD factorization.
			decompose();
		}
		
		
		// Assign the type of matrix U.
		if(u.is(Square.Type()))
		{
			u.setType(Orthogonal.Type());
		}
		
		return u;
	}

	@Override
	public Matrix V()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform SVD factorization.
			decompose();
		}
		
		
		// Assign the type of matrix V.
		if(v.is(Square.Type()))
		{
			v.setType(Orthogonal.Type());
		}
		
		return v;
	}
}