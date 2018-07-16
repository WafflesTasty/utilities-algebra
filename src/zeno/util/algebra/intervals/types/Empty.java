package zeno.util.algebra.intervals.types;

import zeno.util.algebra.intervals.Cut;
import zeno.util.algebra.intervals.Interval;

/**
 * The {@code Empty} class defines an interval that is empty.
 *
 * @author Zeno
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Interval
 */
public class Empty extends Interval
{
	/**
	 * Creates a new {@code Empty}.
	 */
	public Empty()
	{
		super(Cut.Below(0), Cut.Below(0));
	}
	
	
	@Override
	public Interval span(Interval range)
	{
		return range;
	}
	
	@Override
	public Interval intersection(Interval range)
	{
		return this;
	}
	

	@Override
	public boolean isDisjoint(Interval range)
	{
		return true;
	}
	
	@Override
	public boolean contains(Interval range)
	{
		return equals(range);
	}
	
	@Override
	public boolean contains(float val)
	{
		return false;
	}
	
	@Override
	public boolean isEmpty()
	{
		return true;
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
		return 2017;
	}
}