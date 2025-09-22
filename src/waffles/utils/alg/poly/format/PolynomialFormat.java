package waffles.utils.alg.poly.format;

import waffles.utils.alg.poly.Polynomial;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.tools.primitives.Doubles;

/**
 * A {@code PolynomialFormat} parses a {@code Polynomial} into a string.
 *
 * @author Waffles
 * @since 18 Sep 2025
 * @version 1.1
 *
 * 
 * @see Polynomial
 * @see Format
 */
public class PolynomialFormat implements Format<Polynomial>
{
	private char var;
	
	/**
	 * Creates a new {@code PolynomialFormat}.
	 * 
	 * @param v  a variable character
	 */
	public PolynomialFormat(char v)
	{
		var = v;
	}

	
	@Override
	public String parse(Polynomial p)
	{
		String src = "";
		
		boolean isFirst = true;
		for(int deg = p.Degree(); deg >= 0; deg--)
		{
			double val = p.get(deg);
			double abs = Doubles.abs(val);
			
			if(val != 0)
			{
				src += (val < 0 ? " - " : (isFirst ? "" : " + "));
				src += (abs != 1.0d || deg == 0 ? abs + " " : "");
				if(deg > 0)
				{
					src += var + (deg != 1 ? "^" + deg : "");					
				}

				isFirst = false;
				continue;
			}
			
			if(deg == 0)
			{
				if(src.equals(""))
				{
					src += (val < 0 ? "-" : "") + abs;
				}	
			}			
		}
		
		return src;
	}
}