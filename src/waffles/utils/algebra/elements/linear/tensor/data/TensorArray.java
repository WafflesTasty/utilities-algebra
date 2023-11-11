package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.sets.indexed.mutable.primitive.FloatIndex;
import waffles.utils.sets.utilities.iterators.IndexKeys;

/**
 * A {@code TensorArray} defines tensor data through an {@code ArrayIndex}.
 * This is the most basic form of tensor data, leaving little in
 * the way of memory storage or access optimization.
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
//		order = ord;
//		index = new ArrayIndex<>(Size());
	}

			
	@Override
	public Iterable<int[]> NZIndex()
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