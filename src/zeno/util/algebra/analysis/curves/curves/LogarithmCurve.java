package zeno.util.algebra.analysis.curves.curves;

import zeno.util.algebra.analysis.curves.Curve;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code LogarithmCurve} class interpolates along a logarithmic line.
 * 
 * @since Jul 1, 2016
 * @author Zeno
 * 
 * @see Curve
 */
public class LogarithmCurve implements Curve
{
	/**
	 * Interpolates a value along a logarithmic path.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 * @return  an interpolated value
	 */
	public static float getValue(float lambda, float min, float max)
	{
		return LinearCurve.getValue(Floats.ln(lambda + 1) / Floats.ln(2), min, max);
	}
	
	
	private float min, max;
	
	/**
	 * Creates a new {@code LogarithmCurve}.
	 * 
	 * @param min  a minimum interpolation value
	 * @param max  a maximum interpolation value
	 */
	public LogarithmCurve(float min, float max)
	{
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Creates a new {@code LogarithmCurve}.
	 */
	public LogarithmCurve()
	{
		this(0, 1);
	}
	
	
	@Override
	public float getValue(float lambda)
	{
		return getValue(lambda, min, max);
	}
}