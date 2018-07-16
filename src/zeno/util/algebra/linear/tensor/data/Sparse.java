package zeno.util.algebra.linear.tensor.data;

import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * The {@code Sparse} class stores tensor data in a sparse {@code TreeMap}.
 * 
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @see Data
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
	public float get(int i)
	{
		Float val = values.get(i);
		if(val != null)
		{
			return val;
		}

		return 0;
	}
	
	@Override
	public void set(int i, float val)
	{
		if(val != 0f)
			values.put(i, val);
		else
			values.remove(i);
	}
	
	
	@Override
	public float[] Values()
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
