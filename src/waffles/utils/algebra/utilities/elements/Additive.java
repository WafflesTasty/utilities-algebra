package waffles.utils.algebra.utilities.elements;

/**
 * An {@code Additive} object defines an additive operation with objects of similar types.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 */
public interface Additive
{
	/**
	 * Adds an additive to this {@code Additive}.
	 * 
	 * @param a  an additive object
	 * @return   a sum additive
	 */
	public abstract Additive plus(Additive a);
}