package waffles.utils.algebra.elements;

/**
 * A {@code Linear} object defines an element in a linear vector space.
 * It defines a scalar multiplication and vector addition/subtraction.
 *
 * @author Waffles
 * @since Apr 28, 2018
 * @version 1.0
 * 
 * 
 * @param <S>  a scalar object type
 */
public interface Linear<S> extends Abelian
{		
	/**
	 * Returns a scalar product with the {@code Linear}.
	 * 
	 * @param val  a scalar value
	 * @return  a scalar product
	 */
	public abstract Linear<S> times(S val);
}