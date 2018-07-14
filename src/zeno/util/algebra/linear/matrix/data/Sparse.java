package zeno.util.algebra.linear.matrix.data;

import java.util.Map.Entry;

import zeno.util.algebra.attempt4.linear.Tensor;

import java.util.TreeMap;

/**
 * The {@code Sparse} class stores tensor data in a sparse {@code TreeMap}. <br/>
 * <b>NOTE: </b> This class is as of yet untested and unusable. All matrices
 * are generated with a sparse data object.
 * 
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * @see Tensor
 */
public class Sparse extends Data
{
	private TreeMap<Integer, Float> values;
	
	/**
	 * Creates a new {@code Sparse Data}.
	 * 
	 * @param order  the data order
	 */
	public Sparse(int... order)
	{
		super(order);
		
		values = new TreeMap<>();
	}

		
	@Override
	public void set(int index, float val)
	{
		if(val != 0f)
			values.put(index, val);
		else
			values.remove(index);
	}

	@Override
	public float get(int index)
	{
		Float val = values.get(index);
		if(val != null)
		{
			return val;
		}

		return 0;
	}
	
	
	@Override
	public float[] toArray()
	{
		float[] vals = new float[Size()];
		for(Entry<Integer, Float> entry : values.entrySet())
		{
			vals[entry.getKey()] = entry.getValue();
		}
		
		return vals;
	}
		
	@Override
	public Sparse instance()
	{
		return new Sparse(Dimensions());
	}
	
	@Override
	public Sparse copy()
	{
		Sparse copy = (Sparse) super.copy();
		copy.values = (TreeMap<Integer, Float>) values.clone();
		return copy;
	}
}
