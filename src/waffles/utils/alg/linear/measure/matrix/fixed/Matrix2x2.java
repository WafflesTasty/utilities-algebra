package waffles.utils.alg.linear.measure.matrix.fixed;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.vector.fixed.Vector2;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;

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
	 * @param d  a data source
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix2x2(TensorData d)
	{
		super(d);
	}
	
	
	/**
	 * Computes a {@code Matrix2x2} product.
	 * 
	 * @param m  a matrix
	 * @return   a product
	 */
	public Matrix2x2 times(Matrix2x2 m)
	{
		return (Matrix2x2) super.times(m);
	}
	
	/**
	 * Computes a {@code Matrix2x2} product.
	 * 
	 * @param v  a vector
	 * @return   a product
	 * 
	 * 
	 * @see Vector2
	 */
	public Vector2 times(Vector2 v)
	{
		return (Vector2) super.times(v);
	}
	
	
	@Override
	public Matrix2x2 Span()
	{
		return (Matrix2x2) super.Span();
	}

	@Override
	public Matrix2x2 absolute()
	{
		return (Matrix2x2) super.absolute();
	}
	
	@Override
	public Matrix2x2 transpose()
	{
		return (Matrix2x2) super.transpose();
	}
	
	@Override
	public Vector2 Column(int c)
	{
		return (Vector2) super.Column(c);
	}
	
	@Override
	public Vector2 Row(int r)
	{
		return (Vector2) super.Row(r);
	}

		
	@Override
	public Matrix2x2 minus(Abelian a)
	{
		return (Matrix2x2) super.minus(a);
	}
	
	@Override
	public Matrix2x2 hadamard(Tensor t)
	{
		return (Matrix2x2) super.hadamard(t);
	}
	
	@Override
	public Matrix2x2 plus(Additive a)
	{
		return (Matrix2x2) super.plus(a);
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