package waffles.utils.algebra.utilities.iterators;

import java.util.Iterator;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.trees.DRTree;
import waffles.utils.sets.trees.binary.BSNode;

/**
 * A {@code DRIterator} iterates over the intervals of a {@code DRTree}.
 *
 * @author Waffles
 * @since 17 Aug 2020
 * @version 1.0
 * 
 * 
 * @see Interval
 * @see Iterator
 */
public class DRIterator implements Iterator<Interval>
{
	private Iterator<BSNode<Cut>> nodes;

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
		nodes = tree.inorder().iterator();
	}

	
	@Override
	public boolean hasNext()
	{
		return nodes.hasNext();
	}

	@Override
	public Interval next()
	{
		Cut min = nodes.next().Value();
		Cut max = nodes.next().Value();
		return new Interval(min, max);
	}
}