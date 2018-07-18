package zeno.util.algebra.linear.vector.fixed;

import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.Floats;

/**
 * The {@code Vector2} class defines a two-dimensional vector.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @see Vector
 */
public class Vector2 extends Vector
{
	/**
	 * Defines the two-dimensional x-axis unit vector.
	 */
    public static final Vector2 X_AXIS = new Vector2(1, 0);
    /**
     * Defines the two-dimensional y-axis unit vector.
     */
    public static final Vector2 Y_AXIS = new Vector2(0, 1);

    
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
	 */
	public Vector2(float x, float y)
	{
		super(2);
		setX(x);
		setY(y);
	}
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param val  a coördinate value
	 */
	public Vector2(float val)
	{
		this(val, val);
	}
	
	/**
	 * Creates a new {@code Vector2}.
	 */
	public Vector2()
	{
		super(2);
	}
		
		
	/**
	 * Changes the x-coördinate of the {@code Vector2}.
	 * 
	 * @param x  a new x-coördinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector2}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
		
	/**
	 * Returns the x-axis angle of the {@code Vector2}.
	 * 
	 * @return  the vector's angle
	 */
	public float XAngle()
	{
		return Floats.atan2(X(), Y());
	}
	
	/**
	 * Returns the x-coördinate of the {@code Vector2}.
	 * 
	 * @return  the vector's x-coördinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coördinate of the {@code Vector2}.
	 * 
	 * @return  the vector's y-coördinate
	 */
	public float Y()
	{
		return get(1);
	}

	
	/**
	 * Returns the {@code Vector2}'s perpdot product.
	 * This value equals the z-coördinate of the cross
	 * product between the two vectors.
	 * 
	 * @param v  a vector to perpdot
	 * @return  the vector perpdot
	 */
	public float perpdot(Vector2 v)
	{
		return X() * v.Y() - Y() * v.X();
	}
	
	/**
	 * Returns the {@code Vector2}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the vector difference
	 */
	public Vector2 minus(Vector2 v)
	{
		return (Vector2) super.minus(v);
	}
	
	/**
	 * Returns the {@code Vector2}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the vector sum
	 */
	public Vector2 plus(Vector2 v)
	{
		return (Vector2) super.plus(v);
	}
	
	
	@Override
	public Vector2 normalize()
	{
		return (Vector2) super.normalize();
	}
		
	@Override
	public Vector2 times(float v)
	{
		return (Vector2) super.times(v);
	}
			
	@Override
	public Vector2 instance()
	{
		return new Vector2();
	}
	
	@Override
	public Vector2 copy()
	{
		return (Vector2) super.copy();
	}
}