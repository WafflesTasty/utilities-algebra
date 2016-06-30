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
	 * Interpolates a value along a linear path.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public static float linear(float val, float min, float max)
	{
		return Floats.clamp((val - min) / (max - min), 0f, 1f);
	}
	
	/**
	 * Interpolates a value along a sinusoidal path.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public static float sinusoidal(float val, float min, float max)
	{
		return Floats.clamp(Floats.sin(0.5f * Floats.PI * (val - min) / (max - min)), 0f, 1f);
	}
	
	/**
	 * Interpolates a value along a cosinusoidal path.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public static float cosinusoidal(float val, float min, float max)
	{
		return Floats.clamp(1 - Floats.cos(0.5f * Floats.PI * (val - min) / (max - min)), 0f, 1f);
	}
	
	/**
	 * Interpolates a value along a logarithmic path.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public static float logarithmic(float val, float min, float max)
	{
		return Floats.clamp(Floats.ln(val - min + 1) / Floats.ln(max - min + 1), 0f, 1f);
	}
		
	
	/**
	 * Interpolates a value along this {@code Interpolation}.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public abstract float valueAt(float val, float min, float max);
}