package waffles.utils.algebra.elements.interval.cuts;

import waffles.utils.algebra.elements.interval.Cut;

/**
 * The {@code BelowValue} cut extends right below a finite real number.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Cut
 */
public class BelowValue extends Cut
{
	/**
	 * Creates a new {@code BelowValue}.
	 * 
	 * @param value  a cut value
	 */
	public BelowValue(float value)
	{
		super(value);
	}


	@Override
	public int compareTo(float val)
	{
		if(val < value())
			return 1;
		return -1;
	}
	
	@Override
	public int compareTo(Cut c)
	{
		if(isBelow(c.value()))
		{
			if(c.isBelow(value()))
				return 0;
			return -1;
		}
		
		return 1;
	}
}