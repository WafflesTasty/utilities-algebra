package waffles.utils.algebra.elements.interval;

import waffles.utils.algebra.elements.interval.cuts.AboveAll;
import waffles.utils.algebra.elements.interval.cuts.AboveValue;
import waffles.utils.algebra.elements.interval.cuts.BelowAll;
import waffles.utils.algebra.elements.interval.cuts.BelowValue;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Cuts} class defines static-access utilities for {@code Cut} objects.
 * 
 * @author Waffles
 * @since Apr 25, 2017
 * @version 1.0
 */
public class Cuts
{
	/**
	 * Defines a {@code Cut} above all real numbers.
	 */
	public static Cut ABOVE_ALL = new AboveAll();
	
	/**
	 * Defines a {@code Cut} below all real numbers.
	 */
	public static Cut BELOW_ALL = new BelowAll();
	
	/**
	 * Defines a {@code Cut} above a real number.
	 * 
	 * @param value  a real value
	 * @return  a cut above
	 * 
	 * 
	 * @see Cut
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
	 * Defines a {@code Cut} below a real number.
	 * 
	 * @param value  a real value
	 * @return  a cut below
	 * 
	 * 
	 * @see Cut
	 */
	public static Cut Below(float value)
	{
		if(value == Floats.NEG_INFINITY)
			return BELOW_ALL;
		if(value == Floats.POS_INFINITY)
			return ABOVE_ALL;
		
		return new BelowValue(value);
	}
}