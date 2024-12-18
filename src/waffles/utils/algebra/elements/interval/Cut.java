package waffles.utils.algebra.elements.interval;

import waffles.utils.algebra.Partition;
import waffles.utils.tools.primitives.Booleans;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Cut} defines a cut in between the real number line.
 * Each cut occurs either right below, or right above a value,
 * Hence it separates the real numbers into two intervals.
 * One of the intervals is possibly empty.
 * 
 * @author Waffles
 * @since Apr 25, 2017
 * @version 1.0
 * 
 * 
 * @see Comparable
 * @see Partition
 */
public abstract class Cut implements Comparable<Cut>, Partition
{
	private float value;
	
	/**
	 * Creates a new {@code Cut}.
	 * 
	 * @param val  a cut value
	 */
	public Cut(float val)
	{
		value = val;
	}

	/**
	 * Multiplies a value to this {@Cut}.
	 * If the given value is negative, the
	 * cut will switch its bound beween
	 * above and below.
	 * 
	 * @param mul  a real value
	 * @return  a multiple cut
	 */
	public Cut times(float mul)
	{
		// If the value is negative, the cut switches.
		return (mul > 0 == isAbove(value))
			 ? Cuts.Above(value * mul)
			 : Cuts.Below(value * mul);
	}
	
	/**
	 * Subtracts from this {@code Cut}.
	 * 
	 * @param val  a real value
	 * @return     a subtracted cut
	 */
	public Cut minus(float val)
	{
		return plus(-val);
	}	
	
	/**
	 * Returns a sum of the {@code Cut}.
	 * 
	 * @param val  a real value
	 * @return     an added cut
	 */
	public Cut plus(float val)
	{
		if(isAbove(value))
		{
			return Cuts.Above(value + val);
		}
		
		return Cuts.Below(value + val);
	}
	
	/**
	 * Subtracts from this {@code Cut}.
	 * 
	 * @param cut  an interval cut
	 * @return  a subtracted cut
	 */
	public Cut minus(Cut cut)
	{
		float val = value - cut.value;
		if(cut.isBelow(cut.value())) return Cuts.Above(val);
		if(isAbove(value())) return Cuts.Above(val);
		return Cuts.Below(val);
	}
	
	/**
	 * Returns the {@code Cut} value.
	 * 
	 * @return  a cut value
	 */
	public float value()
	{
		return value;
	}


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
		return compareTo(o.value());
	}
	
	@Override
	public int hashCode()
	{
		return 347 * Floats.hashCode(value()) + 331 * Booleans.hashCode(isAbove(value()));
	}
}