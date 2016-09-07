package zeno.util.algebra.vectors.fixed;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.Vector;

/**
 * The {@code Vector3} class defines a three-dimensional vector.
 *
 * @since Mar 21, 2016
 * @author Zeno
 * 
 * @see Vector
 */
public class Vector3 extends Vector
{
	/**
	 * Contains the three-dimensional x-axis unit vector.
	 */
    public static final Vector3 X_AXIS = new Vector3(1, 0, 0);
    /**
     * Contains the three-dimensional y-axis unit vector.
     */
    public static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
    /**
     * Contains the three-dimensional z-axis unit vector.
     */
    public static final Vector3 Z_AXIS = new Vector3(0, 0, 1);
	
	
	/**
	 * Creates a new {@code Vector3}.
	 */
	public Vector3()
	{
		super(3);
	}
	
	/**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param val  a co�rdinate value
	 */
	public Vector3(float val)
	{
		this(val, val, val);
	}
	
	/**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param x  the vector's x-co�rdinate
	 * @param y  the vector's y-co�rdinate
	 * @param z  the vector's z-co�rdinate
	 */
	public Vector3(float x, float y, float z)
	{
		super(3);
		setX(x);
		setY(y);
		setZ(z);
	}
		
	

	/**
	 * Returns the cross product with a {@code Vector3}.
	 * 
	 * @param v  a vector to multiply
	 * @return  a cross product
	 */
	public Vector3 cross(Vector3 v)
	{
		Vector3 cross = new Vector3();
		
		cross.setX(Y() * v.Z() - Z() * v.Y());
		cross.setY(Z() * v.X() - X() * v.Z());
		cross.setZ(X() * v.Y() - Y() * v.X());
		
		return cross;
	}
	
	/**
	 * Returns the angle with another {@code Vector3}.
	 * 
	 * @param v  a vector to calculate the angle for
	 * @return  the angle between two vectors
	 */
	public float angle(Vector3 v)
	{
		return Floats.acos(dot(v));
	}
	
	
	/**
	 * Changes the x-co�rdinate of the {@code Vector3}.
	 * 
	 * @param x  a new x-co�rdinate
	 */
	public void setX(float x)
	{
		set(0, x);
	}
	
	/**
	 * Changes the y-co�rdinate of the {@code Vector3}.
	 * 
	 * @param y  a new y-co�rdinate
	 */
	public void setY(float y)
	{
		set(1, y);
	}
	
	/**
	 * Changes the z-co�rdinate of the {@code Vector3}.
	 * 
	 * @param z  a new z-co�rdinate
	 */
	public void setZ(float z)
	{
		set(2, z);
	}
	
			
	/**
	 * Returns the z-axis angle of the {@code Vector3}.
	 * 
	 * @return  the vector's z-axis angle
	 */
	public float XAngle()
	{
		return Floats.atan2(X(), Y());
	}
	
	/**
	 * Returns the y-axis angle of the {@code Vector3}.
	 * 
	 * @return  the vector's y-axis angle
	 */
	public float YAngle()
	{
		return Floats.atan2(Y(), Z());
	}
	
	/**
	 * Returns the z-axis angle of the {@code Vector3}.
	 * 
	 * @return  the vector's x-axis angle
	 */
	public float ZAngle()
	{
		return Floats.atan2(Z(), X());
	}
	
	/**
	 * Returns the x-co�rdinate of the {@code Vector3}.
	 * 
	 * @return  the vector's x-co�rdinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-co�rdinate of the {@code Vector3}.
	 * 
	 * @return  the vector's y-co�rdinate
	 */
	public float Y()
	{
		return get(1);
	}
	
	/**
	 * Returns the z-co�rdinate of the {@code Vector3}.
	 * 
	 * @return  the vector's z-co�rdinate
	 */
	public float Z()
	{
		return get(2);
	}

	
	@Override
	public Vector3 lerp(IVector v, float alpha)
	{
		return (Vector3) super.lerp(v, alpha);
	}
	
	@Override
	public Vector3 projectTo(IVector norm)
	{
		return (Vector3) super.projectTo(norm);
	}
		
	@Override
	public Vector3 add(IMatrix m)
	{
		return (Vector3) super.add(m);
	}
	
	@Override
	public Vector3 times(float s)
	{
		return (Vector3) super.times(s);
	}
		
	@Override
	public Vector3 normalize()
	{
		return (Vector3) super.normalize();
	}
	
	@Override
	public Vector3 copy()
	{
		return (Vector3) super.copy();
	}
}