package zeno.util.algebra.intervals.types;

import zeno.util.algebra.intervals.Cut;
import zeno.util.algebra.intervals.Interval;

/**
 * The {@code BelowCut} class defines an interval that has no lower bound.
 *
 * @author Zeno
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Interval
 */
public class BelowCut extends Interval
{
	/**
	 * Creates a new {@code BelowCut}.
	 * 
	 * @param max  a maximum cut
	 * @see Cut
	 */
	public BelowCut(Cut max)
	{
		super(Cut.BELOW_ALL, max);
	}

			
	@Override
	public Interval span(Interval range)
	{
		int cmax = max().compareTo(range.max());
		if(cmax < 0)
		{
			return new Interval(min(), range.max());
		}
		
		return this;
	}
	
	@Override
	public Interval intersection(Interval range)
	{
		if(isDisjoint(range))
		{
			return null;
		}
		
		
		int cmax = max().compareTo(range.max());
		if(cmax < 0)
		{
			return new Interval(range.min(), max());
		}
		
		return range;
	}
	
	
	@Override
	public boolean contains(Interval range)
	{
		return max().compareTo(range.max()) >= 0;
	}
	
	@Override
	public boolean contains(float val)
	{
		return max().isAbove(val);
	}
	
	@Override
	public int compareTo(Interval r)
	{
		return (max().compareTo(r.min()) < 0 ? -1 : 0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return false;
	}
}