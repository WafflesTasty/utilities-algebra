package waffles.utils.algebra.elements.interval.trees;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.lang.enums.Extreme;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code DANode} defines a single node in a disjoint angle tree.
 * 
 * @author Waffles
 * @since Jul 1, 2017
 * @version 1.0
 * 
 * 
 * @see DRNode
 */
public class DANode extends DRNode
{
	/**
	 * Creates a new {@code DANode}.
	 * 
	 * @param tree  a parent tree
	 * @param cut  an real line cut
	 * @param ex   an interval extreme
	 * 
	 * 
	 * @see Extreme
	 * @see DATree
	 * @see Cut
	 */
	public DANode(DATree tree, Cut cut, Extreme ex)
	{
		super(tree, cut, ex);
	}

	
	@Override
	public String toString()
	{
		Cut cut = Value();
		
		float val = cut.value() / Floats.PI;
		switch(Extreme())
		{
		case MIN:
		{
			if(cut.isBelow(cut.value()))
				return "[" + val + "Pi.";
			return "(" + val + "Pi.";
		}
		case MAX:
		{
			if(cut.isAbove(cut.value()))
				return "." + val + "Pi]";
			return "." + val + "Pi)";
		}
		default:
			return null;
		}
	}
	
	@Override
	public DANode Parent()
	{
		return (DANode) super.Parent();
	}
}