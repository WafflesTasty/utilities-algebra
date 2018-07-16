package zeno.util.algebra.algorithms.lsquares;

import zeno.util.algebra.algorithms.LeastSquares;
import zeno.util.algebra.algorithms.factor.FCTOrthogonal;
import zeno.util.algebra.algorithms.solvers.SLVCholesky;
import zeno.util.algebra.algorithms.solvers.SLVTriangular;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.matrix.types.square.Symmetric;
import zeno.util.algebra.linear.tensor.Tensors;

/**
 * The {@code LSQNormal} class solves least squares linear systems using normal equations.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns,
 * and R an upper triangular matrix. Normal equations solve a linear system M*MX = R*RX = M*B
 * through Cholesky factorization.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQNormal implements FCTOrthogonal, LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix mat, c;
	private Matrix inv, q, r;
	private SLVCholesky slv;
	private int iError;
	
	/**
	 * Creates a new {@code LSQNormal}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQNormal(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQNormal}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQNormal(Matrix m, int ulps)
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
			// Perform normal factorization.
			decompose();
		}
		
		// Compute the result through substitution.
		M x = (M) mat.transpose().times(b);
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
			
	private void decompose()
	{
		// If the matrix is not tall...
		if(!mat.is(Tall.Type()))
		{
			// A QR factorization cannot be computed.
			throw new Tensors.DimensionError("QR factorization requires a tall matrix: ", mat);
		}
		
		
		// Create the symmetric product.
		c = mat.transpose().times(mat);
		c.setOperator(Symmetric.Type());
		
		// Initialize the Cholesky solver.
		slv = new SLVCholesky(c, iError);		
	}

	
	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform normal factorization.
			decompose();
		}
		
		// If matrix Q hasn't been computed yet...
		if(q == null)
		{
			// Compute the reduced orthonormal matrix Q.
			q = new SLVTriangular(R()).inverse();
			q = mat.times(q);
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
			// Perform normal factorization.
			decompose();
		}
		
		// If matrix R hasn't been computed yet...
		if(r == null)
		{
			// Compute the upper triangular matrix R.
			r = slv.U();
		}
		
		// Assign the type of matrix R.
		if(r.is(Square.Type()))
		{
			r.setOperator(UpperTriangular.Type());
		}
		
		return r;
	}
}