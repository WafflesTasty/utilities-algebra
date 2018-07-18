package zeno.util.algebra.linear.matrix.fixed;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.tools.Floats;

/**
 * The {@code Matrix2x2} class defines a 2x2 square matrix.
 * <br> Static methods are provided to easily generate various
 * three-dimensional transformation matrices.
 * 
 * @author Zeno
 * @since Mar 22, 2016
 * @version 1.0
 * 
 * 
 * @see Matrix
 */
public class Matrix2x2 extends Matrix
{
	/**
	 * Creates a 2x2 matrix that reflects along an arbitrary line.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @return   a reflection matrix
	 */
	public static Matrix2x2 reflect2D(float x, float y)
	{
		return reflect2D(new Vector2(x, y));
	}
	
	/**
	 * Creates a 2x2 matrix that reflects along an arbitrary line.
	 * 
	 * @param v  a reflection normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Vector2
	 */
	public static Matrix2x2 reflect2D(Vector2 v)
	{
		return (Matrix2x2) Matrices.reflection(v);
	}
	
	
	/**
	 * Creates a 2x2 matrix that performs a two-dimensional scaling.
	 * 
	 * @param x  an x-scale
	 * @param y  an y-scale
	 * @return   a scale matrix
	 */
	public static Matrix2x2 scale2D(float x, float y)
	{
		return scale2D(new Vector2(x, y));
	}
	
	/**
	 * Creates a 2x2 matrix that performs a two-dimensional scaling.
	 * 
	 * @param v  a scale vector
	 * @return   a scale matrix
	 * 
	 * 
	 * @see Vector2
	 */
	public static Matrix2x2 scale2D(Vector2 v)
	{
		return (Matrix2x2) Matrices.diagonal(v);
	}
	
	
	/**
	 * Creates a 2x2 matrix that rotates around an angle.
	 * 
	 * @param t  a rotation angle
	 * @return   a rotation matrix
	 */
	public static Matrix2x2 rotate2D(float t)
	{	
		Matrix2x2 m = new Matrix2x2();
		
		float sin = Floats.sin(t);
		float cos = Floats.cos(t);
				
		m.setOperator(Orthogonal.Type());
		
		m.set( cos, 0, 0);
		m.set( sin, 1, 0);
		m.set(-sin, 0, 1);
		m.set( cos, 1, 1);
		
		return m;
	}
	
	/**
	 * Creates a 2x2 matrix with identity values.
	 * 
	 * @return  a 2x2 identity matrix
	 */
	public static Matrix2x2 identity()
	{
		return (Matrix2x2) Matrices.identity(2);
	}
	
	

	/**
	 * Creates a new {@code Matrix2x2}.
	 */
	public Matrix2x2()
	{
		super(2, 2);
	}
	
	/**
	 * Computes the product with a {@code Vector2}.
	 * 
	 * @param v  a vector to multiply
	 * @return  the vector product
	 * 
	 * 
	 * @see Vector2
	 */
	public Vector2 times(Vector2 v)
	{
		return (Vector2) super.times(v);
	}
	
	/**
	 * Computes the difference with a {@code Matrix2x2}.
	 * 
	 * @param m  a matrix to subtract
	 * @return  the matrix difference
	 */
	public Matrix2x2 minus(Matrix2x2 m)
	{
		return (Matrix2x2) super.minus(m);
	}
	
	/**
	 * Computes the product with a {@code Matrix2x2}.
	 * 
	 * @param m  a matrix to multiply
	 * @return  the matrix product
	 */
	public Matrix2x2 times(Matrix2x2 m)
	{
		return (Matrix2x2) super.times(m);
	}
		
	/**
	 * Computes the sum with a {@code Matrix2x2}.
	 * 
	 * @param m  a matrix to add
	 * @return  the matrix sum
	 */
	public Matrix2x2 plus(Matrix2x2 m)
	{
		return (Matrix2x2) super.plus(m);
	}
		
		
	@Override
	public Matrix2x2 times(float v)
	{
		return (Matrix2x2) super.times(v);
	}
	
	@Override
	public Matrix2x2 normalize()
	{
		return (Matrix2x2) super.normalize();
	}
	
	@Override
	public Matrix2x2 transpose()
	{
		return (Matrix2x2) super.transpose();
	}
	
	@Override
	public Matrix2x2 instance()
	{
		return new Matrix2x2();
	}
	
	@Override
	public Matrix2x2 copy()
	{
		return (Matrix2x2) super.copy();
	}
}