package zeno.util.algebra;

/**
 * The {@code Integers} class defines basic math for {@link Integer} values.
 * 
 * @since Jun 3, 2016
 * @author Zeno
 */
public final class Integers
{
	// Constants
	
	/**
	 * Defines the minimum positive value in integer precision.
	 */
	public static final int MIN_VALUE = Integer.MIN_VALUE;
	/**
	 * Defines the maximum positive value in integer precision.
	 */
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	
	/**
	 * Defines the byte count of a single integer number.
	 */
	public static final int BYTECOUNT = Integer.BYTES;
	/**
	 * Defines the bit size of a single integer number.
	 */
	public static final int BIT_SIZE = Integer.SIZE;

	
	// Bitcodes
	
	/**
	 * Reverses the bits of a value.
	 * 
	 * @param val  a value to use
	 * @return  the reversed value
	 */
	public static int reverseBits(int val)
	{
		return Integer.reverse(val);
	}
	
	/**
	 * Returns the trailing zero count of a value.
	 * 
	 * @param val  a value to use
	 * @return  the trailing zero count
	 */
	public static int trailZeroes(int val)
	{
		return Integer.numberOfTrailingZeros(val);
	}
	
	/**
	 * Returns the leading zero count of a value.
	 * 
	 * @param val  a value to use
	 * @return  the leading zero count
	 */
	public static int leadZeroes(int val)
	{
		return Integer.numberOfLeadingZeros(val);
	}
	
	/**
	 * Returns the highest one bit in the value.
	 * 
	 * @param val  a value to use
	 * @return  the highest one bit
	 */
	public static int highestBit(int val)
	{
		return Integer.highestOneBit(val);
	}
	
	/**
	 * Returns the lowest one bit in the value.
	 * 
	 * @param val  a value to use
	 * @return  the lowest one bit
	 */
	public static int lowestBit(int val)
	{
		return Integer.lowestOneBit(val);
	}
	
	/**
	 * Returns the one bits in the value.
	 * 
	 * @param val  a value to use
	 * @return  the one bit count
	 */
	public static int bitcount(int val)
	{
		return Integer.bitCount(val);
	}

	
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
	public static int pow(int x, int p)
	{
		return (int) Math.pow(x, p);
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
	public static int clamp(int val, int min, int max)
	{
		return Math.max(min, Math.min(val, max));
	}

	/**
	 * Returns the minimum of a list of values.
	 * 
	 * @param vals  a list of values
	 * @return  the list's minimum
	 */
	public static int min(int... vals)
	{
		int res = MAX_VALUE;
		for(int val : vals)
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
	public static int max(int... vals)
	{
		int res = -MAX_VALUE;
		for(int val : vals)
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
	public static int sign(int val)
	{
		return Integer.signum(val);
	}

	/**
	 * Returns the absolute of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's absolute
	 */
	public static int abs(int val)
	{
		return Math.abs(val);
	}	
	
	
	// Parsing
	
	/**
	 * Parses a string to a float value.
	 * 
	 * @param text  a string to parse
	 * @return  a parsed float
	 * @see String
	 */
	public static int parse(String text)
	{
		return Integer.parseInt(text);
	}
	
	/**
	 * Returns a hash code of an integer value.
	 * 
	 * @param val  a value to hash
	 * @return  a hash code
	 */
	public static int hashCode(int val)
	{
		return Integer.hashCode(val);
	}
	
	
	// Rounding
	
	/**
	 * Returns the integer closest to a value.
	 * 
	 * @param val  a value to use
	 * @return  a rounded value
	 */
	public static int round(float val)
	{
		return Math.round(val);
	}
	
	/**
	 * Returns the lower integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static int floor(float val)
	{
		return (int) Math.floor(val);
	}

	/**
	 * Returns the higher integer closest to a value.
	 * 
	 * @param val  a value to round
	 * @return  a rounded value
	 */
	public static int ceil(float val)
	{
		return (int) Math.ceil(val);
	}
	
	
	private Integers()
	{
		// NOT APPLICABLE
	}
}