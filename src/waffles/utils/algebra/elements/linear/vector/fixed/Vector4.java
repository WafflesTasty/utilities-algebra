package waffles.utils.algebra.elements.linear.vector.fixed;

import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;

/**
 * A {@code Vector3} defines a {@code Vector} in four dimensions.
 *
 * @author Waffles
 * @since Mar 21, 2016
 * @version 1.1
 * 
 * 
 * @see Vector
 */
public class Vector4 extends Vector
{
	/**
	 * Defines a four-dimensional x-axis unit vector.
	 */
    public static final Vector4 X_AXIS = new Vector4(1, 0, 0, 0);
    /**
     * Defines a four-dimensional y-axis unit vector.
     */
    public static final Vector4 Y_AXIS = new Vector4(0, 1, 0, 0);
    /**
     * Defines a four-dimensional z-axis unit vector.
     */
    public static final Vector4 Z_AXIS = new Vector4(0, 0, 1, 0);
    /**
     * Defines a four-dimensional w-axis unit vector.
     */
    public static final Vector4 W_AXIS = new Vector4(0, 0, 0, 1);
	
    
    /**
	 * Creates a new {@code Vector4}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Vector4(TensorData d)
	{
		super(d);
	}
    
    /**
	 * Creates a new {@code Vector4}.
	 * 
	 * @param x  a vector x-coordinate
	 * @param y  a vector y-coordinate
	 * @param z  a vector z-coordinate
	 * @param w  a vector w-coordinate
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
	 * @param val  a vector value
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
	 * Changes the x-coordinate of the {@code Vector4}.
	 * 
	 * @param x  a vector x-coordinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coordinate of the {@code Vector4}.
	 * 
	 * @param y  a vector y-coordinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
	
	/**
	 * Changes the z-coordinate of the {@code Vector4}.
	 * 
	 * @param z  a vector z-coordinate
	 */
	public void setZ(float z)
	{
		set(z, 2);
	}
	
	/**
	 * Changes the w-coordinate of the {@code Vector4}.
	 * 
	 * @param w  a vector w-coordinate
	 */
	public void setW(float w)
	{
		set(w, 3);
	}
		
	/**
	 * Returns the x-coordinate of the {@code Vector4}.
	 * 
	 * @return  a vector x-coordinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coordinate of the {@code Vector4}.
	 * 
	 * @return  a vector y-coordinate
	 */
	public float Y()
	{
		return get(1);
	}
	
	/**
	 * Returns the z-coordinate of the {@code Vector4}.
	 * 
	 * @return  a vector z-coordinate
	 */
	public float Z()
	{
		return get(2);
	}
	
	/**
	 * Returns the w-coordinate of the {@code Vector4}.
	 * 
	 * @return  a vector w-coordinate
	 */
	public float W()
	{
		return get(3);
	}


	@Override
	public Vector4 plus(Additive a)
	{
		return (Vector4) super.plus(a);
	}
	
	@Override
	public Vector4 minus(Abelian a)
	{
		return (Vector4) super.minus(a);
	}
	
	@Override
	public Vector4 ltimes(Tensor t)
	{
		return (Vector4) super.ltimes(t);
	}
			
	@Override
	public Vector4 times(Float v)
	{
		return (Vector4) super.times(v);
	}
			
	@Override
	public Vector4 normalize()
	{
		return (Vector4) super.normalize();
	}
	
	@Override
	public Vector4 destroy()
	{
		return (Vector4) super.destroy();
	}
	
	@Override
	public Vector4 copy()
	{
		return (Vector4) super.copy();
	}
}