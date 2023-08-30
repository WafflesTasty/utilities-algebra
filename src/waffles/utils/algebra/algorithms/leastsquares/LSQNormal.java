package waffles.utils.algebra.algorithms.leastsquares;

import waffles.utils.algebra.algorithms.LeastSquares;
import waffles.utils.algebra.algorithms.factoring.FCTOrthogonal;
import waffles.utils.algebra.algorithms.solvers.SLVCholesky;
import waffles.utils.algebra.algorithms.solvers.SLVTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;
import waffles.utils.algebra.elements.linear.tensor.Tensors;

/**
 * The {@code LSQNormal} algorithm solves least squares linear systems using normal equations.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns,
 * and R an upper triangular matrix. Normal equations solve a linear system M*MX = R*RX = M*B
 * through Cholesky factorization.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQNormal implements FCTOrthogonal, LeastSquares
{
	private Matrix q, r;
	private Matrix mat, inv;
	
	private SLVCholesky slv;
	private Matrix base;
	
	/**
	 * Creates a new {@code LSQNormal}.
	 * This algorithm requires a matrix marked
	 * as tall. Otherwise, canApprox(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQNormal(Matrix m)
	{
		base = m;
	}
	
	
	private void decompose()
	{		
		// Create the symmetric product.
		mat = base.transpose().times(base);
		mat.setOperator(Symmetric.Type());
		
		// Initialize the Cholesky solver.
		slv = new SLVCholesky(mat);		
	}

	
	@Override
	public <M extends Matrix> boolean canApprox(M b)
	{
		// If the matrix is not tall...
		if(!base.is(Tall.Type()))
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
		M x = (M) base.transpose().times(b);
		x = slv.solve(x);
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
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If Q has not been computed yet...
		if(q == null)
		{
			// Compute the reduced orthonormal matrix Q.
			q = new SLVTriangular(R()).inverse();
			q = base.times(q);
			
			// Assign the type of matrix Q.
			if(Square.Type().allows(q, 0))
			{
				q.setOperator(Orthogonal.Type());
			}
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
		
		// If Q has not been computed yet...
		if(r == null)
		{
			// Compute the upper triangular matrix R.
			r = slv.U();
			
			// Assign the type of matrix R.
			if(Square.Type().allows(r, 0))
			{
				r.setOperator(UpperTriangular.Type());
			}
		}
		
		return r;
	}
}