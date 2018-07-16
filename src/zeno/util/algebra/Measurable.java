package zeno.util.algebra;

import zeno.util.tools.generic.properties.Approximate;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Measurable} interface defines an element in an inner product space.
 *
 * @author Zeno
 * @since Apr 28, 2018
 * @version 1.0
 * 
 * 
 * @param <M>  the type of the element
 * @see Approximate
 * @see Spatial
 */
public interface Measurable<M extends Measurable<M>> extends Approximate<M>, Spatial<M>
{
	/**
	 * Returns the {@code Measurable}'s inner product with another element.
	 * 
	 * @param element  an element to measure to
	 * @return  the element's inner product
	 */
	public abstract float dot(M element);
	
	
	/**
	 * Returns the normalized {@code Measurable}.
	 * 
	 * @return  a normed element
	 */
	public default M normalize()
	{
		float norm = norm();
		if(norm == 0 || norm == 1)
		{
			return copy();
		}
		
		return times(1f / norm);
	}
	
	/**
	 * Returns the element's angle to another {@code Measurable}.
	 * 
	 * @see <a href="https://people.eecs.berkeley.edu/~wkahan/Mindless.pdf">Prof. W. Kahan - Mindless Assessments of Roundoff in Floating-Point Computation</a>
	 * @param element  an element to measure to
	 * @return  the element angle
	 */
	public default float angle(M element)
	{
		M t1 = times(element.norm());
		M t2 = element.times(norm());
		
		float x = t1.plus( t2).norm();
		float y = t1.minus(t2).norm();
		return 2 * Floats.atan2(x, y);
	}
		
	/**
	 * Returns the element's square distance to a {@code Measurable}.
	 * 
	 * @param element  an element to measure to
	 * @return  the element distance
	 */
	@Override
	public default float distSqr(M element)
	{
		return minus(element).normSqr();
	}
	
	
	/**
	 * Returns the {@code Measurable}'s norm squared.
	 * 
	 * @return  the element's squared norm
	 */
	public default float normSqr()
	{
		return dot((M) this);
	}
	
	/**
	 * Returns the {@code Measurable}'s norm.
	 * 
	 * @return  the element's norm
	 */
	public default float norm()
	{
		return Floats.sqrt(normSqr());
	}
}