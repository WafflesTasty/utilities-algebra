package zeno.util.algebra.algorithms.factor.hh;

import zeno.util.algebra.algorithms.factor.FCTBidiagonal;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.banded.upper.UpperBidiagonal;
import zeno.util.algebra.linear.matrix.types.dimensions.Tall;
import zeno.util.algebra.linear.matrix.types.orthogonal.Identity;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code FCTBidiagonalHH} class performs a {@code Bidiagonal} factorization.
 * This algorithm applies {@code Householder} transformations to induce zeroes in a matrix.
 * This is known as the Golub-Kahan Bidiagonalization.
 * 
 * NOTE: The reason why this is limited to tall matrices is because of how the bidiagonal matrix gets resized.
 * If worked on a wide matrix, the bidiagonal matrix can never be square (you have one additional superdiagonal element).
 * Therefore, if factorisation of wide matrices is needed, consider using its transpose as input instead.
 * 
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTBidiagonal
 */
public class FCTBidiagonalHH implements FCTBidiagonal
{
	private static final int ULPS = 3;
	
	
	private Float det;
	private Matrix mat, c;
	private Matrix b, u, v;
	private int iError;
	
	/**
	 * Creates a new {@code FCTBidiagonalHH}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTBidiagonalHH(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code FCTBidiagonalHH}.
	 * This algorithm requires a tall matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTBidiagonalHH(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	
	/**
	 * Returns the sign of the determinant.
	 * The original matrix has a determinant that
	 * equals that of B multiplied by this sign.
	 * 
	 * @return  a determinant sign
	 */
	public float determinantSign()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform bidiagonal factorization.
			decompose();
		}
		
		return det;
	}
	
	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
	}

	@Override
	public void requestUpdate()
	{
		b = c = u = v = null;
	}
	
	private void houseHolder()
	{
		// Copy the target matrix.
		c = mat.copy();
				
				
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();
				
		// Create the orthogonal matrices U, V.
		u = Matrices.identity(rows);
		v = Matrices.identity(cols);
		// Assign the type of matrices U, V.
		u.setOperator(Identity.Type());
		v.setOperator(Identity.Type());


		det = 1f;
		// For every row/column in the target matrix...
		for(int k = 0; k < Integers.min(rows, cols); k++)
		{
			// If the subdiagonal is not finished...
			if(k < rows - 1)
			{
				// Create the column reflection normal.
				Vector uk = c.Column(k);
				for(int i = 0; i < k; i++)
				{
					uk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(!Floats.isZero(uk.normSqr(), iError))
				{
					// Create the column reflection matrix.
					Matrix uhh = Matrices.houseHolder(uk, k);
					// Column reflect the target matrix.
					c = uhh.times(c);
					u = u.times(uhh);
					det = -det;
				}
			}
			
			// If the superdiagonal is not finished...
			if(k < cols - 2)
			{
				// Create the row reflection normal.
				Vector vk = c.Row(k);
				for(int i = 0; i <= k; i++)
				{
					vk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(!Floats.isZero(vk.normSqr(), iError))
				{
					// Create the row reflection matrix.
					Matrix vhh = Matrices.houseHolder(vk, k + 1);
					// Row reflect the target matrix.
					c = c.times(vhh);
					v = v.times(vhh);
					det = -det;
				}
			}
		}
	}
	
	private void decompose()
	{	
		// If the matrix is not tall...
		if(!mat.is(Tall.Type()))
		{
			// A bidiagonal factorization cannot be computed.
			throw new Tensors.DimensionError("Bidiagonal factorization requires a tall matrix: ", mat);
		}

		
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();

		// If the matrix is upper bidiagonal...
		if(mat.is(UpperBidiagonal.Type(), iError))
		{
			// Skip the Householder method.
			c = mat.copy();
			c.setOperator(UpperBidiagonal.Type());
			u = Matrices.identity(rows);
			v = Matrices.identity(cols);
			u.setOperator(Identity.Type());
			v.setOperator(Identity.Type());
			return;
		}

		// Otherwise, perform Householder's method.
		houseHolder();
	}
	
	
	@Override
	public Matrix B()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform bidiagonal factorization.
			decompose();
		}
		
		// If B has not been computed yet...
		if(b == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			// Reduce the matrix to square size.
			int size = Integers.min(rows, cols);
			
			
			// Create the bidiagonal matrix B.
			b = Matrices.create(size, size);
			// Assign the type of matrix B.
			b.setOperator(UpperBidiagonal.Type());

			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < size; i++)
			{
				int jMin = Integers.max(i, 0);
				int jMax = Integers.min(i + 2, size);
				
				for(int j = jMin; j < jMax; j++)
				{
					b.set(c.get(i, j), i, j);
				}
			}
		}
		
		return b;
	}

	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform bidiagonal factorization.
			decompose();
		}

		
		// Matrix dimensions.
		int uRows = u.Rows();
		int uCols = u.Columns();
		int mCols = mat.Columns();
		
		// If U is not in reduced form...
		if(uCols != mCols)
		{
			// Reduce the orthogonal U matrix.
			u = Matrices.resize(u, uRows, mCols);
		}


		// Assign the type of matrix Q.
		if(u.is(Square.Type()))
		{
			u.setOperator(Orthogonal.Type());
		}

		return u;
	}

	@Override
	public Matrix V()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform bidiagonal factorization.
			decompose();
		}


		// Assign the type of matrix V.
		if(v.is(Square.Type()))
		{
			v.setOperator(Orthogonal.Type());
		}
		
		return v;
	}
}