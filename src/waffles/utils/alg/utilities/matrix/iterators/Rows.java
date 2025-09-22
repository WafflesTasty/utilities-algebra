package waffles.utils.alg.utilities.matrix.iterators;

import java.util.Iterator;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.vector.Vector;

/**
 * The {@code Rows} class iterates over the rows of a {@code Matrix}.
 *
 * @author Waffles
 * @since 19 Sep 2025
 * @version 1.1
 *
 * 
 * @see Iterator
 * @see Vector
 */
public class Rows implements Iterator<Vector>
{
	private int r;
	private Matrix mat;
	
	/**
	 * Creates a new {@code Rows}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public Rows(Matrix m)
	{
		mat = m;
	}
	

	@Override
	public boolean hasNext()
	{
		return r < mat.Rows();
	}

	@Override
	public Vector next()
	{
		return mat.Row(r++);
	}
}