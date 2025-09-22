package waffles.utils.alg.reals.cuts.fixed;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code BelowAll} cut extends below all numbers on the real number line.
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
	public boolean equals(Object o)
	{
		return o == Cut.BELOW_ALL;
	}

	@Override
	public int compareTo(Float val)
	{
		if(Floats.isFinite(val))
			return -1;
		return 0;
	}
	
	@Override
	public int compareTo(Cut c)
	{
		if(equals(c))
			return 0;
		return -1;
	}
}