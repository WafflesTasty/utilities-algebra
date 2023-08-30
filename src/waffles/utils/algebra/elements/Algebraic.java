package waffles.utils.algebra.elements;

/**
 * An {@code Algebraic} object is abelian, and also defines a product operation.
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
	 * @param a  an algebraic object
	 * @return   a product algebraic
	 */
	public abstract Algebraic times(Algebraic a);
}