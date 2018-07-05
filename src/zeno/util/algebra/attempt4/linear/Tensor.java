package zeno.util.algebra.linear;

import zeno.util.algebra.Measurable;
import zeno.util.tools.Array;

/**
 * The {@code Tensor} class defines an abstract superclass representing multilinear value components.
 * All matrix and vector objects are designed as an instance of this class.
 *
 * @author Zeno
 * @since Jul 4, 2018
 * @version 1.0
 * 
 * @param <T>  the type of the tensor
 * @see Measurable
 */
public abstract class Tensor<T extends Tensor<T>> implements Measurable<T>
{
	/**
	 * Returns the order of the {@code Tensor}.
	 * The order represents how many indices are needed
	 * to define a value in the tensor.
	 * 
	 * @return  the tensor order
	 */
	public int Order()
	{
		return Dimensions().length;
	}
	
	/**
	 * Returns the size of the {@code Tensor}.
	 * The size represents how many values are stored
	 * inside the tensor.
	 * 
	 * @return  the tensor size
	 */
	public int Size()
	{
		return Array.product.of(Dimensions());
	}
		

	
	/**
	 * Returns the dimensions of the {@code Tensor}.
	 * 
	 * @return  the tensor dimensions
	 */
	public abstract int[] Dimensions();
	
	/**
	 * Returns a single value in the {@code Tensor}.
	 * 
	 * @param coords  a tensor coördinate
	 * @return  a tensor value
	 */
	public abstract float get(int... coords);
	
	/**
	 * Returns all values in the {@code Tensor}.
	 * 
	 * @return  the tensor values
	 */
	public abstract float[] Values();
		
	
	@Override
	public String toString()
	{
		return Array.parse.of(Values());
	}

	@Override
	public int hashCode()
	{
		return Array.hash.of(Values());
	}
}