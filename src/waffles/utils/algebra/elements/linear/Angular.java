package waffles.utils.algebra.elements.linear;

import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.Linear;
import waffles.utils.algebra.utilities.Distanced;
import waffles.utils.algebra.utilities.Normed;
import waffles.utils.tools.primitives.Floats;

/**
 * An {@code Angular} object exists in an inner product space and can compute angles with objects of similar type.
 *
 * @author Waffles
 * @since 18 Aug 2023
 * @version 1.1
 *
 *
 * @see Distanced
 * @see Linear
 * @see Normed
 */
public interface Angular extends Linear<Float>, Distanced, Normed
{	
	/**
	 * Returns the dot product with an object of a similar type.
	 * 
	 * @param a  an angular object
	 * @return   a dot product
	 */
	public abstract float dot(Angular a);

	/**
	 * Returns the angle with an object of a similar type.
	 * 
	 * @param a  an angular object
	 * @return   an object angle
	 * 
	 * 
	 * @see <a href="https://people.eecs.berkeley.edu/~wkahan/Mindless.pdf">Prof. W. Kahan - Mindless Assessments of Roundoff in Floating-Point Computation</a>
	 */
	public default float angle(Angular a)
	{
		Linear<Float> t1 = times(a.norm());
		Linear<Float> t2 = a.times(norm());
		
		float x = ((Normed) t1.plus( t2)).norm();
		float y = ((Normed) t1.minus(t2)).norm();
		
		return 2 * Floats.atan2(x, y);
	}
	
	/**
	 * Returns the cosine with an object of a similar type.
	 * 
	 * @param a  an angular object
	 * @return   a cosine
	 */
	public default float cosine(Angular a)
	{
		float nd = normSqr() * a.normSqr();
		if(!Floats.isZero(nd, 9))
		{
			float cos = dot(a) / Floats.sqrt(nd);
			return Floats.clamp(cos, -1f, 1f);
		}
		
		return 0f;
	}
	
	/**
	 * Returns a normalized object of the same angle.
	 * 
	 * @return  a normalized object
	 */
	public default Angular normalize()
	{
		return (Angular) times(1f / norm());
	}

	
	@Override
	public default Angular minus(Abelian a)
	{
		Linear<Float> min = ((Angular) a).times(-1f);
		return (Angular) plus(min);
	}

	@Override
	public default float distSqr(Distanced d)
	{
		return minus((Abelian) d).normSqr();
	}

	@Override
	public default float normSqr()
	{
		return dot(this);
	}
}