package zeno.util.algebra.intervals.types;

import zeno.util.algebra.intervals.Cut;
import zeno.util.algebra.intervals.Interval;

/**
 * The {@code AboveCut} class defines an interval that has no upper bound.
 *
 * @author Zeno
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Interval
 */
public class AboveCut extends Interval
{
	/**
	 * Creates a new {@code AboveCut}.
	 * 
	 * @param min  a minimum cut
	 * @see Cut
	 */
	public AboveCut(Cut min)
	{
		super(min, Cut.ABOVE_ALL);
	}
	
	
	@Override
	public Interval span(Interval range)
	{
		int cmin = min().compareTo(range.min());
		if(cmin < 0)
		{
			return new Interval(range.min(), max());
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
		
		
		int cmin = min().compareTo(range.min());
		if(cmin < 0)
		{
			return new Interval(min(), range.max());
		}
		
		return range;
	}
	
	
	@Override
	public boolean contains(Interval range)
	{
		return min().compareTo(range.min()) <= 0;
	}
	
	@Override
	public boolean contains(float val)
	{
		return min().isBelow(val);
	}
	
	@Override
	public int compareTo(Interval r)
	{
		return (min().compareTo(r.max()) > 0 ? 1 : 0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return false;
	}
}