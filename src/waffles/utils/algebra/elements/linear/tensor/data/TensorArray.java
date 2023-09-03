package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.sets.indexed.mutable.ArrayIndex;
import waffles.utils.sets.utilities.iterators.IndexKeys;

/**
 * A {@code TensorArray} defines tensor data through an {@code ArrayIndex}.
 * This is the most basic form of tensor data, leaving little in
 * the way of memory storage or access optimization.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see TensorData
 */
public class TensorArray implements TensorData
{	
	private int[] order;
	private ArrayIndex<Float> index;
	
	/**
	 * Creates a new {@code TensorArray}.
	 * 
	 * @param ord  a tensor order
	 */
	public TensorArray(int... ord)
	{
		order = ord;
		index = new ArrayIndex<>(Size());
	}


	@Override
	public Float remove(int... coords)
	{
		int i = toIndex(Order.COL_MAJOR, coords);
		
		Float v = index.remove(i);
		if(v != null)
			return v;
		return 0f;
	}

	@Override
	public Float put(Float val, int... coords)
	{
		int i = toIndex(Order.COL_MAJOR, coords);
		
		Float v = index.put(val, i);
		if(v != null)
			return v;
		return 0f;
	}

	@Override
	public Float get(int... coords)
	{
		int i = toIndex(Order.COL_MAJOR, coords);
		
		Float v = index.get(i);
		if(v != null)
			return v;
		return 0f;
	}

	@Override
	public void clear()
	{
		index.clear();
	}

	
	@Override
	public TensorArray copy()
	{
		TensorArray copy = instance();
		for(int i = 0; i < index.Size(); i++)
		{
			Float v = index.get(i);
			if(v != null)
			{
				copy.index.put(v, i);
			}
		}
		
		return copy;
	}
	
	@Override
	public TensorArray instance()
	{
		return new TensorArray(Dimensions());
	}
	
	@Override
	public Iterable<int[]> NZIndex()
	{
		return () -> new IndexKeys(this);
	}
	
	@Override
	public int[] Dimensions()
	{
		return order;
	}
	
	@Override
	public int NZCount()
	{
		return Count();
	}
}