package zeno.util.algebra.linear.matrix.fixed;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.tools.Floats;

/**
 * The {@code Matrix3x3} class defines a 3x3 square matrix.
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
public class Matrix3x3 extends Matrix
{
	/**
	 * Creates a 3x3 matrix that reflects along an arbitrary plane.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @param z  a normal z-coördinate
	 * @return   a reflection matrix
	 */
	public static Matrix3x3 reflect3D(float x, float y, float z)
	{
		return reflect3D(new Vector3(x, y, z));
	}
	
	/**
	 * Creates a 3x3 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a reflection normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix3x3 reflect3D(Vector3 v)
	{
		return (Matrix3x3) Matrices.reflection(v);
	}
	
	
	/**
	 * Creates a 3x3 matrix that performs a three-dimensional scaling.
	 * 
	 * @param x  an x-scale
	 * @param y  an y-scale
	 * @param z  an z-scale
	 * @return   a scale matrix
	 */
	public static Matrix3x3 scale3D(float x, float y, float z)
	{
		return scale3D(new Vector3(x, y, z));
	}
	
	/**
	 * Creates a 3x3 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  a scale vector
	 * @return   a scale matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix3x3 scale3D(Vector3 v)
	{
		return (Matrix3x3) Matrices.diagonal(v);
	}
	
	
	/**
	 * Creates a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param x  an x-translation
	 * @param y  an y-translation
	 * @return   a translation matrix
	 */
	public static Matrix3x3 translate2D(float x, float y)
	{
		Matrix3x3 m = identity();
		
		m.set(x, 0, 2);
		m.set(y, 1, 2);
		
		return m;
	}
	
	/**
	 * Creates a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param v  a translation vector
	 * @return   a translation matrix
	 */
	public static Matrix3x3 translate2D(Vector2 v)
	{
		return translate2D(v.X(), v.Y());
	}
	
	
	/**
	 * Creates a 3x3 matrix that rotates around an arbitrary vector.
	 * </ br> This matrix is generated using Rodrigues' formula.
	 * 
	 * 
	 * @param v  a rotation vector
	 * @param t  a rotation angle
	 * @return   a rotation matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix3x3 rotate3D(Vector3 v, float t)
	{		
		Matrix3x3 c = cross3D(v.normalize());

		Matrix3x3 m = identity();
		m = m.plus(c.times(Floats.sin(t)));
		m = m.plus(m.times(m).times(1 - Floats.cos(t)));
		return m;
	}

	/**
	 * Creates a 3x3 matrix that performs a cross product.
	 * 
	 * @param v  a cross product vector
	 * @return   a cross product matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix3x3 cross3D(Vector3 v)
	{
		Matrix3x3 m = new Matrix3x3();
		
		m.set(-v.Z(), 0, 1);
		m.set( v.Y(), 0, 2);
		
		m.set( v.Z(), 1, 0);
		m.set(-v.X(), 1, 2);
		
		m.set(-v.Y(), 2, 0);
		m.set( v.X(), 2, 1);
		
		return m;
	}
	
	/**
	 * Creates a 3x3 matrix with identity values.
	 * 
	 * @return  a 3x3 identity matrix
	 */
	public static Matrix3x3 identity()
	{
		return (Matrix3x3) Matrices.identity(3);
	}
	
	

	/**
	 * Creates a new {@code Matrix3x3}.
	 */
	public Matrix3x3()
	{
		super(3, 3);
	}
	
	/**
	 * Computes the product with a {@code Vector3}.
	 * 
	 * @param v  a vector to multiply
	 * @return  the vector product
	 * 
	 * 
	 * @see Vector3
	 */
	public Vector3 times(Vector3 v)
	{
		return (Vector3) super.times(v);
	}
	
	/**
	 * Computes the difference with a {@code Matrix3x3}.
	 * 
	 * @param m  a matrix to subtract
	 * @return  the matrix difference
	 */
	public Matrix3x3 minus(Matrix3x3 m)
	{
		return (Matrix3x3) super.minus(m);
	}
	
	/**
	 * Computes the product with a {@code Matrix3x3}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   the matrix product
	 */
	public Matrix3x3 times(Matrix3x3 m)
	{
		return (Matrix3x3) super.times(m);
	}
			
	/**
	 * Computes the sum with a {@code Matrix3x3}.
	 * 
	 * @param m  a matrix to add
	 * @return   the matrix sum
	 */
	public Matrix3x3 plus(Matrix3x3 m)
	{
		return (Matrix3x3) super.plus(m);
	}
	
	
	@Override
	public Vector3 Row(int i)
	{
		return (Vector3) super.Row(i);
	}
	
	@Override
	public Vector3 Column(int j)
	{
		return (Vector3) super.Column(j);
	}
	
	@Override
	public Matrix3x3 times(float v)
	{
		return (Matrix3x3) super.times(v);
	}
	
	
	@Override
	public Matrix3x3 normalize()
	{
		return (Matrix3x3) super.normalize();
	}
	
	@Override
	public Matrix3x3 transpose()
	{
		return (Matrix3x3) super.transpose();
	}
		
	@Override
	public Matrix3x3 instance()
	{
		return new Matrix3x3();
	}
	
	@Override
	public Matrix3x3 copy()
	{
		return (Matrix3x3) super.copy();
	}
}