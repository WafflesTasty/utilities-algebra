package zeno.util.algebra.analysis;

import java.util.TreeMap;
import java.util.Map.Entry;

import zeno.util.algebra.Floats;
import zeno.util.algebra.Integers;
import zeno.util.algebra.vectors.complex.Complex;
import zeno.util.tools.generic.properties.Copyable;

/**
 * The {@code Polynomial} class defines a univariate (having only one variable) polynomial.
 * <br> The polynomial is of the form {@code a.x^n + b.x^(n-1) + ... + c.x^2 + d.x + e}.
 * 
 * @since Oct 27, 2014
 * @author Zeno
 * 
 * @see Copyable
 */
public class Polynomial implements Copyable<Polynomial>
{
	private static RootFinder rootfinder = new RootFinder();
	
	/**
	 * Parses a text string to a {@code Polynomial}.
	 * <br> The parser ignores white space.
	 * 
	 * @param text  a text string to parse
	 * @return the parsed polynomial
	 */
	public static Polynomial parse(String text)
	{
		Polynomial poly = new Polynomial();
		
		// Normalize the polynomial string.
		String standard = text.replaceAll(" ", "");
		standard = standard.replaceAll("-", "+-");
		
		// Iterate each polynomial term.
		String[] terms = standard.split("\\+");
		for(int i = 0; i < terms.length; i++)
		{
			String term = terms[i];
			
			// Separate term into value and degree.
			String[] split = new String[2];
			if(term.contains("x"))
			{
				if(term.contains("^"))
					split = term.split("x\\^");
				else
				{
					split[0] = term.replace("x", "");
					split[1] = "1";
				}
			}
			else
			{
				split[0] = term;
				split[1] = "0";
			}
			
			// Parse the value.
			float val = 1.0f;
			if(!split[0].isEmpty())
			{
				if(!split[0].equals("-"))
					val = Floats.parse(split[0].trim());
				else
					val = -1.0f;
			}
			
			// Parse the degree.
			int deg = Integers.parse(split[1].trim());
			
			// Add the parsed term.
			poly.add(deg, val);
		}
		
		return poly;
	}


	private TreeMap<Integer, Float> terms;
	
	/**
	 * Creates a new {@code Polynomial}.
	 * Its coëfficients are defined from the highest degree to the lowest.
	 * 
	 * @param vals  the polynomial's coëfficients
	 */
	public Polynomial(float... vals)
	{
		terms = new TreeMap<>();
		for(int i = 0; i < vals.length; i++)
		{
			set(vals.length - i - 1, vals[i]);
		}
	}
	
	/**
	 * Creates a new {@code Polynomial}.
	 */
	public Polynomial()
	{
		this(0);
	}
	
			
	/**
	 * Multiplies a single term with the {@code Polynomial}.
	 * 
	 * @param deg  the term's degree
	 * @param val  the term's coëfficient
	 * @return  the polynomial result
	 */
	public Polynomial times(int deg, float val)
	{
		Polynomial result = new Polynomial();
		for(int degree : terms.navigableKeySet())
		{
			float value = val * getValue(degree);
			result.set(deg + degree, value);
		}
		
		return result;
	}
	
	/**
	 * Divides the {@code Polynomial} with a {@code Polynomial}.
	 * 
	 * @param div  a polynomial to divide with
	 * @return  a polynomial result
	 */
	public Polynomial[] divide(Polynomial div)
	{
		Polynomial remainder = copy();
		Polynomial quotient = new Polynomial();
		while(div.Degree() <= remainder.Degree())
		{
			int degr = remainder.Degree();
			int degd = div.Degree();
			int deg  = degr - degd;

			float valr = remainder.getValue(degr);
			float vald = div.getValue(degd);
			float val  = valr / vald;

			Polynomial res = div.times(deg, -val);
			remainder = remainder.add(res);
			quotient.add(deg, val);
		}
		
		
		return new Polynomial[]
		{
			quotient,
			remainder,
		};
	}
			
	/**
	 * Multiplies the {@code Polynomial} with a {@code Polynomial}.
	 * 
	 * @param poly  a polynomial to multiply with
	 * @return  a polynomial result
	 */
	public Polynomial times(Polynomial poly)
	{
		Polynomial result = new Polynomial();
		for(int deg1 : poly.terms.navigableKeySet())
		{
			for(int deg2 : terms.navigableKeySet())
			{
				float value = poly.getValue(deg1) * getValue(deg2);
				result.add(deg1 + deg2, value);
			}
		}
		
		return result;
	}
	
	/**
	 * Calculates the addition with another {@code Polynomial}.
	 * 
	 * @param poly  a polynomial to add
	 * @return  the polynomial result
	 */
	public Polynomial add(Polynomial poly)
	{
		Polynomial result = copy();
		
		for(int deg : poly.terms.navigableKeySet())
		{
			result.add(deg, poly.getValue(deg));
		}
		
		return result;
	}
		
	/**
	 * Multiplies the {@code Polynomial} with a scalar value.
	 * 
	 * @param val  a value to multiply with
	 * @return  a polynomial result
	 */
	public Polynomial times(float val)
	{
		Polynomial result = new Polynomial();
		for(int deg : terms.navigableKeySet())
		{
			result.set(deg, val * getValue(deg));
		}
		
		return result;
	}
	
	/**
	 * Returns the complex roots of the {@code Polynomial}.
	 * 
	 * @return  an array of real roots
	 * @see Complex
	 */
	public Complex[] ComplexRoots()
	{
		return rootfinder.findComplexRoots(this);
	}
	
	
	/**
	 * Adds a single term to the {@code Polynomial}.
	 * 
	 * @param deg  the term's degree
	 * @param val  a value to add
	 */
	public void add(int deg, float val)
	{
		Float curr = terms.get(deg);
		if(curr != null)
			set(deg, curr + val);
		else
			set(deg, val);
	}
	
	/**
	 * Changes a coëfficient of the {@code Polynomial}.
	 * 
	 * @param deg  the term's degree
	 * @param val  a value to set
	 */
	public void set(int deg, float val)
	{
		if(!Floats.isZero(val))
			terms.put(deg, val);
		else if(deg != 0)
			terms.remove(deg);
		else
			terms.put(0, 0f);
	}
		
	/**
	 * Substitutes the variable of the {@code Polynomial}.
	 * 
	 * @param val  a value to substitute for
	 * @return  the resulting value
	 */
	public float substitute(float val)
	{
		float power = 0;
		float result = 0;

		for(int deg : terms.navigableKeySet())
		{
			power = Floats.pow(val, deg);
			result += terms.get(deg) * power;
		}
		
		return result;
	}
	
	/**
	 * Returns a term coëfficient of the {@code Polynomial}.
	 * 
	 * @param deg  the term's degree
	 * @return  the term's coëfficient
	 */
	public float getValue(int deg)
	{
		Float val = terms.get(deg);
		if(val != null)
		{
			return val;
		}
		
		return 0;
	}
		
	/**
	 * Returns the coëfficients of the {@code Polynomial}.
	 * 
	 * @return  the polynomial's coefficients
	 */
	public float[] Coefficients()
	{
		float[] coef = new float[Degree() + 1];
		for(int deg : terms.keySet())
		{
			coef[deg] = getValue(deg);
		}
		
		return coef;
	}
	
	/**
	 * Returns the real roots of the {@code Polynomial}.
	 * 
	 * @return  an array of real roots
	 */
	public float[] RealRoots()
	{
		return rootfinder.findRealRoots(this);
	}
	
	/**
	 * Returns the degree of the {@code Polynomial}.
	 * 
	 * @return  the polynomial's degree
	 */
	public int Degree()
	{
		if(!terms.isEmpty())
			return terms.lastKey();
		return -1;
	}
	
		
	@Override
	public Polynomial copy()
	{
		Polynomial clone = Copyable.super.copy();
		for(Entry<Integer, Float> entry : terms.entrySet())
		{
			clone.set(entry.getKey(), entry.getValue());
		}
		
		return clone;
	}
	
	@Override
	public Polynomial instance()
	{
		return new Polynomial();
	}
		
	@Override
	public String toString()
	{
		String poly = "";
		boolean isFirst = true;
		for(int deg : terms.descendingKeySet())
		{
			float val = terms.get(deg);
			float abs = Floats.abs(val);
			if(val != 0)
			{
				poly += (val < 0 ? " - " : (isFirst ? "" : " + "));
				poly += (abs != 1 || deg == 0 ? abs + " " : "");
				poly += (deg != 0 ? "x" + (deg != 1 ? "^" + deg : "") : "");
				isFirst = false;
				continue;
			}
			
			if(deg == 0)
			{
				if(poly.equals(""))
				{
					poly += (val < 0 ? "-" : "") + abs;
				}	
			}			
		}
		
		return poly;
	}
}