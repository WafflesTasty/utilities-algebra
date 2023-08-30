package waffles.utils.algebra.elements.interval.fixed;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;

/**
 * The {@code AboveCut} class defines an interval that has no upper bound.
 *
 * @author Waffles
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
	 * 
	 * 
	 * @see Cut
	 */
	public AboveCut(Cut min)
	{
		super(min, Cuts.ABOVE_ALL);
	}
	
	
	@Override
	public Interval span(Interval range)
	{
		int cmin = Minimum().compareTo(range.Minimum());
		if(cmin < 0)
		{
			return new Interval(range.Minimum(), Maximum());
		}
		
		return this;
	}
	
	@Override
	public Interval intersect(Interval range)
	{
		if(isDisjoint(range))
		{
			return null;
		}
		
		
		int cmin = Minimum().compareTo(range.Minimum());
		if(cmin < 0)
		{
			return new Interval(Minimum(), range.Maximum());
		}
		
		return range;
	}
	
	
	@Override
	public boolean contains(Interval range)
	{
		return Minimum().compareTo(range.Minimum()) <= 0;
	}
	
	@Override
	public boolean contains(float val)
	{
		return Minimum().isBelow(val);
	}
	
	@Override
	public int compareTo(Interval r)
	{
		return (Minimum().compareTo(r.Maximum()) > 0 ? 1 : 0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return false;
	}
}