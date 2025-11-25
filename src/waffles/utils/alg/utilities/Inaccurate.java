package waffles.utils.alg.utilities;

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
	 * @param obj  a comparable object
	 * @param err  an error margin
	 * @return  {@code true} if approximately equal
	 */
	public abstract Boolean equals(O obj, double err);
}