package zeno.util.algebra;

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