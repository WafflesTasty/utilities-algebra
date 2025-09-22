package waffles.utils.alg.reals.ranges.trees;

import waffles.utils.alg.reals.cuts.Bracket;
import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;
import waffles.utils.lang.tokens.Token;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.lang.utilities.enums.Extreme;
import waffles.utils.sets.rooted.binary.search.IOTree;

/**
 * A {@code DRTree} defines a disjoint range tree.
 * This tree can be used to store a set of disjoint
 * ranges, and applying set operations on them.
 * 
 * @author Waffles
 * @since Jun 30, 2017
 * @version 1.0
 * 
 * 
 * @see Bracket
 * @see DRNode
 * @see IOTree
 * @see Token
 */
public class DRTree extends IOTree<DRNode, Bracket> implements Token
{
	/**
	 * Adds a range to the {@code DRTree}.
	 * 
	 * @param r  a range
	 * 
	 * 
	 * @see Range
	 */
	public void add(Range r)
	{		
		Cut c1 = r.Minimum();
		Cut c2 = r.Maximum();
		
		DRNode n1 = new DRNode(this, c1, Extreme.MIN);
		DRNode n2 = new DRNode(this, c2, Extreme.MAX);

		if(isEmpty())
		{
			n1.setRChild(n2);
			setRoot(n1);
			return;
		}
		
		
		DRNode b1 = search(n1.Value());
		DRNode b2 = search(n2.Value());

		b1 = (compare(n1, b1) < 0 ? b1.prev() : b1);
		b2 = (compare(b2, n2) < 0 ? b2.next() : b2);

		if(b1 == null)
		{
			if(b2 == null)
			{
				clear();
				
				n1.setRChild(n2);
				setRoot(n1);
				return;
			}
			
			mergeBelow(b2);
			b2.setLChild(n1);
			if(b2.Extreme() == Extreme.MIN)
				n1.setRChild(n2);
			return;
		}
		
		if(b2 == null)
		{
			mergeAbove(b1);
			b1.setRChild(n2);
			if(b1.Extreme() == Extreme.MAX)
				n2.setLChild(n1);
			return;
		}

		merge(b1, b2);
		if(b1.Depth() < b2.Depth())
		{
			if(b2.Extreme() == Extreme.MIN)
			{
				b2.setLChild(n2);
				if(b1.Extreme() == Extreme.MAX)
					n2.setLChild(n1);
				return;
			}
			
			if(b1.Extreme() == Extreme.MAX)
				b2.setLChild(n1);			
			return;
		}

		if(b1.Extreme() == Extreme.MAX)
		{
			b1.setRChild(n1);
			if(b2.Extreme() == Extreme.MIN)
				n1.setRChild(n2);
			return;
		}
		
		if(b2.Extreme() == Extreme.MIN)
			b1.setRChild(n2);
	}
	
	/**
	 * Removes a range from the {@code DRTree}.
	 * 
	 * @param r  a range
	 * 
	 * 
	 * @see Range
	 */
	public void remove(Range r)
	{
		Cut c1 = r.Minimum();
		Cut c2 = r.Maximum();
		
		DRNode n1 = new DRNode(this, c1, Extreme.MAX);
		DRNode n2 = new DRNode(this, c2, Extreme.MIN);

		if(isEmpty())
		{
			return;
		}
		
		
		DRNode s1 = search(n1.Value());
		DRNode s2 = search(n2.Value());

		DRNode b1 = (compare(n1, s1) < 0 ? s1.prev() : s1);
		DRNode b2 = (compare(s2, n2) < 0 ? s2.next() : s2);

		if(b1 == null)
		{
			if(b2 == null)
			{
				clear();
				return;
			}
			
			mergeBelow(b2);
			if(b2.Extreme() == Extreme.MAX)
				b2.setLChild(n2);
			return;
		}
		
		if(b2 == null)
		{
			mergeAbove(b1);
			if(b1.Extreme() == Extreme.MIN)
				b1.setRChild(n1);
			return;
		}

		merge(b1, b2);
		if(b1.Depth() < b2.Depth())
		{
			if(b2.Extreme() == Extreme.MAX)
				b2.setLChild(n2);
			if(b1.Extreme() == Extreme.MIN)
				n2.setLChild(n1);
			return;
		}

		if(b1.Extreme() == Extreme.MIN)
			b1.setRChild(n1);
		if(b2.Extreme() == Extreme.MAX)		
			n1.setRChild(n2);
	}
	
	
	/**
	 * Checks if the {@code DRTree} contains a value.
	 * 
	 * @param v  a value
	 * @return  {@code true} if contained
	 */
	public boolean contains(float v)
	{
		DRNode n = search(v);
		Extreme e = n.Extreme();

		Cut c = n.Cut();
		if(c.isBefore(v))
			return e == Extreme.MIN;
		return e == Extreme.MAX;
	}
	
	/**
	 * Checks if the {@code DRTree} intersects a range.
	 * 
	 * @param r  a range to check
	 * @return  {@code true} if there is intersection
	 */
	public boolean intersects(Range r)
	{
		if(isEmpty())
			return false;
		if(r == Ranges.EMPTY)
			return false;
		
		Cut c1 = r.Minimum();
		Cut c2 = r.Maximum();
		
		DRNode b1 = search(c1.Value());
		DRNode b2 = search(c2.Value());

		b1 = (c1.compareTo(b1.Cut()) < 0 ? b1.prev() : b1);
		b2 = (b2.Cut().compareTo(c2) < 0 ? b2.next() : b2);
		
		if(b1 == null)
		{
			if(b2 == null)
				return true;
			if(b2.Extreme() == Extreme.MAX)
				return true;
			return b2.prev() != null;
		}
		
		if(b1.Extreme() == Extreme.MIN)
			return true;
		if(b2 == null)
			return b1.next() != null;

		DRNode b3 = b1.next();
		return b2 != b3;
	}
		
	/**
	 * Returns the ranges in the {@code DRTree}.
	 * 
	 * @return  a range iterable
	 * 
	 * 
	 * @see Iterable
	 * @see Range
	 */
	public Iterable<Range> Ranges()
	{
		return () -> new DRIterator(this);
	}
	
	/**
	 * Searches the {@code DRTree} for a node
	 * closest to a given value.
	 * 
	 * @param v  a value to search
	 * @return  a closest tree node
	 * 
	 * 
	 * @see DRNode
	 */
	public DRNode search(float v)
	{	
		DRNode node = Root();
		// Start checking from root...
		while(node != null)
		{
			Cut c = node.Cut();

			// The value is lower than the node...
			if(c.isAfter(v))
			{
				// ...but it's the closest one found.
				if(node.LChild() == null)
				{
					return node;
				}
				
				// ...so continue with its left child.
				node = node.LChild();
				continue;
			}			
			// The value is higher than the node...
			else
			{
				// ...but it's the closest one found.
				if(node.RChild() == null)
				{
					return node;
				}
				
				// ...so continue with its right child.
				node = node.RChild();
				continue;
			}
		}
		
		return null;
	}
	
	
	@Override
	public String toString()
	{
		return condense();
	}
	
	@Override
	public Format<DRTree> Formatter()
	{
		return t ->
		{
			if(t.Root() == null)
			{
				 Range r = Ranges.EMPTY;
				return r.condense();
			}
			
			String s = "";
			Iterable<DRNode> nodes = inorder();
			for(DRNode node : nodes)
			{
				s += node;
				if(node.Extreme() == Extreme.MIN)
				{
					s += "..";
				}
			}
			
			return s;
		};
	}
	
	
	void merge(DRNode m1, DRNode m2)
	{
		DRNode n = m1.next();
		while(n != m2)
		{
			DRNode m = n.next();

			n.delete();
			n = m;
		}
	}
	
	void mergeAbove(DRNode m1)
	{
		DRNode n = m1.next();
		while(n != null)
		{
			DRNode m = n.next();

			n.delete();
			n = m;
		}
	}
	
	void mergeBelow(DRNode m1)
	{
		DRNode n = m1.prev();
		while(n != null)
		{
			DRNode m = n.prev();

			n.delete();
			n = m;
		}
	}
	

//		
//	/**
//	 * Checks if the {@code DRTree} intersects an interval.
//	 * 
//	 * @param r  an interval to check
//	 * @return  {@code true} if there is intersection
//	 */
//	public boolean intersects(Range r)
//	{
//		// If the interval is invalid, or the tree is empty...
//		if(r == null || Root() == null)
//		{
//			// Repetition false.
//			return false;
//		}
//		
//		// Find the surrounding nodes in the tree.
//		DRNode prev = lowerBound(r.Minimum());
//		DRNode next = upperBound(r.Maximum());
//		
//		// If the interval has no lower bound...
//		if(prev == null)
//		{
//			// And the interval has no upper bound...
//			if(next == null)
//			{
//				// There is an intersection.
//				return true;
//			}
//			
//			// Otherwise, check if the upper bound is the lowest node.
//			return next.prev() != null;
//		}
//
//		// If the interval has no upper bound...
//		if(next == null)
//		{
//			// Check if the lower bound is the highest node.
//			return prev.next() != null;
//		}
//
//		// If there are nodes between the bounds...
//		if(prev.next() != next)
//		{
//			return true;
//		}
//		
//		// Otherwise, check the bound's contents.
//		return prev.Extreme() == Extreme.MIN
//			|| next.Extreme() == Extreme.MAX;
//	}
//	
//	/**
//	 * Checks if the {@code DRTree} contains a value.
//	 * 
//	 * @param value  a value to check
//	 * @return  {@code true} if the value is contained
//	 */
//	public boolean contains(float value)
//	{
//		// If the tree is empty...
//		if(Root() == null)
//		{
//			// It contains no values.
//			return false;
//		}
//		
//		
//		// Otherwise, find the nearest node.
//		DRNode node = closest(value);
//		// Retrieve the node's cut.
//		Cut cut = node.Cut();
//
//		// If the node is a minimum...
//		if(node.Extreme() == Extreme.MIN)
//		{
//			// The value should be higher.
//			return cut.isBefore(value);
//		}
//		
//		// Otherwise, the value should be lower.
//		return cut.isAfter(value);
//	}
//	
//			
//	/**
//	 * Returns the strict lower bound of a cut in the {@code DRTree}.
//	 * 
//	 * @param cut  a cut to search
//	 * @return  a lower bound
//	 * @see DRNode
//	 */
//	public DRNode strictLowerBound(Cut cut)
//	{
//		DRNode lower = search(cut);
//		if(compare(cut, lower.Value()) <= 0)
//		{
//			return (DRNode) lower.prev();
//		}
//		
//		return lower;
//	}
//	
//	/**
//	 * Returns the strict upper bound of a cut in the {@code DRTree}.
//	 * 
//	 * @param cut  a cut to search
//	 * @return  an upper bound
//	 * @see DRNode
//	 */
//	public DRNode strictUpperBound(Cut cut)
//	{
//		DRNode upper = search(cut);
//		if(compare(cut, upper.Value()) >= 0)
//		{
//			return (DRNode) upper.next();
//		}
//		
//		return upper;
//	}
//	
//	/**
//	 * Returns the lower bound of a cut in the {@code DRTree}.
//	 * 
//	 * @param cut  a cut to search
//	 * @return  a lower bound
//	 * @see DRNode
//	 */
//	public DRNode lowerBound(Cut cut)
//	{
//		DRNode lower = search(cut);
//		if(compare(cut, lower.Value()) < 0)
//		{
//			return (DRNode) lower.prev();
//		}
//		
//		return lower;
//	}
//	
//	/**
//	 * Returns the upper bound of a cut in the {@code DRTree}.
//	 * 
//	 * @param cut  a cut to search
//	 * @return  an upper bound
//	 * @see DRNode
//	 */
//	public DRNode upperBound(Cut cut)
//	{
//		DRNode upper = search(cut);
//		if(compare(cut, upper.Value()) > 0)
//		{
//			return (DRNode) upper.next();
//		}
//		
//		return upper;
//	}
//		
//	/**
//	 * Searches for a {@code DRNode} closest to a value.
//	 * 
//	 * @param val  a value to search
//	 * @return  the closest node
//	 * @see DRNode
//	 */
//	public DRNode closest(float val)
//	{
//		DRNode node = Root();
//		
//		// The tree is empty.
//		if(node == null)
//		{
//			return null;
//		}
//		
//		// Start checking from root...
//		while(true)
//		{
//			int comp = node.Value().compareTo(val);
//			
//			// The value has been found.
//			if(comp == 0)
//			{
//				return node;
//			}
//			
//			// The value is lower than the node...
//			if(comp > 0)
//			{
//				// ...but it's the closest one found.
//				if(node.LChild() == null)
//				{
//					return node;
//				}
//				
//				// ...so continue with its left child.
//				node = node.LChild();
//				continue;
//			}
//			
//			// The value is higher than the node...
//			if(comp < 0)
//			{
//				// ...but it's the closest one found.
//				if(node.RChild() == null)
//				{
//					return node;
//				}
//				
//				// ...so continue with its right child.
//				node = node.RChild();
//				continue;
//			}
//		}
//	}
//
//	
//	@Override
//	public DRNode Root()
//	{
//		return (DRNode) super.Root();
//	}
//	
//	@Override
//	public DRNode search(Bracket b)
//	{
//		return (DRNode) super.search(b);
//	}
//	
//	@Override
//	public DRNode createNode(Object... vals)
//	{
//		Cut cut = (Cut) vals[0];
//		Extreme ex = (Extreme) vals[1];
//		return new DRNode(this, cut, ex);
//	}
//	
//			
//	
//	void deleteBetween(DRNode min, DRNode max)
//	{
//		mergeBetween(min, max);
//		
//		int dmin = min.Depth();
//		int dmax = max.Depth();
//
//		if(dmin < dmax)
//		{
//			max.setLChild(null);
//			onDelete(max);
//		}
//		else
//		{
//			min.setRChild(null);
//			onDelete(min);
//		}
//	}
//	
//	void deleteAbove(DRNode min)
//	{
//		min.setRChild(null);
//		mergeAbove(min);
//		onDelete(min);
//	}
//	
//	void deleteBelow(DRNode max)
//	{
//		max.setLChild(null);
//		mergeBelow(max);
//		onDelete(max);
//	}
//	
//	
//	void mergeAbove(DRNode min)
//	{
//		if(min.isRoot()) return;
//		
//		if(min.TreeIndex() == 1)
//		{
//			mergeAbove(min.Parent());
//		}
//		
//		if(min.TreeIndex() == 0)
//		{
//			min.Parent().replace(min);
//			mergeAbove(min);
//		}
//	}
//	
//	void mergeBelow(DRNode max)
//	{
//		if(max.isRoot()) return;
//		
//		if(max.TreeIndex() == 0)
//		{
//			mergeBelow(max.Parent());
//		}
//		
//		if(max.TreeIndex() == 1)
//		{
//			max.Parent().replace(max);
//			mergeBelow(max);
//		}
//	}
}