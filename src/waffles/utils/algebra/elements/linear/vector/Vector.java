package waffles.utils.algebra.elements.linear.vector;

import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.TensorOps;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;

/**
 * The {@code Vector} class defines an algebraic vector using the standard dot product.
 * A vector describes a linear relation for tensors of the first order.
 *
 * @author Waffles
 * @since Jul 5, 2018
 * @version 1.1
 * 
 * 
 * @see Matrix
 */
public class Vector extends Matrix
{
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Vector(TensorData d)
	{
		super(d); setOperator(VectorOps.Type());
	}
	
	/**
	 * Creates a new {@code Vector}.
	 * 
	 * @param size  a vector size
	 */
	public Vector(int size)
	{
		super(size, 1); setOperator(VectorOps.Type());
	}

	
	/**
	 * Returns a value from the {@code Vector}.
	 * 
	 * @param i  a vector index
	 * @return   a vector value
	 */
	public float get(int i)
	{
		return get(i, 0);
	}
	
	/**
	 * Changes a value from the {@code Vector}.
	 * 
	 * @param val  a vector value
	 * @param i    a vector index
	 */
	public void set(float val, int i)
	{
		set(val, i, 0);
	}
	
	/**
	 * Excludes an index from the {@code Vector}.
	 * 
	 * @param i  a vector index
	 * @return   a resized vector
	 */
	public Vector exclude(int i)
	{
		return Operator().Exclude(i).result();
	}
	
	/**
	 * Returns an absolute valued {@code Vector}.
	 * 
	 * @return  an absolute vector
	 */
	public Vector absolute()
	{
		return Operator().Absolute().result();
	}
	
	/**
	 * Returns a {@code Vector} 1-norm.
	 * 
	 * @return  a 1-norm
	 */
	public float norm1()
	{
		return Operator().Norm1().result();
	}
	
	
	@Override
	public int Size()
	{
		return Data().Count();
	}
	
	@Override
	public VectorOps Operator()
	{
		return (VectorOps) super.Operator();
	}

	@Override
	public void setOperator(TensorOps ops)
	{
		if(ops instanceof VectorOps)
		{
			super.setOperator(ops);			
		}
	}
	
	@Override
	public int[] Dimensions()
	{
		// This is needed because the underlying
		// data object only has 1 dimension.
		return new int[]{Size(), 1};
	}
	
	
	@Override
	public Vector minus(Abelian a)
	{
		return (Vector) super.minus(a);
	}
	
	@Override
	public Vector eltimes(Tensor t)
	{
		return (Vector) super.eltimes(t);
	}
	
	@Override
	public Vector times(Float val)
	{
		return (Vector) super.times(val);
	}
	
	@Override
	public Vector plus(Additive a)
	{
		return (Vector) super.plus(a);
	}

	@Override
	public Vector normalize()
	{
		return (Vector) super.normalize();
	}
		
	@Override
	public Vector destroy()
	{
		return (Vector) super.destroy();
	}
	
	@Override
	public Vector copy()
	{
		return (Vector) super.copy();
	}
}