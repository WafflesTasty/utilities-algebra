package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.sets.arrays.FloatArray;

/**
 * The {@code TensorData} interface defines a data object which backs a {@code Tensor}.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.1
 * 
 * 
 * @see FloatArray
 */
public interface TensorData extends FloatArray
{
	/**
	 * Returns the non-zero count of the {@code TensorData}.
	 * This can be an upper bound estimate, it is simply used
	 * to determine the efficiency of tensor algorithms.
	 * 
	 * @return  a non-zero index count
	 */
	public abstract int NZCount();
	
	/**
	 * Iterates over non-zero keys in the {@code TensorData}.
	 * 
	 * @return  an index key iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<int[]> NZKeys();
		
	
	@Override
	public default TensorData copy()
	{
		return (TensorData) FloatArray.super.copy();
	}
}