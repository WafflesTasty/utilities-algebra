package waffles.utils.alg.reals.ranges;

import waffles.utils.alg.reals.Partition;
import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.format.RangeFormat;
import waffles.utils.alg.utilities.groups.Multiplication;
import waffles.utils.alg.utilities.groups.Subtraction;
import waffles.utils.lang.tokens.Token;
import waffles.utils.lang.tokens.format.Format;

/**
 * A {@code Range} defines a connected section of the real line.
 * Each {@code Range} is defined by a minimum and maximum {@code Cut}.
 *
 * @author Waffles
 * @since 20 Sep 2025
 * @version 1.1
 *
 * 
 * @see Multiplication
 * @see Subtraction
 * @see Partition
 * @see Token
 */
public class Range implements Partition, Subtraction<Range>, Multiplication<Float>, Token
{
	/**
	 * Defines a default format for a {@code Range}.
	 */
	public static final String DEF_FORMAT = "§(§§llllll§..§uuuuuu§§)§";
	
	
	private Cut min, max;
	
	/**
	 * Creates a new {@code Range}.
	 * 
	 * @param min  a minimum cut
	 * @param max  a maximum cut
	 * 
	 * 
	 * @see Cut
	 */
	public Range(Cut min, Cut max)
	{
		this.min = min;
		this.max = max;
	}
	
	
	/**
	 * Checks for containment with a {@code Range}.
	 * 
	 * @param r  a second range
	 * @return  {@code true} if contained
	 */
	public boolean contains(Range r)
	{
		return !r.min.isBefore(min)
			&& !r.max.isAfter(max);
	}
	
	/**
	 * Checks for intersection with a {@code Range}.
	 * 
	 * @param r  a second range
	 * @return  {@code false} if the ranges connect
	 */
	public boolean isDisjoint(Range r)
	{
		return r.max.isBefore(min)
			|| max.isBefore(r.min);
	}

	/**
	 * Creates the intersect with a {@code Range}.
	 * 
	 * @param r  a second range
	 * @return  an intersection
	 */
	public Range intersect(Range r)
	{		
		if(isDisjoint(r))
		{
			return Ranges.EMPTY;
		}
		
		if(min.isBefore(r.min))
		{
			if(max.isBefore(r.max))
			{
				return new Range(r.min, max);
			}
			
			return r;
		}

		if(max.isAfter(r.max))
		{
			return new Range(min, r.max);
		}
		
		return this;
	}
	
	/**
	 * Creates the span with a {@code Range}.
	 * The result contains both given ranges.
	 * 
	 * @param r  a second range
	 * @return   a spanning range
	 */
	public Range span(Range r)
	{
		if(min.isBefore(r.min))
		{
			if(max.isBefore(r.max))
			{
				return new Range(min, r.max);
			}
			
			return this;
		}

		if(max.isAfter(r.max))
		{
			return new Range(r.min, max);
		}
		
		return r;
	}
	
		
	/**
	 * Returns the minimum of the {@code Range}.
	 * 
	 * @return  a minimum cut
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Minimum()
	{
		return min;
	}
	
	/**
	 * Returns the maximum of the {@code Range}.
	 * 
	 * @return  a maximum cut
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Maximum()
	{
		return max;
	}
	
	/**
	 * Returns the length of the {@code Range}.
	 * The cut is taken above its value if and
	 * only if the range is closed.
	 * 
	 * @return  a range length
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Length()
	{
		return max.minus(min);
	}
	
	
	@Override
	public String toString()
	{
		return condense();
	}
	
	@Override
	public Format<Range> Formatter()
	{
		return new RangeFormat(DEF_FORMAT);
	}
	
	@Override
	public int compareTo(Float v)
	{
		if(min.isAfter(v))
			return +1;
		if(max.isBefore(v))
			return -1;

		return 0;
	}
		
	@Override
	public Range minus(Range r)
	{
		Cut c1 = Minimum().minus(r.Maximum());
		Cut c2 = Maximum().minus(r.Minimum());
		
		return Ranges.create(c1, c2);
	}
	
	@Override
	public Range times(Float v)
	{
		Cut c1 = Minimum().times(v);
		Cut c2 = Maximum().times(v);

		return Ranges.create(c1, c2);
	}
	
	String parse(float v)
	{
		return String.format("%1$"+ (6 + 4 + 1) +"." + 4 + "f", v);		
	}
}