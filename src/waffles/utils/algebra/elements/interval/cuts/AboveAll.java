package waffles.utils.algebra.elements.interval.cuts;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code AboveAll} cut extends above all numbers on the real number line.
 *
 * @author Waffles
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