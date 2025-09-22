package waffles.utils.alg.poly;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Algebraic;
import waffles.utils.alg.lin.Linear;
import waffles.utils.alg.lin.measure.Evaluator;
import waffles.utils.alg.lin.measure.vector.complex.Complex;
import waffles.utils.alg.poly.format.PolynomialFormat;
import waffles.utils.alg.poly.solvers.RootFinder;
import waffles.utils.lang.tokens.Token;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.sets.countable.keymaps.search.BSMap;
import waffles.utils.sets.utilities.keymaps.Pair;
import waffles.utils.tools.patterns.properties.values.Copyable;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code Polynomial} defines a real-valued polynomial in double precision.
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
 * @see Token
 */
public class Polynomial implements Algebraic, Token, Copyable<Polynomial>, Evaluator<Double>, Linear<Double>
{	
	private BSMap<Integer, Double> map;
	
	/**
	 * Creates a new {@code Polynomial}.
	 * This creates a monomial of the given
	 * degree and coefficient value.
	 * 
	 * @param d  a term degree
	 * @param c  a term coefficient
	 */
	public Polynomial(int d, double c)
	{
		map = new BSMap<>();
		map.put(0, 0d);
		set(d, c);
	}
	
	/**
	 * Creates a new {@code Polynomial}.
	 * The coefficients must be given from
	 * highest degree to lowest.
	 * 
	 * @param coef  a coefficient set
	 */
	public Polynomial(double... coef)
	{
		map = new BSMap<>();
		map.put(0, 0d);
		
		int max = coef.length;
		for(int k = 0; k < max; k++)
		{
			set(max - k - 1, coef[k]);
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
	 * @param deg  a term degree
	 * @return     a term value
	 */
	public double get(int deg)
	{
		Double val = map.get(deg);
		if(val != null)
			return val;
		return 0d;
	}
	
	/**
	 * Changes a value in the {@code Polynomial}.
	 * 
	 * @param deg  a term degree
	 * @param val  a term value
	 */
	public void set(int deg, double val)
	{
		if(0d < Doubles.abs(val))
			map.put(deg, val);
		else if(0 < Integers.abs(deg))
			map.remove(deg);
		else
			map.put(0, 0d);
	}
	
	/**
	 * Adds a value to the {@code Polynomial}.
	 * 
	 * @param deg  a term degree
	 * @param val  a term value
	 */
	public void add(int deg, double val)
	{
		double coef = get(deg);
		set(deg, coef + val);
	}

	
	/**
	 * Returns a {@code Polynomial} formatter.
	 * 
	 * @param v  a variable character
	 * @return  a formatter
	 * 
	 * 
	 * @see Format
	 */
	public Format<?> Formatter(char v)
	{
		return new PolynomialFormat(v);
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
		return Integers.max(map.Keys());
	}

	
	@Override
	public Double evaluate(Double arg)
	{
		double val = 0;
		for(int deg = Degree(); deg >= 0; deg--)
		{
			val *= arg;
			val += get(deg);
		}
	
		return val;
	}
	
	@Override
	public Polynomial plus(Abelian a)
	{
		Polynomial p = (Polynomial) a;
		Polynomial q = new Polynomial();
		
		int d1 =   Degree();
		int d2 = p.Degree();
		
		for(int d = Integers.max(d1, d2); d >= 0; d--)
		{
			double v1 =   get(d);
			double v2 = p.get(d);
			
			q.set(d, v1 + v2);
		}

		return q;
	}
	
	@Override
	public Polynomial minus(Abelian a)
	{
		Polynomial p = (Polynomial) a;
		Polynomial q = new Polynomial();

		int d1 =   Degree();
		int d2 = p.Degree();
		
		for(int deg = Integers.max(d1, d2); deg >= 0; deg--)
		{
			double v1 =   get(deg);
			double v2 = p.get(deg);
			
			q.set(deg, v1 - v2);
		}

		return q;
	}
		
	@Override
	public Polynomial times(Algebraic a)
	{
		Polynomial p = (Polynomial) a;
		Polynomial q = new Polynomial();
		
		for(Pair<Integer, Double> t1 : map.Pairs())
		{
			int d1 = t1.Key();
			double v1 = t1.Value();
			
			for(Pair<Integer, Double> t2 : p.map.Pairs())
			{
				int d2 = t2.Key();
				double v2 = t2.Value();
				q.add(d1 + d2, v1 * v2);
			}
		}

		return q;
	}
	
	@Override
	public Polynomial times(Double v)
	{
		Polynomial q = new Polynomial();

		for(Pair<Integer, Double> pair : map.Pairs())
		{
			int deg = pair.Key();
			double val = pair.Value();
			
			q.set(deg, val * v);
		}
		
		return q;
	}
	
	
	@Override
	public int[] Dimensions()
	{
		return new int[]{Degree() + 1};
	}
		
	@Override
	public Format<?> Formatter()
	{
		return new PolynomialFormat('X');
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
		for(Pair<Integer, Double> pair : map.Pairs())
		{
			int deg = pair.Key();
			double val = pair.Value();
			
			copy.set(deg, val);
		}
		
		return copy;
	}


	@Override
	public String toString()
	{
		return condense();
	}
}