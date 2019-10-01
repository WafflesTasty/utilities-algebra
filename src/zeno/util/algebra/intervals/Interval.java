package zeno.util.algebra.intervals;

import zeno.util.algebra.intervals.types.AboveCut;
import zeno.util.algebra.intervals.types.All;
import zeno.util.algebra.intervals.types.BelowCut;
import zeno.util.algebra.intervals.types.Empty;
import zeno.util.algebra.intervals.types.Radians;
import zeno.util.tools.Floats;
import zeno.util.tools.helper.Array;

/**
 * The {@code Interval} class defines a single range of real numbers.
 * 
 * @since Apr 25, 2017
 * @author Zeno
 * 
 * 
 * @see Comparable
 */
public class Interval implements Comparable<Interval>
{
	/**
	 * Returns the full {@code Interval}.
	 */
	public static Interval ALL = new All();
	
	/**
	 * Returns a radian angle {@code Interval}.
	 * <br> The interval spans from {@code -PI}
	 * to {@code PI}.
	 */
	public static Interval RADIANS = new Radians();
	
	/**
	 * Returns the empty {@code Interval}.
	 */
	public static Interval EMPTY = new Empty();	
		
				
	/**
	 * Creates a new {@code Interval} from a string.
	 * <br> The format of the string follows the example (0.0, 10.5].
	 * The boundaries of the interval are denoted by '(' and ')' for
	 * an open cut, or '[' and ']' for a closed cut.
	 * 
	 * @param format  an interval format
	 * @return  a new interval
	 * 
	 * 
	 * @see String
	 */
	public static Interval create(String format)
	{
		String[] split = format.split(",");
		
		int len0 = split[0].length();
		int len1 = split[1].length();
		
		
		char bmin = split[0].charAt(0);
		char bmax = split[1].charAt(len1 - 1);
		
		float min = Floats.parse(split[0].substring(1, len0));
		float max = Floats.parse(split[1].substring(0, len1 - 1));
		
		if(bmin != '(' && bmin != '[') return null;
		if(bmax != ')' && bmax != ']') return null;
		
		Cut cmin = (bmin == '(' ? Cut.Above(min) : Cut.Below(min));
		Cut cmax = (bmax == ')' ? Cut.Below(max) : Cut.Above(max));
		
		return create(cmin, cmax);
	}
	
	/**
	 * Creates a new {@code Interval} from two cuts.
	 * <b> If a cut is null, it is treated as an
	 * unbounded range value (+/- infinity).
	 * 
	 * @param min  a minimum cut
	 * @param max  a maximum cut
	 * @return  a new range
	 * 
	 * 
	 * @see Cut
	 */
	public static Interval create(Cut min, Cut max)
	{
		// Swap null values for unbounded cuts.
		Cut cMin = (min != null ? min : Cut.BELOW_ALL);
		Cut cMax = (max != null ? max : Cut.ABOVE_ALL);
		
		// Compare both cuts..
		int compare = cMin.compareTo(cMax);
		// ..if equal, return empty range.
		if(compare == 0) return EMPTY;
		// ..if invalid, return null.
		if(compare > 0) return null;
		
		// If minimum is unbounded..
		if(cMin == Cut.BELOW_ALL)
		{
			// If maximum is unbounded..
			if(cMax == Cut.ABOVE_ALL)
			{
				// ..return full range.
				return ALL;
			}
			
			// ..return maxbounded range.
			return new BelowCut(cMax);
		}
		
		// If maximum is unbounded..
		if(cMax == Cut.ABOVE_ALL)
		{
			// ..return minbounded range.
			return new AboveCut(cMin);
		}
		
		// Repetition doublebounded range.
		return new Interval(cMin, cMax);
	}
		
	/**
	 * Normalizes an interval into radian angle values.
	 * <br> The resulting intervals cover the same angle as the
	 * original in the range of {@code -PI} to {@code PI}.
	 * 
	 * @param ival  an interval to normalize
	 * @return  the normalized intervals
	 */
	public static Interval[] toRadians(Interval ival)
	{
		// If the interval's length is more than 2 * PI...
		if(ival.length().isAbove(2 * Floats.PI))
		{
			// Return the full range.
			return new Interval[]{Interval.RADIANS};
		}
		
		
		Cut min = ival.min();
		Cut max = ival.max();
				
		float vmin = Floats.normrad(min.value());
		float vmax = Floats.normrad(max.value());
		
		// Create normalized cuts of the original interval.
		min = (min.isAbove(min.value()) ? Cut.Above(vmin) : Cut.Below(vmin));
		max = (max.isAbove(max.value()) ? Cut.Above(vmax) : Cut.Below(vmax));
		
		// If the minimum is above the maximum...
		if(min.compareTo(max) > 0)
		{
			// Split the result into two intervals.
			return new Interval[]
			{
				Interval.create(Cut.Below(-Floats.PI), max),
				Interval.create(min, Cut.Below(Floats.PI))
			};
		}
		
		// Otherwise, return the interval.
		return new Interval[]
		{
			Interval.create(min, max)
		};		
	}
	
	/**
	 * Creates a new singleton {@code Interval}.
	 * 
	 * @param val  a singleton value
	 * @return  a singleton
	 */
	public static Interval singleton(float val)
	{
		return create("[" + val + ", " + val + "]");
	}
	
	
	
	private Cut min, max;
		
	/**
	 * Creates a new {@code Interval}.
	 * 
	 * @param min  the range minimum
	 * @param max  the range maximum
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
	 * Creates the span with another range.
	 * 
	 * @param range  a range to span with
	 * @return  the spanning range
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
	 * Creates a subtraction with another range.
	 * 
	 * @param range  a range to subtract
	 * @return  the subtraction result
	 */
	public Interval[] subtract(Interval range)
	{
		if(range.max.compareTo(min) <= 0
		|| range.min.compareTo(max) >= 0)
		{
			return new Interval[]{this};
		}
		
		
		Interval[] ranges = new Interval[0];
		
		Interval rmin = create(min, range.min);
		Interval rmax = create(range.max, max);
		
		if(rmin != null) ranges = Array.add.to(ranges, rmin);		
		if(rmax != null) ranges = Array.add.to(ranges, rmax);
		if(ranges.length == 0)
		{
			ranges = Array.add.to(ranges, EMPTY);
		}
		
		return ranges;
	}
	
	/**
	 * Creates the intersection with another range.
	 * 
	 * @param range  a range to intersect with
	 * @return  the intersecting range if it exists
	 */
	public Interval intersection(Interval range)
	{		
		if(isDisjoint(range))
		{
			return null;
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
	 * Checks if the {@code Interval} connects to another range.
	 * 
	 * @param range  a range to check
	 * @return  {@code true} if the ranges connect
	 */
	public boolean isDisjoint(Interval range)
	{
		return compareTo(range) != 0;
	}
	
	/**
	 * Checks if the {@code Interval} contains another range.
	 * 
	 * @param range  a range to check
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
	 * @param val  a value to check
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
	 * @param val  a value to check
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
	 * Checks whether the {@code Interval} is empty.
	 * 
	 * @return  {@code true} if the range is empty
	 */
	public boolean isEmpty()
	{
		return min.equals(max);
	}
	
	/**
	 * Returns the length of the {@code Interval}.
	 * 
	 * @return  the interval's length
	 * 
	 * 
	 * @see Cut
	 */
	public Cut length()
	{
		return max.minus(min);
	}
	
			
	/**
	 * Returns the minimum of the {@code Interval}.
	 * 
	 * @return  the range's minimum
	 * 
	 * 
	 * @see Cut
	 */
	public Cut min()
	{
		return min;
	}
	
	/**
	 * Returns the maximum of the {@code Interval}.
	 * 
	 * @return  the range's maximum
	 * 
	 * 
	 * @see Cut
	 */
	public Cut max()
	{
		return max;
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
		return min.toLowerBound() + ".." + max.toUpperBound();
	}
	
	@Override
	public int hashCode()
	{
		return 443 * min.hashCode()
			 + 881 * max.hashCode();
	}
}