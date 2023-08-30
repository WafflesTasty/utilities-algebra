package waffles.utils.algebra.algorithms.leastsquares;

import waffles.utils.algebra.algorithms.LeastSquares;
import waffles.utils.algebra.algorithms.factoring.FCTOrthogonal;
import waffles.utils.algebra.algorithms.solvers.SLVTriangular;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.utilities.matrix.Householder;

/**
 * The {@code LSQHouseHolder} algorithm solves least squares linear systems using {@code Householder triangulization}.
 * This method factorizes a matrix {@code M = QR} where Q is a matrix with orthonormal columns, and R an upper triangular
 * matrix. The {@code Householder} process simultaneously orthogonalizes all columns by applying reflections.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see FCTOrthogonal
 * @see LeastSquares
 */
public class LSQHouseHolder implements FCTOrthogonal, LeastSquares
{
	private Matrix q, r;
	private Matrix mat, inv;
	
	private Matrix base;
	
	/**
	 * Creates a new {@code LSQHouseHolder}.
	 * This algorithm requires a matrix marked
	 * as tall. Otherwise, canApprox(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQHouseHolder(Matrix m)
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
			// Skip the Householder method.
			q = Matrices.identity(rows);
			r = mat = base.copy();
			return;
		}
		
		// If the matrix is orthogonal...
		if(base.is(Orthogonal.Type()))
		{
			// Skip the Householder method.
			r = Matrices.identity(cols);
			q = mat = base.copy();
			return;
		}
		
		
		// Copy the coefficient matrix.
		mat = base.copy();
		// Decompose with Householder's method.
		houseHolder();
	}
	
	private void houseHolder()
	{
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();	
				
		// Create the orthogonal matrix Q.
		q = Matrices.identity(rows);
		// Assign the type of matrix Q.
		q.setOperator(Identity.Type());
		
		
		// For each column in the target matrix...
		for(int j = 0; j < cols; j++)
		{
			// Create the reflection normal.
			Vector vk = mat.Column(j);
			for(int i = 0; i < j; i++)
			{
				vk.set(0f, i);
			}
			
			// Create the reflection matrix.
			Matrix vhh = Householder.Matrix(vk, j);
			// Reflect the target matrix.
			mat = vhh.times(mat);
			q = q.times(vhh);
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
		
		
		// Define dimensions.
		int qRows = q.Rows();
		int qCols = q.Columns();
		int cCols = mat.Columns();
		
		// If Q is not in reduced form...
		if(qCols != cCols)
		{
			// Reduce the orthogonal Q matrix.
			q = Matrices.resize(q, qRows, cCols);
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
		
		
		// If matrix R has not been computed yet...
		if(r == null)
		{
			// Define dimensions.
			int cols = mat.Columns();
			
			// Create the upper triangular matrix R.
			r = Matrices.create(cols, cols);
			// Assign the type of matrix R.
			r.setOperator(UpperTriangular.Type());
			
			
			// Copy from the decomposed matrix.
			for(int i = 0; i < cols; i++)
			{
				for(int j = i; j < cols; j++)
				{
					r.set(mat.get(i, j), i, j);
				}
			}			
		}
		
		return r;
	}
}