package waffles.utils.alg;

/**
 * An {@code Abelian} is {@code Additive} and defines an inverse subtraction.
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
	 * @param a  an abelian
	 * @return   a subtraction
	 */
	public abstract Abelian minus(Abelian a);
}