package zeno.util.algebra.tensors.matrices;

import zeno.util.algebra.tensors.vectors.Vector;
import zeno.util.tools.primitives.Floats;
import zeno.util.tools.primitives.Integers;
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
	 * Returns the trace of the {@code Matrix}.
	 * 
	 * @return  the matrix trace
	 */
	public float trace()
	{
		float trc = 0;
		for(int i = 0; i < Integers.min(columns(), rows()); i++)
		{
			trc += get(i, i);
		}
		
		return trc;
	}
	
	/**
	 * Returns the determinant of the {@code Matrix}.
	 * 
	 * @return  the matrix determinant
	 */
	public float determinant()
	{
		return copy().gauss();
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
	 * Multiplies a {@code Matrix} value at an index.
	 * 
	 * @param val  a matrix value to multiply
	 * @param c  a value column index
	 * @param r  a value row index
	 */
	public void times(float val, int c, int r)
	{
		super.times(val, c, r);
	}
	
	/**
	 * Adds a {@code Matrix} value at an index.
	 * 
	 * @param val  a matrix value to add
	 * @param c  a value column index
	 * @param r  a value row index
	 */
	public void plus(float val, int c, int r)
	{
		super.plus(val, c, r);
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

	/**
	 * Solves a linear system on the {@code Matrix}.
	 * 
	 * @param v  a parameter vector
	 * @return  a result vector
	 * @see Vector
	 */
	public Vector solve(Vector v)
	{
		Matrix copy = add(v);
		if(copy.gauss() != 0)
		{
			return copy.substitute();
		}
		
		return null;
	}
		
	/**
	 * Returns the {@code Matrix} multiplication.
	 * 
	 * @param v  a vector to multiply
	 * @return  the result vector
	 * @see Vector
	 */
	public Vector times(Vector v)
	{
		int col1 = columns();
		int col2 = v.size();
		int row1 = rows();
			
		if(col1 != col2)
		{
			return null;
		}
		
		
		Vector result = Vector.create(row1);
		for(int r = 0; r < row1; r++)
		{
			double val = 0;
			for(int d = 0; d < col1; d++)
			{
				double v1 = get(d, r);
				double v2 = v.get(d);
				val += v1 * v2;
			}
			
			result.set((float) val, r);
		}
		
		return result;
	}
	
	/**
	 * Returns the {@code Matrix} multiplication.
	 * 
	 * @param m  a matrix to multiply
	 * @return  the result matrix
	 */
	public Matrix times(Matrix m)
	{
		int col1 = m.columns();
		int col2 = columns();
		int row1 = m.rows();
		int row2 = rows();
	
		if(row1 != col2)
		{
			return null;
		}
		
		
		Tensor result = create(col1, row2);
		for(int c = 0; c < col1; c++)
		{
			for(int r = 0; r < row2; r++)
			{
				double val = 0;
				for(int d = 0; d < col2; d++)
				{
					double v1 = m.get(c, d);
					double v2 = get(d, r);
					val += v1 * v2;
				}
				
				result.set((float) val, c, r);
			}
		}
		
		return (Matrix) result;
	}

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
	
	/**
	 * Solves the {@code Matrix} system.
	 * 
	 * @return  a result vector
	 * @see Vector
	 */
	public Vector solve()
	{
		Matrix copy = copy();
		if(copy.gauss() != 0)
		{
			return copy.substitute();
		}
		
		return null;
	}
	
	
	private float gauss()
	{		
		double det = 1;
		for(int p = 0; p < rows(); p++)
		{
			if(pivot(p))
			{
				det = -det;
			}

			float val = get(p, p);
			for(int r = p + 1; r < rows(); r++)
			{
				float m = -get(p, r) / val;
				for(int c = 0; c < columns(); c++)
				{
					if(c < p + 1)
					{
						set(0, c, r);
						continue;
					}
					
					plus(m * get(c, p), c, r);
				}
			}
			
			det *= val;
			if(det == 0)
			{
				break;
			}
		}
		
		return (float) det;
	}
	
	private Vector substitute()
	{
		Vector result = Vector.create(rows());
		for(int r = rows() - 1; r >= 0; r--)
		{
			double val = get(columns() - 1, r);
			for(int c = r + 1; c < rows(); c++)
			{
				double m = -result.get(c);
				val += m * get(c, r);
			}
			
			result.set((float) (val / get(r, r)), r);
		}
		
		return result;
	}
	
	private boolean pivot(int p)
	{
		// Find highest pivot for best stability.

		int q = p; float cur = 0;
		for(int i = p; i < rows(); i++)
		{
			float val = Floats.abs(get(p, i));
			if(cur < val)
			{
				cur = val;
				q = i;
			}
		}
		
		
		if(p == q) return false;
		
		// Switch the rows p and q.
		
		for(int c = 0; c < columns(); c++)
		{
			cur = get(c, p);
			set(get(c, q), c, p);
			set(cur, c, q);
		}
		
		return true;
	}
	
	private Matrix add(Vector v)
	{
		int col1 = columns();
		int row2 = v.size();
		int row1 = rows();
			
		if(row1 != row2)
		{
			return null;
		}
		
		
		Matrix result = (Matrix) create(col1 + 1, row1);
		for(int r = 0; r < row1; r++)
		{
			result.set(v.get(r), col1, r);
			for(int c = 0; c < col1; c++)
			{
				result.set(get(c, r), c, r);
			}
		}
		
		return result;
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