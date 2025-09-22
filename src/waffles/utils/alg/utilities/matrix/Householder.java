package waffles.utils.alg.utilities.matrix;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.vector.Vector;

/**
 * The {@code Householder} class generates Householder reflection matrices.
 *
 * @author Waffles
 * @since 26 Aug 2023
 * @version 1.0
 */
public class Householder
{
	/**
	 * Creates a Householder reflection {@code Matrix}.
	 * A Householder reflection is designed to project
	 * all components of a vector to zero, except
	 * for one.
	 * 
	 * @param v  a vector to reflect
	 * @param i  a component to keep
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Matrix reflect(Vector v, int i)
	{
		Vector x = v.copy();
		float iErr = (x.get(i) < 0 ? -1 : 1);
		x.set(x.get(i) + iErr * x.norm(), i);
		return Matrices.reflection(x);
	}
	
	
	private Householder()
	{
		// NOT APPLICABLE
	}
}