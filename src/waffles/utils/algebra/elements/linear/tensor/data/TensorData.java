package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.sets.indexed.MutableIndex;
import waffles.utils.sets.utilities.iterators.IndexKeys;
import waffles.utils.sets.utilities.iterators.IndexValues;
import waffles.utils.tools.patterns.semantics.Copyable;

/**
 * The {@code TensorData} interface defines a data object which backs a {@code Tensor}.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see MutableIndex
 * @see Copyable
 * @see Float
 */
public interface TensorData extends MutableIndex<Float>, Copyable<TensorData>
{
	/**
	 * Returns a data array of the {@code TensorData}.
	 * 
	 * @return  a tensor array
	 */
	public default float[] Array()
	{
		float[] data = new float[Count()];
		for(int[] coord : Index())
		{
			int i = toIndex(Order.COL_MAJOR, coord);
			Float v = get(coord);
			if(v != null)
			{
				data[i] = v;
			}
		}

		return data;
	}

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
}