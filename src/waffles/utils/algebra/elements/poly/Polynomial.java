package waffles.utils.algebra.elements.poly;

import waffles.utils.algebra.algorithms.polynomial.RootFinder;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.Algebraic;
import waffles.utils.algebra.elements.Linear;
import waffles.utils.algebra.elements.complex.Complex;
import waffles.utils.algebra.utilities.elements.Additive;
import waffles.utils.algebra.utilities.measures.Evaluator;
import waffles.utils.sets.keymaps.Pair;
import waffles.utils.sets.keymaps.binary.BSMap;
import waffles.utils.tools.patterns.semantics.Copyable;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code Polynomial} defines a real-valued polynomial as a wrapper for a {@code BSMap}.
 *
 * @author Waffles
 * @since 27 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Algebraic
 * @see Evaluator
 * @see Copyable
 * @see Linear
 */
public class Polynomial implements Copyable<Polynomial>, Evaluator<Double>, Linear<Double>, Algebraic
{	
	/**
	 * Parses a string into a {@code Polynomial}.
	 * A polynomial string should be given the form
	 * a + bx + cx^2 + ... where x is the given variable.
	 * 
	 * @param text  a text string
	 * @param var   a variable string
	 * @return  a new polynomial
	 */
	public static Polynomial parse(String text, String var)
	{
		Polynomial p = new Polynomial();
		
		// Normalize the polynomial string.
		String norm = text.replaceAll(" ", "");
		norm = norm.replaceAll("-", "+-");
		
		// Iterate each polynomial term.
		String[] terms = norm.split("\\+");
		for(int i = 0; i < terms.length; i++)
		{
			String term = terms[i];

			// Separate term into value and degree.
			String[] split = new String[2];
			if(term.contains(var))
			{
				if(term.contains("^"))
					split = term.split(var + "\\^");
				else
				{
					split[0] = term.replace(var, "");
					split[1] = "1";
				}
			}
			else
			{
				split[0] = term;
				split[1] = "0";
			}

			// Parse the value.
			double vNew = 1.0;
			if(!split[0].isEmpty())
			{
				if(!split[0].equals("-"))
					vNew = Doubles.parse(split[0].trim());
				else
					vNew = -1.0;
			}
			
			// Parse the degree.
			int deg = Integers.parse(split[1].trim());
			
			// Add the parsed term.
			double vOld = p.get(deg);
			p.set(deg, vOld + vNew);
		}
		
		return p;
	}
	
	
	private BSMap<Integer, Double> values;
	
	/**
	 * Creates a new {@code Polynomial}.
	 * This creates a monomial of the given
	 * degree and coefficient value.
	 * 
	 * @param deg  a coefficient degree
	 * @param val  a coefficient value
	 */
	public Polynomial(int deg, double val)
	{
		values = new BSMap<>();
		values.put(0, 0d);
		set(deg, val);
	}
	
	/**
	 * Creates a new {@code Polynomial}.
	 * The coefficients must be given from
	 * highest degree to lowest.
	 * 
	 * @param vals  a coefficient set
	 */
	public Polynomial(double... vals)
	{
		values = new BSMap<>();
		values.put(0, 0d);
		
		for(int i = 0; i < vals.length; i++)
		{
			set(vals.length-i-1, vals[i]);
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
	 * Returns a value in the {@code Polynomial}.
	 * 
	 * @param deg  a coefficient degree
	 * @return  a coefficient value
	 */
	public double get(int deg)
	{
		Double val = values.get(deg);
		if(val != null)
			return val;
		return 0d;
	}
	
	/**
	 * Changes a value in the {@code Polynomial}.
	 * 
	 * @param deg  a coefficient degree
	 * @param val  a coefficient value
	 */
	public void set(int deg, double val)
	{
		if(!Doubles.isZero(val, 3))
			values.put(deg, val);
		else if(deg != 0)
			values.remove(deg);
		else
			values.put(0, 0d);
	}

	
	/**
	 * Iterates over {@code Polynomial} roots.
	 * 
	 * @return  a complex iterable
	 * 
	 * 
	 * @see Iterable
	 * @see Complex
	 */
	public Iterable<Complex> Roots()
	{
		return () -> new RootFinder(this);
	}
	
	/**
	 * Returns the {@code Polynomial} degree.
	 * 
	 * @return  a polynomial degree
	 */
	public int Degree()
	{
		return Integers.max(values.Keys());
	}

	
	@Override
	public Polynomial minus(Abelian a)
	{
		Polynomial p = (Polynomial) a;
		return plus(p.times(-1d));
	}
	
	@Override
	public Polynomial plus(Additive a)
	{
		Polynomial p = (Polynomial) a;
		
		Polynomial copy = copy();
		for(Pair<Integer, Double> pair : p.values.Pairs())
		{
			int dOld = pair.Key();
			double vOld = pair.Value();
			double vNew = get(dOld);

			copy.set(dOld, vOld + vNew);
		}
		
		return copy;
	}

	@Override
	public Double evaluate(Double arg)
	{
		double result = 0;
		for(int deg = Degree(); deg >= 0; deg--)
		{
			double val = get(deg);
			
			result *= arg;
			result += val;
		}
	
		return result;
	}
	
	@Override
	public Polynomial times(Algebraic a)
	{
		Polynomial p = (Polynomial) a;
		
		Polynomial copy = instance();
		for(Pair<Integer, Double> p1 : p.values.Pairs())
		{
			int d1 = p1.Key();
			double v1 = p1.Value();
			
			for(Pair<Integer, Double> p2 : values.Pairs())
			{
				int d2 = p2.Key();
				double v2 = p2.Value();
				
				double vOld = copy.get(d1 + d2);
				copy.set(d1 + d2, vOld + v1 * v2);
			}
		}

		return copy;
	}
	
	@Override
	public Polynomial times(Double v)
	{
		Polynomial copy = copy();
		for(Pair<Integer, Double> pair : values.Pairs())
		{
			int deg = pair.Key();
			double val = pair.Value();
			
			copy.set(deg, val * v);
		}
		
		return copy;
	}
	
	
	@Override
	public String toString()
	{
		String poly = "";
		
		boolean isFirst = true;
		for(int deg = Degree(); deg >= 0; deg--)
		{
			double val = get(deg);
			double abs = Doubles.abs(val);
			
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
	
	@Override
	public Polynomial instance()
	{
		return new Polynomial();
	}
	
	@Override
	public Polynomial copy()
	{
		Polynomial copy = instance();
		for(Pair<Integer, Double> pair : values.Pairs())
		{
			int deg = pair.Key();
			double val = pair.Value();
			
			copy.set(deg, val);
		}
		
		return copy;
	}
}