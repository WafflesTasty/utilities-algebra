package zeno.util.algebra.intervals;

import zeno.util.tools.primitives.Booleans;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Cut} class defines a cut in the real numbers.
 * It neatly separates the real numbers into two intervals.
 * One of the intervals is possibly empty.
 * 
 * @since Apr 25, 2017
 * @author Zeno
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
		if(value != Floats.POS_INFINITY)
		{
			return new AboveValue(value);
		}
		
		return ABOVE_ALL;
	}
	
	/**
	 * Defines a cut below a real number.
	 * 
	 * @param value  a value to cut below
	 * @return  a below cut
	 */
	public static Cut Below(float value)
	{
		if(value != Floats.NEG_INFINITY)
		{
			return new BelowValue(value);
		}
		
		 return BELOW_ALL;
	}
	
	
	private static class BelowAll extends Cut
	{
		public BelowAll()
		{
			super(Floats.NEG_INFINITY);
		}

	
		@Override
		public String toLowerBound()
		{
			return "(\u221E";
		}
		
		@Override
		public String toUpperBound()
		{
			return null;
		}
		
		@Override
		public boolean isAbove(float v)
		{
			return false;
		}
		
		@Override
		public boolean isBelow(float v)
		{
			return true;
		}
			
		@Override
		public int compareTo(float val)
		{
			if(Floats.isFinite(val))
			{
				return -1;
			}
			
			return 0;
		}
		
		@Override
		public int compareTo(Cut c)
		{
			if(c != this)
			{
				return -1;
			}
			
			return 0;
		}
	}
		
	private static class BelowValue extends Cut
	{
		public BelowValue(float value)
		{
			super(value);
		}
		

		@Override
		public boolean isAbove(float v)
		{
			return v < value();
		}
		
		@Override
		public boolean isBelow(float v)
		{
			return v >= value();
		}
		
		@Override
		public int compareTo(float val)
		{
			int comp = super.compareTo(val);
			if(comp == 0)
			{
				if(isAbove(val))
				{
					return 1;
				}
				
				return -1;
			}
			
			return comp;
		}
		
		@Override
		public int compareTo(Cut c)
		{
			int comp = super.compareTo(c);
			if(comp == 0)
			{
				if(c.isAbove(value()))
				{
					return -1;
				}
				
				return 0;
			}
			
			return comp;
		}
	}
	
	private static class AboveValue extends Cut
	{
		public AboveValue(float value)
		{
			super(value);
		}

		
		@Override
		public boolean isAbove(float v)
		{
			return v <= value();
		}
		
		@Override
		public boolean isBelow(float v)
		{
			return v > value();
		}
		
		@Override
		public int compareTo(float val)
		{
			int comp = super.compareTo(val);
			if(comp == 0)
			{
				if(isAbove(val))
				{
					return 1;
				}
				
				return -1;
			}
			
			return comp;
		}
		
		@Override
		public int compareTo(Cut c)
		{
			int compare = super.compareTo(c);
			if(compare == 0)
			{
				if(c.isBelow(value()))
				{
					return 1;
				}
				
				return 0;
			}
			
			return compare;
		}
	}
	
	private static class AboveAll extends Cut
	{
		public AboveAll()
		{
			super(Floats.POS_INFINITY);
		}

		
		@Override
		public String toLowerBound()
		{
			return null;
		}
		
		@Override
		public String toUpperBound()
		{
			return "\u221E)";
		}
		
		@Override
		public boolean isAbove(float v)
		{
			return true;
		}
		
		@Override
		public boolean isBelow(float v)
		{
			return false;
		}
		
		@Override
		public int compareTo(float val)
		{
			if(Floats.isFinite(val))
			{
				return 1;
			}
			
			return 0;
		}
		
		@Override
		public int compareTo(Cut c)
		{
			if(c == this)
			{
				return 0;
			}
			
			return 1;
		}
	}
	
	
	private float value;
	
	/**
	 * Creates a new {@code Cut}.
	 * 
	 * @param value  a value to cut at
	 */
	protected Cut(float value)
	{
		this.value = value;
	}
			
	/**
	 * Represents the {@code Cut} as a lower bound.
	 * 
	 * @return  a lower bound string
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
		if(isAbove(value))
		{
			return Above(value * val);
		}
		
		return Below(value * val);
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
	public int compareTo(float val)
	{
		if(value < val) return -1;
		if(value > val) return  1;
		return 0;
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
		return compareTo(o.value);
	}
	
	@Override
	public int hashCode()
	{
		return 347 * Floats.hashCode(value) + 331 * Booleans.hashCode(isAbove(value));
	}
}