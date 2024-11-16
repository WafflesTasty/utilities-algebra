package waffles.utils.algebra.utilities.measures;

import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Normed} object can measure its own norm length.
 *
 * @author Waffles
 * @since Apr 28, 2018
 * @version 1.0
 */
public interface Normed
{	
	/**
	 * Returns the object's squared norm.
	 * 
	 * @return  a squared norm
	 */
	public abstract float normSqr();
	
	/**
	 * Returns the object's norm.
	 * 
	 * @return  a norm
	 */
	public default float norm()
	{
		return Floats.sqrt(normSqr());
	}
}