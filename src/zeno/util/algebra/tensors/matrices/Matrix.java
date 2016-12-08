package zeno.util.algebra.tensors.matrices;

import zeno.util.algebra.tensors.vectors.Vector;
import zeno.util.algebra.tensors.Tensor;
import zeno.util.algebra.tensors.matrices.fixed.Matrix2x2;
import zeno.util.algebra.tensors.matrices.fixed.Matrix3x3;
import zeno.util.algebra.tensors.matrices.fixed.Matrix4x4;

/**
 * The {@code Matrix} class provides a default implementation for
 * algebraic matrices using the standard dot product. A matrix describes
 * a linear relation for tensors of the second order.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see Tensor
 */
public class Matrix extends Tensor
{
	/**
	 * Creates a {@code Matrix} with the specified dimensions.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> (2,2) returns a {@code Matrix2x2}. </li>
	 * <li> (3,3) returns a {@code Matrix3x3}. </li>
	 * <li> (4,4) returns a {@code Matrix4x4}. </li>
	 * <li> (R) returns a {@code Vector}. </li>
	 * <li> (2) returns a {@code Vector2}. </li>
	 * <li> (3) returns a {@code Vector3}. </li>
	 * <li> (4) returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param col  a matrix column count
	 * @param row  a matrix row count
	 * @return  a new matrix
	 * @see Tensor
	 */
	public static Tensor create(int col, int row)
	{
		if(col == 1)
		{
			return Vector.create(row);
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
	 * Returns a random value {@code Matrix} of the specified size.
	 * 
	 * @param col  a matrix column count
	 * @param row  a matrix row count
	 * @return  a random matrix
	 */
	public static Tensor random(int col, int row)
	{
		return Tensor.random(col, row);
	}
	
	/**
	 * Returns an identity {@code Matrix} of the specified size.
	 * 
	 * @param size  the size of the matrix
	 * @return  an identity matrix
	 * @see Tensor
	 */
	public static Tensor identity(int size)
	{
		return Tensor.identity(size, 2);
	}
	
	/**
	 * Creates a new {@code Matrix}.
	 * 
	 * @param col  a matrix column count
	 * @param row  a matrix row count
	 */
	public Matrix(int col, int row)
	{
		super(col, row);
	}

	
	
	/**
	 * Returns the {@code Matrix} row count.
	 * 
	 * @return  the matrix's rows
	 */
	public int rows()
	{
		return dimensions()[1];
	}
	
	/**
	 * Returns the {@code Matrix} column count.
	 * 
	 * @return  the matrix's columns
	 */
	public int columns()
	{
		return dimensions()[0];
	}
	
	/**
	 * Returns a {@code Matrix} value at an index.
	 * 
	 * @param c  a value column index
	 * @param r  a value row index
	 * @return  a matrix value
	 */
	public float get(int c, int r)
	{
		return super.get(c, r);
	}
	
	/**
	 * Changes a {@code Matrix} value at an index.
	 * 
	 * @param val  a matrix value
	 * @param c  a value column index
	 * @param r  a value row index
	 */
	public void set(float val, int c, int r)
	{
		super.set(val, c, r);
	}

	
	/**
	 * Performs linear interpolation on the {@code Matrix}.
	 * 
	 * @param v  a matrix to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated matrix
	 */
	public Matrix lerp(Matrix v, float alpha)
    {
		return (Matrix) super.lerp(v, alpha);
    }

//	/**
//	 * Projects the {@code Matrix} to a hyperplane.
//	 * 
//	 * @param plane  a plane to project to
//	 * @return  the projected matrix
//	 */
//	public Matrix projectTo(Matrix plane)
//	{
//		return (Matrix) super.projectTo(plane);
//	}
	
	
	
	/**
	 * Returns the {@code Matrix} multiplication.
	 * 
	 * @param v  a vector to multiply
	 * @return  the result vector
	 * @see Vector
	 */
	public Vector times(Vector v)
	{
		int col1 = dimensions()[0];
		int row1 = dimensions()[1];
		
		int row2 = v.dimensions()[0];
	
		if(col1 != row2)
		{
			return null;
		}
		
		
		Tensor result = create(1, row1);
		for(int r = 0; r < row1; r++)
		{
			float val = 0;
			for(int d = 0; d < col1; d++)
			{
				val += get(d, r) * v.get(d);
			}
			
			result.set(val, r);
		}
		
		return (Vector) result;
	}
	
	/**
	 * Returns the {@code Matrix} multiplication.
	 * 
	 * @param m  a matrix to multiply
	 * @return  the result matrix
	 */
	public Matrix times(Matrix m)
	{
		int col1 = dimensions()[0];
		int row1 = dimensions()[1];
		
		int col2 = m.dimensions()[0];
		int row2 = m.dimensions()[1];
	
		if(col1 != row2)
		{
			return null;
		}
		
		
		Tensor result = create(col2, row1);
		for(int c = 0; c < col2; c++)
		{
			for(int r = 0; r < row1; r++)
			{
				float val = 0;
				for(int d = 0; d < col1; d++)
				{
					val += get(d, r) * m.get(c, d);
				}
				
				result.set(val, c, r);
			}
		}
		
		return (Matrix) result;
	}
			
//	/**
//	 * Returns the {@code Matrix}'s subtraction.
//	 * 
//	 * @param v  a matrix to subtract
//	 * @return  the difference matrix
//	 */
//	public Matrix minus(Matrix v)
//	{
//		return (Matrix) super.minus(v);
//	}
//	
//	/**
//	 * Returns the {@code Matrix}'s summation.
//	 * 
//	 * @param v  a matrix to add
//	 * @return  the sum matrix
//	 */
//	public Matrix plus(Matrix v)
//	{
//		return (Matrix) super.plus(v);
//	}
//	
	/**
	 * Returns the {@code Matrix} transpose.
	 * 
	 * @return  the transpose matrix
	 */
	public Matrix transpose()
	{
		int row = rows();
		int col = columns();
		
		Tensor result = Matrix.create(row, col);
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < row; r++)
			{
				float val = get(c, r);
				result.set(val, r, c);
			}
		}
		
		return (Matrix) result;
	}
	
	
	
	@Override
	public Matrix times(float s)
	{
		return (Matrix) super.times(s);
	}

	@Override
	public Matrix normalize()
	{
		return (Matrix) super.normalize();
	}
	
	@Override
	public Matrix copy()
	{
		return (Matrix) super.copy();
	}
}