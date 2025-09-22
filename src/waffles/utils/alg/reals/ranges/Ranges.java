package waffles.utils.alg.reals.ranges;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.fixed.All;
import waffles.utils.alg.reals.ranges.fixed.Empty;
import waffles.utils.alg.reals.ranges.fixed.Radians;
import waffles.utils.alg.reals.ranges.vars.AboveCut;
import waffles.utils.alg.reals.ranges.vars.BelowCut;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Ranges} class defines static-access utilities for {@code Range} objects.
 * 
 * @author Waffles
 * @since Apr 25, 2017
 * @version 1.0
 */
public final class Ranges
{
	/**
	 * Defines a {@code Range} over the real line.
	 * 
	 * 
	 * @see All
	 */
	public static Range ALL = new All();
	
	/**
	 * Defines a {@code Range} over radian values.
	 * 
	 * 
	 * @see Radians
	 */
	public static Range RADIANS = new Radians();
	
	/**
	 * Defines an empty {@code Range}.
	 * 
	 * 
	 * @see Empty
	 */
	public static Range EMPTY = new Empty();
	
	
	/**
	 * Creates a new {@code Range} from two cuts.
	 * If one or both of the cuts are null, they are
	 * treated as unbounded values ±infinity.
	 * 
	 * @param min  a minimum cut
	 * @param max  a maximum cut
	 * @return  a new range
	 * 
	 * 
	 * @see Range
	 * @see Cut
	 */
	public static Range create(Cut min, Cut max)
	{
		// Swap null values for unbounded cuts.
		Cut cMin = (min != null ? min : Cut.BELOW_ALL);
		Cut cMax = (max != null ? max : Cut.ABOVE_ALL);
		
		if(cMin.equals(cMax))
			return EMPTY;
		if(cMax.isBefore(cMin))
			return null;
		
		if(cMin == Cut.BELOW_ALL)
		{
			if(cMax == Cut.ABOVE_ALL)
				return ALL;
			
			return new BelowCut(cMax);
		}

		if(cMax == Cut.ABOVE_ALL)
		{
			return new AboveCut(cMin);
		}
		
		return new Range(cMin, cMax);
	}
	
	/**
	 * Normalizes a {@code Range} into radian values.
	 * The resulting ranges together cover the same angle
	 * within the range of {@code -Pi} to {@code Pi}.
	 * 
	 * @param r  a range
	 * @return   a radian range
	 * 
	 * 
	 * @see Range
	 */
	public static Range[] toRadians(Range r)
	{
		// If the interval is larger than 2 * PI...
		if(r.Length().isAfter(2 * Floats.PI))
		{
			// Return the full radian range.
			return new Range[]{RADIANS};
		}
		
		
		Cut min = r.Minimum();
		Cut max = r.Maximum();
				
		float vmin = Floats.normrad(min.Value());
		float vmax = Floats.normrad(max.Value());
		
		// Create normalized cuts of the original interval.
		min = (min.isAfter(min.Value()) ? Cut.After(vmin) : Cut.Before(vmin));
		max = (max.isAfter(max.Value()) ? Cut.After(vmax) : Cut.Before(vmax));
		
		// If the minimum is above the maximum...
		if(max.isBefore(min))
		{
			// Split the result into two intervals.
			return new Range[]
			{
				create(Cut.Before(-Floats.PI), max),
				create(min, Cut.Before(Floats.PI))
			};
		}
		
		// Otherwise, return the interval.
		return new Range[]
		{
			create(min, max)
		};		
	}
	
	
	private Ranges()
	{
		// NOT APPLICABLE
	}
}
