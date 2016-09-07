package zeno.util.algebra.matrices;

import zeno.util.algebra.matrices.fixed.Matrix2x2;
import zeno.util.algebra.matrices.fixed.Matrix3x3;
import zeno.util.algebra.matrices.fixed.Matrix4x4;
import zeno.util.algebra.vectors.IVector;
import zeno.util.tools.generic.properties.Copyable;

/**
 * The {@code IMatrix} class defines the base for a floating-point matrix.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see Copyable
 */
public abstract class IMatrix implements Copyable<IMatrix>
{
	/**
	 * Creates a null matrix with the specified dimensions.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> 1xR returns a {@code Vector}. </li>
	 * <li> 1x2 returns a {@code Vector2}. </li>
	 * <li> 1x3 returns a {@code Vector3}. </li>
	 * <li> 1x4 returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param col  the amount of columns in the matrix
	 * @param row  the amount of rows in the matrix
	 * @return  a new null matrix
	 */
	public static IMatrix createMatrix(int col, int row)
	{
		if(col == 1)
		{
			return IVector.createVector(row);
		}
		
		if(col == row)
		{
			if(col == 2)
			{
				return new Matrix2x2();
			}
			
			if(col == 3)
			{
				return new Matrix3x3();
			}
			
			if(col == 4)
			{
				return new Matrix4x4();
			}
		}
		
		return new Matrix(col, row);
	}
	
	/**
	 * Returns an identity matrix of the specified size.
	 * 
	 * @param size  the size of the matrix
	 * @return  an identity matrix
	 */
	public static IMatrix createIdentity(int size)
	{
		IMatrix m = createMatrix(size, size);
		for(int i = 0; i < size; i++)
		{
			m.set(i, i, 1);
		}
		
		return m;
	}
	
	
	/**
	 * Returns a resized copy of the {@code Matrix}.
	 * 
	 * @param col  a new column count
	 * @param row  a new row count
	 * @return  a resized matrix
	 */
	public IMatrix resize(int col, int row)
	{
		IMatrix result = createMatrix(col, row);
		
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				float val = (c == r ? 1 : 0);
				if(c < getColumns())
				{
					if(r < getRows())
					{
						val = get(c, r);
					}
				}
				
				result.set(c, r, val);
			}
		}
		
		return result;
	}
				
	/**
	 * Returns the multiple with another {@code Matrix}.
	 * 
	 * @param mat  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public IMatrix times(IMatrix mat)
	{
		int col1 = getColumns();
		int row1 = getRows();

		int col2 = mat.getColumns();
		int row2 = mat.getRows();
	
		if(col1 != row2)
		{
			return null;
		}
		
		
		IMatrix result = createMatrix(col2, row1);
		for(int c = 0; c < col2; c++)
		{
			for(int r = 0; r < row1; r++)
			{
				float val = 0;
				for(int v = 0; v < col1; v++)
				{
					val += get(v, r) * mat.get(c, v);
				}
				
				result.set(c, r, val);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the scalar multiple of the {@code Matrix}.
	 * 
	 * @param val  a scalar to multiply
	 * @return  the multiplied matrix
	 */
	public IMatrix times(float val)
	{
		int col = getColumns();
		int row = getRows();
		
		IMatrix result = createMatrix(col, row);
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				result.set(c, r, val * get(c, r));
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the addition with another {@code Matrix}.
	 * 
	 * @param m  a matrix to add
	 * @return  the added matrix
	 */
	public IMatrix add(IMatrix m)
	{
		int col1 = getColumns();
		int row1 = getRows();
		
		int col2 = m.getColumns();
		int row2 = m.getRows();
		
		if(col1 != col2 || row1 != row2)
		{
			return null;
		}
		
		IMatrix result = createMatrix(col1, row1);
		for(int c = 0; c < col1; c++)
		{
			for(int r = 0; r < row1; r++)
			{
				float val = get(c, r) + m.get(c, r);
				result.set(c, r, val);
			}
		}
		
		return result;
	}
		
	/**
	 * Returns the transpose of the {@code Matrix}.
	 * 
	 * @return  the transposed matrix
	 */
	public IMatrix transpose()
	{
		int col = getColumns();
		int row = getRows();
		
		IMatrix result = createMatrix(row, col);
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				float val = get(c, r);
				result.set(r, c, val);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns an array of matrix values.
	 * 
	 * @return  a value array
	 */
	public float[] toArray()
	{
		int col = getColumns();
		int row = getRows();
		
		float[] array = new float[col * row];
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				array[c * row + r] = get(c, r);
			}
		}
		
		return array;
	}

	
	/**
	 * Changes a single value in the {@code Matrix}.
	 * 
	 * @param col  the value's column
	 * @param row  the value's row
	 * @param val  a new value
	 */
	protected abstract void set(int col, int row, float val);
	
	/**
	 * Returns a single value in the {@code Matrix}.
	 * 
	 * @param col  the value's column
	 * @param row  the value's row
	 * @return  a matrix value
	 */
	protected abstract float get(int col, int row);
	
	/**
	 * Returns the col count of the {@code Matrix}.
	 * 
	 * @return  the matrix column count
	 */
	protected abstract int getColumns();
	
	/**
	 * Returns the row count of the {@code Matrix}.
	 * 
	 * @return  the matrix row count
	 */
	protected abstract int getRows();

	
	@Override
	public IMatrix copy()
	{
		IMatrix m = Copyable.super.copy();
		
		int col = getColumns();
		int row = getRows();
		
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				m.set(c, r, get(c, r));
			}
		}
		
		return m;
	}
		
	@Override
	public IMatrix instance()
	{
		return IMatrix.createMatrix(getColumns(), getRows());
	}
	
	@Override
	public String toString()
	{
		int col = getColumns();
		int row = getRows();
		
		String val = "";
		for(int r = 0; r < row; r++)
		{
			val += "|";
			for(int c = 0; c < col; c++)
			{
				val+= "\t" + get(c, r);
			}
			val += "\t";
			val	+= "|";
			
			if(r != row - 1)
			{
				val += "\r\n";
			}
		}
		
		return val;
	}
}