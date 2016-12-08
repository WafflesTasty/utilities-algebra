package zeno.util.algebra.interpolate.curves;

import zeno.util.algebra.interpolate.Curve;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code SinusCurve} class interpolates along a sinusoid.
 * 
 * @since Jul 1, 2016
 * @author Zeno
 * 
 * @see Curve
 */
public class SinusCurve implements Curve
{
	/**
	 * Interpolates a value along a sinusoidal path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param amp  a sinusoidal amplitude
	 * @return  an interpolated value
	 */
	public static float getValue(float lambda, float amp)
	{
		return Floats.clamp(Floats.sin(2 * Floats.PI * lambda) * amp, -amp, amp);
	}
	
	
	private float amp;
	
	/**
	 * Creates a new {@code SinusCurve}.
	 * 
	 * @param amp  a sinusoidal amplitude
	 */
	public SinusCurve(float amp)
	{
		this.amp = amp;
	}

	/**
	 * Creates a new {@code SinusCurve}.
	 */
	public SinusCurve()
	{
		this(1);
	}
	
	
	@Override
	public float getValue(float lambda)
	{
		return getValue(lambda, amp);
	}
}