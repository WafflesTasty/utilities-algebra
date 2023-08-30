package waffles.utils.algebra.algorithms.factoring.hholder;

import waffles.utils.algebra.algorithms.factoring.FCTEigenVals;
import waffles.utils.algebra.algorithms.leastsquares.LSQHouseHolder;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;

/**
 * The {@code FCTHessenbergHH} algorithm performs an eigenvalue factorization with {@code Householder's} method.
 * This algorithm diagonalizes a matrix by applying the QR algorithm on a Hessenberg factorization.
 * 
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTEigenVals
 */
public class FCTEigenValsHH implements FCTEigenVals
{
	private static final int MAX_LOOPS = 1000;

	
	private Matrix q, e;
	
	private FCTHessenbergHH fctHess;
	private LSQHouseHolder lsqHH;
	private Matrix base;
	
	/**
	 * Creates a new {@code FCTEigenHH}.
	 * This algorithm requires a symmetric matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a co�fficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTEigenValsHH(Matrix m)
	{
		base = m;
	}

	
	private void decompose()
	{
		// If the matrix is not symmetric...
		if(!base.is(Symmetric.Type()))
		{
			// A Hessenberg factorization cannot be computed.
			throw new Matrices.TypeError(Symmetric.Type());
		}


		// Decompose through Hessenberg factorization.
		fctHess = new FCTHessenbergHH(base);
		e = fctHess.H(); q = fctHess.Q();
		
		
		int iCount = 0;
		// As long as the matrix is not diagonalized...
		while(!Diagonal.Type().allows(e, 3))
		{
			lsqHH = new LSQHouseHolder(e);
			// Decompose through QR factorization.
			q = lsqHH.Q().transpose().times(q);
			e = lsqHH.R().times(lsqHH.Q());
			
			
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
		if(q == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		
		// Assign the type of matrix Q.
		if(q.is(Square.Type()))
		{
			q.setOperator(Orthogonal.Type());
		}
		
		return q;
	}
	
	@Override
	public Matrix E()
	{
		// If no decomposition has been made yet...
		if(e == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		
		// Assign the type of matrix E.
		if(e.is(Square.Type()))
		{
			e.setOperator(Diagonal.Type());
		}
		
		return e;
	}
}
