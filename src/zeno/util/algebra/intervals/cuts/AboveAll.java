package zeno.util.algebra.intervals.cuts;

import zeno.util.algebra.intervals.Cut;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code AboveAll} class defines a {@code Cut} above all real numbers.
 *
 * @author Zeno
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Cut
 */
public class AboveAll extends Cut
{
	/**
	 * Creates a new {@code AboveAll}.
	 */
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