package waffles.utils.algebra.elements.interval.trees;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.Intervals;
import waffles.utils.lang.enums.Extreme;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code DATree} class defines a disjoint angle tree.
 * This tree works the same as a {@link DRTree}, except
 * all intervals are normalized within the range
 * of {@code -PI} to {@code PI}.
 * 
 * @author Waffles
 * @since Jul 2, 2017
 * @version 1.0
 * 
 * 
 * @see DRTree
 */
public class DATree extends DRTree
{	
	@Override
	public void delete(Interval ival)
	{
		Interval[] ranges = Intervals.toRadians(ival);
		for(Interval range : ranges)
		{
			super.delete(range);
		}
	}
	
	@Override
	public void insert(Interval ival)
	{
		Interval[] ranges = Intervals.toRadians(ival);
		for(Interval range : ranges)
		{
			super.insert(range);
		}
	}
	
	
	@Override
	public DANode createNode(Object... vals)
	{
		Cut cut = (Cut) vals[0];
		Extreme ex = (Extreme) vals[1];
		return new DANode(this, cut, ex);
	}
	
	@Override
	public boolean intersects(Interval ival)
	{
		Interval[] ranges = Intervals.toRadians(ival);
		for(Interval range : ranges)
		{
			if(super.intersects(range))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean contains(float value)
	{
		return super.contains(Floats.normrad(value));
	}
}