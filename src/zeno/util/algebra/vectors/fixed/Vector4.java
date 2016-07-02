package zeno.util.algebra.vectors.fixed;

import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.Vector;

/**
 * The {@code Vector4} class defines a four-dimensional vector.
 *
 * @author Zeno
 * @since Mar 21, 2016
 * @see Vector
 */
public class Vector4 extends Vector
{
	/**
	 * Creates a new {@code Vector4}.
	 */
	public Vector4()
	{
		super(4);
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
	 * Changes the x-coördinate of the {@code Vector4}.
	 * 
	 * @param x  a new x-coördinate
	 */
	public void setX(float x)
	{
		set(0, x);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector4}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(1, y);
	}
	
	/**
	 * Changes the z-coördinate of the {@code Vector4}.
	 * 
	 * @param z  a new z-coördinate
	 */
	public void setZ(float z)
	{
		set(2, z);
	}
	
	/**
	 * Changes the w-coördinate of the {@code Vector4}.
	 * 
	 * @param w  a new w-coördinate
	 */
	public void setW(float w)
	{
		set(3, w);
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

	
	@Override
	public Vector4 lerp(IVector v, float alpha)
	{
		return (Vector4) super.lerp(v, alpha);
	}
	
	@Override
	public Vector4 projectTo(IVector norm)
	{
		return (Vector4) super.projectTo(norm);
	}
	
	@Override
	public Vector4 add(IMatrix m)
	{
		return (Vector4) super.add(m);
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