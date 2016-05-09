package zeno.util.algebra.matrices.ortho;

import zeno.util.algebra.FMath;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.fixed.Matrix2x2;
import zeno.util.algebra.vectors.fixed.Vector2;

/**
 * The {@code Ortho2x2} class represents a 2D orthogonal basis in matrix form.
 *
 * @author Zeno
 * @since Apr 21, 2016
 * @see IMatrix
 */
public class Ortho2x2 extends IMatrix
{
	private Matrix2x2 matrix;
	
	/**
	 * Creates a new {@code Ortho2x2}.
	 */
	public Ortho2x2()
	{
		matrix = Matrix2x2.createIdentity();
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
	
	
	/**
	 * Returns the inverse matrix of the {@code Ortho2x2}.
	 * 
	 * @return  the inverse matrix
	 */
	public Ortho2x2 inverse()
	{
		return transpose();
	}
	
	/**
	 * Returns the right vector of the {@code Ortho2x2}.
	 * 
	 * @return  the right vector
	 * @see Vector2
	 */
	public Vector2 right()
	{
		return new Vector2
		(
			matrix.get(0, 0),
			matrix.get(0, 1)
		);
	}
	
	/**
	 * Returns the up vector of the {@code Ortho2x2}.
	 * 
	 * @return  the up vector
	 * @see Vector2
	 */
	public Vector2 up()
	{
		return new Vector2
		(
			matrix.get(1, 0),
			matrix.get(1, 1)
		);
	}
	
	
	/**
	 * Rotates the matrix to a new basis.
	 * 
	 * @param rad  a new angle
	 */
	public void rotateTo(float rad)
	{
		float cos = FMath.cos(rad);
		float sin = FMath.sin(rad);
		
		rotateTo(new Vector2(cos, sin));
	}
	
	/**
	 * Rotates the matrix for an angle.
	 * 
	 * @param rad  an angle to rotate with
	 */
	public void rotateFor(float rad)
	{
		float sin = FMath.sin(rad);
		float cos = FMath.cos(rad);
		
		float rx = matrix.get(0, 0);
		float ry = matrix.get(0, 1);
		float ux = matrix.get(1, 0);
		float uy = matrix.get(1, 1);
		
		
		set(0, 0, rx * cos - ry * sin);
		set(0, 1, ry * cos + rx * sin);
		set(1, 0, ux * cos - uy * sin);
		set(1, 1, uy * cos + ux * sin);
	}
		
	/**
	 * Rotates the matrix to a new basis.
	 * 
	 * @param right  a new right vector
	 * @see Vector2
	 */
	public void rotateTo(Vector2 right)
	{
		Vector2 rwd = right.normalize();
		Matrix2x2 rot = Matrix2x2.getRotate2D(0.5f * FMath.PI);
		Vector2 uwd = rot.times(rwd).normalize();
		
		setRight(rwd);
		setUp(uwd);
	}
	
	/**
	 * Reflects the matrix along a vector.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
	 */
	public void reflect(float x, float y)
	{
		reflect(new Vector2(x, y));
	}
		
	/**
	 * Reflects the matrix along a vector.
	 * 
	 * @param vec  a vector to reflect along
	 * @see Vector2
	 */
	public void reflect(Vector2 vec)
	{
		Vector2 v = vec.normalize();
		Vector2 rwd = right();
		Vector2 uwd = up();
		
		float x = v.X();
		float y = v.Y();

		
		float rx = rwd.X() * (1 - 2 * y * y) + 2 * rwd.Y() * x * y;
		float ry = rwd.Y() * (1 - 2 * y * y) + 2 * rwd.X() * x * y;
		float ux = uwd.X() * (2 * y * y - 1) + 2 * uwd.Y() * x * y;
		float uy = uwd.Y() * (2 * y * y - 1) + 2 * uwd.X() * x * y;
		
		setRight(rx, ry);
		setUp(ux, uy);
	}
	
		
	@Override
	public Matrix2x2 times(float s)
	{
		return (Matrix2x2) super.times(s);
	}
		
	@Override
	public Matrix2x2 add(IMatrix m)
	{
		return (Matrix2x2) super.add(m);
	}
		
	@Override
	public Ortho2x2 transpose()
	{
		return (Ortho2x2) super.transpose();
	}
	
	@Override
	public Ortho2x2 copy()
	{
		return (Ortho2x2) super.copy();
	}

	
	protected void setRight(float x, float y)
	{
		set(0, 0, x);
		set(0, 1, y);
	}
	
	protected void setUp(float x, float y)
	{
		set(1, 0, x);
		set(1, 1, y);
	}
	
	protected void setRight(Vector2 rwd)
	{
		setRight(rwd.X(), rwd.Y());
	}
	
	protected void setUp(Vector2 uwd)
	{
		setUp(uwd.X(), uwd.Y());
	}
	

	@Override
	protected void set(int col, int row, float val)
	{
		matrix.set(col, row, val);
	}

	@Override
	protected float get(int col, int row)
	{
		return matrix.get(col, row);
	}

	@Override
	protected int getColumns()
	{
		return matrix.getColumns();
	}

	@Override
	protected int getRows()
	{
		return matrix.getRows();
	}

	
	@Override
	public boolean equals(Object o)
	{		
		if(o instanceof Ortho2x2)
		{
			return matrix.equals(((Ortho2x2) o).matrix);
		}
				
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return matrix.hashCode();
	}
}