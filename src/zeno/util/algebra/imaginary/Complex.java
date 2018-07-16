package zeno.util.algebra.imaginary;

import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Complex} class defines a complex number in two dimensions.
 * 
 * @since Apr 30, 2016
 * @author Zeno
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
	 * Returns the {@code Quaternion} multiplication.
	 * 
	 * @param q  a quaternion to multiply
	 * @return  the quaternion product
	 * @see Quaternion
	 */
	public Quaternion times(Quaternion q)
    {
		float x1 = X();
		float y1 = Y();
		
		float x2 = q.X();
		float y2 = q.Y();
		float z2 = q.Z();
		float w2 = q.W();

		float x = x1 * x2 - y1 * y2;
		float y = x1 * y2 + y1 * x2;
		float z = x1 * z2 - y1 * w2;
		float w = x1 * w2 + y1 * z2;
		
        return new Quaternion(x, y, z, w);
    }
	
	/**
	 * Returns the {@code Complex} multiplication.
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
	 * Returns the {@code Complex}'s conjugate.
	 * 
	 * @return  the complex conjugate
	 */
	public Complex conjugate()
    {
        return new Complex(X(), -Y());
    }
	
	
	/**
	 * Returns the imaginary part of the {@code Complex}.
	 * 
	 * @return  the complex's imaginary part
	 */
	public float Imaginary()
	{
		return Y();
	}
	
	/**
	 * Checks if the {@code Complex} is a real number.
	 * 
	 * @return  {@code true} if the complex is real
	 */
	public boolean isReal()
	{
		return Y() == 0;
	}
		
	/**
	 * Returns the real part of the {@code Complex}.
	 * 
	 * @return  the complex's real part
	 */
	public float Real()
	{
		return X();
	}
	

	
	/**
	 * Returns the {@code Complex}'s subtraction.
	 * 
	 * @param v  a complex to subtract
	 * @return  the difference complex
	 */
	public Complex minus(Complex v)
	{
		return from(super.minus(v));
	}
	
	/**
	 * Returns the {@code Complex}'s sum.
	 * 
	 * @param v  a complex to add
	 * @return  the sum complex
	 */
	public Complex plus(Complex v)
	{
		return from(super.plus(v));
	}
		
	
	@Override
	public Complex times(float s)
	{
		return from(super.times(s));
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