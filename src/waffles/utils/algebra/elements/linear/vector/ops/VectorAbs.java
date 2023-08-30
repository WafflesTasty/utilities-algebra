package waffles.utils.algebra.elements.linear.vector.ops;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code VectorAbs} operation computes the absolute value of a {@code Vector}.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Vector
 */
public class VectorAbs implements Operation<Vector>
{
	private Vector v;
	
	/**
	 * Creates a new {@code VectorAbs}.
	 * 
	 * @param v  a base vector
	 * 
	 * 
	 * @see Vector
	 */
	public VectorAbs(Vector v)
	{
		this.v = v;
	}
	

	@Override
	public Vector result()
	{
		int size = v.Size();
		
		Vector result = v;
		if(v.isDestructible())
		{
			result = Vectors.create(size);
		}
		
		for(int i = 0; i < size; i++)
		{
			float val = Floats.abs(v.get(i));
			result.set(val, i);
		}

		return result;
	}
	
	@Override
	public int cost()
	{
		int c = v.Data().NZCount();
		
		if(v.isDestructible())
			return c;
		return 2 * c;
	}
}