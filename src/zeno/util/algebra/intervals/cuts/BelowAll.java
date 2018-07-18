package zeno.util.algebra.intervals.cuts;

import zeno.util.algebra.intervals.Cut;
import zeno.util.tools.Floats;

/**
 * The {@code BelowAll} class defines a {@code Cut} below all real numbers.
 *
 * @author Zeno
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Cut
 */
public class BelowAll extends Cut
{
	/**
	 * Creates a new {@code BelowAll}.
	 */
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