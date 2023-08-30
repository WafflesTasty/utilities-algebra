package waffles.utils.algebra.algorithms.eigen;

import waffles.utils.algebra.algorithms.solvers.SLVCrout;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.utilities.matrix.Rayleigh;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code EVRayleigh} algorithm approximates eigenvalues through Rayleigh's iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 */
public class EVRayleigh
{
	private static final int MAX_LOOPS = 1000;
	
	/**
	 * Computes the Rayleigh coefficient for a {@code Matrix}.
	 * This defines the best approximation of an eigenvalue.
	 * 
	 * @param m  a base matrix
	 * @param v  a possible eigenvector
	 * @return   a rayleigh coefficient
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static float rayleigh(Matrix m, Vector v)
	{
		return v.dot(m.times(v)) / v.normSqr();
	}


	private Matrix base;
	private SLVCrout slv;
	
	/**
	 * Creates a new {@code EVRayleigh}.
	 * It requires a matrix marked as square.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public EVRayleigh(Matrix m)
	{
		base = m;
	}

	/**
	 * Computes an eigenvector iteratively.
	 * 
	 * @param x  an initial vector guess
	 * @return  an eigenvector
	 * 
	 * 
	 * @see Vector
	 */
	public Vector EigenVector(Vector x)
	{
		// If the matrix is not square...
		if(!base.is(Square.Type()))
		{
			// Eigenvalue iterators cannot be applied.
			throw new Matrices.TypeError(Square.Type());
		}
		
		
		// Initial vector estimate.
		Vector v = x.normalize();
		Vector w = null;
		
		
		int iCount = 0;
		float err = Floats.MAX_VALUE;
		// Compute Rayleigh iteration.
		while(!Floats.isZero(err, 3))
		{
			try
			{
				// Solve the shifted linear system.
				float val = Rayleigh.coefficient(base, v);
				slv = new SLVCrout(shift(val));
				w = slv.solve(v);
			}
			catch(Matrices.InvertibleError e)
			{
				return v;
			}

			
			v = w.normalize();
			// Compute the error value.
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

	private Matrix shift(float val)
	{
		return base.minus(Matrices.identity(base.Rows()).times(val));
	}
}