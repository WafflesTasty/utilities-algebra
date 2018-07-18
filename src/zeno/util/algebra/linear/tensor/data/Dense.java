package zeno.util.algebra.linear.tensor.data;

import zeno.util.tools.helper.Array;

/**
 * The {@code Dense} class stores tensor data in a dense array.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @see Data
 */
public class Dense extends Data
{
	private float[] values;
	
	/**
	 * Creates a new {@code Dense Data}.
	 * 
	 * @param dims  the data order
	 */
	public Dense(int... dims)
	{
		super(dims);
		
		values = new float[Size()];
	}
	
	
	@Override
	public float get(int i)
	{
		return values[i];
	}
	
	@Override
	public void set(int i, float val)
	{
		values[i] = val;
	}
		
	
	@Override
	public float[] Values()
	{
		return values;
	}
	
	@Override
	public Dense instance()
	{
		return new Dense(Dimensions());
	}
	
	@Override
	public Dense copy()
	{
		Dense copy = (Dense) super.copy();
		copy.values = Array.copy.of(values);
		return copy;
	}
}
