package zeno.util.algebra.matrices;

import java.util.Arrays;

/**
 * The {@code Matrix} class implements a generic floating-point matrix.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see IMatrix
 */
public class Matrix extends IMatrix
{	
	private float[][] values;
	
	/**
	 * Creates a new {@code Matrix}.
	 * 
	 * @param col  the column count
	 * @param row  the row count
	 */
	public Matrix(int col, int row)
	{
		values = new float[col][row];
	}

			
	@Override
	public void set(int col, int row, float val)
	{
		values[col][row] = val;
	}

	@Override
	public float get(int col, int row)
	{
		return values[col][row];
	}
		
	@Override
	public int getColumns()
	{
		return values.length;
	}

	@Override
	public int getRows()
	{
		return values[0].length;
	}
	
	
	@Override
	public Matrix add(IMatrix m)
	{
		return (Matrix) super.add(m);
	}
	
	@Override
	public Matrix times(float s)
	{
		return (Matrix) super.times(s);
	}

	@Override
	public Matrix copy()
	{
		return (Matrix) super.copy();
	}

	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Matrix)
		{
			return Arrays.equals(((Matrix) o).values, values);
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return Arrays.hashCode(values);
	}
}