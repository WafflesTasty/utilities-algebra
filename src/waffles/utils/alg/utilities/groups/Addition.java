package waffles.utils.alg.utilities.groups;

/**
 * An {@code Addition} defines an additive operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @param <A>  a scalar type
 */
@FunctionalInterface
public interface Addition<A>
{
	/**
	 * Adds an element to this {@code Addition}.
	 * 
	 * @param a  an additive
	 * @return   a summation
	 */
	public abstract Addition<A> plus(A a);
}