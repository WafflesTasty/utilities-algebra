package zeno.util.algebra.intervals.cuts;

import zeno.util.algebra.intervals.Cut;

/**
 * The {@code BelowValue} class defines a {@code Cut} below a real number.
 *
 * @author Zeno
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
	 * @param value  a value to cut
	 */
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
		if(isAbove(val))
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