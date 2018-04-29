package zeno.util.algebra.linear;

import zeno.util.algebra.Measurable;
import zeno.util.tools.Array;

/**
 * The {@code Tensor} interface defines a vector element defined
 * as a multi-dimensional floating point array.
 *
 * @author Zeno
 * @since Apr 29, 2018
 * @version 1.0
 * 
 * @param <T>  the type of the tensor
 * @see Measurable
 */
public interface Tensor<T extends Tensor<T>> extends Measurable<T>
{
	/**
	 * Returns the {@code Tensor} values.
	 * 
	 * @return  the tensor values
	 */
	public abstract float[] Values();
	
	/**
	 * returns the {@code Tensor} dimensions.
	 * 
	 * @return  the tensor dimensions
	 */
	public abstract int[] Dimensions();
	
	
	/**
	 * Checks if a {@code Tensor} has equal dimensions.
	 * 
	 * @param t  a tensor to compare with
	 * @return  {@code true} if the dimensions are equal
	 */
	public default boolean hasEqualDimension(Tensor<T> t)
	{
		return Array.equals.of(Dimensions(), t.Dimensions());
	}
	
	/**
	 * Returns the {@code Tensor} order.
	 * The order reflects the number of dimensions
	 * the tensor is defined by.
	 * 
	 * @return  the tensor's order
	 */
	public default int Order()
	{
		return Dimensions().length;
	}

	/**
	 * Returns the {@code Tensor} size.
	 * The size reflects the number of values
	 * the tensor is defined by.
	 * 
	 * @return  the tensor's size
	 */
	public default int Size()
	{
		return Array.product.of(Dimensions());
	}
}