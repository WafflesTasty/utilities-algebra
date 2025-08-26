package waffles.utils.alg.linear.measure.matrix.fixed;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.TensorData;
import waffles.utils.alg.linear.measure.vector.fixed.Vector4;

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
	 * @param d  a data source
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix4x4(TensorData d)
	{
		super(d);
	}
	
	
	/**
	 * Computes a {@code Matrix4x4} product.
	 * 
	 * @param m  a matrix
	 * @return   a product
	 */
	public Matrix4x4 times(Matrix4x4 m)
	{
		return (Matrix4x4) super.times(m);
	}
	
	/**
	 * Computes a {@code Matrix4x4} product.
	 * 
	 * @param v  a vector
	 * @return   a product
	 * 
	 * 
	 * @see Vector4
	 */
	public Vector4 times(Vector4 v)
	{
		return (Vector4) super.times(v);
	}
	
	
	@Override
	public Matrix4x4 Span()
	{
		return (Matrix4x4) super.Span();
	}
	
	@Override
	public Matrix4x4 absolute()
	{
		return (Matrix4x4) super.absolute();
	}
	
	@Override
	public Matrix4x4 transpose()
	{
		return (Matrix4x4) super.transpose();
	}
	
	@Override
	public Vector4 Column(int j)
	{
		return (Vector4) super.Column(j);
	}
	
	@Override
	public Vector4 Row(int i)
	{
		return (Vector4) super.Row(i);
	}
	

	
	@Override
	public Matrix4x4 minus(Abelian m)
	{
		return (Matrix4x4) super.minus(m);
	}
	
	@Override
	public Matrix4x4 hadamard(Tensor t)
	{
		return (Matrix4x4) super.hadamard(t);
	}
	
	@Override
	public Matrix4x4 plus(Additive m)
	{
		return (Matrix4x4) super.plus(m);
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