package zeno.util.algebra.algorithms.lsquares;

import zeno.util.algebra.algorithms.LeastSquares;
import zeno.util.algebra.algorithms.factor.FCTOrthogonal;
import zeno.util.algebra.algorithms.solvers.SLVTriangular;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.vector.Vector;

/**
 * The {@code LSQGramSchmidt} class solves least squares linear systems using {@code Gram-Schmidt orthogonalization}.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Gram-Schmidt} process sequentially orthogonalizes each column of M in order.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQGramSchmidt implements FCTOrthogonal, LeastSquares
{
	private static final int ULPS = 3;
	
	
	private Matrix c, q, r;
	private Matrix mat, inv;
	private int iError;
	
	/**
	 * Creates a new {@code LSQGramSchmidt}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a co�fficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQGramSchmidt(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code LSQGramSchmidt}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a co�fficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQGramSchmidt(Matrix m, int ulps)
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
			// Perform Gram-Schmidt factorization.
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
		
	private void gramSchmidt()
	{
		// Copy the target matrix.
		c = mat.copy();
		
		
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
		
		// Create the orthogonal matrix Q.
		q = Matrices.create(rows, cols);
		// Create the upper triangular matrix R.
		r = Matrices.create(cols, cols);
		
		
		// For each column in the target matrix...
		for(int k = 0; k < cols; k++)
		{
			// Normalize the column.
			Vector vk = c.Column(k);
			float norm = vk.norm();
			r.set(norm, k, k);
			
			
			vk = vk.times(1 / norm);
			// Store the unit vector in Q.
			for(int i = 0; i < rows; i++)
			{
				q.set(vk.get(i), i, k);
			}
			
			
			// For each row in the target matrix...
			for(int j = k + 1; j < cols; j++)
			{
				// Compute the dot product.
				Vector vj = c.Column(j);
				float dot = vk.dot(vj);
				r.set(dot, k, j);
				
				
				vj = vj.minus(vk.times(dot));
				// Subtract the remaining vectors.
				for(int i = 0; i < rows; i++)
				{
					c.set(vj.get(i), i, j);
				}
			}
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
		
		
		// If the matrix is upper triangular...
		if(mat.is(UpperTriangular.Type()))
		{
			// Skip the Gram-Schmidt method.
			q = Matrices.identity(rows);
			r = c = mat.copy();
			return;
		}
		
		// If the matrix is orthogonal...
		if(mat.is(Orthogonal.Type()))
		{
			// Skip the Gram-Schmidt method.
			r = Matrices.identity(cols);
			q = c = mat.copy();
			return;
		}
		
		
		// Otherwise, perform Gram-Schmidt's method.
		gramSchmidt();
	}

	
	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gram-Schmidt factorization.
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
	public Matrix R()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Gram-Schmidt factorization.
			decompose();
		}
		
		// Assign the type of matrix R.
		if(r.is(Square.Type()))
		{
			r.setOperator(UpperTriangular.Type());
		}
		
		return r;
	}
}