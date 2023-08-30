package waffles.utils.algebra.algorithms.eigen;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code EVPower} algorithm approximates eigenvalues through power iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 */
public class EVPower
{
	private static final int MAX_LOOPS = 1000;

	private Matrix base;
	
	/**
	 * Creates a new {@code EVPower}.
	 * It requires a matrix marked as square.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public EVPower(Matrix m)
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
		Vector w = x.normalize();
		Vector v = null;
		
		
		int iCount = 0;
		// Perform power iteration.
		float err = Floats.MAX_VALUE;
		while(!Floats.isZero(err, 3 * 3))
		{
			// Iterate the vector.
			v = w.normalize();
			w = base.times(v);
			
			
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
}