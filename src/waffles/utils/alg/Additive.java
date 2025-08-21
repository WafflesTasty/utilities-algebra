package waffles.utils.alg;

/**
 * An {@code Additive} defines an additive operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 */
@FunctionalInterface
public interface Additive
{
	/**
	 * Adds an additive to this {@code Additive}.
	 * 
	 * @param a  an additive
	 * @return   a summation
	 */
	public abstract Additive plus(Additive a);
}