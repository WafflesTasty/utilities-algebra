package waffles.utils.algebra.algorithms.rankreveal;

import waffles.utils.algebra.algorithms.LeastSquares;
import waffles.utils.algebra.algorithms.LinearSolver;
import waffles.utils.algebra.algorithms.LinearSpace;
import waffles.utils.algebra.algorithms.RankReveal;
import waffles.utils.algebra.algorithms.factoring.FCTSingularVals;
import waffles.utils.algebra.algorithms.factoring.hholder.FCTBidiagonalHH;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.utilities.matrix.Givens;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRSVD} algorithm solves least squares linear systems using {@code SVD factorization}.
 * This method factorizes a matrix {@code M = UEV*} where U, V are orthogonal matrices,
 * and E is a diagonal matrix of singular values.
 * 
 * This algorithm is an implementation of the Demmel & Kahan zero-shift downward sweep.
 * It is a simplified version from the proposal in the paper, using only one algorithm and convergence type.
 * During every iteration Givens iterations are applied to the subdiagonal from top to bottom.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see <a href="https://epubs.siam.org/doi/abs/10.1137/0911052">James Demmel & William Kahan, "Accurate singular values of bidiagonal matrices."</a>
 * @see FCTSingularVals
 * @see LeastSquares
 * @see LinearSolver
 * @see LinearSpace
 * @see RankReveal
 */
public class RRSVD implements FCTSingularVals, LinearSolver, LinearSpace, LeastSquares, RankReveal
{
	private static final int MAX_SWEEPS = 1000;


	private Float det;
	private Integer rank;
	private Matrix e, u, v;
	private Matrix mat, inv;
	
	private Matrix spCols, spRows;
	private Matrix cmCols, cmRows;

	private FCTBidiagonalHH fctHH;
	private Matrix base;
	
	/**
	 * Creates a new {@code RRSVD}.
	 * This algorithm requires a matrix marked
	 * as tall. Otherwise, canApprox(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRSVD(Matrix m)
	{
		base = m;
	}

	/**
	 * Approximates an inverted linear system in {@code RRSVD}.
	 * This solves the min(xA - b) problem instead.
	 * 
	 * @param b  a right-hand side matrix
	 * @return   a matrix of unknowns
	 */
	public <M extends Matrix> M preApprox(M b)
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		

		// Solve through substitution.
		Matrix x = V().transpose();
		x = F().times(x.times(b));
		return (M) U().times(x);
	}
	
			
	private void decompose()
	{
		// If the matrix is not tall...
		if(!base.is(Tall.Type()))
		{
			// An SVD factorization cannot be computed.
			throw new Matrices.TypeError(Tall.Type());
		}
		
		
		// Decompose the coefficient matrix with bidiagonal factorization.
		fctHH = new FCTBidiagonalHH(base);
		u = fctHH.U(); v = fctHH.V();
		mat = fctHH.B();
		
		// If the matrix is square...
		if(Square.Type().allows(base, 0))
		{
			// Compute the determinant.
			det = fctHH.determinant();
		}
		

		givens();
		sign();


		// Assign the type of matrix U.
		if(Square.Type().allows(u, 0))
		{
			u.setOperator(Orthogonal.Type());
		}
		
		// Assign the type of matrix V.
		if(Square.Type().allows(v, 0))
		{
			v.setOperator(Orthogonal.Type());
		}
	}

	private void givens()
	{
		// Define dimensions.
		int cols = mat.Columns();
		
		int iCount = 0;
		// While the coefficient matrix is not diagonalized...
		while(!Diagonal.Type().allows(mat, 3))
		{
			float lErr = 0f, rErr = 0f;
			// For every row/column in the coefficient matrix...
			for(int i = 0; i < cols - 1; i++)
			{
				// Compute the error margin of the right offdiagonal.
				float rVal = mat.get(i, i+1);
				
				if(i != 0)
					rErr = Floats.abs(mat.get(i, i)) * rErr / (rErr + rVal);
				else
					rErr = Floats.abs(mat.get(i, i));
				
				
				// If it is close enough to zero...
				if(Floats.isZero(rVal / rErr, 3))
					// Set it to zero entirely.
					mat.set(0f, i, i + 1);
				else
				{
					// Otherwise, create the right Givens matrix.
					Matrix rg = Givens.Right(mat, i, i+1);
					// Right rotate the coefficient matrix.
					mat = mat.times(rg);
					v = v.times(rg);
				}
				
				
				// Compute the error margin for the left offdiagonal.
				float lVal = mat.get(i+1, i);
				
				if(i != 0)
					lErr = Floats.abs(mat.get(i, i)) * lErr / (lErr + lVal);
				else
					lErr = Floats.abs(mat.get(i, i));
				
				
				// If it is close enough to zero...
				if(Floats.isZero(lVal / lErr, 3))
					// Set it to zero entirely.
					mat.set(0f, i + 1, i);
				else
				{
					// Otherwise, create the left Givens matrix.
					Matrix lg = Givens.Left(mat, i+1, i);
					// Left rotate the coefficient matrix.
					mat = lg.times(mat);
					u = u.times(lg.transpose());
				}
			}
			
						
			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_SWEEPS)
			{
				break;
			}
		}
	}
	
	private void sign()
	{
		// Define dimensions.
		int size = mat.Columns();
		
		// The singular values have to be positive.
		Matrix mSign = Matrices.identity(size);
		mSign.setOperator(Diagonal.Type());
		for(int i = 0; i < size; i++)
		{
			if(mat.get(i, i) < 0)
			{
				mSign.set(-1f, i, i);
			}
		}
		
		mat = mat.times(mSign);
		v = v.times(mSign);
	}
	
	private Matrix F()
	{
		// Define dimensions.
		int rows = E().Rows();
		int cols = E().Columns();		
		
		Matrix f = E().transpose();
		
		int dim = Integers.min(rows, cols);
		for(int i = 0; i < dim; i++)
		{
			f.set(1f / e.get(i, i), i, i);
		}
		
		return f;
	}
	
		
	@Override
	public <M extends Matrix> boolean canApprox(M b)
	{
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
	public <M extends Matrix> boolean canSolve(M b)
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// Compute the column complement space.
		cmCols = ColComplement();
		
		
		// Define dimensions.
		int bRows = b.Rows();
		int bCols = b.Columns();
		int nCols = cmCols.Columns();
		int nRows = cmCols.Rows();
		
		
		// Tolerance depends on the coefficient matrix dimensions.
		int eTol = 3 * Integers.max(bRows, bCols) * Integers.max(nRows, nCols);
		// The system is solvable iff b is orthogonal to the null transpose.
		float norm = b.transpose().times(cmCols).norm();
		return Floats.isZero(norm, eTol);
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
		Matrix x = U().transpose();
		x = F().times(x.times(b));
		return (M) V().times(x);
	}
	
	@Override
	public <M extends Matrix> M solve(M b)
	{
		return approx(b);
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
		

		// The coefficient matrix must be full rank.	
		return base.Columns() == rank();
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
	public float determinant()
	{
		if(canInvert())
			return det;
		return 0f;
	}
		
	@Override
	public Matrix inverse()
	{
		if(canInvert())
		{
			return pseudoinverse();
		}

		return null;
	}
	
	
	@Override
	public Matrix ColSpace()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If the column space has not been computed yet...
		if(spCols == null)
		{
			// Define dimensions.
			int rows = U().Rows();			
			
			// Create the column space matrix.
			spCols = Matrices.create(rows, rank());
			for(int r = 0; r < rows; r++)
			{
				for(int c = 0; c < rank(); c++)
				{
					// It consists of the first r columns of U.
					spCols.set(U().get(r, c), r, c);
				}
			}
			
			// Assign the type of the column space.
			if(Square.Type().allows(spCols, 0))
			{
				spCols.setOperator(Orthogonal.Type());
			}
		}
		
		return spCols;
	}
	
	@Override
	public Matrix RowSpace()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If the row space has not been computed yet...
		if(spRows == null)
		{
			// Define dimensions.
			int rows = V().Rows();			
			
			// Create the row space matrix.
			spRows = Matrices.create(rows, rank());
			for(int r = 0; r < rows; r++)
			{
				for(int c = 0; c < rank(); c++)
				{
					// It consists of the first r columns of V.
					spRows.set(V().get(r, c), r, c);
				}
			}
			
			// Assign the type of the row space.
			if(Square.Type().allows(spRows, 0))
			{
				spRows.setOperator(Orthogonal.Type());
			}
		}
		
		return spRows;
	}

	@Override
	public Matrix ColComplement()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If the null space has not been computed yet...
		if(cmCols == null)
		{
			// Define dimensions.
			int rows = U().Rows();
			int cols = U().Columns();
			
			// Create the column complement space.
			cmCols = Matrices.create(rows, cols - rank());
			for(int r = 0; r < rows; r++)
			{
				for(int c = rank(); c < cols; c++)
				{
					// It consists of the last c - r columns of U.
					cmCols.set(U().get(r, c), r, c - rank());
				}
			}
			
			// Assign the type of the column complement.
			if(Square.Type().allows(cmCols, 0))
			{
				cmCols.setOperator(Orthogonal.Type());
			}
		}
		
		return cmCols;
	}
	
	@Override
	public Matrix RowComplement()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// If the null space has not been computed yet...
		if(cmRows == null)
		{
			// Define dimensions.
			int rows = V().Rows();
			int cols = V().Columns();
			
			// Create the row complement space.
			cmRows = Matrices.create(rows, cols - rank());
			for(int r = 0; r < rows; r++)
			{
				for(int c = rank(); c < cols; c++)
				{
					// It consists of the last c - r columns of V.
					cmRows.set(V().get(r, c), r, c - rank());
				}
			}
			
			// Assign the type of the row complement.
			if(Square.Type().allows(cmRows, 0))
			{
				cmRows.setOperator(Orthogonal.Type());
			}
		}
		
		return cmRows;
	}

	@Override
	public Matrix E()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}

		
		// If matrix E has not been computed yet...
		if(e == null)
		{
			// Define dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();
			
			// Create the diagonal matrix E.
			if(Tall.Type().allows(base, 0))
				e = Matrices.create(rows, cols);
			else
				e = Matrices.create(cols, rows);

			// Copy from the decomposed matrix.
			for(int i = 0; i < cols; i++)
			{
				float val = Floats.abs(mat.get(i, i));
				e.set(val, i, i);
			}	
		}
		
		return e;
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
		
		
		// If the coefficient matrix is tall...
		if(Tall.Type().allows(base, 0))
			// Return the matrix U.
			return u;
		// Return the matrix V.
		return v;
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
		
		
		// If the coefficient matrix is tall...
		if(Tall.Type().allows(base, 0))
			// Return the matrix V.
			return v;
		// Return the matrix U.
		return u;
	}
	
	@Override
	public int rank()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		
		// If rank has not been computed yet...
		if(rank == null)
		{
			rank = 0;
			
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();
			
			
			// Compute the singular value tolerance.
			float eTol = Integers.max(rows, cols) * Floats.nextEps(condition());
			// Loop over all singular values to discover rank.
			TensorData data = SingularValues().Data();
			for(float val : data.Values())
			{
				if(Floats.isZero(val, (int) eTol))
				{
					break;
				}
				
				rank++;
			}
		}
		
		return rank;
	}
}