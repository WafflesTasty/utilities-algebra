package zeno.util.algebra.intervals;

import zeno.util.algebra.intervals.cuts.AboveAll;
import zeno.util.algebra.intervals.cuts.AboveValue;
import zeno.util.algebra.intervals.cuts.BelowAll;
import zeno.util.algebra.intervals.cuts.BelowValue;
import zeno.util.tools.primitives.Booleans;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Cut} class defines a cut in the real numbers.
 * It neatly separates the real numbers into two intervals.
 * One of the intervals is possibly empty.
 * 
 * @author Zeno
 * @since Apr 25, 2017
 * @version 1.0
 * 
 * 
 * @see Comparable
 */
public abstract class Cut implements Comparable<Cut>
{
	/**
	 * Defines a cut above all the real numbers.
	 */
	public static Cut ABOVE_ALL = new AboveAll();
	
	/**
	 * Defines a cut below all the real numbers.
	 */
	public static Cut BELOW_ALL = new BelowAll();
	
	/**
	 * Defines a cut above a real number.
	 * 
	 * @param value  a value to cut above
	 * @return  an above cut
	 */
	public static Cut Above(float value)
	{
		if(value == Floats.NEG_INFINITY)
			return BELOW_ALL;
		if(value == Floats.POS_INFINITY)
			return ABOVE_ALL;
		
		return new AboveValue(value);
	}
	
	/**
	 * Defines a cut below a real number.
	 * 
	 * @param value  a value to cut below
	 * @return  a below cut
	 */
	public static Cut Below(float value)
	{
		if(value == Floats.NEG_INFINITY)
			return BELOW_ALL;
		if(value == Floats.POS_INFINITY)
			return ABOVE_ALL;
		
		return new BelowValue(value);
	}
	
	
	
	private float value;
	
	/**
	 * Creates a new {@code Cut}.
	 * 
	 * @param value  a value to cut at
	 */
	public Cut(float value)
	{
		this.value = value;
	}
			
	/**
	 * Represents the {@code Cut} as a lower bound.
	 * 
	 * @return  a lower bound string
	 * 
	 * 
	 * @see String
	 */
	public String toLowerBound()
	{
		if(isBelow(value))
		{
			return "[" + value;
		}
		
		return "(" + value;
	}
	
	/**
	 * Represents the {@code Cut} as an upper bound.
	 * 
	 * @return  an upper bound string
	 * 
	 * 
	 * @see String
	 */
	public String toUpperBound()
	{
		if(isAbove(value))
		{
			return value + "]";
		}
		
		return value + ")";
	}
		
	/**
	 * Returns a multiple of the {@Cut}.
	 * 
	 * @param val  a value to multiply
	 * @return  a multiplied cut
	 */
	public Cut times(float val)
	{
		// If the value is negative, the cut gets switched.
		return (val > 0 == isAbove(value))
			 ? Above(value * val)
			 : Below(value * val);
	}
	
	/**
	 * Returns a sum of the {@code Cut}.
	 * 
	 * @param val  a value to add
	 * @return  an added cut
	 */
	public Cut plus(float val)
	{
		if(isAbove(value))
		{
			return Above(value + val);
		}
		
		return Below(value + val);
	}
	
	/**
	 * Subtracts from this {@code Cut}.
	 * 
	 * @param cut  a cut to subtract
	 * @return  a subtracted cut
	 * @see Cut
	 */
	public Cut minus(Cut cut)
	{
		float val = value - cut.value;
		if(cut.isBelow(cut.value())) return Above(val);
		if(isAbove(value())) return Above(val);
		return Below(val);
	}
	
	/**
	 * Returns the {@code Cut}'s value.
	 * 
	 * @return  the cut's value
	 */
	public float value()
	{
		return value;
	}
	
		
	/**
	 * Checks if the {@code Cut} is above a value.
	 * 
	 * @param v  a value to check
	 * @return  {@code true} if the cut is above
	 */
	public abstract boolean isAbove(float v);
	
	/**
	 * Checks if the {@code Cut} is below a value.
	 * 
	 * @param v  a value to check
	 * @return  {@code true} if the cut is below
	 */
	public abstract boolean isBelow(float v);
		
	/**
	 * Compares the {@code Cut} to a value.
	 * 
	 * @param val  a value to compare
	 * @return  a comparative integer
	 */
	public abstract int compareTo(float val);
	
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Cut)
		{
			return compareTo((Cut) o) == 0;
		}
		
		return false;
	}
		
	@Override
	public int compareTo(Cut o)
	{
		return compareTo(o.value);
	}
	
	@Override
	public int hashCode()
	{
		return 347 * Floats.hashCode(value) + 331 * Booleans.hashCode(isAbove(value));
	}
}