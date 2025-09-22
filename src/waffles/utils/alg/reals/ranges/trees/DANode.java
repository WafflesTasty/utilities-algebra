package waffles.utils.alg.reals.ranges.trees;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;
import waffles.utils.alg.reals.ranges.format.RangeFormat;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.lang.utilities.enums.Extreme;
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
	private static final float INV_PI = 1f / Floats.PI;
	
	
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
	public Format<DRNode> Formatter()
	{
		return n ->
		{
			switch(Extreme())
			{
			case MIN:
			{
				RangeFormat fmt = new RangeFormat("§(§§llllll§ Pi");
				Range r = Ranges.create(Cut().times(INV_PI), null);
				return fmt.parse(r);
			}
			case MAX:
			{
				RangeFormat fmt = new RangeFormat("§uuuuuu§ Pi§)§");
				Range r = Ranges.create(null, Cut().times(INV_PI));
				return fmt.parse(r);
			}
			default:
				return null;
			}
		};
	}
	
	@Override
	public DANode Parent()
	{
		return (DANode) super.Parent();
	}
}