package waffles.utils.algebra.utilities.matrix;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.Vector;

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
	 * @param i  the component to keep
	 * @return  a householder reflection
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Matrix Matrix(Vector v, int i)
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