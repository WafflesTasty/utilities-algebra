package zeno.util.algebra.algorithms;

import zeno.util.algebra.algorithms.solvers.SLVCrout;
import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.operators.Square;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code EigenValues} class provides algorithms to compute eigenvectors.
 * These iterative methods are limited to compute eigenvectors one at a time.
 *
 * @author Zeno
 * @since Jul 14, 2018
 * @version 1.0
 */
public class EigenValues
{
	/**
	 * Calculates the rayleigh coëfficient for a {@code Matrix}.
	 * The rayleigh coëfficient is the best approximation of an
	 * eigenvalue for any random vector.
	 * 
	 * @param m  a matrix to use
	 * @param v  a vector to use
	 * @return  the rayleigh coëfficient
	 * @see Matrix
	 * @see Vector
	 */
	public static float rayleigh(Matrix m, Vector v)
	{
		return v.dot(m.times(v)) / v.normSqr();
	}
	
	
	private static final int MAX_LOOPS = 100;
	private static final int ULPS = 3;
	
	
	private Matrix mat;
	private SLVCrout slv;
	private int iError;
	
	/**
	 * Creates a new {@code EigenValues}.
	 * This algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @see Matrix
	 */
	public EigenValues(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code SLVCholesky}.
	 * This algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * @see Matrix
	 */
	public EigenValues(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}

	
	/**
	 * Computes an eigenvector through Rayleigh iteration.
	 * 
	 * @param x  an initial vector guess
	 * @return  an eigenvector
	 * @see Vector
	 */
	public Vector rayleighIteration(Vector x)
	{
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// Eigenvalue iterators cannot be applied.
			throw new Tensors.DimensionError("Eigenvalue iteration requires a square matrix: ", mat);
		}
		
		
		// Initial vector estimate.
		Vector v = x.normalize();
		Vector w = null;
		
		
		int iCount = 0;
		// Perform Rayleigh iteration.
		float err = Floats.MAX_VALUE;
		while(!Floats.isZero(err, iError))
		{
			try
			{
				// Solve the shifted linear system.
				float quot = EigenValues.rayleigh(mat, v);
				slv = new SLVCrout(shift(quot));
				w = slv.solve(v);
			}
			catch(Matrices.InvertibleError e)
			{
				return v;
			}

			
			v = w.normalize();
			// Calculate the error value.
			err = 1f / w.normSqr();


			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_LOOPS)
			{
				break;
			}
		}
		
		return v;
	}

	/**
	 * Computes an eigenvector through inverse iteration.
	 * 
	 * @param val  an initial value guess
	 * @param x  an initial vector guess
	 * @return  an eigenvector
	 * @see Vector
	 */
	public Vector inverseIteration(Vector x, float val)
	{
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// Eigenvalue iterators cannot be applied.
			throw new Tensors.DimensionError("Eigenvalue iteration requires a square matrix: ", mat);
		}
		

		// Create the shifted linear system.
		slv = new SLVCrout(shift(val), iError);
		// Initial vector estimate.
		Vector w = x.normalize();
		Vector v = null;
		

		int iCount = 0;
		// Perform inverse iteration.
		float err = Floats.MAX_VALUE;
		while(!Floats.isZero(err, iError))
		{
			v = w.normalize(); w = slv.solve(v);
			// Calculate the error value.
			err = w.minus(v.times(v.dot(w))).norm();
			
			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_LOOPS)
			{
				break;
			}
		}
		
		return v;
	}
		
	/**
	 * Computes an eigenvector through power iteration.
	 * This method always returns the dominant eigenvector.
	 * 
	 * @param x  an initial vector guess
	 * @return  a dominant eigenvector
	 * @see Vector
	 */
	public Vector powerIteration(Vector x)
	{
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// Eigenvalue iterators cannot be applied.
			throw new Tensors.DimensionError("Eigenvalue iteration requires a square matrix: ", mat);
		}
		

		// Initial vector estimate.
		Vector w = x.normalize();
		Vector v = null;
		
		
		int iCount = 0;
		// Perform power iteration.
		float err = Floats.MAX_VALUE;
		while(!Floats.isZero(err, iError * iError))
		{
			v = w.normalize();
			w = (Vector) mat.times(v);
			// Calculate the error value.
			err = w.minus(v.times(v.dot(w))).norm();
			
			
			iCount++;
			// Prevent an infinite loop.
			if(iCount > MAX_LOOPS)
			{
				break;
			}
		}
		
		return v;
	}
	
	
	private Matrix shift(float val)
	{
		return mat.minus(Matrices.identity(mat.Rows()).times(val));
	}
}