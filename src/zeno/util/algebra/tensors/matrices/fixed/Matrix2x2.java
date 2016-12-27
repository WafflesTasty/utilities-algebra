package zeno.util.algebra.tensors.matrices.fixed;

import zeno.util.algebra.tensors.ITensor;
import zeno.util.algebra.tensors.matrices.Matrix;
import zeno.util.algebra.tensors.vectors.fixed.Vector2;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Matrix2x2} class defines a 2x2 square matrix.
 *
 * @since Mar 22, 2016
 * @author Zeno
 * 
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
	public static Matrix2x2 reflect2D(float x, float y)
	{
		float len = x * x + y * y;
		
		Matrix2x2 m = identity();
        
		m.set((x * x - y * y) / len, 0, 0);
        m.set(	  (2 * x * y) / len, 0, 1);
        m.set(	  (2 * x * y) / len, 1, 0);
        m.set((y * y - x * x) / len, 1, 1);
        
        return m;
	}
	
	/**
	 * Returns a 2x2 matrix that performs a two-dimensional scaling.
	 * 
	 * @param x  the x-scaling
	 * @param y  the y-scaling
	 * @return  a scaling matrix
	 */
	public static Matrix2x2 scale2D(float x, float y)
	{
		Matrix2x2 m = identity();
		
		m.set(x, 0, 0);
		m.set(y, 1, 1);
		
		return m;
	}
	
	/**
	 * Returns a 2x2 matrix that rotates around an angle.
	 * 
	 * @param theta  a rotation angle
	 * @return  a rotation matrix
	 */
	public static Matrix2x2 rotate2D(float theta)
	{	
		float sin = Floats.sin(theta);
		float cos = Floats.cos(theta);
		
		Matrix2x2 m = identity();
				
		m.set( cos, 0, 0);
		m.set( sin, 0, 1);
		m.set(-sin, 1, 0);
		m.set( cos, 1, 1);
		
		return m;
	}
	
	/**
	 * Returns a 2x2 matrix with identity values.
	 * 
	 * @return  a 2x2 identity matrix
	 */
	public static Matrix2x2 identity()
	{
		return (Matrix2x2) Matrix.identity(2);
	}
	
	/**
	 * Creates a new {@code Matrix2x2}.
	 */
	public Matrix2x2()
	{
		super(2, 2);
	}
	
	
	
	/**
	 * Performs linear interpolation on the {@code Matrix}.
	 * 
	 * @param v  a matrix to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated matrix
	 */
	public Matrix2x2 lerp(Matrix2x2 v, float alpha)
    {
		return (Matrix2x2) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Matrix} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected matrix
	 */
	public Matrix2x2 projectTo(Matrix2x2 plane)
	{
		return (Matrix2x2) super.projectTo(plane);
	}
	
	
	/**
	 * Returns the multiplication with a {@code Matrix}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  the multiplied matrix
	 */
	public Matrix2x2 times(Matrix2x2 m)
	{
		return (Matrix2x2) super.times(m);
	}
	
	/**
	 * Returns the difference with a {@code Matrix}.
	 * 
	 * @param v  a matrix to subtract
	 * @return  the difference matrix
	 */
	public Matrix2x2 minus(Matrix2x2 v)
	{
		return (Matrix2x2) super.minus(v);
	}
	
	/**
	 * Returns the sum with a {@code Matrix}.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Matrix2x2 plus(Matrix2x2 v)
	{
		return (Matrix2x2) super.plus(v);
	}
	
	/**
	 * Solves a {@code Matrix} equation.
	 * 
	 * @param v  a parameter vector
	 * @return  a result vector
	 * @see Vector2
	 */
	public Vector2 solve(Vector2 v)
	{
		return (Vector2) super.solve(v);
	}
	
	/**
	 * Multiplies with a {@code Vector}.
	 * 
	 * @param v  a vector to multiply with
	 * @return  the multiplied vector
	 */
	public Vector2 times(Vector2 v)
	{
		return (Vector2) super.times(v);
	}


	@Override
	public Matrix2x2 plus(ITensor m)
	{
		return (Matrix2x2) super.plus(m);
	}
		
	@Override
	public Matrix2x2 times(float s)
	{
		return (Matrix2x2) super.times(s);
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
	public Matrix2x2 copy()
	{
		return (Matrix2x2) super.copy();
	}

	@Override
	public Vector2 solve()
	{
		return (Vector2) super.solve();
	}
}