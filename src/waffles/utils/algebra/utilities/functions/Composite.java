package waffles.utils.algebra.utilities.functions;

import waffles.utils.algebra.Function;
import waffles.utils.algebra.utilities.Functions;
import waffles.utils.tools.primitives.Array;

/**
 * A {@code Composite} function maps a sequence of functions one after another.
 * 
 *
 * @author Waffles
 * @since 18 Aug 2023
 * @version 1.0
 *
 *
 * @param <X>  a function source type
 * @param <Y>  a function target type
 * @see Function
 */
public class Composite<X,Y> implements Function<X,Y>
{
	private Function<?,?>[] base;
	
	/**
	 * Creates a new {@code Composite}.
	 * 
	 * @param base  a set of functions
	 * 
	 * 
	 * @see Function
	 */
	public Composite(Function<?,?>... base)
	{
		this.base = base;
	}

	
	@Override
	public X unmap(Y val)
	{
		Object x = val;
		for(Function<?, ?> f : Array.reverse.of(base))
		{
			x = Functions.unmap(f, x);
		}
		
		return (X) x;
	}

	@Override
	public Y map(X val)
	{
		Object y = val;
		for(Function<?, ?> f : base)
		{
			y = Functions.map(f, y);
		}
		
		return (Y) y;
	}
}