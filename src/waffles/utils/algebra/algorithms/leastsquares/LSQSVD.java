package waffles.utils.algebra.algorithms.leastsquares;

import waffles.utils.algebra.algorithms.Determinant;
import waffles.utils.algebra.algorithms.LeastSquares;
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
 * The {@code LSQSVD} algorithm solves least squares linear systems using {@code SVD factorization}.
 * This method factorizes a matrix {@code M = UEV*} where U is a matrix with orthonormal columns,
 * E is a diagonal matrix of singular values, and V is an orthogonal matrix.
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
 * @see Determinant
 * @see RankReveal
 */
public class LSQSVD implements FCTSingularVals, Determinant, LeastSquares, RankReveal
{
	private static final int MAX_SWEEPS = 1000;
	
	
	private Float det;
	private Integer rank;
	private Matrix e, u, v;
	private Matrix mat, inv;
	
	
	private FCTBidiagonalHH fctHH;
	private Matrix base;
	
	/**
	 * Creates a new {@code LSQSVD}.
	 * This algorithm requires a matrix marked
	 * as tall. Otherwise, canApprox(b)
	 * will throw an exception.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LSQSVD(Matrix m)
	{
		base = m;
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
		fctHH = new FCTBidiagonalHH(base, true);
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
		// If an inverse has not been computed yet...
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
			int cols = mat.Columns();
			
			// Create the diagonal matrix E.
			e = Matrices.create(cols, cols);
			// Assign the type of matrix E.
			e.setOperator(Diagonal.Type());
			
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