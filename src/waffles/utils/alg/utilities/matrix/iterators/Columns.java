package waffles.utils.alg.utilities.matrix.iterators;

import java.util.Iterator;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.vector.Vector;

/**
 * The {@code Columns} class iterates over the columns of a {@code Matrix}.
 *
 * @author Waffles
 * @since 19 Sep 2025
 * @version 1.1
 *
 * 
 * @see Iterator
 * @see Vector
 */
public class Columns implements Iterator<Vector>
{
	private int c;
	private Matrix mat;
	
	/**
	 * Creates a new {@code Columns}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public Columns(Matrix m)
	{
		mat = m;
	}
	

	@Override
	public boolean hasNext()
	{
		return c < mat.Columns();
	}

	@Override
	public Vector next()
	{
		return mat.Column(c++);
	}
}