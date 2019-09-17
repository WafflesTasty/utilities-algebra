package zeno.util.algebra;

import zeno.util.tools.helper.Array;

/**
 * The {@code Function} interface defines a functional map.
 *
 * @author Zeno
 * @since Dec 18, 2018
 * @version 1.0
 * 
 *
 * @param <X>  a source type
 * @param <Y>  a target type
 */
public interface Function<X, Y>
{	
	/**
	 * Returns the inverse of a {@code Function}.
	 * 
	 * @param func  a function to invert
	 * @return  an inverted function
	 */
	public static <X, Y> Function<X, Y> inverse(Function<Y, X> func)
	{
		return new Function<>()
		{
			@Override
			public X unmap(Y val)
			{
				return func.map(val);
			}

			@Override
			public Y map(X val)
			{
				return func.unmap(val);
			}
		};
	}
	
	/**
	 * The {@code Composite} interface composes multiple {@code Functions}.
	 *
	 * @author Zeno
	 * @since Feb 10, 2019
	 * @version 1.0
	 * 
	 *
	 * @param <X>  a source type
	 * @param <Y>  a target type
	 * 
	 * 
	 * @see Function
	 */
	@FunctionalInterface
	public static interface Composite<X, Y> extends Function<X, Y>
	{
		/**
		 * Returns the functions in the {@code Composite}.
		 * 
		 * @return  a set of functions
		 * 
		 * 
		 * @see Function
		 */
		public abstract Function<?, ?>[] Functions();
		
		
		@Override
		public default X unmap(Y val)
		{
			Object x = val;
			for(Function<?, ?> f : Array.reverse.of(Functions()))
			{
				x = Function.unmap(f, x);
			}
			
			return (X) x;
		}
		
		@Override
		public default Y map(X val)
		{
			Object y = val;
			for(Function<?, ?> f : Functions())
			{
				y = Function.map(f, y);
			}
			
			return (Y) y;
		}
	}
		
	
	/**
	 * Maps a target to its source {@code Function} through static access.
	 * This function is not type-safe, allowing dynamic casting to be used.
	 * 
	 * @param f  a function to apply
	 * @param val  a target element
	 * @return  a source element
	 */
	public static <X, Y> X unmap(Function<X, Y> f, Object val)
	{
		return f.unmap((Y) val);
	}
	
	/**
	 * Maps a source to its target {@code Function} through static access.
	 * This function is not type-safe, allowing dynamic casting to be used.
	 * 
	 * @param f  a function to apply
	 * @param val  a source element
	 * @return  a target element
	 */
	public static <X, Y> Y map(Function<X, Y> f, Object val)
	{
		return f.map((X) val);
	}

	
	/**
	 * Maps a target to its source {@code Function}.
	 * 
	 * @param val  a target element
	 * @return     a source element
	 */
	public abstract X unmap(Y val);
	
	/**
	 * Maps a source to its target {@code Function}.
	 * 
	 * @param val  a source element
	 * @return     a target element
	 */
	public abstract Y map(X val);
}