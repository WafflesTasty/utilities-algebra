package waffles.utils.algebra.algorithms.eigen;

import waffles.utils.algebra.algorithms.solvers.SLVCrout;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code EVInverse} algorithm approximates eigenvalues through inverse iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 */
public class EVInverse
{
	private static final int MAX_LOOPS = 1000;

	
	private Matrix base;
	private SLVCrout slv;
	
	/**
	 * Creates a new {@code EVInverse}.
	 * It requires a matrix marked as square.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public EVInverse(Matrix m)
	{
		base = m;
	}

	/**
	 * Computes an eigenvector iteratively.
	 * 
	 * @param x    an initial vector guess
	 * @param val  an initial value guess
	 * @return  an eigenvector
	 * 
	 * 
	 * @see Vector
	 */
	public Vector EigenVector(Vector x, float val)
	{
		// If the matrix is not square...
		if(!base.is(Square.Type()))
		{
			// Eigenvalue iterators cannot be applied.
			throw new Matrices.TypeError(Square.Type());
		}
		

		// Create the shifted linear system.
		slv = new SLVCrout(shift(val));
		// Initial vector estimate.
		Vector w = x.normalize();
		Vector v = null;
		

		int iCount = 0;
		// Perform inverse iteration.
		float err = Floats.MAX_VALUE;
		while(!Floats.isZero(err, 3))
		{
			// Iterate the vector.
			v = w.normalize();
			w = slv.solve(v);
			
			
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
		return base.minus(Matrices.identity(base.Rows()).times(val));
	}
}