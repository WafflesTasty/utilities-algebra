package zeno.util.algebra.tensors.vectors.complex;

import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.algebra.tensors.vectors.fixed.Vector4;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Quaternion} class extends complex numbers to four dimensions.
 * <br> They are useful in representing 3D space rotations.
 * 
 * @since Apr 30, 2016
 * @author Zeno
 * 
 * @see Vector4
 */
public class Quaternion extends Vector4
{
	/**
	 * Casts a {@code Vector4} to a {@code Quaternion}.
	 * 
	 * @param vec  a four-dimensional vector
	 * @return  a complex number
	 * @see Vector4
	 */
	public static Quaternion from(Vector4 vec)
	{
		return new Quaternion(vec.X(), vec.Y(), vec.Z(), vec.W());
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
	 * @param val  a coördinate value
	 */
	public Quaternion(float val)
	{
		super(val);
	}
	
	/**
	 * Creates a new {@code Quaternion}.
	 */
	public Quaternion()
	{
		super();
	}
	
	
	
	/**
	 * Returns the {@code Quaternion} multiplication.
	 * 
	 * @param q  a quaternion to multiply
	 * @return  the quaternion product
	 */
	public Quaternion times(Quaternion q)
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
	 * Returns the {@code Complex} multiplication.
	 * 
	 * @param c  a complex to multiply
	 * @return  the complex product
	 * @see Complex
	 */
	public Quaternion times(Complex c)
    {
		return c.times(this);
    }
		
	/**
	 * Returns the {@code Quaternion}'s conjugate.
	 * 
	 * @return  the quaternion conjugate
	 */
	public Quaternion conjugate()
    {
		 return new Quaternion(-X(), -Y(), -Z(), W());
    }
		
	
	/**
	 * Checks if the {@code Quaternion} is a real number.
	 * 
	 * @return  {@code true} if the quaternion is real
	 */
	public boolean isReal()
	{
		return X() == 0
			&& Y() == 0
			&& Z() == 0;
	}
	
	/**
	 * Returns the real part of the {@code Quaternion}.
	 * 
	 * @return  the quaternion's real part
	 */
	public float Real()
	{
		return W();
	}
	
	
	
	/**
	 * performs spherical interpolation with a {@code Quaternion}.
	 * 
	 * @param q  a quaternion to interpolate to
	 * @param alpha  an interpolation alpha
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
	 * Performs linear interpolation on the {@code Quaternion}.
	 * 
	 * @param v  a vector to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated vector
	 */
	public Quaternion lerp(Quaternion v, float alpha)
    {
		return from(super.lerp(v, alpha));
    }

	/**
	 * Projects the {@code Quaternion} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected vector
	 */
	public Quaternion projectTo(Quaternion plane)
	{
		return from(super.projectTo(plane));
	}
	
	/**
	 * Returns the {@code Quaternion}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the difference vector
	 */
	public Quaternion minus(Quaternion v)
	{
		return from(super.minus(v));
	}
	
	/**
	 * Returns the {@code Quaternion}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Quaternion plus(Quaternion v)
	{
		return from(super.plus(v));
	}
	
	
	@Override
	public Quaternion times(float s)
	{
		return from(super.times(s));
	}
	
	@Override
	public Quaternion normalize()
	{
		return from(super.normalize());
	}
		
	@Override
	public Quaternion copy()
	{
		return from(super.copy());
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
		txt += (Floats.isZero(w) ? "" : Floats.abs(w));
		txt += (Floats.isZero(x) ? "" : x < 0 ? " - " : " + ");
		txt += (Floats.isZero(x) ? "" : Floats.abs(x) + "i");
		txt += (Floats.isZero(y) ? "" : y < 0 ? " - " : " + ");
		txt += (Floats.isZero(y) ? "" : Floats.abs(y) + "j");
		txt += (Floats.isZero(z) ? "" : z < 0 ? " - " : " + ");
		txt += (Floats.isZero(z) ? "" : Floats.abs(z) + "k");
		txt += (txt.equals("") ? " + " + w : "");
		
		return txt;
	}
}