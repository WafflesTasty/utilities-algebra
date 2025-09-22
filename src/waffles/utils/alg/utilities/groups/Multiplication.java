package waffles.utils.alg.utilities.groups;

/**
 * A {@code Multiplication} defines a multiplicative operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @param <M>  a scalar type
 */
@FunctionalInterface
public interface Multiplication<M>
{
	/**
	 * Multiplies an element to this {@code Multiplication}.
	 * 
	 * @param m  a multiplicative
	 * @return   a multiplication
	 */
	public abstract Multiplication<M> times(M m);
}