package waffles.utils.algebra.elements.linear.matrix.fixed;

import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.algebra.utilities.elements.Additive;

/**
 * A {@code Matrix2x2} defines a 2x2-dimensional {@code Matrix}.
 * 
 * @author Waffles
 * @since Mar 22, 2016
 * @version 1.1
 * 
 * 
 * @see Matrix
 */
public class Matrix2x2 extends Matrix
{
	/**
	 * Creates a new {@code Matrix2x2}.
	 */
	public Matrix2x2()
	{
		super(2, 2);
	}
	
	/**
	 * Creates a new {@code Matrix2x2}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix2x2(TensorData d)
	{
		super(d);
	}
	
		
	/**
	 * Computes the product with a {@code Matrix2x2}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a matrix product
	 */
	public Matrix2x2 times(Matrix2x2 m)
	{
		return (Matrix2x2) super.times(m);
	}
	
	/**
	 * Computes the product with a {@code Vector2}.
	 * 
	 * @param v  a vector to multiply
	 * @return   a vector product
	 * 
	 * 
	 * @see Vector2
	 */
	public Vector2 times(Vector2 v)
	{
		return (Vector2) super.times(v);
	}
	
	
	@Override
	public Vector2 Row(int i)
	{
		return (Vector2) super.Row(i);
	}
	
	@Override
	public Vector2 Column(int j)
	{
		return (Vector2) super.Column(j);
	}
	
	@Override
	public Matrix2x2 transpose()
	{
		return (Matrix2x2) super.transpose();
	}
	
	@Override
	public Matrix2x2 plus(Additive a)
	{
		return (Matrix2x2) super.plus(a);
	}
	
	@Override
	public Matrix2x2 minus(Abelian a)
	{
		return (Matrix2x2) super.minus(a);
	}
	
	@Override
	public Matrix2x2 ltimes(Tensor t)
	{
		return (Matrix2x2) super.ltimes(t);
	}
	
	@Override
	public Matrix2x2 times(Float v)
	{
		return (Matrix2x2) super.times(v);
	}
		
	@Override
	public Matrix2x2 normalize()
	{
		return (Matrix2x2) super.normalize();
	}
			
	@Override
	public Matrix2x2 destroy()
	{
		return (Matrix2x2) super.destroy();
	}
	
	@Override
	public Matrix2x2 copy()
	{
		return (Matrix2x2) super.copy();
	}
}