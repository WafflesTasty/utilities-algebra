package zeno.util.algebra.tensors;

import zeno.util.tools.Array;
import zeno.util.tools.generic.properties.Copyable;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code ITensor} interface defines the basic contract for an
 * algebraic tensor. A tensor describes a linear relation for objects
 * in any possible combination of dimensions.
 * 
 * @since Dec 7, 2016
 * @author Zeno
 * 
 * @see Copyable
 */
public interface ITensor extends Copyable<ITensor>
{
	/**
	 * Returns the {@code Tensor}'s product with a scalar value.
	 * 
	 * @param val  a value to multiply
	 * @return  the tensor's product
	 */
	public abstract ITensor times(float val);
	
	/**
	 * Returns the {@code Tensor}'s sum with another tensor.
	 * 
	 * @param t  a tensor to add
	 * @return  the sum tensor
	 */
	public abstract ITensor plus(ITensor t);
	
	/**
	 * Returns the {@code Tensor}'s inproduct with another.
	 * 
	 * @param t  a tensor to multiply
	 * @return  the dot product
	 */
	public abstract float dot(ITensor t);
		
	
	/**
	 * Returns the {@code Tensor}'s list of dimensions.
	 * 
	 * @return  the tensor's dimensions
	 */
	public abstract int[] dimensions();
	
	/**
	 * Returns the {@code Tensor}'s value array.
	 * 
	 * @return  the tensor's values
	 */
	public abstract float[] values();

	
	
	/**
	 * Performs linear interpolation on the {@code Tensor}.
	 * 
	 * @param t  a tensor to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated tensor
	 */
	public default ITensor lerp(ITensor t, float alpha)
    {		
		ITensor t1 = times(1 - alpha);
		ITensor t2 = t.times(alpha);
		return t1.plus(t2);
    }

	/**
	 * Projects the {@code Tensor} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected tensor
	 */
	public default ITensor projectTo(ITensor plane)
	{
		return plus(plane.times(-dot(plane) / plane.normsqr()));
	}
	
	/**
	 * Returns the {@code Tensor}'s subtraction.
	 * 
	 * @param t  a tensor to subtract
	 * @return  the tensor difference
	 */
	public default ITensor minus(ITensor t)
	{
		return plus(t.times(-1));
	}
	
	/**
	 * Returns the normalized {@code Tensor}.
	 * 
	 * @return  a norm tensor
	 */
	public default ITensor normalize()
	{
		float norm = norm();
		if(norm == 0 || norm == 1)
		{
			return copy();
		}
		
		return times(1f / norm);
	}
	
		
	/**
	 * Returns the {@code Tensor}'s distance to another.
	 * 
	 * @param t  a tensor to measure to
	 * @return  the tensor distance
	 */
	public default float distance(ITensor t)
	{
		return minus(t).norm();
	}
	
	/**
	 * Returns the {@code Tensor}'s angle to another.
	 * 
	 * @param t  a tensor to measure to
	 * @return  the tensor angle
	 */
	public default float angle(ITensor t)
	{
		ITensor t1 = times(t.norm());
		ITensor t2 = t.times(norm());
		
		float x = t1.plus( t2).norm();
		float y = t1.minus(t2).norm();
		return 2 * Floats.atan2(x, y);
	}
	
	/**
	 * Returns the {@code Tensor}'s norm squared.
	 * 
	 * @return  the tensor's squared norm
	 */
	public default float normsqr()
	{
		return dot(this);
	}
		
	/**
	 * Returns the {@code Tensor}'s norm.
	 * 
	 * @return  the tensor's norm
	 */
	public default float norm()
	{
		return Floats.sqrt(normsqr());
	}
	
	
	/**
	 * Returns the {@code Tensor}'s order.
	 * The order reflects the number of dimensions
	 * the tensor is defined by.
	 * 
	 * @return  the tensor's order
	 */
	public default int order()
	{
		return dimensions().length;
	}

	/**
	 * Returns the {@code Tensor}'s size.
	 * The size reflects the number of values
	 * the tensor is defined by.
	 * 
	 * @return  the tensor's size
	 */
	public default int size()
	{
		return Array.product.of(dimensions());
	}
}