package waffles.utils.algebra.elements.interval.format;

import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.lang.format.ChunkFormat;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code FMTInterval} class defines a {@code Format} for a {@code Interval} object.
 * </br> The formatter accepts the following chunk strings.
 * <ul>
 * <li>{@code (}: Displays the lower cut of the interval.</li>
 * <li>{@code )}: Displays the upper cut of the interval.</li>
 * <li>{@code l+}: Displays the lower value of the interval.</li>
 * <li>{@code u+}: Displays the upper value of the interval.</li>
 * </ul>
 * 
 * @author Waffles
 * @since 27 Aug 2023
 * @version 1.1
 *
 * 
 * @see Interval
 * @see ChunkFormat
 */
public class FMTInterval extends ChunkFormat<Interval>
{
	/**
	 * Creates a new {@code FMTInterval}.
	 * 
	 * @param fmt    a format string
	 * @param delim  a chunk delimiter
	 * 
	 * 
	 * @see String
	 */
	public FMTInterval(String fmt, String delim)
	{
		super(fmt, delim);
	}
	
	@Override
	public Chunk<Interval> create(String fmt)
	{
		if(fmt.equals("("))
			return LowerCut();
		if(fmt.equals(")"))
			return UpperCut();
		if(fmt.matches("l+"))
			return LowerVal(fmt.length());
		if(fmt.matches("u+"))
			return UpperVal(fmt.length());
		
		return range -> fmt;
	}

		
	Chunk<Interval> LowerVal(int size)
	{
		return range ->
		{
			Cut cut = range.Minimum();
			if(!Floats.isFinite(cut.value()))
			{
				if(cut.value() < 0)
					return "-\u221E";
				return "+\u221E";
			}
			
			String parse = "" + cut.value();
			int len = parse.length();
			if(cut.value() < 0)
				len = Integers.min(len, size+1);
			else
				len = Integers.min(len, size);	
			
			return parse.substring(0, len);
		};
	}
	
	Chunk<Interval> UpperVal(int size)
	{
		return range ->
		{
			Cut cut = range.Maximum();
			if(!Floats.isFinite(cut.value()))
			{
				if(cut.value() < 0)
					return "-\u221E";
				return "+\u221E";
			}
			
			String parse = "" + cut.value();
			int len = parse.length();
			if(cut.value() < 0)
				len = Integers.min(len, size+1);
			else
				len = Integers.min(len, size);	
			
			return parse.substring(0, len);
		};
	}
	
	Chunk<Interval> LowerCut()
	{
		return range ->
		{	
			Cut cut = range.Minimum();
			if(cut.isBelow(cut.value()))
				return "[";
			else
				return "(";
		};
	}
	
	Chunk<Interval> UpperCut()
	{
		return range ->
		{	
			Cut cut = range.Maximum();
			if(cut.isAbove(cut.value()))
				return "]";
			else
				return ")";
		};
	}
}