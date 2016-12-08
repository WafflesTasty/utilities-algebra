package zeno.util.algebra.tensors.matrices.ortho;

import zeno.util.algebra.tensors.matrices.fixed.Matrix3x3;
import zeno.util.algebra.tensors.vectors.fixed.Vector3;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Ortho3x3} class represents a 3D orthogonal basis in matrix form.
 *
 * @since Apr 21, 2016
 * @author Zeno
 * 
 * @see Matrix3x3
 */
public class Ortho3x3 extends Matrix3x3
{
	/**
	 * Creates a new {@code Ortho3x3}.
	 */
	public Ortho3x3()
	{
		set(1f, 0, 0);
		set(1f, 1, 1);
		set(1f, 2, 2);
	}
	
	
	/**
	 * Rotates the {@code Matrix2} to a new basis.
	 * 
	 * @param rwd  a new right vector
	 * @param uwd  a new up vector
	 * @see Vector3
	 */
	public void rotateTo(Vector3 rwd, Vector3 uwd)
	{	
		Vector3 fwd = rwd.cross(uwd).normalize();
		
		setForward(fwd.normalize());
		setRight(rwd.normalize());
		setUp(uwd.normalize());
	}
	
	/**
	 * Rotates the {@code Matrix2} around an arbitrary vector.
	 * 
	 * @param v  a vector to rotate around
	 * @param rad  an angle to rotate with
	 * @see Vector3
	 */
	public void rotateFor(Vector3 v, float rad)
	{
		Matrix3x3 mat = Matrix3x3.rotate3D(v, rad);
		
		Vector3 fwd = mat.times(forward());
		Vector3 rwd = mat.times(right());
		Vector3 uwd = mat.times(up());
		
		setForward(fwd.normalize());
		setRight(rwd.normalize());
		setUp(uwd.normalize());
	}

	/**
	 * Reflects the {@code Matrix2} around an arbitrary plane.
	 * 
	 * @param norm  a plane normal to reflect along
	 * @see Vector3
	 */
	public void reflectTo(Vector3 norm)
	{
		Vector3 fwd = forward();
		Vector3 rwd = right();
		Vector3 uwd = up();
		
		
		float ndf = norm.dot(fwd);
		float ndr = norm.dot(rwd);
		float ndu = norm.dot(uwd);
		
		fwd = fwd.minus(norm.times(2 * ndf));
		rwd = rwd.minus(norm.times(2 * ndr));
		uwd = uwd.minus(norm.times(2 * ndu));
		
		setForward(fwd.normalize());
		setRight(rwd.normalize());
		setUp(uwd.normalize());
	}
	
		
	/**
	 * Pitches the {@code Matrix2} around its right vector.
	 * 
	 * @param rad  an angle to pitch with
	 */
	public void pitchFor(float rad)
	{
		if(rad == 0) return;
		
		float cos = Floats.cos(rad);
		float sin = Floats.sin(rad);
		
		Vector3 fwd0 = forward();
		Vector3 uwd0 = up();
		
		
		Vector3 fwd1 = fwd0.times(cos).plus(uwd0.times(-sin));
		Vector3 uwd1 = uwd0.times(cos).plus(fwd0.times(sin));
		
		setForward(fwd1.normalize());
		setUp(uwd1.normalize());
	}
	
	/**
	 * Rolls the {@code Matrix2} around its forward vector.
	 * 
	 * @param rad  an angle to roll with
	 */
	public void rollFor(float rad)
	{
		if(rad == 0) return;
		
		float cos = Floats.cos(rad);
		float sin = Floats.sin(rad);
		
		Vector3 rwd0 = right();
		Vector3 uwd0 = up();
		
		
		Vector3 rwd1 = rwd0.times(cos).plus(uwd0.times(-sin));
		Vector3 uwd1 = uwd0.times(cos).plus(rwd0.times(sin));
		
		setRight(rwd1.normalize());
		setUp(uwd1.normalize());
	}
	
	/**
	 * Yaws the {@code Matrix2} around its upward vector.
	 * 
	 * @param rad  an angle to yaw with
	 */
	public void yawFor(float rad)
	{
		if(rad == 0) return;
		
		float cos = Floats.cos(rad);
		float sin = Floats.sin(rad);
		
		Vector3 fwd0 = forward();
		Vector3 rwd0 = right();
		
		
		Vector3 rwd1 = rwd0.times(cos).plus(fwd0.times(-sin));
		Vector3 fwd1 = fwd0.times(cos).plus(rwd0.times(sin));
		
		setForward(fwd1.normalize());
		setRight(rwd1.normalize());
	}


	
	/**
	 * Returns the inverse of the {@code Matrix2}.
	 * 
	 * @return  the inverse matrix
	 */
	public Ortho3x3 inverse()
	{
		return transpose();
	}
	
	/**
	 * Returns the forward vector of the {@code Matrix2}.
	 * 
	 * @return  the forward vector
	 * @see Vector3
	 */
	public Vector3 forward()
	{
		return new Vector3
		(
			get(2, 0),
			get(2, 1),
			get(2, 2)
		);
	}
	
	/**
	 * Returns the right vector of the {@code Matrix2}.
	 * 
	 * @return  the right vector
	 * @see Vector3
	 */
	public Vector3 right()
	{
		return new Vector3
		(
			get(0, 0),
			get(0, 1),
			get(0, 2)
		);
	}
	
	/**
	 * Returns the up vector of the {@code Matrix2}.
	 * 
	 * @return  the up vector
	 * @see Vector3
	 */
	public Vector3 up()
	{
		return new Vector3
		(
			get(1, 0),
			get(1, 1),
			get(1, 2)
		);
	}
	
	
	
	protected void setForward(Vector3 fwd)
	{
		set(fwd.X(), 2, 0);
		set(fwd.Y(), 2, 1);
		set(fwd.Z(), 2, 2);
	}

	protected void setRight(Vector3 rwd)
	{
		set(rwd.X(), 0, 0);
		set(rwd.Y(), 0, 1);
		set(rwd.Z(), 0, 2);
	}

	protected void setUp(Vector3 uwd)
	{
		set(uwd.X(), 1, 0);
		set(uwd.Y(), 1, 1);
		set(uwd.Z(), 1, 2);
	}
	
	
	@Override
	public Ortho3x3 transpose()
	{
		return (Ortho3x3) super.transpose();
	}
	
	@Override
	public Ortho3x3 copy()
	{
		return (Ortho3x3) super.copy();
	}
}