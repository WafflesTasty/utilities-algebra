package waffles.utils.algebra.utilities.measures;

import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Distanced} object can measure its distance to other objects of a similar type.
 *
 * @author Waffles
 * @since Apr 28, 2018
 * @version 1.0
 */
public interface Distanced
{	
	/**
	 * Returns the squared distance to another object.
	 * 
	 * @param obj  an object to measure to
	 * @return  a squared object distance
	 */
	public abstract float distSqr(Distanced obj);

	/**
	 * Returns the distance to another object.
	 * 
	 * @param obj  an object to measure to
	 * @return  an object distance
	 */
	public default float dist(Distanced obj)
	{
		return Floats.sqrt(distSqr(obj));
	}
}