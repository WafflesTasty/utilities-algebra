package waffles.utils.alg.linear.measure.vector;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.TensorOps;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;

/**
 * A {@code Vector} is defined as a {@code Matrix} with one column.
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
	 * @param d  a data source
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
	 * Returns a single value in the {@code Vector}.
	 * 
	 * @param idx  a vector index
	 * @return     a vector value
	 */
	public float get(int idx)
	{
		return get(idx, 0);
	}
	
	/**
	 * Changes a single value in the {@code Vector}.
	 * 
	 * @param val  a vector value
	 * @param idx  a vector index
	 */
	public void set(float val, int idx)
	{
		set(val, idx, 0);
	}
	
	/**
	 * Returns a resized {@code Vector}.
	 * 
	 * @param s  a vector size
	 * @return  a resized vector
	 */
	public Vector resize(int s)
	{
		return (Vector) super.resize(s, 1);
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
	public Vector minus(Abelian a)
	{
		return (Vector) super.minus(a);
	}
	
	@Override
	public Vector hadamard(Tensor t)
	{
		return (Vector) super.hadamard(t);
	}
		
	@Override
	public Vector plus(Additive a)
	{
		return (Vector) super.plus(a);
	}

	@Override
	public Vector times(Float v)
	{
		return (Vector) super.times(v);
	}
	
	@Override
	public Vector normalize()
	{
		return (Vector) super.normalize();
	}
		
	@Override
	public Vector absolute()
	{
		return (Vector) super.absolute();
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