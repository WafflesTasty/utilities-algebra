package waffles.utils.alg;

/**
 * An {@code Algebraic} is abelian and defines a product operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Abelian
 */
public interface Algebraic extends Abelian
{
	/**
	 * Multiplies an algebraic with this {@code Algebraic}.
	 * 
	 * @param a  an algebraic
	 * @return   a multiplication
	 */
	public abstract Algebraic times(Algebraic a);
}