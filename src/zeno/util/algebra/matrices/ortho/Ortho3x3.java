package zeno.util.algebra.matrices.ortho;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.matrices.fixed.Matrix3x3;
import zeno.util.algebra.vectors.fixed.Vector3;

/**
 * The {@code Ortho3x3} class represents a 3D orthogonal basis in matrix form.
 *
 * @author Zeno
 * @since Apr 21, 2016
 * @see IMatrix
 */
public class Ortho3x3 extends IMatrix
{
	private Matrix3x3 matrix;
	
	/**
	 * Creates a new {@code Ortho3x3}.
	 */
	public Ortho3x3()
	{
		matrix = Matrix3x3.createIdentity();
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
	
	
	/**
	 * Returns the inverse matrix of the {@code Ortho3x3}.
	 * 
	 * @return  the inverse matrix
	 */
	public Ortho3x3 inverse()
	{
		return transpose();
	}
	
	/**
	 * Returns the forward vector of the {@code Ortho3x3}.
	 * 
	 * @return  the forward vector
	 * @see Vector3
	 */
	public Vector3 forward()
	{
		return new Vector3
		(
			matrix.get(2, 0),
			matrix.get(2, 1),
			matrix.get(2, 2)
		);
	}
	
	/**
	 * Returns the right vector of the {@code Ortho3x3}.
	 * 
	 * @return  the right vector
	 * @see Vector3
	 */
	public Vector3 right()
	{
		return new Vector3
		(
			matrix.get(0, 0),
			matrix.get(0, 1),
			matrix.get(0, 2)
		);
	}
	
	/**
	 * Returns the up vector of the {@code Ortho3x3}.
	 * 
	 * @return  the up vector
	 * @see Vector3
	 */
	public Vector3 up()
	{
		return new Vector3
		(
			matrix.get(1, 0),
			matrix.get(1, 1),
			matrix.get(1, 2)
		);
	}
	
	
	/**
	 * Pitches the matrix around its right vector.
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
		
		
		Vector3 fwd1 = fwd0.times(cos).add(uwd0.times(-sin));
		Vector3 uwd1 = uwd0.times(cos).add(fwd0.times(sin));
		
		setForward(fwd1.normalize());
		setUp(uwd1.normalize());
	}
	
	/**
	 * Rolls the matrix around its forward vector.
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
		
		
		Vector3 rwd1 = rwd0.times(cos).add(uwd0.times(-sin));
		Vector3 uwd1 = uwd0.times(cos).add(rwd0.times(sin));
		
		setRight(rwd1.normalize());
		setUp(uwd1.normalize());
	}
	
	/**
	 * Yaws the matrix around its upward vector.
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
		
		
		Vector3 rwd1 = rwd0.times(cos).add(fwd0.times(-sin));
		Vector3 fwd1 = fwd0.times(cos).add(rwd0.times(sin));
		
		setForward(fwd1.normalize());
		setRight(rwd1.normalize());
	}
	
	
	/**
	 * Rotates the matrix to a new basis.
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
	 * Rotates the matrix around an arbitrary vector.
	 * 
	 * @param v  a vector to rotate around
	 * @param rad  an angle to rotate with
	 * @see Vector3
	 */
	public void rotateFor(Vector3 v, float rad)
	{
		Matrix3x3 mat = Matrix3x3.getRotate3D(v, rad);
		
		Vector3 fwd = mat.times(forward());
		Vector3 rwd = mat.times(right());
		Vector3 uwd = mat.times(up());
		
		setForward(fwd.normalize());
		setRight(rwd.normalize());
		setUp(uwd.normalize());
	}

	/**
	 * Reflects the matrix around an arbitrary plane.
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
		
		fwd = fwd.add(norm.times(-2 * ndf));
		rwd = rwd.add(norm.times(-2 * ndr));
		uwd = uwd.add(norm.times(-2 * ndu));
		
		setForward(fwd.normalize());
		setRight(rwd.normalize());
		setUp(uwd.normalize());
	}
		
	
	@Override
	public Matrix3x3 times(float s)
	{
		return (Matrix3x3) super.times(s);
	}
	
	@Override
	public Matrix3x3 add(IMatrix m)
	{
		return (Matrix3x3) super.add(m);
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
	
	
	protected void setForward(Vector3 fwd)
	{
		set(2, 0, fwd.X());
		set(2, 1, fwd.Y());
		set(2, 2, fwd.Z());
	}

	protected void setRight(Vector3 rwd)
	{
		set(0, 0, rwd.X());
		set(0, 1, rwd.Y());
		set(0, 2, rwd.Z());
	}

	protected void setUp(Vector3 uwd)
	{
		set(1, 0, uwd.X());
		set(1, 1, uwd.Y());
		set(1, 2, uwd.Z());
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
		if(o instanceof Ortho3x3)
		{
			return matrix.equals(((Ortho3x3) o).matrix);
		}
				
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return matrix.hashCode();
	}
}