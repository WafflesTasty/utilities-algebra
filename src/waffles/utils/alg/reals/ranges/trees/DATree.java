package waffles.utils.alg.reals.ranges.trees;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;
import waffles.utils.lang.utilities.enums.Extreme;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code DATree} defines a disjoint angle tree.
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
	public void add(Range r)
	{
		Range[] ranges = Ranges.toRadians(r);
		for(Range s : ranges)
		{
			super.add(s);
		}
	}
	
	@Override
	public void remove(Range r)
	{
		Range[] ranges = Ranges.toRadians(r);
		for(Range s : ranges)
		{
			super.remove(s);
		}
	}
	
	
	@Override
	public boolean contains(float v)
	{
		return super.contains(Floats.normrad(v));
	}
	
	@Override
	public boolean intersects(Range r)
	{
		Range[] ranges = Ranges.toRadians(r);
		for(Range s : ranges)
		{
			if(super.intersects(s))
			{
				return true;
			}
		}
		
		return false;
	}
		
	
	@Override
	public DANode createNode(Object... vals)
	{
		Cut cut = (Cut) vals[0];
		Extreme ex = (Extreme) vals[1];
		return new DANode(this, cut, ex);
	}
}