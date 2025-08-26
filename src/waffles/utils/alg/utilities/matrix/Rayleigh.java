package waffles.utils.alg.utilities.matrix;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.vector.Vector;

/**
 * The {@code Rayleigh} class generates Rayleigh coefficients.
 *
 * @author Waffles
 * @since 26 Aug 2023
 * @version 1.0
 */
public final class Rayleigh
{
	/**
	 * Computes a Rayleigh coefficient for a {@code Matrix}.
	 * This defines the best approximation of an eigenvalue.
	 * 
	 * @param m  a base matrix
	 * @param v  an eigen vector
	 * @return   an eigen value
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