package zeno.util.algebra.tensors.matrices.fixed;

import zeno.util.algebra.tensors.ITensor;
import zeno.util.algebra.tensors.matrices.Matrix;
import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.algebra.tensors.vectors.fixed.Vector4;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Matrix4x4} class defines a 4x4 square matrix.
 * <br> Static methods are provided to quickly generate various
 * three-dimensional transformation matrices.
 *
 * @since Mar 22, 2016
 * @author Zeno
 * 
 * @see Matrix
 */
public class Matrix4x4 extends Matrix
{
	/**
	 * Returns a 4x4 matrix that rotates around an arbitrary vector.
	 * 
	 * @param v  a vector to rotate around
	 * @param theta  an angle to rotate with
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 rotate3D(Vector3 v, float theta)
	{
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		float x = v.X();
		float y = v.Y();
		float z = v.Z();
		
		
		Matrix4x4 m = identity();
        
		m.set(x * x * (1 - cos) +     cos, 0, 0);
        m.set(x * y * (1 - cos) + z * sin, 0, 1);
        m.set(x * z * (1 - cos) - y * sin, 0, 2);

        m.set(x * y * (1 - cos) - z * sin, 1, 0);
        m.set(y * y * (1 - cos) +     cos, 1, 1);
        m.set(y * z * (1 - cos) + x * sin, 1, 2);

        m.set(x * z * (1 - cos) + y * sin, 2, 0);
        m.set(y * z * (1 - cos) - x * sin, 2, 1);
        m.set(z * z * (1 - cos) +     cos, 2, 2);

        return m;
	}
	
	/**
	 * Returns a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @param z  a normal z-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix4x4 reflect3D(float x, float y, float z)
	{
		Matrix4x4 m = identity();
        
		m.set(1 - 2 * x * x, 0, 0);
        m.set(  - 2 * x * y, 0, 1);
        m.set(  - 2 * x * z, 0, 2);

        m.set(  - 2 * y * x, 1, 0);
        m.set(1 - 2 * y * y, 1, 1);
        m.set(  - 2 * y * z, 1, 2);

        m.set(  - 2 * z * x, 2, 0);
        m.set(  - 2 * z * y, 2, 1);
        m.set(1 - 2 * z * z, 2, 2);
        
        return m;
	}
	
	/**
	 * Returns a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a plane normal to reflect along
	 * @return  a reflection matrix
	 * @see Vector3
	 */
	public static Matrix4x4 reflect3D(Vector3 v)
	{
		return reflect3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 4x4 matrix that rotates around the x-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 rotate3DX(float theta)
	{	
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix4x4 m = identity();
					
		m.set( cos, 1, 1);
		m.set( sin, 1, 2);
		m.set(-sin, 2, 1);
		m.set( cos, 2, 2);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that rotates around the y-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 rotate3DY(float theta)
	{
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix4x4 m = identity();
				
		m.set( cos, 0, 0);
		m.set(-sin, 0, 2);
		m.set( sin, 2, 0);
		m.set( cos, 2, 2);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that rotates around the z-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 rotate3DZ(float theta)
	{
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix4x4 m = identity();
		
		m.set( cos, 0, 0);
		m.set( sin, 0, 1);
		m.set(-sin, 1, 0);
		m.set( cos, 1, 1);
		
		return m;
	}
		
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @param z  the z-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix4x4 scale3D(float x, float y, float z)
	{
		Matrix4x4 m = identity();
		
		m.set(x, 0, 0);
		m.set(y, 1, 1);
		m.set(z, 2, 2);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix4x4 scale3D(Vector3 v)
	{
		return scale3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @param z  the z-translation
	 * @return  a translation matrix
	 */
	public static Matrix4x4 translate3D(float x, float y, float z)
	{
		Matrix4x4 m = identity();
		
		m.set(x, 3, 0);
		m.set(y, 3, 1);
		m.set(z, 3, 2);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix4x4 translate3D(Vector3 v)
	{
		return translate3D(v.X(), v.Y(), v.Z());
	}
	
	/**
	 * Returns a 4x4 matrix with identity values.
	 * 
	 * @return  a 4x4 identity matrix
	 */
	public static Matrix4x4 identity()
	{
		return (Matrix4x4) Matrix.identity(4);
	}
	
	/**
	 * Creates a new {@code Matrix4x4}.
	 */
	public Matrix4x4()
	{
		super(4, 4);
	}
	
	
	
	/**
	 * Performs linear interpolation on the {@code Matrix}.
	 * 
	 * @param v  a matrix to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated matrix
	 */
	public Matrix4x4 lerp(Matrix4x4 v, float alpha)
    {
		return (Matrix4x4) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Matrix} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected matrix
	 */
	public Matrix4x4 projectTo(Matrix4x4 plane)
	{
		return (Matrix4x4) super.projectTo(plane);
	}
	
	
	/**
	 * Returns the multiplication with a {@code Matrix}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public Matrix4x4 times(Matrix4x4 m)
	{
		return (Matrix4x4) super.times(m);
	}
	
	/**
	 * Returns the difference with a {@code Matrix}.
	 * 
	 * @param v  a matrix to subtract
	 * @return  the difference matrix
	 */
	public Matrix4x4 minus(Matrix4x4 v)
	{
		return (Matrix4x4) super.minus(v);
	}
	
	/**
	 * Returns the sum with a {@code Matrix}.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Matrix4x4 plus(Matrix4x4 v)
	{
		return (Matrix4x4) super.plus(v);
	}
	
	/**
	 * Multiplies with a {@code Vector}.
	 * 
	 * @param v  a vector to multiply with
	 * @return  the multiplied vector
	 */
	public Vector4 times(Vector4 v)
	{
		return (Vector4) super.times(v);
	}


	@Override
	public Matrix4x4 plus(ITensor m)
	{
		return (Matrix4x4) super.plus(m);
	}
		
	@Override
	public Matrix4x4 times(float s)
	{
		return (Matrix4x4) super.times(s);
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
	public Matrix4x4 copy()
	{
		return (Matrix4x4) super.copy();
	}
}