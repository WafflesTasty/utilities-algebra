package waffles.utils.alg.reals.ranges.format;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.lang.tokens.format.regex.RegexFormat;
import waffles.utils.lang.tokens.format.regex.RegexFormat.Hints;
import waffles.utils.lang.tokens.format.regex.RegexValue;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code RangeFormat} defines a formatter for a {@code Range}.
 * As an implementation of {@code RegexFormat}, it allows the following expressions.
 * <ul>
 *  <li>{@code l+}: Displays the lower bound, padded with zeroes on the right.</li>
 *  <li>{@code u+}: Displays the upper bound, padded with zeroes on the right.</li>
 *  <li>{@code (}: Displays the lower bound symbol.</li>
 *  <li>{@code )}: Displays the upper bound symbol.</li>
 * </ul>
 *
 * @author Waffles
 * @since 10 Aug 2025
 * @version 1.1
 *
 * 
 * @see RegexFormat
 * @see Range
 */
public class RangeFormat extends RegexFormat<Range>
{
	/**
	 * Creates a new {@code RangeFormat}.
	 * 
	 * @param h  format hints
	 * 
	 * 
	 * @see Hints
	 */
	public RangeFormat(Hints h)
	{
		super(h);
		
		put("l+", LowerValue());
		put("u+", UpperValue());
		
		put("\\(", LowerCut());
		put("\\)", UpperCut());
	}
	
	/**
	 * Creates a new {@code RangeFormat}.
	 * 
	 * @param s  a format string
	 */
	public RangeFormat(String s)
	{
		this(() -> s);
	}
	
	
	RegexValue<Range> LowerValue()
	{
		return (r, fmt) ->
		{
			Cut c = r.Minimum();
			if(!Floats.isFinite(c.Value()))
			{
				if(c.Value() < 0)
					return "-\u221E";
				return "+\u221E";
			}
			
			String s = "" + c.Value();
			int len = s.length();
			if(c.Value() < 0)
				len = Integers.min(len, fmt.length()+1);
			else
				len = Integers.min(len, fmt.length());	
			
			return s.substring(0, len);
		};
	}
	
	RegexValue<Range> UpperValue()
	{
		return (r, fmt) ->
		{
			Cut c = r.Maximum();
			if(!Floats.isFinite(c.Value()))
			{
				if(c.Value() < 0)
					return "-\u221E";
				return "+\u221E";
			}
			
			String s = "" + c.Value();
			int len = s.length();
			if(c.Value() < 0)
				len = Integers.min(len, fmt.length()+1);
			else
				len = Integers.min(len, fmt.length());	
			
			return s.substring(0, len);
		};
	}
	
	
	RegexValue<Range> LowerCut()
	{
		return (r, fmt) ->
		{	
			Cut c = r.Minimum();
			if(c.isBefore(c.Value()))
				return "[";
			else
				return "(";
		};
	}
	
	RegexValue<Range> UpperCut()
	{
		return (r, fmt) ->
		{	
			Cut c = r.Maximum();
			if(c.isAfter(c.Value()))
				return "]";
			else
				return ")";
		};
	}
}