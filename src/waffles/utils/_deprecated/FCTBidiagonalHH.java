package waffles.utils.algebra.algorithms.factoring.hholder;

import waffles.utils.algebra.algorithms.Determinant;
import waffles.utils.algebra.algorithms.factoring.FCTBidiagonal;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperBidiagonal;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.utilities.matrix.Householder;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code FCTBidiagonalHH} algorithm performs a {@code Bidiagonal} factorization.
 * This algorithm applies {@code Householder} transformations to induce zeroes in a matrix.
 * This is known as the Golub-Kahan Bidiagonalization.
 * 
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTBidiagonal
 * @see Determinant
 */
public class FCTBidiagonalHH implements Determinant, FCTBidiagonal
{
	private Float det;
	private Matrix b, u, v;
	private Matrix mat;
	
	private boolean isReduced;
	private Matrix base;
	
	/**
	 * Creates a new {@code FCTBidiagonalHH}.
	 * This algorithm can either reduce U, which
	 * makes B square, or leave it at full rank.
	 * 
	 * @param m  a coefficient matrix
	 * @param canReduce  a matrix reduction
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTBidiagonalHH(Matrix m, boolean canReduce)
	{
		isReduced = canReduce;
		base = m;
	}
	
	/**
	 * Creates a new {@code FCTBidiagonalHH}.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTBidiagonalHH(Matrix m)
	{
		this(m, false);
	}

	
	private void decompose()
	{	
		// If a reduced form is requested without a tall matrix...
		if(isReduced && !base.is(Tall.Type()))
		{
			// A bidiagional factorization cannot be computed.
			throw new Matrices.TypeError(Tall.Type());
		}
		
		
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();	
		
		// Copy the coefficient matrix.
		mat = base.copy();
		// Create the orthogonal matrices U, V.
		u = Matrices.identity(rows);
		v = Matrices.identity(cols);
		// Assign the type of matrices U, V.
		u.setOperator(Identity.Type());
		v.setOperator(Identity.Type());
		
		
		// If the coefficient matrix is not upper bidiagonal...
		if(!base.is(UpperBidiagonal.Type()))
		{
			// Decompose with Householder's method.
			houseHolder();
		}


		// If the coefficient matrix is square...
		if(base.is(Square.Type()))
		{
			// Compute the determinant.
			Vector d = Diagonal.of(mat);
			for(float val : d.Data().Values())
			{
				det *= val;
			}
		}
	}
	
	private void houseHolder()
	{	
		// Define dimensions.
		int rows = base.Rows();
		int cols = base.Columns();
				
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
				Vector uk = mat.Column(k);
				for(int i = 0; i < k; i++)
				{
					uk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(!Floats.isZero(uk.normSqr(), rows))
				{
					// Create the column reflection matrix.
					Matrix uhh = Householder.Matrix(uk, k);
					// Column reflect the target matrix.
					mat = uhh.times(mat);
					u = u.times(uhh);
					det = -det;
				}
			}
			
			// If the superdiagonal is not finished...
			if(k < cols - 2)
			{
				// Create the row reflection normal.
				Vector vk = mat.Row(k);
				for(int i = 0; i <= k; i++)
				{
					vk.set(0f, i);
				}
				
				// If the reflection is feasible...
				if(!Floats.isZero(vk.normSqr(), cols))
				{
					// Create the row reflection matrix.
					Matrix vhh = Householder.Matrix(vk, k + 1);
					// Row reflect the target matrix.
					mat = mat.times(vhh);
					v = v.times(vhh);
					det = -det;
				}
			}
		}
	}
		
	
	@Override
	public boolean canInvert()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If the coefficient matrix is not square...
		if(!base.is(Square.Type()))
		{
			// It cannot define an inverse matrix.
			throw new Matrices.TypeError(Square.Type());
		}

		return !Floats.isZero(det, 3);
	}

	@Override
	public float determinant()
	{
		if(canInvert())
			return det;
		return 0f;
	}


	@Override
	public Matrix B()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If B has not been computed yet...
		if(b == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
			// If a reduced form is requested...
			if(isReduced)
			{
				// Reduce the matrix to square size.
				int size = Integers.min(rows, cols);
				// Create the bidiagonal matrix B.
				b = Matrices.create(size, size);
				// Assign the type of matrix B.
				b.setOperator(UpperBidiagonal.Type());
				
				// Copy from the decomposed matrix.
				for(int i = 0; i < size; i++)
				{
					int jMin = Integers.max(i, 0);
					int jMax = Integers.min(i + 2, size);
					
					for(int j = jMin; j < jMax; j++)
					{
						b.set(mat.get(i, j), i, j);
					}
				}
			}
			else
			{
				// Create the bidiagonal matrix B.
				b = Matrices.create(rows, cols);
				
				// Copy from the decomposed matrix.
				for(int i = 0; i < rows; i++)
				{
					int jMin = Integers.max(i, 0);
					int jMax = Integers.min(i + 2, cols);
					
					for(int j = jMin; j < jMax; j++)
					{
						b.set(mat.get(i, j), i, j);
					}
				}
			}
		}
		
		return b;
	}

	@Override
	public Matrix U()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}

		// If a reduced form is requested...
		if(isReduced)
		{
			// Define dimensions.
			int uRows = u.Rows();
			int uCols = u.Columns();
			int mCols = base.Columns();
					
			// If U is not in reduced form...
			if(uCols != mCols)
			{
				// Reduce the orthogonal U matrix.
				u = Matrices.resize(u, uRows, mCols);
			}
		}
		
		// Assign the type of matrix U.
		if(Square.Type().allows(u, 0))
		{
			u.setOperator(Orthogonal.Type());
		}

		return u;
	}

	@Override
	public Matrix V()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// Assign the type of matrix V.
		if(Square.Type().allows(v, 0))
		{
			v.setOperator(Orthogonal.Type());
		}
		
		return v;
	}
}