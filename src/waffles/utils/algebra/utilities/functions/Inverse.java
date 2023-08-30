package waffles.utils.algebra.utilities.functions;

import waffles.utils.algebra.Function;

/**
 * An {@code Inverse} is a function that maps the inverse of another function.
 *
 * @author Waffles
 * @since 18 Aug 2023
 * @version 1.0
 *
 *
 * @param <Y>  a function target type
 * @param <X>  a function source type
 * @see Function
 */
public class Inverse<Y,X> implements Function<Y,X>
{
	private Function<X,Y> base;
	
	/**
	 * Creates a new {@code Inverse}.
	 * 
	 * @param base  a base function
	 * 
	 * 
	 * @see Function
	 */
	public Inverse(Function<X,Y> base)
	{
		this.base = base;
	}

	
	@Override
	public Y unmap(X val)
	{
		return base.map(val);
	}

	@Override
	public X map(Y val)
	{
		return base.unmap(val);
	}
}