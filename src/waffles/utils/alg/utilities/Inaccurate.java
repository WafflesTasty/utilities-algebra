package waffles.utils.alg.utilities;

import waffles.utils.tools.primitives.Floats;

/**
 * An {@code Inaccurate} object can be approximated.
 * </br> This interface can be used to compare numeric data to within an error margin.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <O>  an object type
 */
@FunctionalInterface
public interface Inaccurate<O>
{
	/**
	 * Checks if two objects are approximately equal.
	 * 
	 * @param obj   an object to compare
	 * @param ulps  an error margin
	 * @return  {@code true} if the objects are equal within the margin of error
	 */
	@Deprecated
	public default Boolean equals(O obj, int ulps)
	{
		return equals(obj, Floats.EPSILON * ulps);
	}
	
	/**
	 * Checks if two objects are approximately equal.
	 * 
	 * @param obj  a comparable object
	 * @param err  an error margin
	 * @return  {@code true} if approximately equal
	 */
	public abstract Boolean equals(O obj, float err);
}