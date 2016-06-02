package zeno.util.algebra.matrices.fixed;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.Matrix;
import zeno.util.algebra.vectors.fixed.Vector2;

/**
 * The {@code Matrix2x2} class defines a 2x2 square matrix.
 *
 * @author Zeno
 * @since Mar 22, 2016
 * @see Matrix
 */
public class Matrix2x2 extends Matrix
{
	/**
	 * Returns a 2x2 matrix that reflects along an arbitrary vector.
	 * 
	 * @param x  a normal x-coördinate
	 * @param y  a normal y-coördinate
	 * @return  a reflection matrix
	 */
	public static Matrix2x2 getReflect2D(float x, float y)
	{
		float len = x * x + y * y;
		
		Matrix2x2 m = createIdentity();
        
        m.set(0, 0, (x * x - y * y) / len);
        m.set(0, 1, 2 * x * y / len);

        m.set(1, 0, 2 * x * y / len);
        m.set(1, 1, (y * y - x * x) / len);
        
        return m;
	}
	
	/**
	 * Returns a 2x2 matrix that performs a two-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix2x2 getScale2D(float x, float y)
	{
		Matrix2x2 m = createIdentity();
		
		m.set(0, 0, x);
		m.set(1, 1, y);
		
		return m;
	}
	
	/**
	 * Returns a 2x2 matrix that rotates around an angle.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix2x2 getRotate2D(float theta)
	{	
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix2x2 m = createIdentity();
						
		m.set(0, 0,  cos);
		m.set(0, 1,  sin);
		m.set(1, 0, -sin);
		m.set(1, 1,  cos);
		
		return m;
	}
	
	/**
	 * Returns a 2x2 matrix with identity values.
	 * 
	 * @return  a 2x2 identity matrix
	 */
	public static Matrix2x2 createIdentity()
	{
		return (Matrix2x2) IMatrix.createIdentity(2);
	}
	
	
	/**
	 * Creates a new {@code Matrix2x2}.
	 */
	public Matrix2x2()
	{
		super(2, 2);
	}
	
	/**
	 * Returns the multiple with a {@code Vector2}.
	 * 
	 * @param v  a vector to multiply with
	 * @return  the multiplied vector
	 */
	public Vector2 times(Vector2 v)
	{
		return (Vector2) super.times(v);
	}

	/**
	 * Returns the multiple with a {@code Matrix2x2}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public Matrix2x2 times(Matrix2x2 m)
	{
		return (Matrix2x2) super.times(m);
	}
	
	
	@Override
	public Matrix2x2 add(IMatrix m)
	{
		return (Matrix2x2) super.add(m);
	}
		
	@Override
	public Matrix2x2 times(float s)
	{
		return (Matrix2x2) super.times(s);
	}
	
	@Override
	public Matrix2x2 transpose()
	{
		return (Matrix2x2) super.transpose();
	}
	
	@Override
	public Matrix2x2 copy()
	{
		return (Matrix2x2) super.copy();
	}
}