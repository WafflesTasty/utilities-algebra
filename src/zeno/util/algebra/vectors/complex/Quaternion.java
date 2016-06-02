package zeno.util.algebra.vectors.complex;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.fixed.Vector3;
import zeno.util.algebra.vectors.fixed.Vector4;

/**
 * The {@code Quaternion} class extends complex numbers to four dimensions.
 * <br> They are useful in representing 3D space rotations.
 * 
 * @author Zeno
 * @since Apr 30, 2016
 * @see Vector4
 */
public class Quaternion extends Vector4
{
	/**
	 * Creates a new {@code Quaternion}.
	 */
	public Quaternion()
	{
		super();
	}
	
	/**
	 * Creates a new {@code Quaternion}.
	 * 
	 * @param val  a coördinate value
	 */
	public Quaternion(float val)
	{
		super(val);
	}
	
	/**
	 * Creates a new {@code Quaternion}.
	 * 
	 * @param axis  a rotation axis
	 * @param angle  a rotation angle
	 * @see Vector3
	 */
	public Quaternion(Vector3 axis, float angle)
    {
		super(4);
		
		Vector3 norm = axis.normalize();
        float sin = Floats.sin(angle / 2);
        float cos = Floats.cos(angle / 2);

        setX(norm.X() * sin);
        setY(norm.Y() * sin);
        setZ(norm.Z() * sin);
        setW(cos);
    }
	
	/**
	 * Creates a new {@code Quaternion}.
	 * 
	 * @param x  the quaternion's x-coördinate
	 * @param y  the quaternion's y-coördinate
	 * @param z  the quaternion's z-coördinate
	 * @param w  the quaternion's w-coördinate
	 */
	public Quaternion(float x, float y, float z, float w)
	{
		super(x, y, z, w);
	}
	
	
	/**
	 * Calculates spherical interpolation with a {@code Quaternion}.
	 * 
	 * @param q  a quaternion to interpolate with
	 * @param alpha  an interpolation alpha value
	 * @return an interpolated quaternion
	 */
	public Quaternion slerp(Quaternion q, float alpha)
    {
		float dot = dot(q);
        if (1 - dot > 0.1)
        {
        	return lerp(q, alpha);
        }

        float s1 = 1f - alpha;
        float s2 = (dot < 0 ? -alpha : alpha);

        float x = s1 * X() + s2 * q.X();
        float y = s1 * Y() + s2 * q.Y();
        float z = s1 * Z() + s2 * q.Z();
        float w = s1 * W() + s2 * q.W();

        return new Quaternion(x, y, z, w);
    }
		
	/**
	 * Returns the product with another {@code Quaternion}.
	 * 
	 * @param q  a quaternion to multiply
	 * @return  the quaternion product
	 * @see Quaternion
	 */
	public Quaternion multiply(Quaternion q)
    {
		float x1 = X();
		float y1 = Y();
		float z1 = Z();
		float w1 = W();
		
		float x2 = q.X();
		float y2 = q.Y();
		float z2 = q.Z();
		float w2 = q.W();
		
		float x = w1 * x2 + x1 * w2 + y1 * z2 - z1 * y2;
    	float y = w1 * y2 + y1 * w2 + z1 * x2 - x1 * z2;
    	float z = w1 * z2 + z1 * w2 + x1 * y2 - y1 * x2;
    	float w = w1 * w2 - x1 * x2 - y1 * y2 - z1 * z2;

        return new Quaternion(x, y, z, w);
    }
		
	/**
	 * Returns the product with a {@code Complex} number.
	 * 
	 * @param c  a complex number to multiply
	 * @return  the complex product
	 * @see Complex
	 */
	public Quaternion multiply(Complex c)
    {
		float x1 = X();
		float y1 = Y();
		float z1 = Z();
		float w1 = W();
		
		float x2 = c.X();
		float y2 = c.Y();
		
		float x = x1 * x2 - y1 * y2;
		float y = x1 * y2 + y1 * x2;
		float z = z1 * x1 + w1 * y2;
		float w = w1 * x2 - z1 * y2;
		
        return new Quaternion(x, y, z, w);
    }
	
	/**
	 * Returns the conjugate of the {@code Quaternion}.
	 * 
	 * @return  the quaternion's conjugate
	 */
	public Quaternion conjugate()
    {
        return new Quaternion(-X(), -Y(), -Z(), W());
    }
		
	/**
	 * Returns the real part of the {@code Quaternion}.
	 * 
	 * @return  the quaternion's real part
	 */
	public float getReal()
	{
		return W();
	}
	
	
	@Override
	public Quaternion add(IMatrix m)
	{
		return (Quaternion) super.add(m);
	}
	
	@Override
	public Quaternion times(float s)
	{
		return (Quaternion) super.times(s);
	}
	
	@Override
	public Quaternion lerp(IVector v, float alpha)
	{
		return (Quaternion) super.lerp(v, alpha);
	}
	
	@Override
	public Quaternion projectTo(IVector norm)
	{
		return (Quaternion) super.projectTo(norm);
	}
	
	@Override
	public Quaternion normalize()
	{
		return (Quaternion) super.normalize();
	}
	
	@Override
	public Quaternion copy()
	{
		return (Quaternion) super.copy();
	}

	@Override
	public String toString()
	{
		float x = X();
		float y = Y();
		float z = Z();
		float w = W();
		
		String txt = "";
		txt += (Floats.isZero(w) ? "" : w < 0 ? " - " : " + ");
		txt += (Floats.isZero(w) ? "" : Math.abs(w));
		txt += (Floats.isZero(x) ? "" : x < 0 ? " - " : " + ");
		txt += (Floats.isZero(x) ? "" : Math.abs(x) + "i");
		txt += (Floats.isZero(y) ? "" : y < 0 ? " - " : " + ");
		txt += (Floats.isZero(y) ? "" : Math.abs(y) + "j");
		txt += (Floats.isZero(z) ? "" : z < 0 ? " - " : " + ");
		txt += (Floats.isZero(z) ? "" : Math.abs(z) + "k");
		txt += (txt.equals("") ? " + " + w : "");
		
		return txt;
	}
}