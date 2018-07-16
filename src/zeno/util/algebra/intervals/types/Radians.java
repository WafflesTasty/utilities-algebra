package zeno.util.algebra.intervals.types;

import zeno.util.algebra.intervals.Cut;
import zeno.util.algebra.intervals.Interval;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Radians} class defines an interval across all radian values.
 * It includes the lower bound -Pi, and excludes the upper bound Pi.
 * 
 * @author Zeno
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
		super(Cut.Below(-Floats.PI), Cut.Below(Floats.PI));
	}	
}