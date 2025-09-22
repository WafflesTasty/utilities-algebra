package waffles.utils.alg.reals.cuts.fixed;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.tools.primitives.Floats;

/**
 * An {@code AboveAll} cut extends above all numbers on the real number line.
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
	public boolean equals(Object o)
	{
		return o == Cut.ABOVE_ALL;
	}

	@Override
	public int compareTo(Float v)
	{
		if(Floats.isFinite(v))
			return +1;
		return 0;
	}
	
	@Override
	public int compareTo(Cut c)
	{
		if(equals(c))
			return 0;
		return +1;
	}
}