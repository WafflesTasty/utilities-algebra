package waffles.utils.alg.linear.measure.tensor.data;

import waffles.utils.alg.linear.measure.tensor.TensorData;
import waffles.utils.sets.indexed.array.index.FloatIndex;
import waffles.utils.sets.utilities.indexed.iterators.IndexKeys;

/**
 * A {@code TensorArray} defines tensor data through an {@code ObjectIndex}.
 * This is the most basic form of tensor data, leaving little
 * in the way of storage or access optimization.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.1
 * 
 * 
 * @see FloatIndex
 * @see TensorData
 */
public class TensorArray extends FloatIndex implements TensorData
{	
	/**
	 * Creates a new {@code TensorArray}.
	 * 
	 * @param ord  a tensor order
	 */
	public TensorArray(int... ord)
	{
		super(ord);
	}

			
	@Override
	public Iterable<int[]> NZKeys()
	{
		return () -> new IndexKeys(this);
	}
	
	@Override
	public TensorArray instance()
	{
		return new TensorArray(Dimensions());
	}
	
	@Override
	public TensorArray copy()
	{
		return (TensorArray) super.copy();
	}
	
	@Override
	public int NZCount()
	{
		return Count();
	}
}