package waffles.utils.alg.reals.ranges.trees;

import java.util.Iterator;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;

/**
 * A {@code DRIterator} iterates over the intervals of a {@code DRTree}.
 *
 * @author Waffles
 * @since 17 Aug 2020
 * @version 1.0
 * 
 * 
 * @see Iterator
 * @see Range
 */
public class DRIterator implements Iterator<Range>
{
	private Iterator<DRNode> nodes;

	/**
	 * Creates a new {@code DRIterator}.
	 * 
	 * @param tree  a target tree
	 * 
	 * 
	 * @see DRTree
	 */
	public DRIterator(DRTree tree)
	{
		Iterable<DRNode> order = tree.inorder();
		nodes = order.iterator();
	}

	
	@Override
	public boolean hasNext()
	{
		return nodes.hasNext();
	}

	@Override
	public Range next()
	{
		Cut min = nodes.next().Cut();
		Cut max = nodes.next().Cut();
		return Ranges.create(min, max);
	}
}