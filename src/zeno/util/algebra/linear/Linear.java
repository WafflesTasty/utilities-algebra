package zeno.util.algebra.linear;

import zeno.util.tools.patterns.manipulators.Copyable;

/**
 * The {@code Linear} interface defines an element in a linear space.
 *
 * @author Zeno
 * @since Apr 28, 2018
 * @version 1.0
 * 
 * 
 * @param <L>  the type of the element
 * @see Copyable
 */
public interface Linear<L extends Linear<L>> extends Copyable<L>
{	
	/**
	 * Returns the {@code Linear}'s sum with an element.
	 * 
	 * @param element  an element to add
	 * @return  the sum element
	 */
	public abstract L plus(L element);

	/**
	 * Returns the {@code Linear}'s product with a scalar value.
	 * 
	 * @param val  a value to multiply
	 * @return  the scalar product
	 */
	public abstract L times(float val);
	
	
	/**
	 * Returns the {@code Linear}'s difference with an element.
	 * 
	 * @param element  an element to subtract
	 * @return  the difference element
	 */
	public default L minus(L element)
	{
		return plus(element.times(-1));
	}
}