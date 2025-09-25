package waffles.utils.alg.utilities.groups;

/**
 * A {@code Composition} can create compositions of objects.
 *
 * @author Waffles
 * @since 23 Sep 2025
 * @version 1.1
 *
 *
 * @param <C>  a composable type
 */
@FunctionalInterface
public interface Composition<C>
{
	/**
	 * Composes an object with the {@code Composition}.
	 * 
	 * @param c  a composable object
	 * @return   a composed object
	 */
	public abstract C compose(C c);
}