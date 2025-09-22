package waffles.utils.alg;

/**
 * A {@code Function} defines a generic functional map.
 * It maps objects of some type X to some type Y.
 *
 * @author Waffles
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
	 * Maps a target to its source through this {@code Function}.
	 * 
	 * @param val  a target
	 * @return     a source
	 */
	public abstract X unmap(Y val);
	
	/**
	 * Maps a source to its target through this {@code Function}.
	 * 
	 * @param val  a source
	 * @return     a target
	 */
	public abstract Y map(X val);
}