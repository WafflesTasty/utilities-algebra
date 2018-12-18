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
	 * Unmaps a target to its source {@code Function}.
	 * 
	 * @param val  a target element
	 * @return     a source element
	 */
	public default X unmap(Y val)
	{
		return null;
	}
	
	/**
	 * Maps a source to its target {@code Function}.
	 * 
	 * @param val  a source element
	 * @return     a target element
	 */
	public abstract Y map(X val);
}