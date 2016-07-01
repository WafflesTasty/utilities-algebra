package zeno.util.algebra.interpolate;

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
	 * Interpolates a value along this {@code Curve}.
	 * 
	 * @param lambda  a value between 0 and 1
	 * @return  an interpolated value
	 */
	public abstract float getValue(float lambda);
}