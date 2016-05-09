package zeno.util.algebra.vectors.complex;

import zeno.util.algebra.FMath;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.fixed.Vector2;

/**
 * The {@code Complex} class defies a complex number in two dimensions.
 * 
 * @author Zeno
 * @since Apr 30, 2016
 * @see Vector2
 */
public class Complex extends Vector2
{
	/**
	 * Creates a new {@code Complex}.
	 */
	public Complex()
	{
		super();
	}
	
	/**
	 * Creates a new {@code Complex}.
	 * 
	 * @param val  a coördinate value
	 */
	public Complex(float val)
	{
		super(val);
	}
		
	/**
	 * Creates a new {@code Complex}.
	 * 
	 * @param x  the quaternion's x-coördinate
	 * @param y  the quaternion's y-coördinate
	 */
	public Complex(float x, float y)
	{
		super(x, y);
	}
	
	
	/**
	 * Returns the conjugate of the {@code Complex}.
	 * 
	 * @return  the complex conjugate
	 */
	public Complex conjugate()
    {
        return new Complex(-X(), -Y());
    }
		
	/**
	 * Returns the product with a {@code Quaternion}.
	 * 
	 * @param q  a quaternion to multiply
	 * @return  the quaternion product
	 * @see Quaternion
	 */
	public Quaternion multiply(Quaternion q)
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
	 * Returns the product with another {@code Complex}.
	 * 
	 * @param c  a complex number to multiply
	 * @return  the complex product
	 * @see Complex
	 */
	public Complex multiply(Complex c)
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
	
	
	@Override
	public Complex add(IMatrix m)
	{
		return (Complex) super.add(m);
	}
	
	@Override
	public Complex times(float s)
	{
		return (Complex) super.times(s);
	}
	
	@Override
	public Complex lerp(IVector v, float alpha)
	{
		return (Complex) super.lerp(v, alpha);
	}
	
	@Override
	public Complex alerp(Vector2 v, float alpha)
	{
		return super.alerp(v, alpha).toComplex();
	}
	
	@Override
	public Complex projectTo(IVector norm)
	{
		return (Complex) super.projectTo(norm);
	}
	
	@Override
	public Complex normalize()
	{
		return (Complex) super.normalize();
	}

	@Override
	public String toString()
	{
		float x = X();
		float y = Y();
		
		String txt = "";
		txt += (FMath.isZero(x) ? "" : x < 0 ? " - " : " + ");
		txt += (FMath.isZero(x) ? "" : Math.abs(x));
		txt += (FMath.isZero(y) ? "" : y < 0 ? " - " : " + ");
		txt += (FMath.isZero(y) ? "" : Math.abs(y) + "i");
		txt += (txt.equals("") ? " + " + x : "");
		
		return txt;
	}
	
	@Override
	public Complex copy()
	{
		return (Complex) super.copy();
	}
}