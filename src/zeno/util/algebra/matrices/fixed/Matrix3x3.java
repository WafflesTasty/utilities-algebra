package zeno.util.algebra.matrices.fixed;

import zeno.util.algebra.FMath;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.Matrix;
import zeno.util.algebra.vectors.fixed.Vector2;
import zeno.util.algebra.vectors.fixed.Vector3;

/**
 * The {@code Matrix3x3} class defines a 3x3 square matrix.
 * <br> Static methods are provided to quickly generate various
 * two-dimensional transformation matrices.
 *
 * @author Zeno
 * @since Mar 22, 2016
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
	public static Matrix3x3 getReflect3D(float x, float y, float z)
	{
		Matrix3x3 m = createIdentity();
        
        m.set(0, 0, 1 - 2 * x * x);
        m.set(0, 1, 0 - 2 * x * y);
        m.set(0, 2, 0 - 2 * x * z);

        m.set(1, 0, 0 - 2 * y * x);
        m.set(1, 1, 1 - 2 * y * y);
        m.set(1, 2, 0 - 2 * y * z);

        m.set(2, 0, 0 - 2 * z * x);
        m.set(2, 1, 0 - 2 * z * y);
        m.set(2, 2, 1 - 2 * z * z);
        
        return m;
	}
	
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a plane normal to reflect along
	 * @return  a reflection matrix
	 * @see Vector3
	 */
	public static Matrix3x3 getReflect3D(Vector3 v)
	{
		return getReflect3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary vector.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix3x3 getReflect2D(float x, float y)
	{
		float len = x * x + y * y;
		
		Matrix3x3 m = createIdentity();
        
        m.set(0, 0, (x * x - y * y) / len);
        m.set(0, 1, 2 * x * y / len);

        m.set(1, 0, 2 * x * y / len);
        m.set(1, 1, (y * y - x * x) / len);
        
        return m;
	}
	
	/**
	 * Returns a 3x3 matrix that reflects along an arbitrary vector.
	 * 
	 * @param v  a vector to reflect along
	 * @return  a reflection matrix
	 * @see Vector2
	 */
	public static Matrix3x3 getReflect2D(Vector2 v)
	{
		return getReflect2D(v.X(), v.Y());
	}
	
	
	/**
	 * Returns a 3x3 matrix that rotates around an arbitrary vector.
	 * 
	 * @param v  a vector to rotate around
	 * @param theta  an angle to rotate with
	 * @return  a rotation matrix
	 */
	public static Matrix3x3 getRotate3D(Vector3 v, float theta)
	{
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		float x = v.X();
		float y = v.Y();
		float z = v.Z();
		
		
		Matrix3x3 m = createIdentity();
        
        m.set(0, 0, cos + x * x * (1 - cos));
        m.set(0, 1, x * y * (1 - cos) + z * sin);
        m.set(0, 2, x * z * (1 - cos) - y * sin);

        m.set(1, 0, x * y * (1 - cos) - z * sin);
        m.set(1, 1, cos + y * y * (1 - cos));
        m.set(1, 2, y * z * (1 - cos) + x * sin);

        m.set(2, 0, x * z * (1 - cos) + y * sin);
        m.set(2, 1, y * z * (1 - cos) - x * sin);
        m.set(2, 2, cos + z * z * (1 - cos));
        
        return m;
	}
	
	/**
	 * Returns a 3x3 matrix that rotates around an angle.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix3x3 getRotate2D(float theta)
	{	
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		Matrix3x3 m = createIdentity();
						
		m.set(0, 0,  cos);
		m.set(0, 1,  sin);
		m.set(1, 0, -sin);
		m.set(1, 1,  cos);
		
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
	public static Matrix3x3 getScale3D(float x, float y, float z)
	{
		Matrix3x3 m = createIdentity();
		
		m.set(0, 0, x);
		m.set(1, 1, y);
		m.set(2, 2, z);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 getScale3D(Vector3 v)
	{
		return getScale3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 getScale2D(float x, float y)
	{
		Matrix3x3 m = createIdentity();
		
		m.set(0, 0, x);
		m.set(1, 1, y);
		
		return m;
	}
		
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix3x3 getScale2D(Vector2 v)
	{
		return getTranslate2D(v.X(), v.Y());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @param z  the z-translation
	 * @return  a translation matrix
	 */
	public static Matrix3x3 getTranslate3D(float x, float y, float z)
	{
		Matrix3x3 m = createIdentity();
		
		m.set(3, 0, x);
		m.set(3, 1, y);
		m.set(3, 2, z);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a three-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix3x3 getTranslate3D(Vector3 v)
	{
		return getTranslate3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @return  a translation matrix
	 */
	public static Matrix3x3 getTranslate2D(float x, float y)
	{
		Matrix3x3 m = createIdentity();
		
		m.set(2, 0, x);
		m.set(2, 1, y);
		
		return m;
	}
	
	/**
	 * Returns a 3x3 matrix that performs a two-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix3x3 getTranslate2D(Vector2 v)
	{
		return getTranslate2D(v.X(), v.Y());
	}
	
	/**
	 * Returns a 3x3 matrix with identity values.
	 * 
	 * @return  a 3x3 identity matrix
	 */
	public static Matrix3x3 createIdentity()
	{
		return (Matrix3x3) IMatrix.createIdentity(3);
	}
	
	
	/**
	 * Creates a new {@code Matrix3x3}.
	 */
	public Matrix3x3()
	{
		super(3, 3);
	}
	
	/**
	 * Returns the multiple with a {@code Vector3}.
	 * 
	 * @param v  a vector to multiply with
	 * @return  the multiplied vector
	 */
	public Vector3 times(Vector3 v)
	{
		return (Vector3) super.times(v);
	}

	/**
	 * Returns the multiple with a {@code Matrix3x3}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public Matrix3x3 times(Matrix3x3 m)
	{
		return (Matrix3x3) super.times(m);
	}
	
	
	@Override
	public Matrix3x3 add(IMatrix m)
	{
		return (Matrix3x3) super.add(m);
	}
		
	@Override
	public Matrix3x3 times(float s)
	{
		return (Matrix3x3) super.times(s);
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
}