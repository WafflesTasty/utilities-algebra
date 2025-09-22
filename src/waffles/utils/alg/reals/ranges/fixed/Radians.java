package waffles.utils.alg.reals.ranges.fixed;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Radians} range covers all radian values.
 * It includes {@code -Pi}, and excludes {@code Pi}.
 * 
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Range
 */
public class Radians extends Range
{
	/**
	 * Creates a new {@code Radians}.
	 */
	public Radians()
	{
		super(Cut.Before(-Floats.PI), Cut.Before(Floats.PI));
	}	
}