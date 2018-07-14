package zeno.util.algebra.linear.alg.factor.hh;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.types.Orthogonal;
import zeno.util.algebra.attempt4.linear.types.Symmetric;
import zeno.util.algebra.attempt4.linear.types.banded.Diagonal;
import zeno.util.algebra.attempt4.linear.types.size.Square;
import zeno.util.algebra.linear.alg.factor.FCTEigen;
import zeno.util.algebra.linear.alg.lsquares.LSQHouseHolder;
import zeno.util.algebra.linear.error.SymmetricError;

/**
 * The {@code FCTHessenbergHH} class performs an eigenvalue factorization.
 * This algorithm applies the QR algorithm to diagonalize a matrix.
 * 
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * @see FCTEigen
 */
public class FCTEigenHH implements FCTEigen
{
	private static final int MAX_LOOPS = 100;
	private static final int ULPS = 3;
	
	
	private Matrix mat;
	private Matrix q, e;
	private int iError;
	
	/**
	 * Creates a new {@code FCTEigenHH}.
	 * This algorithm requires a symmetric matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public FCTEigenHH(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code FCTEigenHH}.
	 * This algorithm requires a symmetric matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public FCTEigenHH(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
		
	@Override
	public boolean needsUpdate()
	{
		return e == null;
	}
	
	@Override
	public void requestUpdate()
	{
		q = e = null;
	}
	
	private void decompose()
	{
		// If the matrix is not symmetric...
		if(!mat.is(Symmetric.Type()))
		{
			// The QR algorithm cannot be applied.
			throw new SymmetricError(mat);
		}
		
		
		// Perform Hessenberg factorization on the matrix.
		FCTHessenbergHH hess = new FCTHessenbergHH(mat, iError);
		e = hess.H(); q = hess.Q();
		
		
		int iCount = 0;
		// As long as the matrix is not diagonalized...
		while(!e.is(Diagonal.Type(), iError))
		{
			// Perform QR factorization on the matrix.
			LSQHouseHolder hh = new LSQHouseHolder(e, iError);
			q = hh.Q().transpose().times(q);
			e = hh.R().times(hh.Q());
			
			
			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_LOOPS)
			{
				break;
			}
		}
	}
	
	
	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform eigenvalue factorization.
			decompose();
		}
		
		
		// Assign the type of matrix Q.
		if(q.is(Square.Type()))
		{
			q.setType(Orthogonal.Type());
		}
		
		return q;
	}
	
	@Override
	public Matrix E()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform eigenvalue factorization.
			decompose();
		}
		
		
		// Assign the type of matrix E.
		if(e.is(Square.Type()))
		{
			e.setType(Diagonal.Type());
		}
		
		return e;
	}
}
