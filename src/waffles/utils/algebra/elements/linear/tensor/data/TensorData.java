package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.sets.arrays.FloatArray;
import waffles.utils.sets.utilities.iterators.IndexKeys;
import waffles.utils.sets.utilities.iterators.IndexValues;

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
	 * Iterates over the index in the {@code TensorData}.
	 * This iterator should return exactly Count() elements.
	 * 
	 * @return  an index iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public default Iterable<int[]> Index()
	{
		return () -> new IndexKeys(this);
	}
	
	/**
	 * Iterates over the values in the {@code TensorData}.
	 * This iterator should return exactly Count() elements.
	 * 
	 * @return  a value iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public default Iterable<Float> Values()
	{
		return () -> new IndexValues<>(this);
	}
	
	/**
	 * Iterates over non-zero indices in the {@code TensorData}.
	 * 
	 * @return  an index iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<int[]> NZIndex();
	
	/**
	 * Returns the non-zero index count of the {@code TensorData}.
	 * This can be an upper bound estimate, it is simply used
	 * to determine the efficiency of tensor algorithms.
	 * 
	 * @return  a non-zero index count
	 */
	public abstract int NZCount();
	
	
	@Override
	public default TensorData copy()
	{
		return (TensorData) FloatArray.super.copy();
	}
	
	@Override
	public default int Count()
	{
		return FloatArray.super.Count();
	}
}