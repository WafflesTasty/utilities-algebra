package waffles.utils.algebra.algorithms.leastsquares;

import waffles.utils.algebra.algorithms.LeastSquares;
import waffles.utils.algebra.algorithms.factoring.FCTOrthogonal;
import waffles.utils.algebra.algorithms.solvers.SLVTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.vector.Vector;

/**
 * The {@code LSQGramSchmidt} class solves least squares linear systems using {@code Gram-Schmidt} orthogonalization.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Gram-Schmidt} process sequentially orthogonalizes each column of M in order.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQGramSchmidt implements FCTOrthogonal, LeastSquares
{
	private Matrix q, r;
	private Matrix mat, inv;
	
	private Matrix base;
	
	/**
	 * Creates a new {@code LSQGramSchmidt}.
	 * This algorithm requires a matrix marked
	 * as tall. Otherwise, canApprox(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQGramSchmidt(Matrix m)
	{
		base = m;
	}
	
	
	private void decompose()
	{		
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
		
		
		// If the matrix is upper triangular...
		if(base.is(UpperTriangular.Type()))
		{
			// Skip the Gram-Schmidt method.
			q = Matrices.identity(rows);
			r = mat = base.copy();
		}
		// If the matrix is orthogonal...
		else if(base.is(Orthogonal.Type()))
		{
			// Skip the Gram-Schmidt method.
			r = Matrices.identity(cols);
			q = mat = base.copy();
			return;
		}
		// Otherwise...
		else
		{
			// Copy the base matrix.
			mat = base.copy();
			// Decompose with Gram-Shmidt's method.
			gramSchmidt();
		}
	}
	
	private void gramSchmidt()
	{		
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
		
		// Create the orthogonal matrix Q.
		q = Matrices.create(rows, cols);
		// Create the upper triangular matrix R.
		r = Matrices.create(cols, cols);
		
		
		// For each column in the target matrix...
		for(int k = 0; k < cols; k++)
		{
			// Normalize the column.
			Vector vk = mat.Column(k);
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
				Vector vj = mat.Column(j);
				float dot = vk.dot(vj);
				r.set(dot, k, j);
				
				
				vj = vj.minus(vk.times(dot));
				// Subtract the remaining vectors.
				for(int i = 0; i < rows; i++)
				{
					mat.set(vj.get(i), i, j);
				}
			}
		}
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
		M x = (M) Q().transpose().times(b);
		x = new SLVTriangular(R()).solve(x);
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
		
		// Assign the type of matrix Q.
		if(Square.Type().allows(q, 0))
		{
			q.setOperator(Orthogonal.Type());
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
		
		// Assign the type of matrix R.
		if(Square.Type().allows(r, 0))
		{
			r.setOperator(UpperTriangular.Type());
		}
		
		return r;
	}
}