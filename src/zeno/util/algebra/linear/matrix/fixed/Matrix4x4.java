package zeno.util.algebra.linear.matrix.fixed;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.algebra.linear.vector.fixed.Vector4;
import zeno.util.tools.Floats;

/**
 * The {@code Matrix4x4} class defines a 4x4 square matrix.
 * <br> Static methods are provided to easily generate various
 * homogeneous three-dimensional transformation matrices.
 * 
 * @author Zeno
 * @since Mar 22, 2016
 * @version 1.0
 * 
 * 
 * @see Matrix
 */
public class Matrix4x4 extends Matrix
{	
	/**
	 * Creates a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @param z  a normal z-coördinate
	 * @return   a reflection matrix
	 */
	public static Matrix4x4 reflect3D(float x, float y, float z)
	{
		return reflect4D(new Vector4(x, y, z, 0f));
	}
	
	/**
	 * Creates a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a reflection normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix4x4 reflect3D(Vector3 v)
	{
		return reflect4D((Vector4) Vectors.resize(v, 4));
	}
	
	/**
	 * Creates a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a reflection normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Vector4
	 */
	public static Matrix4x4 reflect4D(Vector4 v)
	{
		return (Matrix4x4) Matrices.reflection(v);
	}
	
	
	/**
	 * Creates a 4x4 matrix that performs a three-dimensional scaling.
	 * 
	 * @param x  an x-scale
	 * @param y  an y-scale
	 * @param z  an z-scale
	 * @return   a scale matrix
	 */
	public static Matrix4x4 scale3D(float x, float y, float z)
	{
		return scale4D(new Vector4(x, y, z, 1f));
	}
	
	/**
	 * Creates a 4x4 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  a scale vector
	 * @return   a scale matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix4x4 scale3D(Vector3 v)
	{
		return scale3D(v.X(), v.Y(), v.Z());
	}
	
	/**
	 * Creates a 4x4 matrix that performs a four-dimensional scaling.
	 * 
	 * @param v  a scale vector
	 * @return   a scale matrix
	 * 
	 * 
	 * @see Vector4
	 */
	public static Matrix4x4 scale4D(Vector4 v)
	{
		return (Matrix4x4) Matrices.diagonal(v);
	}
	
	
	/**
	 * Creates a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param x  an x-translation
	 * @param y  an y-translation
	 * @param z  an z-translation
	 * @return   a translation matrix
	 */
	public static Matrix4x4 translate3D(float x, float y, float z)
	{
		Matrix4x4 m = identity();
		
		m.set(x, 0, 3);
		m.set(y, 1, 3);
		m.set(z, 2, 3);
		
		return m;
	}
	
	/**
	 * Creates a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param v  a translation vector
	 * @return   a translation matrix
	 */
	public static Matrix4x4 translate3D(Vector3 v)
	{
		return translate3D(v.X(), v.Y(), v.Z());
	}
	
		
	/**
	 * Creates a 4x4 matrix that rotates around an arbitrary vector.
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
	public static Matrix4x4 rotate3D(Vector3 v, float t)
	{
		Matrix4x4 c = cross3D(v.normalize());
				
		Matrix4x4 m = identity();
		m = m.plus(c.times(Floats.sin(t)));
		m = m.plus(m.times(m).times(1 - Floats.cos(t)));
		return m;
	}
	
	/**
	 * Creates a 4x4 matrix that performs a cross product.
	 * 
	 * @param v  a cross product vector
	 * @return   a cross product matrix
	 * 
	 * 
	 * @see Vector3
	 */
	public static Matrix4x4 cross3D(Vector3 v)
	{
		Matrix4x4 m = new Matrix4x4();
		
		m.set(-v.Z(), 0, 1);
		m.set( v.Y(), 0, 2);
		
		m.set( v.Z(), 1, 0);
		m.set(-v.X(), 1, 2);
		
		m.set(-v.Y(), 2, 0);
		m.set( v.X(), 2, 1);
		
		return m;
	}

	/**
	 * Creates a 4x4 matrix with identity values.
	 * 
	 * @return  a 4x4 identity matrix
	 */
	public static Matrix4x4 identity()
	{
		return (Matrix4x4) Matrices.identity(4);
	}
	
	
	/**
	 * Creates a new {@code Matrix4x4}.
	 */
	public Matrix4x4()
	{
		super(4, 4);
	}
	
	/**
	 * Computes the product with a {@code Vector4}.
	 * 
	 * @param v  a vector to multiply
	 * @return  the vector product
	 * @see Vector4
	 */
	public Vector4 times(Vector4 v)
	{
		return (Vector4) super.times(v);
	}
	
	/**
	 * Computes the difference with a {@code Matrix4x4}.
	 * 
	 * @param m  a matrix to subtract
	 * @return  the matrix difference
	 */
	public Matrix4x4 minus(Matrix4x4 m)
	{
		return (Matrix4x4) super.minus(m);
	}
	
	/**
	 * Computes the product with a {@code Matrix4x4}.
	 * 
	 * @param m  a matrix to multiply
	 * @return  the matrix product
	 */
	public Matrix4x4 times(Matrix4x4 m)
	{
		return (Matrix4x4) super.times(m);
	}
			
	/**
	 * Computes the sum with a {@code Matrix4x4}.
	 * 
	 * @param m  a matrix to add
	 * @return  the matrix sum
	 */
	public Matrix4x4 plus(Matrix4x4 m)
	{
		return (Matrix4x4) super.plus(m);
	}

	
	@Override
	public Vector4 Row(int i)
	{
		return (Vector4) super.Row(i);
	}
	
	@Override
	public Vector4 Column(int j)
	{
		return (Vector4) super.Column(j);
	}
		
	@Override
	public Matrix4x4 times(float v)
	{
		return (Matrix4x4) super.times(v);
	}
	
	
	@Override
	public Matrix4x4 normalize()
	{
		return (Matrix4x4) super.normalize();
	}
	
	@Override
	public Matrix4x4 transpose()
	{
		return (Matrix4x4) super.transpose();
	}
	
	@Override
	public Matrix4x4 instance()
	{
		return new Matrix4x4();
	}
	
	@Override
	public Matrix4x4 copy()
	{
		return (Matrix4x4) super.copy();
	}
}