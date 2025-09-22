package waffles.utils.alg.reals.ranges.fixed;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;

/**
 * An {@code All} range covers the real number line.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Range
 */
public class All extends Range
{
	/**
	 * Creates a new {@code All}.
	 */
	public All()
	{
		super(Cut.BELOW_ALL, Cut.ABOVE_ALL);
	}
	
	
	@Override
	public boolean isDisjoint(Range r)
	{
		return false;
	}
	
	@Override
	public Range intersect(Range r)
	{
		return r;
	}
	
	@Override
	public Range span(Range r)
	{
		return this;
	}
}