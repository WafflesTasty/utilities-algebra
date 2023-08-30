package waffles.utils.algebra.utilities.matrix;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.Vector;

/**
 * The {@code Rayleigh} class generates Rayleigh coefficients.
 *
 * @author Waffles
 * @since 26 Aug 2023
 * @version 1.0
 */
public class Rayleigh
{
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
	public static float coefficient(Matrix m, Vector v)
	{
		return v.dot(m.times(v)) / v.normSqr();
	}
	
	
	private Rayleigh()
	{
		// NOT APPLICABLE
	}
}