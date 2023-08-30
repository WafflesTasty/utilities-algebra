package waffles.utils.algebra.elements.interval.fixed;

import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;

/**
 * The {@code All} class defines an interval that covers the entire real number line.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Interval
 */
public class All extends Interval
{
	/**
	 * Creates a new {@code All}.
	 */
	public All()
	{
		super(Cuts.BELOW_ALL, Cuts.ABOVE_ALL);
	}
	
	
	@Override
	public Interval span(Interval range)
	{
		return this;
	}
			
	@Override
	public Interval intersect(Interval range)
	{
		return range;
	}
	
			
	@Override
	public boolean isDisjoint(Interval range)
	{
		return false;
	}
	
	@Override
	public boolean contains(Interval range)
	{
		return true;
	}
	
	@Override
	public boolean contains(float val)
	{
		return true;
	}
			
	@Override
	public boolean isEmpty()
	{
		return false;
	}

	
	@Override
	public int compareTo(Interval range)
	{
		return 0;
	}
		
	@Override
	public boolean equals(Object o)
	{
		return o == this;
	}
	
	@Override
	public int hashCode()
	{
		return 2111;
	}
}