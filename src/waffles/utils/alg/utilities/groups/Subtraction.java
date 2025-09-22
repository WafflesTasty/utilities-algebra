package waffles.utils.alg.utilities.groups;

/**
 * A {@code Subtraction} defines a subtractive operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @param <S>  a scalar type
 */
@FunctionalInterface
public interface Subtraction<S>
{
	/**
	 * Subtracts an element from this {@code Subtraction}.
	 * 
	 * @param s  a subtractive
	 * @return   a subtraction
	 */
	public abstract Subtraction<S> minus(S s);
}