package waffles.utils.algebra.algorithms.solvers;

import waffles.utils.algebra.algorithms.LinearSolver;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code SLVTriangular} algorithm solves exact linear systems through substitution.
 * This solver fails if anything other than a triangular coefficient matrix is used.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see LinearSolver
 */
public class SLVTriangular implements LinearSolver
{	
	private Float det;
	private Matrix mat, inv;
	
	/**
	 * Creates a new {@code SLVTriangular} algorithm.
	 * This algorithm requires a matrix marked by one of
	 * the triangular types. Otherwise, an exception
	 * is thrown by canSolve(b).
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVTriangular(Matrix m)
	{
		mat = m;
	}

	
	private Matrix scalar(Matrix b)
	{
		// Define dimensions.
		int mRows = mat.Rows();
		int bCols = b.Columns();


		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform matrix scaling.
			for(int i = 0; i < mRows; i++)
			{
				// If a diagonal element is zero...
				if(Floats.isZero(mat.get(i, i), 3))
				{
					// The coefficient matrix is not invertible.
					throw new Matrices.InvertibleError(mat);
				}
				
				
				double val = x.get(i, k);
				val = val / mat.get(i, i);
				x.set((float) val, i, k);
			}
		}
		
		return x;
	}

	private Matrix backward(Matrix b)
	{
		// Define dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
		int bCols = b.Columns();


		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform backward substitution.
			for(int i = mRows - 1; i >= 0; i--)
			{	
				// If a diagonal element is zero...
				if(Floats.isZero(mat.get(i, i), 3))
				{
					// The coefficient matrix is not invertible.
					throw new Matrices.InvertibleError(mat);
				}
				
				
				double val = 0;
				for(int j = i + 1; j < mCols; j++)
				{
					val += (double) mat.get(i, j) * x.get(j, k);
				}
				
				val = (x.get(i, k) - val) / mat.get(i, i);
				x.set((float) val, i, k);
			}
		}
			
		return x;
	}
	
	private Matrix forward(Matrix b)
	{
		// Define dimensions.
		int mRows = mat.Rows();
		int bCols = b.Columns();
		
		
		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform forward substitution.
			for(int i = 0; i < mRows; i++)
			{
				// If a diagonal element is zero...
				if(Floats.isZero(mat.get(i, i), 3))
				{
					// The coefficient matrix is not invertible.
					throw new Matrices.InvertibleError(mat);
				}
				
				
				double val = 0;
				for(int j = 0; j < i; j++)
				{
					val += (double) mat.get(i, j) * x.get(j, k);
				}
				
				val = (x.get(i, k) - val) / mat.get(i, i);
				x.set((float) val, i, k);
			}
		}
		
		return x;
	}
	
	
	@Override
	public <M extends Matrix> boolean canSolve(M b)
	{
		// Define dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand matrix does not have the right dimensions...
		if(mRows != bRows)
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires compatible dimensions: ", mat, b);
		}

		
		// If the coefficient matrix is not triangular...
		if(!mat.is(LowerTriangular.Type())
		&& !mat.is(UpperTriangular.Type()))
		{
			// The triangular solver cannot be used.
			throw new Matrices.TriangularError(mat);
		}
		
		for(int i = 0; i < mRows; i++)
		{
			// If a diagonal element is zero...
			if(Floats.isZero(mat.get(i, i), 3))
			{
				// The coefficient matrix is not invertible.
				throw new Matrices.InvertibleError(mat);
			}
		}
		
		return true;
	}
	
	@Override
	public <M extends Matrix> M solve(M b)
	{
		// Perform direct scaling on diagonal matrices.
		if(mat.is(Diagonal.Type()))
		{
			return (M) scalar(b);
		}
		
		// Perform forward substitution on lower triangulars.
		if(mat.is(LowerTriangular.Type()))
		{
			return (M) forward(b);
		}
		
		// Perform backward substitution on upper triangulars.
		if(mat.is(UpperTriangular.Type()))
		{
			return (M) backward(b);
		}
		
		return null;
	}

	
	@Override
	public boolean canInvert()
	{
		int rows = mat.Rows(); int cols = mat.Columns();
		return Floats.isZero(determinant(), (rows + cols) / 2);
	}
	
	@Override
	public float determinant()
	{
		// If no determinant has been computed yet...
		if(det == null)
		{
			double val = 1d;
			// Compute the determinant.
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			
			det = (float) val;
		}
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		// If no inverse has been computed yet...
		if(inv == null)
		{
			// Compute the inverse through substitution.
			Matrix b = Matrices.identity(mat.Rows());
			b.setOperator(Identity.Type());
			b.setDestructible(true);
			inv = solve(b);
						
			// If the matrix is lower triangular...
			if(mat.is(LowerTriangular.Type()))
			{
				// So is the inverse.
				inv.setOperator(LowerTriangular.Type());
			}
			
			// If the matrix is upper triangular...
			if(mat.is(UpperTriangular.Type()))
			{
				// So is the inverse.
				inv.setOperator(UpperTriangular.Type());
			}
			
			// If the matrix is diagonal...
			if(mat.is(Diagonal.Type()))
			{
				// So is the inverse.
				inv.setOperator(Diagonal.Type());
			}
		}
		
		return inv;
	}
}