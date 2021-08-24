package zeno.util.algebra.linear.vector;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.Floats;

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
	 * @param size  a vector size
	 */
	public Vector(int size)
	{
		super(size, 1);
	}

	/**
	 * Returns the absolute {@code Vector}.
	 * 
	 * @return  the vector absolute
	 */
	public Vector Absolute()
	{
		Vector abs = Vectors.create(Size());
		for(int i = 0; i < Size(); i++)
		{
			float val = Floats.abs(get(i));
			abs.set(val, i);
		}
		
		return abs;
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
	 * Returns the {@code Vector}'s 1-norm.
	 * 
	 * @return  a 1-norm
	 */
	public float norm1()
	{
		float norm = 0f;
		for(int i = 0; i < Size(); i++)
		{
			norm += Floats.abs(get(i));
		}

		return norm;
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