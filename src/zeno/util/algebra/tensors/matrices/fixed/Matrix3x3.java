package zeno.util.algebra.tensors.matrices.fixed;

import zeno.util.algebra.tensors.ITensor;
import zeno.util.algebra.tensors.matrices.Matrix;
import zeno.util.algebra.tensors.vectors.fixed.Vector2;
import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Matrix3x3} class defines a 3x3 square matrix.
 * <br> Static methods are provided to quickly generate various
 * two-dimensional transformation matrices.
 *
 * @since Mar 22, 2016
 * @author Zeno
 * 
 * @see Matrix
 */
public class Matrix3x3 extends Matrix
{
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary plane.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @param z  a normal z-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix3x3 reflect3D(float x, float y, float z)
	{
		Matrix3x3 m = identity();
        
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
	 * Returns a 3x3 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a plane normal to reflect along
	 * @return  a reflection matrix
	 * @see Vector3
	 */
	public static Matrix3x3 reflect3D(Vector3 v)
	{
		return reflect3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary vector.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix3x3 reflect2D(float x, float y)
	{
		float len = x * x + y * y;
		
		Matrix3x3 m = identity();
        
		m.set((x * x - y * y) / len, 0, 0);
        m.set((    2 * x * y) / len, 0, 1);
        m.set((    2 * x * y) / len, 1, 0);
        m.set((y * y - x * x) / len, 1, 1);
        
        return m;
	}
	
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary vector.
	 * 
	 * @param v  a vector to reflect along
	 * @return  a reflection matrix
	 * @see Vector2
	 */
	public static Matrix3x3 reflect2D(Vector2 v)
	{
		return reflect2D(v.X(), v.Y());
	}
	
	
	/**
	 * Returns a 3x3 matrix that rotates around an arbitrary vector.
	 * 
	 * @param v  a vector to rotate around
	 * @param theta  an angle to rotate with
	 * @return  a rotation matrix
	 */
	public static Matrix3x3 rotate3D(Vector3 v, float theta)
	{
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		float x = v.X();
		float y = v.Y();
		float z = v.Z();
		
		
		Matrix3x3 m = identity();
        
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
	 * Returns a 3x3 matrix that rotates around an angle.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix3x3 rotate2D(float theta)
	{	
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix3x3 m = identity();
				
		m.set( cos, 0, 0);
		m.set( sin, 0, 1);
		m.set(-sin, 1, 0);
		m.set( cos, 1, 1);
		
		return m;
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @param z  the z-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 scale3D(float x, float y, float z)
	{
		Matrix3x3 m = identity();
		
		m.set(x, 0, 0);
		m.set(y, 1, 1);
		m.set(z, 2, 2);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 scale3D(Vector3 v)
	{
		return scale3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 scale2D(float x, float y)
	{
		Matrix3x3 m = identity();
		
		m.set(x, 0, 0);
		m.set(y, 1, 1);
		
		return m;
	}
		
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 scale2D(Vector2 v)
	{
		return scale2D(v.X(), v.Y());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @param z  the z-translation
	 * @return  a translation matrix
	 */
	public static Matrix3x3 translate3D(float x, float y, float z)
	{
		Matrix3x3 m = identity();
		
		m.set(x, 3, 0);
		m.set(y, 3, 1);
		m.set(z, 3, 2);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix3x3 translate3D(Vector3 v)
	{
		return translate3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @return  a translation matrix
	 */
	public static Matrix3x3 translate2D(float x, float y)
	{
		Matrix3x3 m = identity();
		
		m.set(x, 2, 0);
		m.set(y, 2, 1);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix3x3 translate2D(Vector2 v)
	{
		return translate2D(v.X(), v.Y());
	}
	
	/**
	 * Returns a 3x3 matrix with identity values.
	 * 
	 * @return  a 3x3 identity matrix
	 */
	public static Matrix3x3 identity()
	{
		return (Matrix3x3) Matrix.identity(3);
	}
	
	/**
	 * Creates a new {@code Matrix3x3}.
	 */
	public Matrix3x3()
	{
		super(3, 3);
	}
	
	
	
	/**
	 * Performs linear interpolation on the {@code Matrix}.
	 * 
	 * @param v  a matrix to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated matrix
	 */
	public Matrix3x3 lerp(Matrix3x3 v, float alpha)
    {
		return (Matrix3x3) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Matrix} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected matrix
	 */
	public Matrix3x3 projectTo(Matrix3x3 plane)
	{
		return (Matrix3x3) super.projectTo(plane);
	}

	
	/**
	 * Returns the multiplication with a {@code Matrix}.
	 * 
	 * @param m  a matrix to multiply
	 * @return  the result matrix
	 */
	public Matrix3x3 times(Matrix3x3 m)
	{
		return (Matrix3x3) super.times(m);
	}
		
	/**
	 * Returns the difference with a {@code Matrix}.
	 * 
	 * @param v  a matrix to subtract
	 * @return  the difference matrix
	 */
	public Matrix3x3 minus(Matrix3x3 v)
	{
		return (Matrix3x3) super.minus(v);
	}
	
	/**
	 * Returns the sum with a {@code Matrix}.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Matrix3x3 plus(Matrix3x3 v)
	{
		return (Matrix3x3) super.plus(v);
	}
	
	/**
	 * Solves a {@code Matrix} equation.
	 * 
	 * @param v  a parameter vector
	 * @return  a result vector
	 * @see Vector3
	 */
	public Vector3 solve(Vector3 v)
	{
		return (Vector3) super.solve(v);
	}
	
	/**
	 * Multiplies with a {@code Vector}.
	 * 
	 * @param v  a vector to multiply
	 * @return  the result vector
	 * @see Vector3
	 */
	public Vector3 times(Vector3 v)
	{
		return (Vector3) super.times(v);
	}
	
	
	@Override
	public Matrix3x3 plus(ITensor m)
	{
		return (Matrix3x3) super.plus(m);
	}
		
	@Override
	public Matrix3x3 times(float s)
	{
		return (Matrix3x3) super.times(s);
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
	public Matrix3x3 copy()
	{
		return (Matrix3x3) super.copy();
	}

	@Override
	public Vector3 solve()
	{
		return (Vector3) super.solve();
	}
}