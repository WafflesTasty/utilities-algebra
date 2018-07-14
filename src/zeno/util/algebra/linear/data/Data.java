package zeno.util.algebra.linear.data;

import zeno.util.algebra.Dimensional;
import zeno.util.algebra.attempt4.Algebra;
import zeno.util.tools.Array;
import zeno.util.tools.generic.properties.Copyable;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code Data} class defines how value data is stored in a {@code Tensor}.
 *
 * @author Zeno
 * @since Jul 4, 2018
 * @version 1.0
 * 
 * @see Dimensional
 * @see Copyable
 */
public abstract class Data implements Copyable<Data>, Dimensional
{
	private int[] order;

	/**
	 * Creates a new {@code Tensor.Data}.
	 * 
	 * @param order  the data order
	 */
	public Data(int... order)
	{
		this.order = order;
	}
	
	
	/**
	 * Returns an array representing the {@code Data}.
	 * Regardless of how the data is represented, this
	 * method should always return the full contents
	 * of the tensor being represented.
	 * 
	 * @return  a value array
	 */
	public abstract float[] toArray();
			
	/**
	 * Returns a value represented in the {@code Data}.
	 * 
	 * @param index  an array index
	 * @return  a tensor value
	 */
	public abstract float get(int index);
	
	/**
	 * Changes a value represented in the {@code Data}.
	 * 
	 * @param index  an array index
	 * @param val  a tensor value
	 */
	public abstract void set(int index, float val);
	
	/**
	 * Changes a value represented in the {@code Data}.
	 * 
	 * @param val  a tensor value
	 * @param coords  a tensor coördinate
	 */
	public void set(float val, int... coords)
	{
		if(!isValid(coords))
		{
			throw new Algebra.AccessException(coords);
		}
		
		set(toIndex(coords), val);
	}

	/**
	 * Returns a value represented in the {@code Data}.
	 * 
	 * @param coords  a tensor coördinate
	 * @return  a tensor value
	 */
	public float get(int... coords)
	{
		if(!isValid(coords))
		{
			throw new Algebra.AccessException(coords);
		}
			
		return get(toIndex(coords));
	}

	
	private boolean isValid(int... coords)
	{
		int count = coords.length;
		
		// Allow more coördinates than necessary...
		if(count > Order())
		{
			// Only if the excess coördinates are zero.
			for(int i = Order(); i < count; i++)
			{
				if(coords[i] != 0)
				{
					return false;
				}
			}
		}
		
		// Make sure the relevant coördinates are within range.
		for(int i = 0; i < Integers.min(count, Order()); i++)
		{
			if(coords[i] < 0 || order[i] <= coords[i])
			{
				return false;
			}
		}
		
		return true;
	}

	private int toColMajorIndex(int... coords)
	{
		int index = 0;
		for(int i = Order() - 1; i >= 0; i--)
		{
			index *= Dimensions()[i];
			index += coords[i];
		}

		return index;
	}
	
	private int toRowMajorIndex(int... coords)
	{
		int index = 0;
		for(int i = 0; i < Order(); i++)
		{
			index *= Dimensions()[i];
			index += coords[i];
		}

		return index;
	}

	private int toIndex(int... coords)
	{		
		switch(Algebra.ORDER)
		{
		case COLUMN:
			return toColMajorIndex(Array.copy.of(coords, Order()));
		case ROW:
			return toRowMajorIndex(Array.copy.of(coords, Order()));
		default:
			throw new Algebra.OrderException();
		}
	}

	
	@Override
	public int[] Dimensions()
	{
		return order;
	}
}