package zeno.util.algebra.tensors;

import zeno.util.algebra.tensors.matrices.Matrix;
import zeno.util.algebra.tensors.vectors.Vector;
import zeno.util.tools.Array;
import zeno.util.tools.Randomizer;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Tensor} class provides a default implementation for
 * algebraic tensors using the standard dot product. A tensor describes
 * a linear relation for objects in any possible combination of dimensions.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see ITensor
 */
public class Tensor implements ITensor
{
	/**
	 * Creates a {@code Tensor} with the specified dimensions.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> (C,R) returns a {@code Matrix2}. </li>
	 * <li> (2,2) returns a {@code Matrix2x2}. </li>
	 * <li> (3,3) returns a {@code Matrix3x3}. </li>
	 * <li> (4,4) returns a {@code Matrix4x4}. </li>
	 * <li> (R) returns a {@code Vector}. </li>
	 * <li> (2) returns a {@code Vector2}. </li>
	 * <li> (3) returns a {@code Vector3}. </li>
	 * <li> (4) returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param dims  the dimensions of the tensor
	 * @return  a new tensor
	 * @see Tensor
	 */
	public static Tensor create(int... dims)
	{
		if(dims.length == 2)
		{
			return Matrix.create(dims[0], dims[1]);
		}
		
		if(dims.length == 1)
		{
			return Vector.create(dims[0]);
		}
		
		return new Tensor(dims);
	}
	
	/**
	 * Returns an identity {@code Tensor} of the specified size.
	 * 
	 * @param size  the size of the tensor
	 * @param order  the order of the tensor
	 * @return  an identity tensor
	 * @see Tensor
	 */
	public static Tensor identity(int size, int order)
	{
		int[] dims = new int[order];
		for(int i = 0; i < order; i++)
		{
			dims[i] = size;
		}
		
		Tensor tensor = create(dims);
		for(int i = 0; i < size; i++)
		{
			tensor.set(1f, i, i);
		}
		
		return tensor;
	}
	
	/**
	 * Returns a random {@code Tensor} of the specified size.
	 * 
	 * @param dims  the tensor's dimensions
	 * @return  a random tensor
	 */
	public static Tensor random(int... dims)
	{
		Tensor t = create(dims);
		for(int i = 0; i < t.size(); i++)
		{
			t.values()[i] = Randomizer.randomFloat(-1f, 1f);
		}
		
		return t;
	}
	
	
	
	private float[] values;
	private int[] dimensions;
	
	/**
	 * Creates a new {@code Tensor}.
	 * 
	 * @param dims  the tensor's dimension
	 */
	public Tensor(int... dims)
	{
		dimensions = dims;
		values = new float[size()];
	}

	/**
	 * Returns a {@code Tensor} value at an index.
	 * 
	 * @param coords  a value coördinate
	 * @return  a matrix value
	 */
	public float get(int... coords)
	{
		return values[toIndex(coords)];
	}
	
	/**
	 * Changes a {@code Tensor} value at an index.
	 * 
	 * @param val  a matrix value
	 * @param coords  a value coördinate
	 */
	public void set(float val, int... coords)
	{
		values[toIndex(coords)] = val;
	}
	
	
	
	@Override
	public Tensor times(float val)
	{
		Tensor result = copy();
		for(int i = 0; i < size(); i++)
		{
			result.values[i] *= val;
		}
		
		return result;
	}

	@Override
	public Tensor plus(ITensor t)
	{
		if(hasSameDimension(t))
		{
			Tensor result = copy();
			for(int i = 0; i < size(); i++)
			{
				result.values[i] += t.values()[i];
			}
			
			return result;
		}
		
		return null;
	}

	@Override
	public float dot(ITensor t)
	{
		if(hasSameDimension(t))
		{
			float product = 0;
			for(int i = 0; i < size(); i++)
			{
				product += values[i] * t.values()[i];
			}
			
			return product;
		}
		
		return Floats.NaN;
	}
	
	@Override
	public int[] dimensions()
	{
		return dimensions;
	}
	
	@Override
	public float[] values()
	{
		return values;
	}
	
	
	@Override
	public String toString()
	{
		return Array.parse.of(values);
	}
	
	@Override
	public Tensor instance()
	{
		return Tensor.create(dimensions);
	}
	
	@Override
	public Tensor copy()
	{
		Tensor copy = (Tensor) ITensor.super.copy();
		copy.values = Array.copy.of(values);
		return copy;
	}
	
	
	
	private boolean hasSameDimension(ITensor t)
	{
		return Array.equals.of(dimensions, t.dimensions());
	}
	
	private int toIndex(int... coords)
	{
		int index = 0;
		for(int i = 0; i < order(); i++)
		{
			index *= dimensions[i];
			index += coords[i];
		}

		return index;
	}
			
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Tensor)
		{
			if(hasSameDimension((Tensor) o))
			{
				return Array.equals.of(values, ((Tensor) o).values);
			}
		}

		return false;
	}
	
	@Override
	public int hashCode()
	{
		return Array.hash.of(values);
	}
}