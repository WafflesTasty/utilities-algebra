package zeno.util.algebra.linear;

import zeno.util.tools.Array;

/**
 * The {@code Dimensional} interface defines an object represented by
 * a multi-dimensional set of values.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 */
public interface Dimensional
{
	/**
	 * Returns the dimensions of the {@code Dimensional}.
	 * 
	 * @return  the object dimensions
	 */
	public abstract int[] Dimensions();
	
	
	/**
	 * Returns the order of the {@code Dimensional}.
	 * The order represents how many indices are needed
	 * to define a value in a single dimensional object.
	 * 
	 * @return  the tensor order
	 */
	public default int Order()
	{
		return Dimensions().length;
	}
		
	/**
	 * Returns the size of the {@code Dimensional}.
	 * The size represents how many values are stored
	 * inside a single dimensional object.
	 * 
	 * @return  the tensor size
	 */
	public default int Size()
	{
		return Array.product.of(Dimensions());
	}
}