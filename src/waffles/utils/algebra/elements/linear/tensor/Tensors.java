package waffles.utils.algebra.elements.linear.tensor;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Array;

/**
 * The {@code Tensors} class defines static-access utilities for {@code Tensor} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 */
public final class Tensors
{
	/**
	 * An {@code AccessError} is thrown when {@code Tensor}
	 * coordinates are requested which are out of bounds.
	 *
	 * @author Waffles
	 * @since Jul 5, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class AccessError extends RuntimeException
	{
		private static final long serialVersionUID = -6890751497419042594L;

		/**
		 * Creates a new {@code AccessError}.
		 * 
		 * @param coords  a coordinate set
		 * @param order   valid coordinate bounds
		 */
		public AccessError(int[] coords, int[] order)
		{
			super("The coordinates " + Array.parse.of(coords) + " are out of bounds of " + Array.parse.of(order) + ".");
		}
	}
	
	/**
	 * A {@code DimensionError} is thrown when a {@code Tensor}
	 * set has incompatible dimensions for any operation.
	 *
	 * @author Waffles
	 * @since Jul 13, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class DimensionError extends RuntimeException
	{
		private static final long serialVersionUID = -2858384093246469008L;

		private static String dimensions(Tensor... tensors)
		{
			String result = "";
			for(Tensor t : tensors)
			{
				result += Array.parse.of(t.Dimensions());
			}
			
			return result;
		}
		
		
		/**
		 * Creates a new {@code DimensionError}.
		 * 
		 * @param message  a message to append
		 * @param tensors  a set of tensors
		 * 
		 * 
		 * @see Tensor
		 */
		public DimensionError(String message, Tensor... tensors)
		{
			super(message + dimensions(tensors) + ".");
		}
	}
	
			
	/**
	 * Creates an empty {@code Tensor} of the specified order.
	 * The tensor data is stored in a basic array.
	 * 
	 * @param dims  a tensor dimension
	 * @return  a new tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T create(int... dims)
	{
		if(dims.length == 2)
		{
			return (T) Matrices.create(dims[0], dims[1]);
		}
		
		if(dims.length == 1)
		{
			return (T) Vectors.create(dims[0]);
		}
		
		return (T) new Tensor(dims);
	}
	
	/**
	 * Creates a {@code Tensor} with the specified data object.
	 * 
	 * @param data  a tensor data
	 * @return  a new tensor
	 * 
	 * 
	 * @see TensorData
	 * @see Tensor
	 */
	public static <T extends Tensor> T create(TensorData data)
	{
		if(data.Order() == 2)
		{
			return (T) Matrices.create(data);
		}

		return (T) new Tensor(data);
	}
		
	/**
	 * Creates an identity {@code Tensor} of the given size.
	 * 
	 * @param size   a tensor size
	 * @param order  a tensor order
	 * @return  an identity tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T identity(int size, int order)
	{
		int[] dims = new int[order];
		for(int i = 0; i < order; i++)
		{
			dims[i] = size;
		}
		
		
		Tensor t = create(dims);
		for(int i = 0; i < size; i++)
		{
			int[] coords = new int[order];
			for(int j = 0; j < order; j++)
			{
				coords[j] = i;
			}
			
			t.set(1f, coords);
		}
		
		return (T) t;
	}
	
	/**
	 * Creates a {@code Tensor} as a resized copy of another tensor.
	 * 
	 * @param t     a tensor to resize
	 * @param dims  a tensor dimension
	 * @return  a resized tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T resize(Tensor t, int... dims)
	{
		return (T) t.Operator().Resize(dims).result();
	}
		
	/**
	 * Creates a random {@code Tensor} of the given dimensions.
	 * 
	 * @param rng  a value randomizer
	 * @param dims  a tensor dimension
	 * @return  a random tensor
	 * 
	 * 
	 * @see Randomizer
	 * @see Tensor
	 */
	public static <T extends Tensor> T random(Randomizer rng, int... dims)
	{
		Tensor t = create(dims);
		for(int[] coord : t.Data().Index())
		{
			t.set(rng.randomFloat(), coord);
		}
		
		return (T) t;
	}
	
	/**
	 * Creates a random {@code Tensor} of the given dimensions.
	 * 
	 * @param dims  a tensor dimension
	 * @return  a random tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T random(int... dims)
	{
		return random(Randomizer.Global(), dims);
	}
	
	/**
	 * Checks if {@code Tensors} have the same dimension.
	 * 
	 * @param t1  a  first tensor to check
	 * @param t2  a second tensor to check
	 * @return  {@code true} if the dimensions are equal
	 * 
	 * 
	 * @see Tensor
	 */
	public static boolean isomorph(Tensor t1, Tensor t2)
	{
		return Array.equals.of(t1.Dimensions(), t2.Dimensions());
	}
	
	
	private Tensors()
	{
		// NOT APPLICABLE
	}
}