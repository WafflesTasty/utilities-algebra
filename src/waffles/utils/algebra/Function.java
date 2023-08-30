package waffles.utils.algebra;

/**
 * The {@code Function} interface defines a generic functional map.
 * Each X can be mapped to some Y, and each Y can be unmapped to some X.
 *
 * @author Waffles
 * @since Dec 18, 2018
 * @version 1.0
 * 
 *
 * @param <X>  a map source type
 * @param <Y>  a map target type
 */
public interface Function<X, Y>
{			
	/**
	 * Maps a target to its source through this {@code Function}.
	 * 
	 * @param val  a target element
	 * @return     a source element
	 */
	public abstract X unmap(Y val);
	
	/**
	 * Maps a source to its target through this {@code Function}.
	 * 
	 * @param val  a source element
	 * @return     a target element
	 */
	public abstract Y map(X val);
}