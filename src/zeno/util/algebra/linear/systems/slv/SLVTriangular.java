package zeno.util.algebra.linear.systems.slv;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.systems.LinearSolver;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code SLVTriangular} class solves exact linear systems through substitution.
 * This solver fails if anything other than a triangular coëfficient matrix is used.
 *
 * @author Zeno
 * @since Jul 9, 2018
 * @version 1.0
 * 
 * @see LinearSolver
 */
public class SLVTriangular implements LinearSolver
{
	/**
	 * Checks if a matrix can be solved by a {@code SLVTriangular}.
	 * 
	 * @param m  a matrix to check
	 * @param ulps  an error margin
	 * @return  {@code true} if the matrix can be solved
	 * @see Matrix
	 */
	public static boolean canSolve(Matrix m, int ulps)
	{
		return Matrices.isLowerTriangular(m, ulps)
			|| Matrices.isUpperTriangular(m, ulps);
	}
	
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix mat, inv;
	private int iError;
	
	/**
	 * Creates a new {@code SLVTriangular}.
	 * The following types have an influence on the solver:
	 * <ul>
	 * <li> {@code Unknown matrix}: The matrix is checked for triangular type. If it isn't, an exception will be thrown.</li>
	 * <li> {@code Lower triangular}: The matrix is automatically processed as a lower triangular matrix, skipping checks.</li>
	 * <li> {@code Upper triangular}: The matrix is automatically processed as an upper triangular matrix, skipping checks.</li>
	 * </ul>
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public SLVTriangular(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVTriangular}.
	 * The following types have an influence on the solver:
	 * <ul>
	 * <li> {@code Unknown matrix}: The matrix is checked for triangular type. If it isn't, an exception will be thrown.</li>
	 * <li> {@code Lower triangular}: The matrix is automatically processed as a lower triangular matrix, skipping checks.</li>
	 * <li> {@code Upper triangular}: The matrix is automatically processed as an upper triangular matrix, skipping checks.</li>
	 * </ul>
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public SLVTriangular(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
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
	public Matrix solve(Matrix b)
	{
		// Perform direct scaling on diagonal matrices.
		if(Matrices.isDiagonal(mat, iError))
		{
			return scalar(b);
		}
		
		// Perform forward substitution on lower triangulars.
		if(Matrices.isLowerTriangular(mat, iError))
		{
			return forward(b);
		}
		
		// Perform backward substitution on upper triangulars.
		if(Matrices.isUpperTriangular(mat, iError))
		{
			return backward(b);
		}
		
		// Other matrices are invalid for this operation.
		throw new Matrices.TriangularException();
	}

	@Override
	public float determinant()
	{
		if(det == null)
		{
			double val = 1d;
			if(Matrices.isLowerTriangular(mat, iError)
			|| Matrices.isUpperTriangular(mat, iError))
			{
				for(int i = 0; i < mat.Rows(); i++)
				{
					val *= mat.get(i, i);
				}
				
				det = (float) val;
				return det;
			}
			
			throw new Matrices.TriangularException();
		}
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		if(needsUpdate())
		{
			// Compute the inverse through substitution.
			inv = solve(Matrices.identity(mat.Columns()));
		}
		
		return inv;
	}


	private Matrix backward(Matrix b)
	{
		// Left-hand side dimensions.
		int mRows = mat.Rows();
		int mCols = mat.Columns();
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();
		
		// Check dimension compatibility.
		if(mCols != bRows)
		{
			throw new Matrices.DimensionException(mat, b);
		}
		
		
		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform backward substitution.
			for(int i = mRows - 1; i >= 0; i--)
			{	
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.RegularException();
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
		int mCols = mat.Columns();	
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();
		
		// Check dimension compatibility.
		if(mCols != bRows)
		{
			throw new Matrices.DimensionException(mat, b);
		}
		
		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform forward substitution.
			for(int i = 0; i < mRows; i++)
			{
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.RegularException();
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
		int mCols = mat.Columns();	
		
		// Right-hand side dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();
		
		// Check dimension compatibility.
		if(mCols != bRows)
		{
			throw new Matrices.DimensionException(mat, b);
		}
		
		Matrix x = Matrices.create(bRows, bCols);
		// For each column in the solution matrix...
		for(int k = 0; k < bCols; k++)
		{
			// ...perform matrix scaling.
			for(int i = 0; i < mRows; i++)
			{
				if(Floats.isZero(mat.get(i, i), iError))
				{
					throw new Matrices.RegularException();
				}
				
				double val = b.get(i, k);
				val = val / mat.get(i, i);
				x.set((float) val, i, k);
			}
		}
		
		return x;
	}
}