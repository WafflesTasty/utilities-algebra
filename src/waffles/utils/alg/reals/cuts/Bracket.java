package waffles.utils.alg.reals.cuts;

import waffles.utils.lang.utilities.enums.Extreme;

/**
 * A {@code Bracket} defines a lower or upper bound for a {@code Range}.
 *
 * @author Waffles
 * @since 21 Sep 2025
 * @version 1.1
 *
 * 
 * @see Comparable
 * @see Bracket
 */
public class Bracket implements Comparable<Bracket>
{
	private Cut cut;
	private Extreme ext;
	
	/**
	 * Creates a new {@code Bracket}.
	 * 
	 * @param c  a bracket cut
	 * @param e  a bracket extreme
	 * 
	 * 
	 * @see Extreme
	 * @see Cut
	 */
	public Bracket(Cut c, Extreme e)
	{
		cut = c;
		ext = e;
	}
	
	/**
	 * Returns the extreme of the {@code Bracket}.
	 * 
	 * @return  a bracket extreme
	 * 
	 * 
	 * @see Extreme
	 */
	public Extreme Extreme()
	{
		return ext;
	}
	
	/**
	 * Returns the cut of the {@code Bracket}.
	 * 
	 * @return  a bracket cut
	 * 
	 * 
	 * @see Cut
	 */
	public Cut Cut()
	{
		return cut;
	}
	

	@Override
	public int compareTo(Bracket b)
	{
		int cmp = Cut().compareTo(b.Cut());
		if(cmp == 0)
		{
			Extreme e1 =   Extreme();
			Extreme e2 = b.Extreme();
			
			return e1.compareTo(e2);
		}
		
		return cmp;
	}
}