package zeno.util.algebra;

import zeno.util.tools.Array;
import zeno.util.tools.generic.properties.Approximate;

/**
 * The {@code Dimensional} interface defines an object represented by
 * a multi-dimensional set of values.
 *
 * @author Zeno
 * @since Jul 5, 2018
 * @version 1.0
 * 
 * 
 * @param <O>  the type of the object
 * @see Approximate
 */
public interface Dimensional<O> extends Approximate<O>
{	
	/**
	 * Returns the dimensions of the {@code Dimensional}.
	 * 
	 * @return  the object dimensions
	 */
	public abstract int[] Dimensions();
	
	/**
	 * Returns the values of the {@code Dimensional}.
	 * 
	 * @return  the object values
	 */
	public abstract float[] Values();
	
	
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