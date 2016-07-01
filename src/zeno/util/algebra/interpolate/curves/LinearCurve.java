package zeno.util.algebra.interpolate.curves;

import zeno.util.algebra.Floats;
import zeno.util.algebra.interpolate.Curve;

/**
 * The {@code LinearCurve} class interpolates along a straight line.
 * 
 * @author Zeno
 * @since Jul 1, 2016
 * @see Curve
 */
public class LinearCurve implements Curve
{
	/**
	 * Interpolates a value along a linear path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 * @return  an interpolated value
	 */
	public static float getValue(float lambda, float min, float max)
	{
		return Floats.clamp(min + lambda * (max - min), min, max);
	}
	
	
	private float min, max;
		
	/**
	 * Creates a new {@code LinearCurve}.
	 * 
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 */
	public LinearCurve(float min, float max)
	{
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Creates a new {@code LinearCurve}.
	 */
	public LinearCurve()
	{
		this(0, 1);
	}
	
	

	@Override
	public float getValue(float lambda)
	{
		return getValue(lambda, min, max);
	}
}