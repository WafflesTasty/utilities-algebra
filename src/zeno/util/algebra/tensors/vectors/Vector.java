package zeno.util.algebra.tensors.vectors;

import zeno.util.algebra.tensors.Tensor;
import zeno.util.algebra.tensors.vectors.fixed.Vector2;
import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.algebra.tensors.vectors.fixed.Vector4;

/**
 * The {@code Vector} class provides a default implementation for
 * algebraic vectors using the standard dot product. A vector describes
 * a linear relation for tensors of the first order.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see Tensor
 */
public class Vector extends Tensor
{
	/**
	 * Creates a {@code Vector} with the specified size.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> (2) returns a {@code Vector2}. </li>
	 * <li> (3) returns a {@code Vector3}. </li>
	 * <li> (4) returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param size  the size of the vector
	 * @return  a new vector
	 * @see Tensor
	 */
	public static Tensor create(int size)
	{
		if(size == 4)
		{
			return new Vector4();
		}
		
		if(size == 3)
		{
			return new Vector3();
		}
		
		if(size == 2)
		{
			return new Vector2();
		}
		
		return new Vector(size);
	}

	/**
	 * Creates a random {@code Vector} with the specified size.
	 * 
	 * @param size  the size of the vector
	 * @return  a random vector
	 */
	public static Tensor random(int size)
	{
		return Tensor.random(size);
	}
	
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param values  the vector's values
	 */
	public Vector(float... values)
	{
		this(values.length);
		for(int i = 0; i < values.length; i++)
		{
			set(values[i], i);
		}
	}
	
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param size  the vector's size
	 */
	public Vector(int size)
	{
		super(size);
	}

	
	
	/**
	 * Returns a {@code Vector} value.
	 * 
	 * @param i  the component index
	 * @return  a component value
	 */
	public float get(int i)
	{
		return super.get(i);
	}
	
	/**
	 * Changes a {@code Vector} value.
	 * 
	 * @param val  a component value
	 * @param i  the component index
	 */
	public void set(float val, int i)
	{
		super.set(val, i);
	}
		
	
	/**
	 * Performs linear interpolation on the {@code Vector}.
	 * 
	 * @param v  a vector to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated vector
	 */
	public Vector lerp(Vector v, float alpha)
    {
		return (Vector) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Vector} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected vector
	 */
	public Vector projectTo(Vector plane)
	{
		return (Vector) super.projectTo(plane);
	}
	
	/**
	 * Returns the {@code Vector}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the difference vector
	 */
	public Vector minus(Vector v)
	{
		return (Vector) super.minus(v);
	}
	
	/**
	 * Returns the {@code Vector}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Vector plus(Vector v)
	{
		return (Vector) super.plus(v);
	}
	
	
	@Override
	public Vector times(float s)
	{
		return (Vector) super.times(s);
	}

	@Override
	public Vector normalize()
	{
		return (Vector) super.normalize();
	}
	
	@Override
	public Vector copy()
	{
		return (Vector) super.copy();
	}
}