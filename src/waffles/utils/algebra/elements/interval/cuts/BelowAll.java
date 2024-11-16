package waffles.utils.algebra.elements.interval.cuts;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code BelowAll} cut extends below all numbers on the real number line.
 *
 * @author Waffles
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