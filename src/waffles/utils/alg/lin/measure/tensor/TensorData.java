package waffles.utils.alg.lin.measure.tensor;

import waffles.utils.alg.lin.measure.tensor.data.TensorArray;
import waffles.utils.sets.indexed.array.like.FloatArray;
import waffles.utils.tools.patterns.properties.counters.data.Data;

/**
 * The {@code TensorData} interface defines the value storage of a {@code Tensor}.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.1
 * 
 * 
 * @see FloatArray
 * @see Data
 */
public interface TensorData extends Data, FloatArray
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
	 * @return  an index iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<int[]> NZKeys();
	
	
	@Override
	public default TensorData instance()
	{
		return new TensorArray(Dimensions());
	}
	
	@Override
	public default TensorData copy()
	{
		TensorData copy = instance();
		for(int[] crd : NZKeys())
		{
			copy.put(get(crd), crd);
		}
		
		return copy;
	}
	
	@Override
	public default int DataSize()
	{
		return Count();
	}
}