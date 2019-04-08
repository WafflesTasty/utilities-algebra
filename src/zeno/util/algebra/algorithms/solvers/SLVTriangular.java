package zeno.util.algebra.algorithms.solvers;

import zeno.util.algebra.algorithms.LinearSolver;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.banded.Diagonal;
import zeno.util.algebra.linear.matrix.types.banded.lower.LowerTriangular;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperTriangular;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Floats;

/**
 * The {@code SLVTriangular} class solves exact linear systems through substitution.
 * This solver fails if anything other than a triangular coëfficient matrix is used.
 *
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * 
 * @see LinearSolver
 */
public class SLVTriangular implements LinearSolver
{	
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix mat;
	private Matrix inv;
	private int iError;
	
	/**
	 * Creates a new {@code SLVTriangular}.
	 * This algorithm requires a triangular matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVTriangular(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVTriangular}.
	 * This algorithm requires a triangular matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public SLVTriangular(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
		
	@Override
	public <M extends Matrix> boolean canSolve(M b)
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires compatible dimensions: ", mat, b);
		}
		
		return mat.is(LowerTriangular.Type())
			|| mat.is(UpperTriangular.Type());
	}
	
	@Override
	public <M extends Matrix> M solve(M b)
	{
		// Matrix dimensions.
		int mRows = mat.Rows();
		int bRows = b.Rows();
				
		// If the right-hand side does not have the right dimensions...
		if(mRows != bRows)
		{
			// The linear system cannot be solved.
			throw new Tensors.DimensionError("Solving a linear system requires compatible dimensions: ", mat, b);
		}
		
		
		// Perform direct scaling on diagonal matrices.
		if(mat.is(Diagonal.Type(), iError))
		{
			return (M) scalar(b);
		}
		
		// Perform forward substitution on lower triangulars.
		if(mat.is(LowerTriangular.Type(), iError))
		{
			return (M) forward(b);
		}
		
		// Perform backward substitution on upper triangulars.
		if(mat.is(UpperTriangular.Type(), iError))
		{
			return (M) backward(b);
		}
		
		
		// Other matrices are invalid for this operation.
		throw new Matrices.TriangularError(mat);
	}

	
	private Matrix backward(Matrix b)
	{
		// Left-hand side dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();


		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform backward substitution.
			for(int i = mRows - 1; i >= 0; i--)
			{	
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.InvertibleError(mat);
				}
				
				x.set(b.get(i, k), i, k);
				
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
		// Left-hand side dimensions.
		int mRows = mat.Rows();
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();

		
		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform forward substitution.
			for(int i = 0; i < mRows; i++)
			{
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.InvertibleError(mat);
				}
				
				x.set(b.get(i, k), i, k);
				
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

	private Matrix scalar(Matrix b)
	{
		// Left-hand side dimensions.
		int mRows = mat.Rows();
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();


		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform matrix scaling.
			for(int i = 0; i < mRows; i++)
			{
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.InvertibleError(mat);
				}
				
				double val = b.get(i, k);
				val = val / mat.get(i, i);
				x.set((float) val, i, k);
			}
		}
		
		return x;
	}
	
	
	@Override
	public void requestUpdate()
	{
		det = null; inv = null;
	}

	@Override
	public boolean needsUpdate()
	{
		return inv == null;
	}
	
	@Override
	public boolean isInvertible()
	{
		return Floats.isZero(determinant(), iError);
	}
	
	@Override
	public float determinant()
	{
		// If no determinant has been computed yet...
		if(det == null)
		{
			// If the matrix is not triangular...
			if(!canSolve(mat))
			{
				// The linear system cannot be solved.
				throw new Matrices.TriangularError(mat);
			}
			
			// Compute the determinant.
			double val = 1d;
			for(int i = 0; i < mat.Rows(); i++)
			{
				val *= mat.get(i, i);
			}
			
			det = (float) val;
			return det;
		}
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		// If no inverse has been computed yet...
		if(needsUpdate())
		{
			// Compute the inverse through substitution.
			inv = solve(Matrices.identity(mat.Rows()));
						
			// If the matrix is lower triangular...
			if(mat.is(LowerTriangular.Type(), iError))
			{
				// The inverse is lower triangular too.
				inv.setOperator(LowerTriangular.Type());
			}
			
			// If the matrix is upper triangular...
			if(mat.is(UpperTriangular.Type(), iError))
			{
				// The inverse is upper triangular too.
				inv.setOperator(UpperTriangular.Type());
			}
			
			// If the matrix is diagonal...
			if(mat.is(Diagonal.Type(), iError))
			{
				// The inverse is diagonal too.
				inv.setOperator(Diagonal.Type());
			}
		}
		
		return inv;
	}
}