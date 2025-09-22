package waffles.utils.alg.reals.cuts.vars;

import waffles.utils.alg.reals.cuts.Cut;

/**
 * A {@code BelowValue} cut extends right below a finite real number.
 *
 * @author Waffles
 * @since Jul 16, 2018
 * @version 1.0
 * 
 * 
 * @see Cut
 */
public class BelowValue extends Cut
{
	/**
	 * Creates a new {@code BelowValue}.
	 * 
	 * @param value  a cut value
	 */
	public BelowValue(float value)
	{
		super(value);
	}

	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Cut)
		{
			Cut c = (Cut) o;
			
			float v1 =   Value();
			float v2 = c.Value();
			
			return c.isBefore(v1)
				&& isBefore(v2);
		}

		return false;
	}

	@Override
	public int compareTo(Float v)
	{
		if(v < Value())
			return +1;
		return -1;
	}
	
	@Override
	public int compareTo(Cut c)
	{
		Float v1 =    Value();
		Float v2 =  c.Value();
		
		int c1 = v1.compareTo(v2);
		if(c1 != 0)	return c1;
		int c2 = c.compareTo(v2);
		if(c2 < 0) return 0;
		return -1;
	}
}