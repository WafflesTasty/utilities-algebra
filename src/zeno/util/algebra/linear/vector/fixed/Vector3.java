package zeno.util.algebra.linear.vector.fixed;

import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.primitives.Floats;

/**
 * 
 * The {@code Vector3} class defines a three-dimensional vector.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @see Vector
 */
public class Vector3 extends Vector
{
	/**
	 * Defines the three-dimensional x-axis unit vector.
	 */
    public static final Vector3 X_AXIS = new Vector3(1, 0, 0);
    /**
     * Defines the three-dimensional y-axis unit vector.
     */
    public static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
    /**
     * Defines the three-dimensional z-axis unit vector.
     */
    public static final Vector3 Z_AXIS = new Vector3(0, 0, 1);
    
    
    
    /**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
	 * @param z  the vector's z-coördinate
	 */
	public Vector3(float x, float y, float z)
	{
		super(3);
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param val  a coördinate value
	 */
	public Vector3(float val)
	{
		this(val, val, val);
	}
	
	/**
	 * Creates a new {@code Vector3}.
	 */
	public Vector3()
	{
		super(3);
	}
		
		
	/**
	 * Changes the x-coördinate of the {@code Vector3}.
	 * 
	 * @param x  a new x-coördinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector3}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
	
	/**
	 * Changes the z-coördinate of the {@code Vector3}.
	 * 
	 * @param z  a new z-coördinate
	 */
	public void setZ(float z)
	{
		set(z, 2);
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
	 * Returns the x-coördinate of the {@code Vector3}.
	 * 
	 * @return  the vector's x-coördinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coördinate of the {@code Vector3}.
	 * 
	 * @return  the vector's y-coördinate
	 */
	public float Y()
	{
		return get(1);
	}
	
	/**
	 * Returns the z-coördinate of the {@code Vector3}.
	 * 
	 * @return  the vector's z-coördinate
	 */
	public float Z()
	{
		return get(2);
	}


	/**
	 * Returns the {@code Vector3}'s cross product.
	 * 
	 * @param v  a vector to cross
	 * @return  the vector cross
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
	 * Returns the {@code Vector3}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the vector difference
	 */
	public Vector3 minus(Vector3 v)
	{
		return (Vector3) super.minus(v);
	}
	
	/**
	 * Returns the {@code Vector3}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the vector sum
	 */
	public Vector3 plus(Vector3 v)
	{
		return (Vector3) super.plus(v);
	}
	
	
	@Override
	public Vector3 normalize()
	{
		return (Vector3) super.normalize();
	}
		
	@Override
	public Vector3 times(float v)
	{
		return (Vector3) super.times(v);
	}
			
	@Override
	public Vector3 instance()
	{
		return new Vector3();
	}
	
	@Override
	public Vector3 copy()
	{
		return (Vector3) super.copy();
	}
}