package zeno.util.algebra;

/**
 * The {@code Longs} class defines basic math for {@link Long} values.
 * 
 * @author Zeno
 * @since Jun 3, 2016
 */
public final class Longs
{
	// Constants
	
	/**
	 * Defines the minimum positive value in long precision.
	 */
	public static final long MIN_VALUE = Long.MIN_VALUE;
	/**
	 * Defines the maximum positive value in long precision.
	 */
	public static final long MAX_VALUE = Long.MAX_VALUE;
	
	/**
	 * Defines the byte count of a single long number.
	 */
	public static final long BYTECOUNT = Long.BYTES;
	/**
	 * Defines the bit size of a single long number.
	 */
	public static final long BIT_SIZE = Long.SIZE;

	
	// Bitcodes
	
	/**
	 * Reverses the bits of a value.
	 * 
	 * @param val  a value to use
	 * @return  the reversed value
	 */
	public static long reverseBits(long val)
	{
		return Long.reverse(val);
	}
	
	/**
	 * Returns the trailing zero count of a value.
	 * 
	 * @param val  a value to use
	 * @return  the trailing zero count
	 */
	public static long trailZeroes(long val)
	{
		return Long.numberOfTrailingZeros(val);
	}
	
	/**
	 * Returns the leading zero count of a value.
	 * 
	 * @param val  a value to use
	 * @return  the leading zero count
	 */
	public static long leadZeroes(long val)
	{
		return Long.numberOfLeadingZeros(val);
	}
	
	/**
	 * Returns the highest one bit in the value.
	 * 
	 * @param val  a value to use
	 * @return  the highest one bit
	 */
	public static long highestBit(long val)
	{
		return Long.highestOneBit(val);
	}
	
	/**
	 * Returns the lowest one bit in the value.
	 * 
	 * @param val  a value to use
	 * @return  the lowest one bit
	 */
	public static long lowestBit(long val)
	{
		return Long.lowestOneBit(val);
	}
	
	/**
	 * Returns the one bits in the value.
	 * 
	 * @param val  a value to use
	 * @return  the one bit count
	 */
	public static long bitcount(long val)
	{
		return Long.bitCount(val);
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
	public static long pow(long x, long p)
	{
		return (long) Math.pow(x, p);
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
	public static long clamp(long val, long min, long max)
	{
		return Math.max(min, Math.min(val, max));
	}

	/**
	 * Returns the minimum of a list of values.
	 * 
	 * @param vals  a list of values
	 * @return  the list's minimum
	 */
	public static long min(long... vals)
	{
		long res = MAX_VALUE;
		for(long val : vals)
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
	public static long max(long... vals)
	{
		long res = -MAX_VALUE;
		for(long val : vals)
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
	public static long sign(long val)
	{
		return Long.signum(val);
	}

	/**
	 * Returns the absolute of a value.
	 * 
	 * @param val  a value to use
	 * @return  the value's absolute
	 */
	public static long abs(long val)
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
	public static long parse(String text)
	{
		return Long.parseLong(text);
	}
	
	
	private Longs()
	{
		// NOT APPLICABLE
	}
}