package waffles.utils.algebra.elements.linear.matrix.fixed;

import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector4;

/**
 * A {@code Matrix4x4} defines a 4x4-dimensional {@code Matrix}.
 *
 * @author Waffles
 * @since Mar 22, 2016
 * @version 1.1
 * 
 * 
 * @see Matrix
 */
public class Matrix4x4 extends Matrix
{
	/**
	 * Creates a new {@code Matrix4x4}.
	 */
	public Matrix4x4()
	{
		super(4, 4);
	}
	
	/**
	 * Creates a new {@code Matrix4x4}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix4x4(TensorData d)
	{
		super(d);
	}
	
	
	/**
	 * Computes the product with a {@code Matrix4x4}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a matrix product
	 */
	public Matrix4x4 times(Matrix4x4 m)
	{
		return (Matrix4x4) super.times(m);
	}
	
	/**
	 * Computes the product with a {@code Vector4}.
	 * 
	 * @param v  a vector to multiply
	 * @return   a vector product
	 * 
	 * 
	 * @see Vector4
	 */
	public Vector4 times(Vector4 v)
	{
		return (Vector4) super.times(v);
	}
		
	
	@Override
	public Vector4 Row(int i)
	{
		return (Vector4) super.Row(i);
	}
	
	@Override
	public Vector4 Column(int j)
	{
		return (Vector4) super.Column(j);
	}
	
	@Override
	public Matrix4x4 transpose()
	{
		return (Matrix4x4) super.transpose();
	}
	
	@Override
	public Matrix4x4 plus(Additive m)
	{
		return (Matrix4x4) super.plus(m);
	}
	
	@Override
	public Matrix4x4 minus(Abelian m)
	{
		return (Matrix4x4) super.minus(m);
	}
	
	@Override
	public Matrix4x4 eltimes(Tensor t)
	{
		return (Matrix4x4) super.eltimes(t);
	}
	
	@Override
	public Matrix4x4 times(Float v)
	{
		return (Matrix4x4) super.times(v);
	}
		
	@Override
	public Matrix4x4 normalize()
	{
		return (Matrix4x4) super.normalize();
	}
			
	@Override
	public Matrix4x4 destroy()
	{
		return (Matrix4x4) super.destroy();
	}
	
	@Override
	public Matrix4x4 copy()
	{
		return (Matrix4x4) super.copy();
	}
}