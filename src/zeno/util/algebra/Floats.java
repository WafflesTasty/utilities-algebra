package zeno.util.algebra;

/**
 * The {@code Floats} class defines basic math for {@link Float} values.
 *
 * @since Dec 9, 2014
 * @author Zeno
 */
public final class Floats
{
	// Constants
	
	/**
	 * Defines positive infinity in floating point precision.
	 */
	public static final float NEG_INFINITY = Float.NEGATIVE_INFINITY;
	/**
	 * Defines negative infinity in floating point precision.
	 */
	public static final float POS_INFINITY = Float.POSITIVE_INFINITY;
	
	/**
	 * Defines the minimum exponent in floating point precision.
	 */
	public static final int MIN_EXPONENT = Float.MIN_EXPONENT;
	/**
	 * Defines the maximum exponent in floating point precision.
	 */
	public static final int MAX_EXPONENT = Float.MAX_EXPONENT;
	
	/**
	 * Defines the minimum positive value in floating point precision.
	 */
	public static final float MIN_VALUE = Float.MIN_VALUE;
	/**
	 * Defines the minimum positive normal in floating point precision.
	 */
	public static final float MIN_NORMAL = Float.MIN_NORMAL;
	/**
	 * Defines the maximum positive value in floating point precision.
	 */
	public static final float MAX_VALUE = Float.MAX_VALUE;
	
	/**
	 * Defines the byte size of a single floating point number.
	 */
	public static final int BYTE_SIZE = Float.BYTES;
	/**
	 * Defines the bit size of a single floating point number.
	 */
	public static final int BIT_SIZE = Float.SIZE;
	
	
	/**
	 * Defines an undefined number in floating point precision.
	 */
	public static final float NaN = Float.NaN;
	/**
	 * Defines euler's constant in floating point precision.
	 */
	public static final float EULER = (float) Math.E;
	/**
	 * Defines pi constant in floating point precision.
	 */
	public static final float PI = (float) Math.PI;
	/**
	 * Defines a floating point error epsilon.
	 */
	public static final float EPSILON = 1e-6f;
	
	
	// Exponential
	
	/**
	 * Returns a value raised to the power of an exponent.
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li>If the second argument is positive or negative zero, then the result is 1.0.</li>
	 * 		<li>If the second argument is 1.0, then the result is the same as the first argument.</li>
	 * 		<li>If the second argument is NaN, then the result is NaN.</li>
	 * 		<li>If the first argument is NaN and the second argument is nonzero, then the result is NaN.</li>
	 * </ul>
	 * 
	 * @param x  the power's base
	 * @param p  the power's exponent
	 * @return  the base raised to the exponent
	 */
	public static float pow(float x, float p)
	{
		return (float) Math.pow(x, p);
	}
		
	/**
	 * Returns e raised to the power of a value.
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li>If the argument is NaN, the result is NaN.</li>
	 * 		<li>If the argument is positive infinity, then the result is positive infinity.</li>
	 * 		<li>If the argument is negative infinity, then the result is positive zero.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the exponential value
	 */
	public static float exp(float val)
	{
		return (float) Math.exp(val);
	}	
	
	
	// Extremes
	
	/**
	 * Clamps a value between a minimum and a maximum.
	 * 
	 * @param val  a value to clamp
	 * @param min  the value's minimum
	 * @param max  the value's maximum
	 * @return  a clamped value
	 */
	public static float clamp(float val, float min, float max)
	{
		return Math.max(min, Math.min(val, max));
	}

	/**
	 * Returns the minimum of a list of values.
	 * 
	 * @param vals  a list of values
	 * @return  the list's minimum
	 */
	public static float min(float... vals)
	{
		float res = MAX_VALUE;
		for(float val : vals)
		{
			res = Math.min(res, val);
		}
		
		return res;
	}
		
	/**
	 * Returns the maximum of a list of values.
	 * 
	 * @param vals  a list of values
	 * @return  the list's maximum
	 */
	public static float max(float... vals)
	{
		float res = -MAX_VALUE;
		for(float val : vals)
		{
			res = Math.max(res, val);
		}
		
		return res;
	}
	
	/**
	 * Returns the sign function of the value.
	 * 
	 * @param val  a value to use
	 * @return  the value's sign
	 */
	public static float sign(float val)
	{
		return Math.signum(val);
	}

	/**
	 * Returns the absolute of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's absolute
	 */
	public static float abs(float val)
	{
		return Math.abs(val);
	}
	
	
	// Logarithms
	
	/**
	 * Returns the base 10 logarithm of a value.
	 * 
	 * </br>Special cases:
	 * <ul>
	 * 		<li>If the argument is NaN or less than zero, then the result is NaN.</li>
	 * 		<li>If the argument is positive infinity, then the result is positive infinity.</li>
	 * 		<li>If the argument is positive zero or negative zero, then the result is negative infinity.</li>
	 * 		<li>If the argument is equal to 10n for integer n, then the result is n.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the base 10 logarithm
	 */
	public static float log10(float val)
	{
		return (float) Math.log10(val);
	}
	
	/**
	 * Returns the logarithm of a value.
	 * 
	 * @param val  a value to use
	 * @param base  a base to use
	 * @return  the value's logarithm
	 */
	public static float log(float val, float base)
	{
		return ln(val) / ln(base);
	}
			
	/**
	 * Returns the natural logarithm of a value.
	 * 
	 * <br> Special cases:
	 * <ul>
	 * 		<li>If the argument is NaN or less than zero, then the result is NaN.</li>
	 * 		<li>If the argument is positive infinity, then the result is positive infinity.</li>
	 * 		<li>If the argument is positive zero or negative zero, then the result is negative infinity.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the natural logarithm
	 */
	public static float ln(float val)
	{
		if(0 < val && val < 2)
		{
			return (float) Math.log1p(val - 1);
		}
		
		return (float) Math.log(val);
	}
	
	
	// Parsing
		
	/**
	 * Parses a string to a float value.
	 * 
	 * @param text  a string to parse
	 * @return  a parsed float
	 * @see String
	 */
	public static float parse(String text)
	{
		return Float.parseFloat(text);
	}
		
	/**
	 * Checks if a value is not an actual number.
	 * 
	 * @param val  a value to check
	 * @return  {@code true} if it is {@code NaN}
	 */
	public static boolean isNaN(float val)
	{
		return Float.isNaN(val);
	}
	
	/**
	 * Checks if a value is a finite real value.
	 * 
	 * @param val  a value to use
	 * @return  {@code true} if the value is finite
	 */
	public static boolean isFinite(float val)
	{
		return Float.isFinite(val);
	}
	
	/**
	 * Parses a bit code to a float value.
	 * 
	 * @param bits  a bit code to use
	 * @return  a parsed float
	 */
	public static float fromBits(int bits)
	{
		return Float.intBitsToFloat(bits);
	}
	
	/**
	 * Returns a hash code of a float value.
	 * 
	 * @param val  a value to hash
	 * @return  a hash code
	 */
	public static int hashCode(float val)
	{
		return Float.hashCode(val);
	}
	
	/**
	 * Parses a float value to a bit code.
	 * 
	 * @param val  a value to use
	 * @return  a parsed bitcode
	 */
	public static int toBits(float val)
	{
		return Float.floatToIntBits(val);
	}
		
	
	// Roots
		
	/**
	 * Returns the cube root of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's cube root
	 */
	public static float cbrt(float val)
	{
		return (float) Math.cbrt(val);
	}
	
	/**
	 * Returns the square root of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's square root
	 */
	public static float sqrt(float val)
	{
		return (float) Math.sqrt(val);
	}
	
	
	// Rounding

	/**
	 * Checks if a value is roughly equal to zero.
	 * </br> {@link #EPSILON} is used as a rounding error.
	 * 
	 * @param val  a value to check
	 * @return  {@code true} if the value is zero
	 */
	public static boolean isZero(float val)
	{
		return -EPSILON < val && val < EPSILON;
	}
	
	/**
	 * Checks if one value is roughly equal to the other.
	 * </br> {@link #EPSILON} is used as a rounding error.
	 * 
	 * @param val1  a first value to check
	 * @param val2  a second value to check
	 * @return  {@code true} if the values are equal
	 */
	public static boolean isEqual(float val1, float val2)
	{
		return isZero(val1 - val2);
	}
	
	/**
	 * Rounds a value to a set amount of decimals.
	 * 
	 * @param val  a value to round
	 * @param dec  a decimal count
	 * @return  a rounded value
	 */
	public static float round(float val, int dec)
	{		
		if(dec == 0)
		{
			return round(val);
		}
		
		float pow = pow(10, dec);
		return Math.round(pow * val) / pow;
	}
			
	/**
	 * Returns the integer closest to a value.
	 * 
	 * @param val  a value to use
	 * @return  a rounded value
	 */
	public static float round(float val)
	{
		return Math.round(val);
	}
	
	/**
	 * Returns the lower integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static float floor(float val)
	{
		return (float) Math.floor(val);
	}

	/**
	 * Returns the higher integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static float ceil(float val)
	{
		return (float) Math.ceil(val);
	}
	
	
	// Trigonometry
	
	/**
	 * Returns the polar angle of cartesian coördinates.
	 * </br> The phase theta is computed as an arc tangent of y/x in the range of [-PI, PI].
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li>If either argument is NaN, then the result is NaN.</li>
	 * 		<li>If the first argument is positive zero and the second argument is positive,
	 * 	   		or the first argument is positive and finite and the second argument is positive infinity, 
	 *     		then the result is positive zero.</li>
	 * 		<li>If the first argument is negative zero and the second argument is positive,
	 *     		or the first argument is negative and finite and the second argument is positive infinity,
	 *     		then the result is negative zero.</li>
	 * 		<li>If the first argument is positive zero and the second argument is negative,
	 * 	   		or the first argument is positive and finite and the second argument is negative infinity,
	 * 	   		then the result is the double value closest to PI.</li>
	 * 		<li>If the first argument is negative zero and the second argument is negative,
	 *     		or the first argument is negative and finite and the second argument is negative infinity,
	 *     		then the result is the double value closest to -PI.</li>
	 * 		<li>If the first argument is positive and the second argument is positive zero or negative zero,
	 *     		or the first argument is positive infinity and the second argument is finite,
	 *     		then the result is the double value closest to PI/2.</li>
	 * 		<li>If the first argument is negative and the second argument is positive zero or negative zero,
	 *     		or the first argument is negative infinity and the second argument is finite,
	 *     		then the result is the double value closest to -PI/2.</li>
	 * 		<li>If both arguments are positive infinity, then the result is the double value closest to PI/4.</li>
	 * 		<li>If the first argument is positive infinity and the second argument is negative infinity,
	 *     		then the result is the double value closest to 3*PI/4.</li>
	 * 		<li>If the first argument is negative infinity and the second argument is positive infinity,
	 * 	   		then the result is the double value closest to -PI/4.</li>
	 * 		<li>If both arguments are negative infinity, then the result is the double value closest to -3*PI/4.</li>
	 * </ul>
	 * 
	 * @param x  the cartesian x-coördinate
	 * @param y  the cartesian y-coördinate
	 * @return  the coördinate's angle
	 */
	public static float atan2(float x, float y)
	{
		return (float) Math.atan2(y, x);
	}
	
	/**
	 * Converts an angle in radians to an angle in degrees.
	 * 
	 * @param theta  an angle in radians
	 * @return  an angle in degrees
	 */
	public static float toDegrees(float theta)
	{
		return (float) Math.toDegrees(theta);
	}
	
	/**
	 * Converts an angle in degrees to an angle in radians.
	 * 
	 * @param theta  an angle in degrees
	 * @return  an angle in radians
	 */
	public static float toRadians(float theta)
	{
		return (float) Math.toRadians(theta);
	}
	
	/**
	 * Returns a normalized value of an angle in radians.
	 * </br> The result is in the range of [-PI, PI].
	 * 
	 * @param theta  an angle in radians
	 * @return  a normalized angle
	 */
	public static float normrad(float theta)
	{
		float circle = 2 * PI;
		float norm = theta % circle;
		if(norm > PI) norm -= circle;
		if(norm < PI) norm += circle;
		
		return norm;
	}
		
	
	/**
	 * Returns the trigonometric sine of an angle.
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN or an infinity, then the result is NaN.</li>
	 * 		<li> If the argument is zero, then the result is a zero with the same sign as the argument.</li>
	 * </ul>
	 * 
	 * @param theta  an angle, in radians
	 * @return  the angle's sine
	 */
	public static float sin(float theta)
	{
		return (float) Math.sin(theta);
	}
	
	/**
	 * Returns the trigonometric cosine of an angle.
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN or an infinity, then the result is NaN.</li>
	 * </ul>
	 * 
	 * @param theta  an angle, in radians
	 * @return  the angle's cosine
	 */
	public static float cos(float theta)
	{
		return (float) Math.cos(theta);
	}
	
	/**
	 * Returns the trigonometric tangent of an angle.
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN or an infinity, then the result is NaN. </li>
	 * 		<li> If the argument is zero, then the result is a zero with the same sign as the argument.</li>
	 * </ul>
	 * 
	 * @param theta  an angle, in radians
	 * @return  the angle's tangent
	 */
	public static float tan(float theta)
	{
		return (float) Math.tan(theta);
	}
	
	
	/**
	 * Returns the hyperbolic cosine of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic cosine
	 */
	public static float cosh(float val)
	{
		return (float) Math.cosh(val);
	}

	/**
	 * Returns the hyperbolic sine of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic sine
	 */
	public static float sinh(float val)
	{
		return (float) Math.sinh(val);
	}
		
	/**
	 * Returns the hyperbolic tangent of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic tangent
	 */
	public static float tanh(float val)
	{
		return (float) Math.tanh(val);
	}
	
	
	/**
	 * Returns the arc cosine of a value in the range of [0.0, PI].
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN or its absolute value is greater than 1, then the result is NaN.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the value's arc cosine
	 */
	public static float acos(float val)
	{
		return (float) Math.acos(val);
	}

	/**
	 * Returns the arc sine of a value in the range of [-PI/2, PI/2].
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN or its absolute value is greater than 1, then the result is NaN.</li>
	 * 		<li> If the argument is zero, then the result is a zero with the same sign as the argument.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the value's arc sine
	 */
	public static float asin(float val)
	{
		return (float) Math.asin(val);
	}
		
	/**
	 * Returns the arc tangent of a value in the range of [-PI/2, PI/2].
	 * 
	 * </br> Special cases:
	 * <ul>
	 * 		<li> If the argument is NaN, then the result is NaN.</li>
	 * 		<li> If the argument is zero, then the result is a zero with the same sign as the argument.</li>
	 * </ul>
	 * 
	 * @param val  a value to use
	 * @return  the value's arc tangent
	 */
	public static float atan(float val)
	{
		return (float) Math.atan(val);
	}

	
	private Floats()
	{
		// NOT APPLICABLE
	}
}