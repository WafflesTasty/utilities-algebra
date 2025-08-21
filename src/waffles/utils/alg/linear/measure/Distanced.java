package waffles.utils.alg.linear.measure;

import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Distanced} defines a distance operation.
 *
 * @author Waffles
 * @since Apr 28, 2018
 * @version 1.0
 */
@FunctionalInterface
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