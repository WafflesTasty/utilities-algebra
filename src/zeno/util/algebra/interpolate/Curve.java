package zeno.util.algebra.interpolate;

import zeno.util.algebra.Floats;

/**
 * The {@code Curve} interface interpolates a value along a curve between 0 and 1.
 * <br> The input value is handled between a predetermined minimum and maximum.
 *
 * @author Zeno
 * @since Apr 6, 2016
 */
@FunctionalInterface
public interface Curve
{	
	/**
	 * Interpolates a value along a sinusoidal path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param amp  a sinusoidal amplitude
	 * @return  an interpolated value
	 */
	public static float sinusoidal(float lambda, float amp)
	{
		
		return Floats.clamp(Floats.sin(0.5f * Floats.PI * lambda) * amp, -amp, amp);
	}
	
	/**
	 * Interpolates a value along a logarithmic path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 * @return  an interpolated value
	 */
	public static float logarithmic(float lambda, float min, float max)
	{
		return linear(Floats.ln(lambda + 1) / Floats.ln(2), min, max);
	}
	
	/**
	 * Interpolates a value along a linear path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 * @return  an interpolated value
	 */
	public static float linear(float lambda, float min, float max)
	{
		return Floats.clamp(min + lambda * (max - min), min, max);
	}
		
	
	/**
	 * Interpolates a value along this {@code Curve}.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @return  an interpolated value
	 */
	public abstract float getValue(float lambda);
}