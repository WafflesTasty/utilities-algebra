package waffles.utils.algebra.elements.interval.cuts;

import waffles.utils.algebra.elements.interval.Cut;

/**
 * The {@code AboveValue} cut extends right above a finite real number.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Cut
 */
public class AboveValue extends Cut
{
	/**
	 * Creates a new {@code AboveValue}.
	 * 
	 * @param value  a cut value
	 */
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
		if(isAbove(val))
			return 1;
		return -1;
	}
	
	@Override
	public int compareTo(Cut c)
	{
		if(isAbove(c.value()))
		{
			if(c.isAbove(value()))
				return 0;
			return 1;
		}
		
		return -1;
	}
}