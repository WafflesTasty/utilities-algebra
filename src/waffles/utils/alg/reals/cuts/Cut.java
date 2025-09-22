package waffles.utils.alg.reals.cuts;

import waffles.utils.alg.reals.Partition;
import waffles.utils.alg.reals.cuts.fixed.AboveAll;
import waffles.utils.alg.reals.cuts.fixed.BelowAll;
import waffles.utils.alg.reals.cuts.vars.AboveValue;
import waffles.utils.alg.reals.cuts.vars.BelowValue;
import waffles.utils.alg.utilities.groups.Multiplication;
import waffles.utils.alg.utilities.groups.Subtraction;
import waffles.utils.tools.patterns.properties.values.Valuable;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Cut} defines a Dedekind cut at a value on the real number line.
 *
 * @author Waffles
 * @since 20 Sep 2025
 * @version 1.1
 *
 * 
 * @see Multiplication
 * @see Subtraction
 * @see Partition
 * @see Valuable
 */
public abstract class Cut implements Partition, Subtraction<Cut>, Multiplication<Float>, Valuable<Float>
{	
	/**
	 * Defines a {@code Cut} above all real numbers.
	 */
	public static Cut ABOVE_ALL = new AboveAll();
	
	/**
	 * Defines a {@code Cut} below all real numbers.
	 */
	public static Cut BELOW_ALL = new BelowAll();
		
	
	/**
	 * Defines a {@code Cut} before a real number.
	 * 
	 * @param val  a real value
	 * @return  a cut below
	 * 
	 * 
	 * @see Cut
	 */
	public static Cut Before(float val)
	{
		if(Floats.isFinite(val))
			return new BelowValue(val);
		if(val == Floats.NEG_INFINITY)
			return BELOW_ALL;
		if(val == Floats.POS_INFINITY)
			return ABOVE_ALL;
		return null;
	}
	
	/**
	 * Defines a {@code Cut} after a real number.
	 * 
	 * @param val  a real value
	 * @return  a cut above
	 * 
	 * 
	 * @see Cut
	 */
	public static Cut After(float val)
	{
		if(Floats.isFinite(val))
			return new AboveValue(val);		
		if(val == Floats.NEG_INFINITY)
			return BELOW_ALL;
		if(val == Floats.POS_INFINITY)
			return ABOVE_ALL;
		return null;
	}
	
	
	private float val;
	
	/**
	 * Creates a new {@code Cut}.
	 * 
	 * @param v  a cut value
	 */
	public Cut(float v)
	{
		val = v;
	}
	
	
	/**
	 * Check if this occurs before a {@code Cut}.
	 * 
	 * @param c  a cut
	 * @return  {@code true} if this exists before
	 */
	public boolean isBefore(Cut c)
	{
		return isBefore(c.Value()) && c.isAfter(Value());
	}
	
	/**
	 * Check if this occurs after a {@code Cut}.
	 * 
	 * @param c  a cut
	 * @return  {@code true} if this exists after
	 */
	public boolean isAfter(Cut c)
	{
		return isAfter(c.Value()) && c.isBefore(Value());
	}
	
	/**
	 * Compares this with another {@code Cut}.
	 * 
	 * @param c  a cut
	 * @return  an integer comparison
	 */
	public int compareTo(Cut c)
	{
		return compareTo(c.Value());
	}
	
	
	@Override
	public Cut minus(Cut c)
	{
		float v1 =   Value();
		float v2 = c.Value();
		float min = v1 - v2;
		
		if( isBefore(v1))
			return Before(min);
		if(c.isAfter(v2))
			return Before(min);
		
		return After(min);
	}
	
	@Override
	public Cut times(Float v)
	{
		// If the value is negative...
		return (v < 0f == isBefore(val))
		// ...the cut switches.
			? Before(val * v)
			: After(val * v);
	}
		
	@Override
	public Float Value()
	{
		return val;
	}
}