package waffles.utils.alg.reals.ranges.vars;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;

/**
 * An {@code AboveCut} is a range without an upper bound.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Range
 */
public class AboveCut extends Range
{
	/**
	 * Creates a new {@code AboveCut}.
	 * 
	 * @param min  a minimum cut
	 * 
	 * 
	 * @see Cut
	 */
	public AboveCut(Cut min)
	{
		super(min, Cut.ABOVE_ALL);
	}
}