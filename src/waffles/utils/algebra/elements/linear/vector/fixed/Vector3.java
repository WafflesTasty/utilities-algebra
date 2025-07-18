package waffles.utils.algebra.elements.linear.vector.fixed;

import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.utilities.elements.Additive;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Vector3} defines a {@code Vector} in three dimensions.
 *
 * @author Waffles
 * @since Jul 5, 2018
 * @version 1.1
 * 
 * 
 * @see Vector
 */
public class Vector3 extends Vector
{
	/**
	 * Defines a three-dimensional x-axis unit vector.
	 */
    public static final Vector3 X_AXIS = new Vector3(1, 0, 0);
    /**
     * Defines a three-dimensional y-axis unit vector.
     */
    public static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
    /**
     * Defines a three-dimensional z-axis unit vector.
     */
    public static final Vector3 Z_AXIS = new Vector3(0, 0, 1);
    
    
    /**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Vector3(TensorData d)
	{
		super(d);
	}
    
    /**
	 * Creates a new {@code Vector3}.
	 * 
	 * @param x  a vector x-coordinate
	 * @param y  a vector y-coordinate
	 * @param z  a vector z-coordinate
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
	 * @param val  a vector value
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
	 * Returns the cross product of two {@code Vector3}'s.
	 * 
	 * @param v  a vector to multiply
	 * @return   a vector cross product
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
	 * Changes the x-coordinate of the {@code Vector3}.
	 * 
	 * @param x  a vector x-coordinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coordinate of the {@code Vector3}.
	 * 
	 * @param y  a vector y-coordinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
	
	/**
	 * Changes the z-coordinate of the {@code Vector3}.
	 * 
	 * @param z  a vector z-coordinate
	 */
	public void setZ(float z)
	{
		set(z, 2);
	}
			
	/**
	 * Returns the x-axis angle of the {@code Vector3}.
	 * 
	 * @return  a vector angle
	 */
	public float XAngle()
	{
		return Floats.atan2(X(), Y());
	}
	
	/**
	 * Returns the y-axis angle of the {@code Vector3}.
	 * 
	 * @return  a vector angle
	 */
	public float YAngle()
	{
		return Floats.atan2(Y(), Z());
	}
	
	/**
	 * Returns the z-axis angle of the {@code Vector3}.
	 * 
	 * @return  a vector angle
	 */
	public float ZAngle()
	{
		return Floats.atan2(Z(), X());
	}
	
	/**
	 * Returns the x-coordinate of the {@code Vector3}.
	 * 
	 * @return  a vector x-coordinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coordinate of the {@code Vector3}.
	 * 
	 * @return  a vector y-coordinate
	 */
	public float Y()
	{
		return get(1);
	}
	
	/**
	 * Returns the z-coordinate of the {@code Vector3}.
	 * 
	 * @return  a vector z-coordinate
	 */
	public float Z()
	{
		return get(2);
	}
	
	
	@Override
	public Vector3 absolute()
	{
		return (Vector3) super.absolute();
	}
	
	@Override
	public Vector3 plus(Additive a)
	{
		return (Vector3) super.plus(a);
	}
	
	@Override
	public Vector3 minus(Abelian a)
	{
		return (Vector3) super.minus(a);
	}
	
	@Override
	public Vector3 ltimes(Tensor t)
	{
		return (Vector3) super.ltimes(t);
	}
			
	@Override
	public Vector3 times(Float v)
	{
		return (Vector3) super.times(v);
	}
			
	@Override
	public Vector3 normalize()
	{
		return (Vector3) super.normalize();
	}
	
	@Override
	public Vector3 destroy()
	{
		return (Vector3) super.destroy();
	}
	
	@Override
	public Vector3 copy()
	{
		return (Vector3) super.copy();
	}
}