package zeno.util.algebra.interfaces;

import zeno.util.algebra.FMath;

/**
 * The {@code Interpolation} interface interpolates a value along a curve between 0 and 1.
 * <br> The input value is handled between a predetermined minimum and maximum.
 *
 * @author Zeno
 * @since Apr 6, 2016
 */
@FunctionalInterface
public interface Interpolation
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
		return FMath.clamp((val - min) / (max - min), 0f, 1f);
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
		return FMath.clamp(FMath.sin(0.5f * FMath.PI * (val - min) / (max - min)), 0f, 1f);
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
		return FMath.clamp(1 - FMath.cos(0.5f * FMath.PI * (val - min) / (max - min)), 0f, 1f);
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
		return FMath.clamp(FMath.ln(val - min + 1) / FMath.ln(max - min + 1), 0f, 1f);
	}
		
	
	/**
	 * Interpolates a value along this {@code Interpolation}.
	 * 
	 * @param val  the x-coördinate of the value
	 * @param min  the minimum x-coördinate
	 * @param max  the maximum x-coördinate
	 * @return  a value between 0 and 1
	 */
	public abstract float interpolate(float val, float min, float max);
}