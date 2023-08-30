package waffles.utils.algebra.elements.interval.fixed;

import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Radians} class defines an interval that covers all radian values.
 * It includes the lower bound {@code -Pi}, and excludes the upper bound {@code Pi}.
 * 
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Interval
 */
public class Radians extends Interval
{
	/**
	 * Creates a new {@code Radians}.
	 */
	public Radians()
	{
		super(Cuts.Below(-Floats.PI), Cuts.Below(Floats.PI));
	}	
}