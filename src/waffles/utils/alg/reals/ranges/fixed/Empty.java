package waffles.utils.alg.reals.ranges.fixed;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;

/**
 * An {@code Empty} range contains no values.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Range
 */
public class Empty extends Range
{
	/**
	 * Creates a new {@code Empty}.
	 */
	public Empty()
	{
		super(Cut.Before(0), Cut.Before(0));
	}
	
	
	@Override
	public boolean isDisjoint(Range r)
	{
		return true;
	}
	
	@Override
	public Range intersect(Range r)
	{
		return this;
	}
	
	@Override
	public Range span(Range r)
	{
		return r;
	}
}