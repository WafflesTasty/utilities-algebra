package zeno.util.algebra.tensors.vectors;

import zeno.util.algebra.tensors.Tensor;
import zeno.util.algebra.tensors.vectors.fixed.Vector2;
import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.algebra.tensors.vectors.fixed.Vector4;
import zeno.util.tools.Array;

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
	 * Splits a value array into equal size {@code Vectors}.
	 * 
	 * @param count  the amount of vectors to create
	 * @param vals  the values to split
	 * @return  a list of vectors
	 */
	public static Vector[] split(int count, float... vals)
	{
		Vector[] result = new Vector[count];
		
		int size = vals.length / count;
		for(int i = 0; i < count; i++)
		{
			result[i] = new Vector(Array.copy.of(vals, size * i, size * (i + 1)));
		}
		
		return result;
	}
	
	/**
	 * Creates a zero {@code Vector} with the specified size.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> (2) returns a {@code Vector2}. </li>
	 * <li> (3) returns a {@code Vector3}. </li>
	 * <li> (4) returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param size  the size of the vector
	 * @return  a new vector
	 */
	public static Vector create(int size)
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
	public static Vector random(int size)
	{
		return (Vector) Tensor.random(size);
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
	 * Sets a new {@code Vector} value.
	 * 
	 * @param val  a component value
	 * @param i  the component index
	 */
	public void set(float val, int i)
	{
		super.set(val, i);
	}
		
	/**
	 * Multiplies a {@code Vector} value.
	 * 
	 * @param val  a component value
	 * @param i  the component index
	 */
	public void times(float val, int i)
	{
		super.times(val, i);
	}
	
	/**
	 * Adds a {@code Vector} value.
	 * 
	 * @param val  a component value
	 * @param i  the component index
	 */
	public void plus(float val, int i)
	{
		super.plus(val, i);
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