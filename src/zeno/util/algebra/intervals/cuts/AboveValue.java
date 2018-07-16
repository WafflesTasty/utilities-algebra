package zeno.util.algebra.intervals.cuts;

import zeno.util.algebra.intervals.Cut;

/**
 * The {@code AboveValue} class defines a {@code Cut} above a real number.
 *
 * @author Zeno
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
	 * @param value  a value to cut
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