package zeno.util.algebra.imaginary;

import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.tools.Floats;

/**
 * The {@code Complex} class defines a complex number in two dimensions.
 * 
 * @author Zeno
 * @since Apr 30, 2016
 * @version 1.0
 *  
 *  
 * @see Vector2
 */
public class Complex extends Vector2
{
	/**
	 * Casts a {@code Vector2} to a {@code Complex}.
	 * 
	 * @param vec  a two-dimensional vector
	 * @return  a complex number
	 * @see Vector2
	 */
	public static Complex from(Vector2 vec)
	{
		return new Complex(vec.X(), vec.Y());
	}
	
	
	/**
	 * Creates a new {@code Complex}.
	 * 
	 * @param x  the complex's x-coördinate
	 * @param y  the complex's y-coördinate
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
	 * @return  the complex conjugate
	 */
	public Complex conjugate()
    {
        return new Complex(X(), -Y());
    }
		
	/**
	 * Returns a {@code Complex} element sum.
	 * 
	 * @param v  a complex to add
	 * @return  the sum complex
	 */
	public Complex plus(Complex v)
	{
		return from(super.plus(v));
	}

	/**
	 * Returns a {@code Complex} difference.
	 * 
	 * @param v  a complex to subtract
	 * @return  the difference complex
	 */
	public Complex minus(Complex v)
	{
		return from(super.minus(v));
	}
	
	/**
	 * Returns a {@code Complex} product.
	 * 
	 * @param c  a complex to multiply
	 * @return  the complex product
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
		return Y() == 0;
	}


	@Override
	public Complex times(float v)
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
		float x = X();
		float y = Y();
		
		String txt = "";
		txt += (x == 0 ? "" : x < 0 ? " - " : " + ");
		txt += (x == 0 ? "" : Floats.abs(x)			);
		txt += (y == 0 ? "" : y < 0 ? " - " : " + ");
		txt += (y == 0 ? "" : Floats.abs(y) + "i");
		txt += (txt.equals("") ? " + " + x : "");
		
		return txt;
	}
	
	@Override
	public Complex copy()
	{
		return from(super.copy());
	}
}