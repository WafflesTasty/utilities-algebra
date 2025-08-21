package waffles.utils.alg.linear.measure.matrix.fixed;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.vector.fixed.Vector3;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;

/**
 * A {@code Matrix3x3} defines a 3x3-dimensional {@code Matrix}.
 *
 * @author Waffles
 * @since Mar 22, 2016
 * @version 1.1
 * 
 * 
 * @see Matrix
 */
public class Matrix3x3 extends Matrix
{
	/**
	 * Creates a new {@code Matrix3x3}.
	 */
	public Matrix3x3()
	{
		super(3, 3);
	}
	
	/**
	 * Creates a new {@code Matrix3x3}.
	 * 
	 * @param d  a data source
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix3x3(TensorData d)
	{
		super(d);
	}
	
	
	/**
	 * Computes a {@code Matrix3x3} product.
	 * 
	 * @param m  a matrix
	 * @return   a product
	 */
	public Matrix3x3 times(Matrix3x3 m)
	{
		return (Matrix3x3) super.times(m);
	}
	
	/**
	 * Computes a {@code Matrix3x3} product.
	 * 
	 * @param v  a vector
	 * @return   a product
	 * 
	 * 
	 * @see Vector3
	 */
	public Vector3 times(Vector3 v)
	{
		return (Vector3) super.times(v);
	}

	
	@Override
	public Matrix3x3 Span()
	{
		return (Matrix3x3) super.Span();
	}
	
	@Override
	public Matrix3x3 absolute()
	{
		return (Matrix3x3) super.absolute();
	}
	
	@Override
	public Matrix3x3 transpose()
	{
		return (Matrix3x3) super.transpose();
	}
		
	@Override
	public Vector3 Column(int c)
	{
		return (Vector3) super.Column(c);
	}
	
	@Override
	public Vector3 Row(int r)
	{
		return (Vector3) super.Row(r);
	}
	
		
	@Override
	public Matrix3x3 minus(Abelian a)
	{
		return (Matrix3x3) super.minus(a);
	}
	
	@Override
	public Matrix3x3 hadamard(Tensor t)
	{
		return (Matrix3x3) super.hadamard(t);
	}
	
	@Override
	public Matrix3x3 plus(Additive a)
	{
		return (Matrix3x3) super.plus(a);
	}
	
	@Override
	public Matrix3x3 times(Float v)
	{
		return (Matrix3x3) super.times(v);
	}
		
	@Override
	public Matrix3x3 normalize()
	{
		return (Matrix3x3) super.normalize();
	}
			
	@Override
	public Matrix3x3 destroy()
	{
		return (Matrix3x3) super.destroy();
	}
	
	@Override
	public Matrix3x3 copy()
	{
		return (Matrix3x3) super.copy();
	}
}