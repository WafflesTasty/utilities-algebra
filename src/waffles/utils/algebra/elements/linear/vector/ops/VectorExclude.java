package waffles.utils.algebra.elements.linear.vector.ops;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code VectorExclude} operation excludes an index from a {@code Vector}.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Vector
 */
public class VectorExclude implements Operation<Vector>
{
	private int index;
	private Vector vec;
	
	/**
	 * Creates a new {@code VectorExclude}.
	 * 
	 * @param v  a base vector
	 * @param i  a vector index
	 * 
	 * 
	 * @see Vector
	 */
	public VectorExclude(Vector v, int i)
	{
		index = i;
		vec = v;
	}
	

	@Override
	public Vector result()
	{
		int size = vec.Size()-1;
		
		Vector w = Vectors.resize(vec, size);
		for(int i = index; i < size; i++)
		{
			w.set(vec.get(i+1), i);
		}
		
		return w;
	}
	
	@Override
	public int cost()
	{
		int size = vec.Size();
		return 2 * size - index;
	}
}