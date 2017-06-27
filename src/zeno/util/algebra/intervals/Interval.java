package zeno.util.algebra.intervals;

import zeno.util.tools.Array;

/**
 * The {@code Interval} class defines a single range of real numbers.
 * 
 * @since Apr 25, 2017
 * @author Zeno
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
	 * Returns the empty {@code Interval}.
	 */
	public static Interval EMPTY = new Empty();
	
	
	/**
	 * Creates a new {@code Interval} from two cuts.
	 * <b> If a cut is null, it is treated as an
	 * unbounded range value (+/- infinity).
	 * 
	 * @param min  a minimum cut
	 * @param max  a maximum cut
	 * @return  a new range
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
		
		// Return doublebounded range.
		return new Interval(cMin, cMax);
	}
	
	/**
	 * Creates a new {@code Interval} from two cuts.
	 * The range boundaries are given by '[' or '(' for a
	 * closed or open lower bound respectively, and ']' or ')'
	 * for a closed or open upper bound respectively.
	 * 
	 * @param bmin  a minimum boundary
	 * @param min   a minimum value
	 * @param max   a maximum value
	 * @param bmax  a maximum boundary
	 * @return  a new range
	 */
	public static Interval create(char bmin, float min, float max, char bmax)
	{
		if(bmin != '(' && bmin != '[') return null;
		if(bmax != ')' && bmax != ']') return null;
		
		Cut cmin = (bmin == '(' ? Cut.Above(min) : Cut.Below(min));
		Cut cmax = (bmax == ')' ? Cut.Below(max) : Cut.Above(max));
		
		return create(cmin, cmax);
	}
		
	/**
	 * Creates a new singleton {@code Interval}.
	 * 
	 * @param val  a singleton value
	 * @return  a singleton
	 */
	public static Interval singleton(float val)
	{
		return create('[', val, val, ']');
	}
	
	
	private static class AboveCut extends Interval
	{
		public AboveCut(Cut min)
		{
			super(min, Cut.ABOVE_ALL);
		}
		
		
		@Override
		public Interval span(Interval range)
		{
			int cmin = min().compareTo(range.min());
			if(cmin < 0)
			{
				return new Interval(range.min(), max());
			}
			
			return this;
		}
		
		@Override
		public Interval intersection(Interval range)
		{
			if(isDisjoint(range))
			{
				return null;
			}
			
			
			int cmin = min().compareTo(range.min());
			if(cmin < 0)
			{
				return new Interval(min(), range.max());
			}
			
			return range;
		}
		
		
		@Override
		public boolean contains(Interval range)
		{
			return min().compareTo(range.min()) <= 0;
		}
		
		@Override
		public boolean contains(float val)
		{
			return min().isBelow(val);
		}
		
		@Override
		public int compareTo(Interval r)
		{
			return (min().compareTo(r.max()) > 0 ? 1 : 0);
		}
		
		@Override
		public boolean isEmpty()
		{
			return false;
		}
	}
	
	private static class BelowCut extends Interval
	{
		public BelowCut(Cut max)
		{
			super(Cut.BELOW_ALL, max);
		}

				
		@Override
		public Interval span(Interval range)
		{
			int cmax = max().compareTo(range.max());
			if(cmax < 0)
			{
				return new Interval(min(), range.max());
			}
			
			return this;
		}
		
		@Override
		public Interval intersection(Interval range)
		{
			if(isDisjoint(range))
			{
				return null;
			}
			
			
			int cmax = max().compareTo(range.max());
			if(cmax < 0)
			{
				return new Interval(range.min(), max());
			}
			
			return range;
		}
		
		
		@Override
		public boolean contains(Interval range)
		{
			return max().compareTo(range.max()) >= 0;
		}
		
		@Override
		public boolean contains(float val)
		{
			return max().isAbove(val);
		}
		
		@Override
		public int compareTo(Interval r)
		{
			return (max().compareTo(r.min()) < 0 ? -1 : 0);
		}
		
		@Override
		public boolean isEmpty()
		{
			return false;
		}
	}
	
	private static class Empty extends Interval
	{
		public Empty()
		{
			super(Cut.Below(0), Cut.Below(0));
		}
		
		
		@Override
		public Interval span(Interval range)
		{
			return range;
		}
		
		@Override
		public Interval intersection(Interval range)
		{
			return this;
		}
		

		@Override
		public boolean isDisjoint(Interval range)
		{
			return true;
		}
		
		@Override
		public boolean contains(Interval range)
		{
			return equals(range);
		}
		
		@Override
		public boolean contains(float val)
		{
			return false;
		}
		
		@Override
		public boolean isEmpty()
		{
			return true;
		}
	
		
		@Override
		public int compareTo(Interval range)
		{
			return 0;
		}
			
		@Override
		public boolean equals(Object o)
		{
			return o == this;
		}
		
		@Override
		public int hashCode()
		{
			return 2017;
		}
	}
	
	private static class All extends Interval
	{
		public All()
		{
			super(Cut.BELOW_ALL, Cut.ABOVE_ALL);
		}
		
		
		@Override
		public Interval span(Interval range)
		{
			return this;
		}
				
		@Override
		public Interval intersection(Interval range)
		{
			return range;
		}
		
				
		@Override
		public boolean isDisjoint(Interval range)
		{
			return false;
		}
		
		@Override
		public boolean contains(Interval range)
		{
			return true;
		}
		
		@Override
		public boolean contains(float val)
		{
			return true;
		}
				
		@Override
		public boolean isEmpty()
		{
			return false;
		}

		
		@Override
		public int compareTo(Interval range)
		{
			return 0;
		}
			
		@Override
		public boolean equals(Object o)
		{
			return o == this;
		}
		
		@Override
		public int hashCode()
		{
			return 2111;
		}
	}
	
	
	private Cut min, max;
		
	/**
	 * Creates a new {@code Interval}.
	 * 
	 * @param min  the range minimum
	 * @param max  the range maximum
	 * @see Cut
	 */
	protected Interval(Cut min, Cut max)
	{
		this.min = min;
		this.max = max;
	}

	
	/**
	 * Returns the minimum of the {@code Interval}.
	 * 
	 * @return  the range's minimum
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
	 * @see Cut
	 */
	public Cut max()
	{
		return max;
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
	 * Checks if the {@code Interval} is empty.
	 * 
	 * @return  {@code true} if the range is empty
	 */
	public boolean isEmpty()
	{
		return min.equals(max);
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
	public int compareTo(Interval r)
	{
		if(max.compareTo(r.min) < 0) return -1;
		if(min.compareTo(r.max) > 0) return  1;
		return 0;
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