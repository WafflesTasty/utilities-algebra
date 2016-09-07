package zeno.util.algebra.vectors;

import java.util.Arrays;

import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.Matrix;

/**
 * The {@code Vector} class implements a generic floating-point vector.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see IVector
 */
public class Vector extends IVector
{
	private float[] values;
	
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
			set(i, values[i]);
		}
	}
	
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param size  the vector's size
	 */
	public Vector(int size)
	{
		values = new float[size];
	}

	
	@Override
	protected void set(int col, int row, float val)
	{
		if(col != 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		
		values[row] = val;
	}

	@Override
	protected float get(int col, int row)
	{
		if(col != 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		
		return values[row];
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Vector)
		{
			return Arrays.equals(((Vector) o).values, values);
		}
		
		return false;
	}
	
	@Override
	protected int getColumns()
	{
		return 1;
	}

	@Override
	protected int getRows()
	{
		return values.length;
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(values);
	}

		
	@Override
	public Matrix transpose()
	{
		return (Matrix) super.transpose();
	}
		
	@Override
	public Vector lerp(IVector v, float alpha)
	{
		return (Vector) super.lerp(v, alpha);
	}
	
	@Override
	public Vector projectTo(IVector norm)
	{
		return (Vector) super.projectTo(norm);
	}
	
	@Override
	public Vector add(IMatrix m)
	{
		return (Vector) super.add(m);
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