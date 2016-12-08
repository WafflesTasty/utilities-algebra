package zeno.util.algebra.tensors.vectors.fixed;

import zeno.util.algebra.tensors.vectors.Vector;

/**
 * The {@code Vector4} class defines a four-dimensional vector.
 *
 * @since Mar 21, 2016
 * @author Zeno
 * 
 * @see Vector
 */
public class Vector4 extends Vector
{
	/**
	 * Contains the four-dimensional x-axis unit vector.
	 */
    public static final Vector4 X_AXIS = new Vector4(1, 0, 0, 0);
    /**
     * Contains the four-dimensional y-axis unit vector.
     */
    public static final Vector4 Y_AXIS = new Vector4(0, 1, 0, 0);
    /**
     * Contains the four-dimensional z-axis unit vector.
     */
    public static final Vector4 Z_AXIS = new Vector4(0, 0, 1, 0);
    /**
     * Contains the four-dimensional w-axis unit vector.
     */
    public static final Vector4 W_AXIS = new Vector4(0, 0, 0, 1);
	
    
    
    /**
	 * Creates a new {@code Vector4}.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
	 * @param z  the vector's z-coördinate
	 * @param w  the vector's w-coördinate
	 */
	public Vector4(float x, float y, float z, float w)
	{
		super(4);
		setX(x);
		setY(y);
		setZ(z);
		setW(w);
	}
		
	/**
	 * Creates a new {@code Vector4}.
	 * 
	 * @param val  a coördinate value
	 */
	public Vector4(float val)
	{
		this(val, val, val, val);
	}
	
	/**
	 * Creates a new {@code Vector4}.
	 */
	public Vector4()
	{
		super(4);
	}

	
	
	/**
	 * Changes the x-coördinate of the {@code Vector4}.
	 * 
	 * @param x  a new x-coördinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector4}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
	
	/**
	 * Changes the z-coördinate of the {@code Vector4}.
	 * 
	 * @param z  a new z-coördinate
	 */
	public void setZ(float z)
	{
		set(z, 2);
	}
	
	/**
	 * Changes the w-coördinate of the {@code Vector4}.
	 * 
	 * @param w  a new w-coördinate
	 */
	public void setW(float w)
	{
		set(w, 3);
	}
		
	/**
	 * Returns the x-coördinate of the {@code Vector4}.
	 * 
	 * @return  the vector's x-coördinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coördinate of the {@code Vector4}.
	 * 
	 * @return  the vector's y-coördinate
	 */
	public float Y()
	{
		return get(1);
	}
	
	/**
	 * Returns the z-coördinate of the {@code Vector4}.
	 * 
	 * @return  the vector's z-coördinate
	 */
	public float Z()
	{
		return get(2);
	}
	
	/**
	 * Returns the w-coördinate of the {@code Vector4}.
	 * 
	 * @return  the vector's w-coördinate
	 */
	public float W()
	{
		return get(3);
	}

	
	
	/**
	 * Performs linear interpolation on the {@code Vector}.
	 * 
	 * @param v  a vector to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated vector
	 */
	public Vector4 lerp(Vector4 v, float alpha)
    {
		return (Vector4) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Vector} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected vector
	 */
	public Vector4 projectTo(Vector4 plane)
	{
		return (Vector4) super.projectTo(plane);
	}
	
	/**
	 * Returns the {@code Vector}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the difference vector
	 */
	public Vector4 minus(Vector4 v)
	{
		return (Vector4) super.minus(v);
	}
	
	/**
	 * Returns the {@code Vector}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Vector4 plus(Vector4 v)
	{
		return (Vector4) super.plus(v);
	}
	
	
	@Override
	public Vector4 times(float s)
	{
		return (Vector4) super.times(s);
	}
	
	@Override
	public Vector4 normalize()
	{
		return (Vector4) super.normalize();
	}
	
	@Override
	public Vector4 copy()
	{
		return (Vector4) super.copy();
	}
}