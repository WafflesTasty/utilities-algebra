package zeno.util.algebra;

/**
 * The {@code Doubles} class defines basic math for {@link Double} values.
 *
 * @since Dec 9, 2014
 * @author Zeno
 */
public final class Doubles
{
	// Constants
	
	/**
	 * Defines positive infinity in double precision.
	 */
	public static final double NEG_INFINITY = Double.NEGATIVE_INFINITY;
	/**
	 * Defines negative infinity in double precision.
	 */
	public static final double POS_INFINITY = Double.POSITIVE_INFINITY;
	
	/**
	 * Defines the minimum exponent in double precision.
	 */
	public static final int MIN_EXPONENT = Double.MIN_EXPONENT;
	/**
	 * Defines the maximum exponent in double precision.
	 */
	public static final int MAX_EXPONENT = Double.MAX_EXPONENT;
	
	/**
	 * Defines the minimum positive value in double precision.
	 */
	public static final double MIN_VALUE = Double.MIN_VALUE;
	/**
	 * Defines the minimum positive normal in double precision.
	 */
	public static final double MIN_NORMAL = Double.MIN_NORMAL;
	/**
	 * Defines the maximum positive value in double precision.
	 */
	public static final double MAX_VALUE = Double.MAX_VALUE;
	
	/**
	 * Defines the byte count of a single double number.
	 */
	public static final int BYTECOUNT = Double.BYTES;
	/**
	 * Defines the bit size of a single double number.
	 */
	public static final int BIT_SIZE = Double.SIZE;
	
	/**
	 * Defines pi constant in double precision.
	 */
	public static final double PI = Math.PI;
	/**
	 * Defines euler's constant in double precision.
	 */
	public static final double EULER = Math.E;
	/**
	 * Defines an undefined number in double precision.
	 */
	public static final double NaN = Double.NaN;
	/**
	 * Defines a double error epsilon.
	 */
	public static final double EPSILON = 1e-12f;
	
	
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
	public static double pow(double x, double p)
	{
		return Math.pow(x, p);
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
	public static double exp(double val)
	{
		return Math.exp(val);
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
	public static double clamp(double val, double min, double max)
	{
		return Math.max(min, Math.min(val, max));
	}

	/**
	 * Returns the minimum of a list of values.
	 * 
	 * @param vals  a list of values
	 * @return  the list's minimum
	 */
	public static double min(double... vals)
	{
		double res = MAX_VALUE;
		for(double val : vals)
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
	public static double max(double... vals)
	{
		double res = -MAX_VALUE;
		for(double val : vals)
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
	public static double sign(double val)
	{
		return Math.signum(val);
	}

	/**
	 * Returns the absolute of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's absolute
	 */
	public static double abs(double val)
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
	public static double log10(double val)
	{
		return Math.log10(val);
	}
	
	/**
	 * Returns the logarithm of a value.
	 * 
	 * @param val  a value to use
	 * @param base  a base to use
	 * @return  the value's logarithm
	 */
	public static double log(double val, double base)
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
	public static double ln(double val)
	{
		if(0 < val && val < 2)
		{
			return Math.log1p(val - 1);
		}
		
		return Math.log(val);
	}
	
	
	// Parsing
		
	/**
	 * Parses a string to a double value.
	 * 
	 * @param text  a string to parse
	 * @return  a parsed double
	 * @see String
	 */
	public static double parse(String text)
	{
		return Double.parseDouble(text);
	}
		
	/**
	 * Checks if a value is not an actual number.
	 * 
	 * @param val  a value to check
	 * @return  {@code true} if it is {@code NaN}
	 */
	public static boolean isNaN(double val)
	{
		return Double.isNaN(val);
	}
	
	/**
	 * Checks if a value is a finite real value.
	 * 
	 * @param val  a value to use
	 * @return  {@code true} if the value is finite
	 */
	public static boolean isFinite(double val)
	{
		return Double.isFinite(val);
	}
	
	/**
	 * Parses a bit code to a double value.
	 * 
	 * @param bits  a bit code to use
	 * @return  a parsed double
	 */
	public static double fromBits(long bits)
	{
		return Double.longBitsToDouble(bits);
	}
	
	/**
	 * Returns a hash code of a double value.
	 * 
	 * @param val  a value to hash
	 * @return  a hash code
	 */
	public static int hashCode(double val)
	{
		return Double.hashCode(val);
	}
	
	/**
	 * Parses a double value to a bit code.
	 * 
	 * @param val  a value to use
	 * @return  a parsed bitcode
	 */
	public static long toBits(double val)
	{
		return Double.doubleToLongBits(val);
	}
		
	
	// Roots
		
	/**
	 * Returns the cube root of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's cube root
	 */
	public static double cbrt(double val)
	{
		return Math.cbrt(val);
	}
	
	/**
	 * Returns the square root of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's square root
	 */
	public static double sqrt(double val)
	{
		return Math.sqrt(val);
	}
	
	
	// Rounding

	/**
	 * Checks if a value is roughly equal to zero.
	 * </br> {@link #EPSILON} is used as a rounding error.
	 * 
	 * @param val  a value to check
	 * @return  {@code true} if the value is zero
	 */
	public static boolean isZero(double val)
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
	public static boolean isEqual(double val1, double val2)
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
	public static double round(double val, int dec)
	{		
		if(dec == 0)
		{
			return round(val);
		}
		
		double pow = pow(10, dec);
		return Math.round(pow * val) / pow;
	}
			
	/**
	 * Returns the integer closest to a value.
	 * 
	 * @param val  a value to use
	 * @return  a rounded value
	 */
	public static double round(double val)
	{
		return Math.round(val);
	}
	
	/**
	 * Returns the lower integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static double floor(double val)
	{
		return Math.floor(val);
	}

	/**
	 * Returns the higher integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static double ceil(double val)
	{
		return Math.ceil(val);
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
	public static double atan2(double x, double y)
	{
		return Math.atan2(y, x);
	}
	
	/**
	 * Converts an angle in radians to an angle in degrees.
	 * 
	 * @param theta  an angle in radians
	 * @return  an angle in degrees
	 */
	public static double toDegrees(double theta)
	{
		return Math.toDegrees(theta);
	}
	
	/**
	 * Converts an angle in degrees to an angle in radians.
	 * 
	 * @param theta  an angle in degrees
	 * @return  an angle in radians
	 */
	public static double toRadians(double theta)
	{
		return Math.toRadians(theta);
	}
	
	/**
	 * Returns a normalized value of an angle in radians.
	 * </br> The result is in the range of [-PI, PI].
	 * 
	 * @param theta  an angle in radians
	 * @return  a normalized angle
	 */
	public static double normrad(double theta)
	{
		double circle = 2 * PI;
		double norm = theta % circle;
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
	public static double sin(double theta)
	{
		return Math.sin(theta);
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
	public static double cos(double theta)
	{
		return Math.cos(theta);
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
	public static double tan(double theta)
	{
		return Math.tan(theta);
	}
	
	
	/**
	 * Returns the hyperbolic cosine of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic cosine
	 */
	public static double cosh(double val)
	{
		return Math.cosh(val);
	}

	/**
	 * Returns the hyperbolic sine of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic sine
	 */
	public static double sinh(double val)
	{
		return Math.sinh(val);
	}
		
	/**
	 * Returns the hyperbolic tangent of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's hyperbolic tangent
	 */
	public static double tanh(double val)
	{
		return Math.tanh(val);
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
	public static double acos(double val)
	{
		return Math.acos(val);
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
	public static double asin(double val)
	{
		return Math.asin(val);
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
	public static double atan(double val)
	{
		return Math.atan(val);
	}

	
	private Doubles()
	{
		// NOT APPLICABLE
	}
}