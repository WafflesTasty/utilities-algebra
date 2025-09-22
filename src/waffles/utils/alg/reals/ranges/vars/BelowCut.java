package waffles.utils.alg.reals.ranges.vars;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;

/**
 * A {@code BelowCut} is a range without a lower bound.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Range
 */
public class BelowCut extends Range
{
	/**
	 * Creates a new {@code BelowCut}.
	 * 
	 * @param max  a maximum cut
	 * 
	 * 
	 * @see Cut
	 */
	public BelowCut(Cut max)
	{
		super(Cut.BELOW_ALL, max);
	}
}