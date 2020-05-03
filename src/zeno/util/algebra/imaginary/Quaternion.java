package zeno.util.algebra.imaginary;

import zeno.util.algebra.linear.matrix.fixed.Matrix3x3;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.algebra.linear.vector.fixed.Vector4;
import zeno.util.tools.Floats;

/**
 * The {@code Quaternion} class extends complex numbers to four dimensions.
 * <br> They are useful in representing 3D space rotations.
 * 
 * @author Zeno
 * @since Apr 30, 2016
 * @version 1.0
 * 
 * 
 * @see Vector4
 */
public class Quaternion extends Vector4
{
	/**
	 * Casts a {@code Vector4} to a {@code Quaternion}.
	 * 
	 * @param vec  a four-dimensional vector
	 * @return  a complex quaternion
	 * 
	 * 
	 * @see Vector4
	 */
	public static Quaternion from(Vector4 vec)
	{
		return new Quaternion(vec.X(), vec.Y(), vec.Z(), vec.W());
	}
	
	/**
	 * Creates a rotation matrix from a {@code Quaternion}.
	 * 
	 * @param q  a rotation quaternion
	 * @return   a rotation matrix
	 * 
	 * 
	 * @see Matrix3x3
	 */
	public static Matrix3x3 rotate3D(Quaternion q)
	{
		Quaternion p = q.normalize();
		
		float x = p.X();
		float y = p.Y();
		float z = p.Z();
		float w = p.W();
		
		
		Matrix3x3 m = Matrix3x3.identity();
		
		m.set(1 - 2 * (y * y + z * z), 0, 0);
		m.set(0 + 2 * (x * y - z * w), 0, 1);
		m.set(0 + 2 * (x * z + y * w), 0, 2);
		
		m.set(0 + 2 * (x * y + z * w), 1, 0);
		m.set(1 - 2 * (x * x + z * z), 1, 1);
		m.set(0 + 2 * (y * z - x * w), 1, 2);
		
		m.set(0 + 2 * (x * z - y * w), 2, 0);
		m.set(0 + 2 * (x * w + y * z), 2, 1);
		m.set(1 - 2 * (x * x + y * y), 2, 2);
		
		
		return m;
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
	 */
	public Quaternion()
	{
		this(0f, 0f, 0f, 1f);
	}
	
	
	/**
	 * Returns the {@code Quaternion} angle.
	 * 
	 * @return  a rotation angle
	 */
	public float Angle()
	{
		return Floats.normrad(2 * Floats.acos(W()));
	}
	
	/**
	 * Returns the {@code Quaternion} axis.
	 * 
	 * @return  a rotation axis
	 * 
	 * 
	 * @see Vector3
	 */
	public Vector3 Axis()
	{
		return (Vector3) Vectors.resize(this, 3).normalize();
	}
	
	/**
	 * Returns a {@code Quaternion} conjugate.
	 * 
	 * @return  a conjugate quaternion
	 */
	public Quaternion conjugate()
    {
		 return new Quaternion(-X(), -Y(), -Z(), W());
    }
	
	/**
	 * Returns a {@code Quaternion} element sum.
	 * 
	 * @param q  a quaternion to add
	 * @return   a sum quaternion
	 */
	public Quaternion plus(Quaternion q)
	{
		return from(super.plus(q));
	}
	
	/**
	 * Returns a {@code Quaternion} difference.
	 * 
	 * @param v  a quaternion to subtract
	 * @return   a quaternion difference
	 */
	public Quaternion minus(Quaternion v)
	{
		return from(super.minus(v));
	}
	
	/**
	 * Returns a {@code Quaternion} product.
	 * 
	 * @param q  a quaternion to multiply
	 * @return   a quaternion product
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
	 * Checks if this number is real.
	 * 
	 * @return  {@code true} if the quaternion is real
	 */
	public boolean isReal()
	{
		return X() == 0
			&& Y() == 0
			&& Z() == 0;
	}
	

	@Override
	public Quaternion times(float v)
	{
		return from(super.times(v));
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
		txt += (w == 0 ? "" : w < 0 ? " - " : " + ");
		txt += (w == 0 ? "" : Floats.abs(w)			);
		txt += (x == 0 ? "" : x < 0 ? " - " : " + ");
		txt += (x == 0 ? "" : Floats.abs(x) + "i"	);
		txt += (y == 0 ? "" : y < 0 ? " - " : " + ");
		txt += (y == 0 ? "" : Floats.abs(y) + "j"	);
		txt += (z == 0 ? "" : z < 0 ? " - " : " + ");
		txt += (z == 0 ? "" : Floats.abs(z) + "k");
		txt += (txt.equals("") ? " + " + w : "");
		
		return txt;
	}
}