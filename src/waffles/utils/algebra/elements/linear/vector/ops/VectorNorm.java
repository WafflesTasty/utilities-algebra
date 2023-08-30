package waffles.utils.algebra.elements.linear.vector.ops;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code VectorNorm} operation computes the 1-norm of a {@code Vector}.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 */
public class VectorNorm implements Operation<Float>
{
	private Vector v;
	
	/**
	 * Creates a new {@code VectorNorm}.
	 * 
	 * @param v  a base vector
	 * 
	 * 
	 * @see Vector
	 */
	public VectorNorm(Vector v)
	{
		this.v = v;
	}
	

	@Override
	public Float result()
	{
		float result = 0f;
		for(int[] coord : v.Data().NZIndex())
		{
			result += Floats.abs(v.get(coord));
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		return v.Data().NZCount();
	}
}