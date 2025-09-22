package waffles.utils.alg.lin.measure.vector.fixed;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.tensor.TensorData;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Vector2} defines a {@code Vector} in two dimensions.
 *
 * @author Waffles
 * @since Jul 5, 2018
 * @version 1.1
 * 
 * 
 * @see Vector
 */
public class Vector2 extends Vector
{
	/**
	 * Defines a two-dimensional x-axis unit vector.
	 */
    public static final Vector2 X_AXIS = new Vector2(1, 0);
    /**
     * Defines a two-dimensional y-axis unit vector.
     */
    public static final Vector2 Y_AXIS = new Vector2(0, 1);

    
    
    /**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param d  a data source
	 * 
	 * 
	 * @see TensorData
	 */
	public Vector2(TensorData d)
	{
		super(d);
	}
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param x  an x-coordinate
	 * @param y  an y-coordinate
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
	 * @param val  a constant value
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
	 * Returns the perpdot with a {@code Vector2}.
	 * This value equals the z-coordinate of the
	 * cross product between the vectors
	 * in the plane {@code Z = 0}.
	 * 
	 * @param v  a vector
	 * @return   a perpdot product
	 */
	public float perpdot(Vector2 v)
	{
		return X() * v.Y() - Y() * v.X();
	}
		
	/**
	 * Changes the x-coordinate of the {@code Vector2}.
	 * 
	 * @param x  an x-coordinate
	 */
	public void setX(float x)
	{
		set(x, 0);
	}
	
	/**
	 * Changes the y-coordinate of the {@code Vector2}.
	 * 
	 * @param y  an y-coordinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
		
	/**
	 * Returns the x-axis angle of the {@code Vector2}.
	 * 
	 * @return  a vector angle
	 */
	public float Angle()
	{
		return Floats.atan2(X(), Y());
	}
	
	/**
	 * Returns the x-coordinate of the {@code Vector2}.
	 * 
	 * @return  an x-coordinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coordinate of the {@code Vector2}.
	 * 
	 * @return  an y-coordinate
	 */
	public float Y()
	{
		return get(1);
	}

	
	@Override
	public Vector2 absolute()
	{
		return (Vector2) super.absolute();
	}

	@Override
	public Vector2 plus(Abelian a)
	{
		return (Vector2) super.plus(a);
	}
	
	@Override
	public Vector2 minus(Abelian a)
	{
		return (Vector2) super.minus(a);
	}
	
	@Override
	public Vector2 hadamard(Tensor t)
	{
		return (Vector2) super.hadamard(t);
	}
	
	@Override
	public Vector2 times(Float v)
	{
		return (Vector2) super.times(v);
	}
		
	@Override
	public Vector2 normalize()
	{
		return (Vector2) super.normalize();
	}
	
	@Override
	public Vector2 destroy()
	{
		return (Vector2) super.destroy();
	}
			
	@Override
	public Vector2 copy()
	{
		return (Vector2) super.copy();
	}
}