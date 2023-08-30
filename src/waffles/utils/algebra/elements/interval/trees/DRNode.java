package waffles.utils.algebra.elements.interval.trees;

import waffles.util.sets.trees.binary.BSNode;
import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.Intervals;
import waffles.utils.algebra.elements.interval.format.FMTInterval;
import waffles.utils.lang.enums.Extreme;

/**
 * A {@code DRNode} defines a single node in a disjoint range tree.
 * Each node defines a {@code Cut} of the real line together with
 * an {@code Extreme} which governs whether the cut is at the
 * start or end of a disjoint interval.
 * 
 * @author Waffles
 * @since Jul 1, 2017
 * @version 1.0
 * 
 * 
 * @see BSNode
 * @see Cut
 */
public class DRNode extends BSNode<Cut>
{	
	private Extreme extreme;
	
	/**
	 * Creates a new {@code DRNode}.
	 * 
	 * @param t  a source tree
	 * @param cut  a real line cut
	 * @param ex   an interval extreme
	 * 
	 * 
	 * @see Extreme
	 * @see DRTree
	 * @see Cut
	 */
	public DRNode(DRTree t, Cut cut, Extreme ex)
	{
		super(t, cut); extreme = ex;
	}
	
	/**
	 * Returns the {@code DRNode} extreme.
	 * 
	 * @return  an interval extreme
	 * 
	 * 
	 * @see Extreme
	 */
	public Extreme Extreme()
	{
		return extreme;
	}


	@Override
	public String toString()
	{
		switch(Extreme())
		{
		case MIN:
		{
			FMTInterval fmt = new FMTInterval("(§llllll", "§");
			Interval ival = Intervals.create(Value(), null);
			return fmt.parse(ival);
		}
		case MAX:
		{
			FMTInterval fmt = new FMTInterval("uuuuuu§)", "§");
			Interval ival = Intervals.create(null, Value());
			return fmt.parse(ival);
		}
		default:
			return null;
		}
	}
	
	@Override
	public DRNode Sibling()
	{
		return (DRNode) super.Sibling();
	}
	
	@Override
	public DRNode LChild()
	{
		return (DRNode) super.LChild();
	}
	
	@Override
	public DRNode RChild()
	{
		return (DRNode) super.RChild();
	}

	@Override
	public DRNode Parent()
	{
		return (DRNode) super.Parent();
	}

	@Override
	public DRNode Root()
	{
		return (DRNode) super.Root();
	}
}