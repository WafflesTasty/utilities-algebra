package waffles.utils.algebra.elements.linear.matrix.fixed;

import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector3;

/**
 * A {@code Matrix3x3} defines a 3x3-dimensional {@code Matrix}.
 *
 * @author Waffles
 * @since Mar 22, 2016
 * @version 1.0
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
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix3x3(TensorData d)
	{
		super(d);
	}
	
	
	/**
	 * Computes the product with a {@code Matrix3x3}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a matrix product
	 */
	public Matrix3x3 times(Matrix3x3 m)
	{
		return (Matrix3x3) super.times(m);
	}
	
	/**
	 * Computes the product with a {@code Vector3}.
	 * 
	 * @param v  a vector to multiply
	 * @return   a vector product
	 * 
	 * 
	 * @see Vector3
	 */
	public Vector3 times(Vector3 v)
	{
		return (Vector3) super.times(v);
	}
		
	
	@Override
	public Vector3 Row(int i)
	{
		return (Vector3) super.Row(i);
	}
	
	@Override
	public Vector3 Column(int j)
	{
		return (Vector3) super.Column(j);
	}
	
	@Override
	public Matrix3x3 transpose()
	{
		return (Matrix3x3) super.transpose();
	}
	
	@Override
	public Matrix3x3 plus(Additive a)
	{
		return (Matrix3x3) super.plus(a);
	}
	
	@Override
	public Matrix3x3 minus(Abelian a)
	{
		return (Matrix3x3) super.minus(a);
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
	public Matrix3x3 copy()
	{
		return (Matrix3x3) super.copy();
	}
}