package waffles.utils.algebra.algorithms.polynomial;

import waffles.utils.algebra.elements.poly.Polynomial;

/**
 * The {@code Division} algorithm computes quotient and remainder when dividing polynomials.
 * 
 * @author Waffles
 * @since Jan 10, 2017
 * @version 1.0
 */
public class Division
{
	private Polynomial lower, upper, quot;
	
	/**
	 * Creates a new {@code Division}.
	 * 
	 * @param numer  a numerator
	 * @param denom  a denominator
	 * 
	 * 
	 * @see Polynomial
	 */
	public Division(Polynomial numer, Polynomial denom)
	{
		upper = numer.copy();
		lower = denom.copy();


		quot = new Polynomial();
		while(lower.Degree() <= upper.Degree())
		{
			int degr = upper.Degree();
			int degd = lower.Degree();
			int deg  = degr - degd;

			double valr = upper.get(degr);
			double vald = lower.get(degd);
			double val  = valr / vald;
			
			
			Polynomial mon = new Polynomial(deg, val);
			Polynomial ply = lower.times(mon);
			
			upper = upper.minus(ply);
			quot = quot.plus(mon);
		}
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
		return upper;
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
		return quot;
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
		return lower;
	}
}