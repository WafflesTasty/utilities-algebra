package waffles.utils.alg.linear.measure.vector.ops;

import waffles.utils.alg.linear.measure.vector.Vector;
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
	private Vector v1;
	
	/**
	 * Creates a new {@code VectorNorm}.
	 * 
	 * @param v1  a base vector
	 * 
	 * 
	 * @see Vector
	 */
	public VectorNorm(Vector v1)
	{
		this.v1 = v1;
	}
	

	@Override
	public Float result()
	{
		double n1 = 0f;
		for(int[] crd : v1.Data().NZKeys())
		{
			n1 += Floats.abs(v1.get(crd));
		}
		
		return (float) n1;
	}
	
	@Override
	public int cost()
	{
		return v1.Data().NZCount();
	}
}