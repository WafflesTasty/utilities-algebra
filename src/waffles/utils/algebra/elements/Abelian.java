package waffles.utils.algebra.elements;

import waffles.utils.algebra.utilities.elements.Additive;

/**
 * An {@code Abelian} object is additive, and also defines an inverse subtraction operation.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Additive
 */
public interface Abelian extends Additive
{
	/**
	 * Substracts an abelian from this {@code Abelian}.
	 * 
	 * @param a  an abelian object
	 * @return   a subtracted abelian
	 */
	public abstract Abelian minus(Abelian a);
}