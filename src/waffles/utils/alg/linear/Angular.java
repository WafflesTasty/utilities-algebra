package waffles.utils.alg.linear;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.linear.measure.Distanced;
import waffles.utils.alg.linear.measure.Normed;
import waffles.utils.tools.primitives.Array;
import waffles.utils.tools.primitives.Floats;

/**
 * An {@code Angular} exists in an inner product space.
 * It can compute angles with objects of similar type.
 *
 * @author Waffles
 * @since 18 Aug 2023
 * @version 1.1
 *
 *
 * @see Normed
 * @see Distanced
 * @see Linear
 */
public interface Angular extends Linear<Float>, Distanced, Normed
{	
	/**
	 * Returns the dot with an {@code Angular}.
	 * 
	 * @param a  an angular
	 * @return   a dot product
	 */
	public abstract float dot(Angular a);

	/**
	 * Returns the angle with an {@code Angular}.
	 * This angle is computed through a particular
	 * algorithm for accuracy purposes.
	 * 
	 * @param a  an angular
	 * @return   an angle
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
	 * Returns the cosine with an {@code Angular}.
	 * 
	 * @param a  an angular
	 * @return   a cosine
	 */
	public default float cosine(Angular a)
	{
		float n1 =   normSqr();
		float n2 = a.normSqr();
		
		int p1 = Array.product.of(  Dimensions());
		int p2 = Array.product.of(a.Dimensions());
		
		
		float cos = n1 * n2;
		if(!Floats.isZero(cos, p1 * p2))
		{
			cos = dot(a) / Floats.sqrt(cos);
			return Floats.clamp(cos, -1f, 1f);
		}
		
		return 0f;
	}
	
	/**
	 * Returns a normalized {@code Angular}.
	 * 
	 * @return  an normal angular
	 */
	public default Angular normalize()
	{
		return (Angular) times(1f / norm());
	}

	
	@Override
	public default Angular minus(Abelian a)
	{
		return (Angular) plus(((Angular) a).times(-1f));
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