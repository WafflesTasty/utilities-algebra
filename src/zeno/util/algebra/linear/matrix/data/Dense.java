package zeno.util.algebra.linear.matrix.data;

import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.tools.Array;

/**
 * The {@code Dense} class stores tensor data in a dense array.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * @see Tensor
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
	public void set(int index, float val)
	{
		values[index] = val;
	}
	
	@Override
	public float get(int index)
	{
		return values[index];
	}
	
	
	@Override
	public float[] toArray()
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
