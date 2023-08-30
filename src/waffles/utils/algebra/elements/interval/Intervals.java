package waffles.utils.algebra.elements.interval;

import waffles.utils.algebra.elements.interval.fixed.AboveCut;
import waffles.utils.algebra.elements.interval.fixed.All;
import waffles.utils.algebra.elements.interval.fixed.BelowCut;
import waffles.utils.algebra.elements.interval.fixed.Empty;
import waffles.utils.algebra.elements.interval.fixed.Radians;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Intervals} class defines static-access utilities for {@code Interval} objects.
 * 
 * @author Waffles
 * @since Apr 25, 2017
 * @version 1.0
 */
public class Intervals
{
	/**
	 * Defines an {@code Interval} over the entire real line.
	 */
	public static Interval ALL = new All();
	
	/**
	 * Defines a radian angle {@code Interval}.
	 * It spans from {@code -Pi} to {@code Pi}.
	 */
	public static Interval RADIANS = new Radians();
	
	/**
	 * Defines an empty {@code Interval}.
	 */
	public static Interval EMPTY = new Empty();	
		
				
	/**
	 * Parses a new {@code Interval} from a string.
	 * The format of the string follows the example (0.0, 10.5].
	 * The boundaries of the interval are denoted by '(' or ')' for
	 * an open cut, and '[' or ']' for a closed cut.
	 * 
	 * @param text  an interval format
	 * @return  a new interval
	 * 
	 * 
	 * @see Interval
	 * @see String
	 */
	public static Interval parse(String text)
	{
		String[] split = text.split(",");
		
		int len0 = split[0].length();
		int len1 = split[1].length();
		
		
		char bmin = split[0].charAt(0);
		char bmax = split[1].charAt(len1 - 1);
		
		float min = Floats.parse(split[0].substring(1, len0));
		float max = Floats.parse(split[1].substring(0, len1 - 1));
		
		if(bmin != '(' && bmin != '[') return null;
		if(bmax != ')' && bmax != ']') return null;
		
		Cut cmin = (bmin == '(' ? Cuts.Above(min) : Cuts.Below(min));
		Cut cmax = (bmax == ')' ? Cuts.Below(max) : Cuts.Above(max));
		
		return create(cmin, cmax);
	}
	
	/**
	 * Creates a new {@code Interval} from two cuts.
	 * If one or both of the cuts are null, they are
	 * treated as unbounded values ±infinity.
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
		Cut cMin = (min != null ? min : Cuts.BELOW_ALL);
		Cut cMax = (max != null ? max : Cuts.ABOVE_ALL);
		
		// Compare both cuts in natural order...
		int compare = cMin.compareTo(cMax);
		// ...if equal, return an empty range.
		if(compare == 0) return EMPTY;
		// ...if invalid, return null.
		if(compare > 0) return null;
		
		// If the minimum is unbounded...
		if(cMin == Cuts.BELOW_ALL)
		{
			// If the maximum is unbounded...
			if(cMax == Cuts.ABOVE_ALL)
			{
				// ...return a full range.
				return ALL;
			}
			
			// ...return a semibounded range.
			return new BelowCut(cMax);
		}
		
		// If the maximum is unbounded...
		if(cMax == Cuts.ABOVE_ALL)
		{
			// ...return a semibounded range.
			return new AboveCut(cMin);
		}
		
		// Otherwise, return a bounded range.
		return new Interval(cMin, cMax);
	}
		
	/**
	 * Normalizes an interval into radian angle values.
	 * The resulting intervals together cover the same angle
	 * as the original in the range of {@code -Pi} to {@code Pi}.
	 * 
	 * @param ival  an interval range
	 * @return  radian angle intervals
	 * 
	 * 
	 * @see Interval
	 */
	public static Interval[] toRadians(Interval ival)
	{
		// If the interval is larger than 2*PI...
		if(ival.Length().isAbove(2 * Floats.PI))
		{
			// Return the full radian range.
			return new Interval[]{RADIANS};
		}
		
		
		Cut min = ival.Minimum();
		Cut max = ival.Maximum();
				
		float vmin = Floats.normrad(min.value());
		float vmax = Floats.normrad(max.value());
		
		// Create normalized cuts of the original interval.
		min = (min.isAbove(min.value()) ? Cuts.Above(vmin) : Cuts.Below(vmin));
		max = (max.isAbove(max.value()) ? Cuts.Above(vmax) : Cuts.Below(vmax));
		
		// If the minimum is above the maximum...
		if(min.compareTo(max) > 0)
		{
			// Split the result into two intervals.
			return new Interval[]
			{
				create(Cuts.Below(-Floats.PI), max),
				create(min, Cuts.Below(Floats.PI))
			};
		}
		
		// Otherwise, return the interval.
		return new Interval[]
		{
			create(min, max)
		};		
	}
	
	/**
	 * Creates a singleton {@code Interval}.
	 * 
	 * @param val  a singleton value
	 * @return  a singleton interval
	 * 
	 * 
	 * @see Interval
	 */
	public static Interval singleton(float val)
	{
		return parse("[" + val + ", " + val + "]");
	}
}