package waffles.utils.alg.poly.solvers;

import waffles.utils.alg.poly.Polynomial;
import waffles.utils.alg.poly.PolynomialSolver;

/**
 * A {@code Division} computes quotient and remainder of a {@code Polynomial} division.
 * 
 * @author Waffles
 * @since Jan 10, 2017
 * @version 1.0
 */
public class Division implements PolynomialSolver
{
	/**
	 * The {@code Hints} interface defines hints for a {@code Division}.
	 *
	 * @author Waffles
	 * @since 19 Sep 2025
	 * @version 1.1
	 *
	 *
	 */
	public static interface Hints extends PolynomialSolver.Hints
	{
		/**
		 * Returns the divisor of the {@code Hints}.
		 * 
		 * @return  a polynomial divisor
		 * 
		 * 
		 * @see Polynomial
		 */
		public abstract Polynomial Divisor();
	}
	
	
	private Hints hints;
	private Polynomial l, u, q;
		
	/**
	 * Creates a new {@code Division}.
	 * 
	 * @param n  a numerator
	 * @param d  a denominator
	 * 
	 * 
	 * @see Polynomial
	 */
	public Division(Polynomial n, Polynomial d)
	{
		this(new Hints()
		{
			@Override
			public Polynomial Polynomial()
			{
				return n.copy();
			}
			
			@Override
			public Polynomial Divisor()
			{
				return d.copy();
			}
		});
	}

	/**
	 * Creates a new {@code Division}.
	 * 
	 * @param h  algorithm hints
	 * 
	 * 
	 * @see Hints
	 */
	public Division(Hints h)
	{
		hints = h;
	}
	
	
	/**
	 * Returns the remainder of the {@code Division}.
	 * 
	 * @return  a polynomial remainder
	 * 
	 * 
	 * @see Polynomial
	 */
	public Polynomial Remainder()
	{
		if(u == null)
			q = divide();
		return u;
	}
	
	/**
	 * Returns the quotient of the {@code Division}.
	 * 
	 * @return  a polynomial quotient
	 * 
	 * 
	 * @see Polynomial
	 */
	public Polynomial Quotient()
	{
		if(q == null)
			q = divide();
		return q;
	}
	
	/**
	 * Returns the divisor of the {@code Division}.
	 * 
	 * @return  a polynomial divisor
	 * 
	 * 
	 * @see Polynomial
	 */
	public Polynomial Divisor()
	{
		if(l == null)
			q = divide();
		return l;
	}
	
	
	@Override
	public Hints Hints()
	{
		return hints;
	}
	
	Polynomial divide()
	{
		u = Hints().Polynomial();
		l = Hints().Divisor();
		
		
		q = new Polynomial();
		while(l.Degree() <= u.Degree())
		{
			int du = u.Degree();
			int dl = l.Degree();
			int deg = du - dl;

			double vu = u.get(du);
			double vl = l.get(dl);
			double val = vu / vl;

			
			Polynomial mon = new Polynomial(deg, val);
			Polynomial ply = l.times(mon);

			u = u.minus(ply);
			q = q.plus(mon);
		}
		
		return q;
	}
}