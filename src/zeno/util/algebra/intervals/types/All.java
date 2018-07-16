package zeno.util.algebra.intervals.types;

import zeno.util.algebra.intervals.Cut;
import zeno.util.algebra.intervals.Interval;

/**
 * The {@code All} class defines an interval that covers all real numbers.
 *
 * @author Zeno
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
		super(Cut.BELOW_ALL, Cut.ABOVE_ALL);
	}
	
	
	@Override
	public Interval span(Interval range)
	{
		return this;
	}
			
	@Override
	public Interval intersection(Interval range)
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