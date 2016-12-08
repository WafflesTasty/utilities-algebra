package zeno.util.algebra.tensors.matrices.ortho;

import zeno.util.algebra.tensors.matrices.fixed.Matrix2x2;
import zeno.util.algebra.tensors.vectors.fixed.Vector2;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Ortho2x2} class represents a 2D orthogonal basis in matrix form.
 *
 * @since Apr 21, 2016
 * @author Zeno
 * 
 * @see Matrix2x2
 */
public class Ortho2x2 extends Matrix2x2
{
	/**
	 * Creates a new {@code Ortho2x2}.
	 */
	public Ortho2x2()
	{
		set(1f, 0, 0);
		set(1f, 1, 1);
	}
	
	
	/**
	 * Reflects the {@code Matrix2} along a vector.
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
	
	/**
	 * Reflects the {@code Matrix2} along a vector.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
	 */
	public void reflect(float x, float y)
	{
		reflect(new Vector2(x, y));
	}
		
	
	/**
	 * Rotates the {@code Matrix2} to a new basis.
	 * 
	 * @param right  a new right vector
	 * @see Vector2
	 */
	public void rotateTo(Vector2 right)
	{
		Vector2 rwd = right.normalize();
		Matrix2x2 rot = Matrix2x2.rotate2D(0.5f * Floats.PI);
		Vector2 uwd = rot.times(rwd).normalize();
		
		setRight(rwd);
		setUp(uwd);
	}
		
	/**
	 * Rotates the {@code Matrix2} to a new basis.
	 * 
	 * @param rad  an angle to rotate with
	 */
	public void rotateFor(float rad)
	{
		float rx = get(0, 0);
		float ry = get(0, 1);
		float ux = get(1, 0);
		float uy = get(1, 1);
		
		float sin = Floats.sin(rad);
		float cos = Floats.cos(rad);	
		
		set(rx * cos - ry * sin, 0, 0);
		set(ry * cos + rx * sin, 0, 1);
		set(ux * cos - uy * sin, 1, 0);
		set(uy * cos + ux * sin, 1, 1);
	}
		
	/**
	 * Rotates the {@code Matrix2} to a new basis.
	 * 
	 * @param rad  a new angle
	 */
	public void rotateTo(float rad)
	{
		float cos = Floats.cos(rad);
		float sin = Floats.sin(rad);
		
		rotateTo(new Vector2(cos, sin));
	}
	
		
	/**
	 * Returns the inverse of the {@code Matrix2}.
	 * 
	 * @return  the inverse matrix
	 */
	public Ortho2x2 inverse()
	{
		return transpose();
	}
	
	/**
	 * Returns the right vector of the {@code Matrix2}.
	 * 
	 * @return  the right vector
	 * @see Vector2
	 */
	public Vector2 right()
	{
		return new Vector2
		(
			get(0, 0),
			get(0, 1)
		);
	}
	
	/**
	 * Returns the up vector of the {@code Matrix2}.
	 * 
	 * @return  the up vector
	 * @see Vector2
	 */
	public Vector2 up()
	{
		return new Vector2
		(
			get(1, 0),
			get(1, 1)
		);
	}

	
	
	protected void setRight(float x, float y)
	{
		set(x, 0, 0);
		set(y, 0, 1);
	}
	
	protected void setUp(float x, float y)
	{
		set(x, 1, 0);
		set(y, 1, 1);
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
	public Ortho2x2 transpose()
	{
		return (Ortho2x2) super.transpose();
	}
	
	@Override
	public Ortho2x2 copy()
	{
		return (Ortho2x2) super.copy();
	}
}