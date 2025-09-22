package waffles.utils.alg.reals.ranges.trees;

import waffles.utils.alg.reals.cuts.Bracket;
import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;
import waffles.utils.alg.reals.ranges.format.RangeFormat;
import waffles.utils.lang.tokens.Token;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.lang.utilities.enums.Extreme;
import waffles.utils.sets.rooted.binary.search.IONode;

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
 * @see IONode
 * @see Token
 * @see Cut
 */
public class DRNode extends IONode<Bracket> implements Token
{	
	/**
	 * Creates a new {@code DRNode}.
	 * 
	 * @param t  a parent tree
	 * @param c  a range cut
	 * @param e  an extreme
	 * 
	 * 
	 * @see Extreme
	 * @see DRTree
	 * @see Cut
	 */
	public DRNode(DRTree t, Cut c, Extreme e)
	{
		super(t, new Bracket(c, e));
	}
	
	/**
	 * Returns a {@code DRNode} extreme.
	 * 
	 * @return  a bracket extreme
	 * 
	 * 
	 * @see Extreme
	 */
	public Extreme Extreme()
	{
		return Value().Extreme();
	}

	/**
	 * Returns a {@code DRNode} cut.
	 * 
	 * @return  a bracket cut
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Cut()
	{
		return Value().Cut();
	}


	@Override
	public String toString()
	{
		return condense();
	}
	
	@Override
	public Format<DRNode> Formatter()
	{
		return n ->
		{
			switch(Extreme())
			{
			case MIN:
			{
				RangeFormat fmt = new RangeFormat("§(§§llllll§");
				Range r = Ranges.create(Cut(), null);
				return fmt.parse(r);
			}
			case MAX:
			{
				RangeFormat fmt = new RangeFormat("§uuuuuu§§)§");
				Range r = Ranges.create(null, Cut());
				return fmt.parse(r);
			}
			default:
				return null;
			}
		};
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

	@Override
	public DRNode prev()
	{
		return (DRNode) super.prev();
	}
	
	@Override
	public DRNode next()
	{
		return (DRNode) super.next();
	}
}