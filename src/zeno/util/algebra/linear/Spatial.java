package zeno.util.algebra.linear;

import zeno.util.algebra.Linear;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Spatial} interface defines an element in a metric space.
 *
 * @author Zeno
 * @since Apr 28, 2018
 * @version 1.0
 * 
 *
 * @param <S>  the type of the element
 * @see Linear
 */
public interface Spatial<S extends Spatial<S>> extends Linear<S>
{	
	/**
	 * Returns the {@code Spatials}'s square distance to another element.
	 * 
	 * @param element  an element to measure to
	 * @return  the element distance
	 */
	public abstract float distSqr(S element);

	/**
	 * Returns the {@code Spatials}'s distance to another element.
	 * 
	 * @param element  an element to measure to
	 * @return  the element distance
	 */
	public default float dist(S element)
	{
		return Floats.sqrt(distSqr(element));
	}
}