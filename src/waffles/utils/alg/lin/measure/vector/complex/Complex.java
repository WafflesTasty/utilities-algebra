package waffles.utils.alg.lin.measure.vector.complex;

import waffles.utils.alg.Algebraic;
import waffles.utils.alg.lin.measure.vector.fixed.Vector2;
import waffles.utils.lang.tokens.format.Format;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Complex} vector defines a complex number in two dimensions.
 * 
 * @author Waffles
 * @since Apr 30, 2016
 * @version 1.0
 *  
 *  
 * @see Algebraic
 * @see Vector2
 */
public class Complex extends Vector2 implements Algebraic
{
	/**
	 * Casts a {@code Vector2} to a {@code Complex}.
	 * 
	 * @param v  a two-dimensional vector
	 * @return  a complex number
	 * 
	 * 
	 * @see Vector2
	 */
	public static Complex from(Vector2 v)
	{
		return new Complex(v.X(), v.Y());
	}
	
	
	/**
	 * Creates a new {@code Complex}.
	 * 
	 * @param x  a complex real part
	 * @param y  a complex imaginary part
	 */
	public Complex(float x, float y)
	{
		super(x, y);
	}
		
	/**
	 * Creates a new {@code Complex}.
	 */
	public Complex()
	{
		super();
	}
	
	
	/**
	 * Returns a {@code Complex} conjugate.
	 * 
	 * @return  a complex conjugate
	 */
	public Complex conjugate()
    {
        return new Complex(X(), -Y());
    }
		
	/**
	 * Returns a {@code Complex} element sum.
	 * 
	 * @param c  a complex to add
	 * @return   a sum complex
	 */
	public Complex plus(Complex c)
	{
		return from(super.plus(c));
	}

	/**
	 * Returns a {@code Complex} difference.
	 * 
	 * @param c  a complex to subtract
	 * @return   a difference complex
	 */
	public Complex minus(Complex c)
	{
		return from(super.minus(c));
	}
	
	/**
	 * Returns a {@code Complex} product.
	 * 
	 * @param c  a complex to multiply
	 * @return   a complex product
	 */
	public Complex times(Complex c)
    {
		float x1 = X();
		float y1 = Y();
		
		float x2 = c.X();
		float y2 = c.Y();
		
		float x = x1 * x2 - y1 * y2;
		float y = x1 * y2 + y1 * x2;

        return new Complex(x, y);
    }
	
	/**
	 * Checks if this number is real.
	 * 
	 * @return  {@code true} if the number is real
	 */
	public boolean isReal()
	{
		return Y() == 0f;
	}

	
	@Override
	public Format<Complex> Formatter()
	{
		return c ->
		{
			String txt = "";
			
			txt += (c.X() == 0 ? "" : c.X() < 0 ? " - " : " + ");
			txt += (c.X() == 0 ? "" : Floats.abs(c.X())			);
			txt += (c.Y() == 0 ? "" : c.Y() < 0 ? " - " : " + ");
			txt += (c.Y() == 0 ? "" : Floats.abs(c.Y()) + "i");
			txt += (txt.equals("") ? " + " + c.X() : "");
			
			return txt;
		};
	}
	
	@Override
	public Complex times(Algebraic a)
	{
		return times((Complex) a);
	}

	@Override
	public Complex times(Float v)
	{
		return from(super.times(v));
	}
	
	@Override
	public Complex normalize()
	{
		return from(super.normalize());
	}

	@Override
	public String toString()
	{
		return condense();
	}
	
	@Override
	public Complex copy()
	{
		return from(super.copy());
	}
}