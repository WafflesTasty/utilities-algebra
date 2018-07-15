package zeno.util.algebra.linear.vector;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.attempt4.linear.Vectors;

/**
 * The {@code Vector} class defines an algebraic vector using the standard dot product.
 * A vector describes a linear relation for tensors of the first order.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @see Matrix
 */
public class Vector extends Matrix
{
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param values  a value array
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
	 * @param size  a vector size
	 */
	public Vector(int size)
	{
		super(size, 1);
	}

	
	/**
	 * Returns a value in the {@code Vector}.
	 * 
	 * @param i  a vector index
	 * @return  a vector value
	 */
	public float get(int i)
	{
		return get(i, 0);
	}
	
	/**
	 * Changes a value in the {@code Vector}.
	 * 
	 * @param val  a vector value
	 * @param i  a vector index
	 */
	public void set(float val, int i)
	{
		set(val, i, 0);
	}

	/**
	 * Returns the absolute {@code Vector}.
	 * 
	 * @return  the vector absolute
	 */
	public Vector Absolute()
	{
		return Vectors.absolute(this);
	}

	
	@Override
	public Vector normalize()
	{
		return (Vector) super.normalize();
	}
	
	@Override
	public Vector minus(Tensor m)
	{
		return (Vector) super.minus(m);
	}
	
	@Override
	public Vector times(float val)
	{
		return (Vector) super.times(val);
	}
	
	@Override
	public Vector plus(Tensor m)
	{
		return (Vector) super.plus(m);
	}

	@Override
	public Vector instance()
	{
		return new Vector(Size());
	}
	
	@Override
	public Vector copy()
	{
		return (Vector) super.copy();
	}
}