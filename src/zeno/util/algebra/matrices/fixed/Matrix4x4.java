package zeno.util.algebra.matrices.fixed;

import zeno.util.algebra.FMath;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.Matrix;
import zeno.util.algebra.vectors.fixed.Vector3;
import zeno.util.algebra.vectors.fixed.Vector4;

/**
 * The {@code Matrix4x4} class defines a 4x4 square matrix.
 * <br> Static methods are provided to quickly generate various
 * three-dimensional transformation matrices.
 *
 * @author Zeno
 * @since Mar 22, 2016
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
	public static Matrix4x4 getRotate3D(Vector3 v, float theta)
	{
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		float x = v.X();
		float y = v.Y();
		float z = v.Z();
		
		
		Matrix4x4 m = createIdentity();
        
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
	 * Returns a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @param z  a normal z-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix4x4 getReflect3D(float x, float y, float z)
	{
		Matrix4x4 m = createIdentity();
        
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
	 * Returns a 4x4 matrix that reflects along an arbitrary plane.
	 * 
	 * @param v  a plane normal to reflect along
	 * @return  a reflection matrix
	 * @see Vector3
	 */
	public static Matrix4x4 getReflect3D(Vector3 v)
	{
		return getReflect3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 4x4 matrix that rotates around the x-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 getRotate3DX(float theta)
	{	
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		Matrix4x4 m = createIdentity();
						
		m.set(1, 1,  cos);
		m.set(1, 2,  sin);
		m.set(2, 1, -sin);
		m.set(2, 2,  cos);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that rotates around the y-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 getRotate3DY(float theta)
	{
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		Matrix4x4 m = createIdentity();
				
		m.set(0, 0,  cos);
		m.set(0, 2, -sin);
		m.set(2, 0,  sin);
		m.set(2, 2,  cos);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that rotates around the z-axis.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix4x4 getRotate3DZ(float theta)
	{
		float sin = FMath.sin(theta);
		float cos = FMath.cos(theta);
		
		Matrix4x4 m = createIdentity();
		
		m.set(0, 0,  cos);
		m.set(0, 1,  sin);
		m.set(1, 0, -sin);
		m.set(1, 1,  cos);
		
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
	public static Matrix4x4 getScale3D(float x, float y, float z)
	{
		Matrix4x4 m = createIdentity();
		
		m.set(0, 0, x);
		m.set(1, 1, y);
		m.set(2, 2, z);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional scaling.
	 * 
	 * @param v  the scaling vector
	 * @return  a scaling matrix
	 */
	public static Matrix4x4 getScale3D(Vector3 v)
	{
		return getScale3D(v.X(), v.Y(), v.Z());
	}
	
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param x  the x-translation
	 * @param y  the y-translation
	 * @param z  the z-translation
	 * @return  a translation matrix
	 */
	public static Matrix4x4 getTranslate3D(float x, float y, float z)
	{
		Matrix4x4 m = createIdentity();
		
		m.set(3, 0, x);
		m.set(3, 1, y);
		m.set(3, 2, z);
		
		return m;
	}
	
	/**
	 * Returns a 4x4 matrix that performs a three-dimensional translation.
	 * 
	 * @param v  the translation vector
	 * @return  a translation matrix
	 */
	public static Matrix4x4 getTranslate3D(Vector3 v)
	{
		return getTranslate3D(v.X(), v.Y(), v.Z());
	}
	
	/**
	 * Returns a 4x4 matrix with identity values.
	 * 
	 * @return  a 4x4 identity matrix
	 */
	public static Matrix4x4 createIdentity()
	{
		return (Matrix4x4) IMatrix.createIdentity(4);
	}
	
	
	/**
	 * Creates a new {@code Matrix4x4}.
	 */
	public Matrix4x4()
	{
		super(4, 4);
	}
	
	/**
	 * Returns the multiple with a {@code Vector4}.
	 * 
	 * @param v  a vector to multiply with
	 * @return  the multiplied vector
	 */
	public Vector4 times(Vector4 v)
	{
		return (Vector4) super.times(v);
	}

	/**
	 * Returns the multiple with a {@code Matrix4x4}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public Matrix4x4 times(Matrix4x4 m)
	{
		return (Matrix4x4) super.times(m);
	}
	
	
	@Override
	public Matrix4x4 add(IMatrix m)
	{
		return (Matrix4x4) super.add(m);
	}
		
	@Override
	public Matrix4x4 times(float s)
	{
		return (Matrix4x4) super.times(s);
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