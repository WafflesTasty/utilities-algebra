package waffles.utils.algebra.elements.interval.fixed;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;

/**
 * The {@code BelowCut} class defines an interval that has no lower bound.
 *
 * @author Waffles
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
	 * 
	 * 
	 * @see Cut
	 */
	public BelowCut(Cut max)
	{
		super(Cuts.BELOW_ALL, max);
	}

			
	@Override
	public Interval span(Interval range)
	{
		int cmax = Maximum().compareTo(range.Maximum());
		if(cmax < 0)
		{
			return new Interval(Minimum(), range.Maximum());
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
		
		
		int cmax = Maximum().compareTo(range.Maximum());
		if(cmax < 0)
		{
			return new Interval(range.Minimum(), Maximum());
		}
		
		return range;
	}
	
	
	@Override
	public boolean contains(Interval range)
	{
		return Maximum().compareTo(range.Maximum()) >= 0;
	}
	
	@Override
	public boolean contains(float val)
	{
		return Maximum().isAbove(val);
	}
	
	@Override
	public int compareTo(Interval r)
	{
		return (Maximum().compareTo(r.Minimum()) < 0 ? -1 : 0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return false;
	}
}