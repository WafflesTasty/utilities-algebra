package waffles.utils.algebra.elements.interval;

import waffles.utils.algebra.elements.interval.format.FMTInterval;
import waffles.utils.lang.Formattable;
import waffles.utils.tools.primitives.Array;

/**
 * An {@code Interval} defines a connected range of real numbers.
 * 
 * @author Waffles
 * @since Apr 25, 2017
 * @version 1.0
 * 
 * 
 * @see Comparable
 */
public class Interval implements Comparable<Interval>, Formattable<Interval>
{
	private Cut min, max;
		
	/**
	 * Creates a new {@code Interval}.
	 * 
	 * @param min  an interval minimum
	 * @param max  an interval maximum
	 * 
	 * 
	 * @see Cut
	 */
	public Interval(Cut min, Cut max)
	{
		this.min = min;
		this.max = max;
	}
	
	
	/**
	 * Creates the span with another {@code Interval}.
	 * The result contains both given intervals.
	 * 
	 * @param range  an interval range
	 * @return  a spanning interval
	 */
	public Interval span(Interval range)
	{
		int cmin = min.compareTo(range.min);
		int cmax = max.compareTo(range.max);
		
		if(cmin >= 0)
		{
			if(cmax > 0)
			{
				return new Interval(range.min, max);
			}
			
			return range;
		}
		
		if(cmax < 0)
		{
			return new Interval(min, range.max);
		}
		
		return this;
	}
	
	/**
	 * Subtracts another interval from this {@code Interval}.
	 * The resulting array will contain either one
	 * or two intervals.
	 * 
	 * @param range  an interval range
	 * @return  a subtracted interval
	 */
	public Interval[] subtract(Interval range)
	{
		if(range.max.compareTo(min) <= 0
		|| range.min.compareTo(max) >= 0)
		{
			return new Interval[]{this};
		}
		
		
		Interval[] rng = new Interval[0];
		
		Interval rmin = Intervals.create(min, range.min);
		Interval rmax = Intervals.create(range.max, max);
		
		if(rmin != null) rng = Array.add.to(rng, rmin);		
		if(rmax != null) rng = Array.add.to(rng, rmax);
		if(rng.length == 0)
		{
			rng = Array.add.to(rng, Intervals.EMPTY);
		}
		
		return rng;
	}
	
	/**
	 * Intersects another interval with this {@code Interval}.
	 * 
	 * @param range  an interval range
	 * @return  an intersected interval
	 */
	public Interval intersect(Interval range)
	{		
		if(isDisjoint(range))
		{
			return Intervals.EMPTY;
		}
		
		
		int cmin = min.compareTo(range.min);
		int cmax = max.compareTo(range.max);
		
		if(cmin <= 0)
		{
			if(cmax < 0)
			{
				return new Interval(range.min, max);
			}
			
			return range;
		}
		
		if(cmax > 0)
		{
			return new Interval(min, range.max);
		}
		
		return this;
	}
	
			
	/**
	 * Checks if the {@code Interval} connects to another interval.
	 * 
	 * @param range  an interval range
	 * @return  {@code false} if the ranges connect
	 */
	public boolean isDisjoint(Interval range)
	{
		return compareTo(range) != 0;
	}
	
	/**
	 * Checks if the {@code Interval} contains another interval.
	 * 
	 * @param range  an interval range
	 * @return  {@code true} if the range is contained
	 */
	public boolean contains(Interval range)
	{
		return min.compareTo(range.min) <= 0
			&& max.compareTo(range.max) >= 0;
	}
	
	/**
	 * Checks if the {@code Interval} contains a value.
	 * 
	 * @param val  a real value
	 * @return  {@code true} if the value is contained
	 */
	public boolean contains(float val)
	{
		return compareTo(val) == 0;
	}
	
	/**
	 * Compares a value relative to the {@code Interval}.
	 * If -1, the value is higher than the range;
	 * if 1, the value is lower than the range;
	 * otherwise, the value is contained.
	 * 
	 * @param val  interval
	 * @return  a comparison value
	 */
	public int compareTo(float val)
	{
		if(min.isAbove(val)) return  1;
		if(max.isBelow(val)) return -1;
		return 0;
	}
	
	/**
	 * Checks if the {@code Interval} is a singleton.
	 * 
	 * @return  {@code true} if the interval is a singleton
	 */
	public boolean isSingleton()
	{
		if(!isEmpty())
		{
			return min.value() == max.value();
		}
		
		return false;
	}
	
	/**
	 * Checks if the {@code Interval} is empty.
	 * 
	 * @return  {@code true} if there are no values
	 */
	public boolean isEmpty()
	{
		return min.equals(max);
	}
	
	
	/**
	 * Returns the length of the {@code Interval}.
	 * The cut is taken above its value if and
	 * only if the interval is closed.
	 * 
	 * @return  an interval length
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Length()
	{
		return max.minus(min);
	}
	
	/**
	 * Returns the minimum of the {@code Interval}.
	 * 
	 * @return  an interval minimum
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Minimum()
	{
		return min;
	}
	
	/**
	 * Returns the maximum of the {@code Interval}.
	 * 
	 * @return  an interval minimum
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Maximum()
	{
		return max;
	}
	

	
	@Override
	public FMTInterval Formatter(String fmt, String delim)
	{
		return new FMTInterval(fmt, delim);
	}
	
	@Override
	public int compareTo(Interval r)
	{
		if(max.compareTo(r.min) < 0) return -1;
		if(min.compareTo(r.max) > 0) return  1;
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Interval)
		{
			return min.equals(((Interval) o).min)
				&& max.equals(((Interval) o).max);
		}
		
		return false;
	}
		
	@Override
	public String toString()
	{
		return Formatter("§(§llllll§..§uuuuuu§)§", "§").parse(this);
	}
	
	@Override
	public int hashCode()
	{
		return 443 * min.hashCode()
			 + 881 * max.hashCode();
	}
}