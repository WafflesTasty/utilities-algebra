package zeno.util.algebra.linear.tensor;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.tools.helper.Array;
import zeno.util.tools.helper.Randomizer;

/**
 * The {@code Tensors} class defines basic {@code Tensor} operations.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 */
public final class Tensors
{
	/**
	 * The {@code OrderError} class defines an error thrown when
	 * the {@code Tensors} class has not been initialized.
	 *
	 * @author Zeno
	 * @since Jul 5, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class OrderError extends RuntimeException
	{
		private static final long serialVersionUID = 1067184884164094406L;

		/**
		 * Creates a new {@code OrderException}.
		 */
		public OrderError()
		{
			super("Initialize a data order using the Algebra$setOrder method.");
		}
	}
	
	/**
	 * The {@code DimensionError} class defines an error thrown when
	 * multiple {@code Tensor} dimensions are incompatible.
	 *
	 * @author Zeno
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
		 * @param message  a message to append to the error
		 * @param tensors  a list of tensors
		 * @see String
		 * @see Tensor
		 */
		public DimensionError(String message, Tensor... tensors)
		{
			super(message + dimensions(tensors) + ".");
		}
	}
	
	/**
	 * The {@code AccessError} class defines an error thrown when
	 * requested tensor coördinates are out of bounds.
	 *
	 * @author Zeno
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
		 * @param coords  the invalid coördinates
		 * @param order  the valid coördinate bounds
		 */
		public AccessError(int[] coords, int[] order)
		{
			super("The coördinates " + Array.parse.of(coords) + " are out of bounds of " + Array.parse.of(order) + ".");
		}
	}
	
	/**
	 * The {@code Major} enum defines the major order in
	 * which tensor data can be stored.
	 *
	 * @author Zeno
	 * @since Jul 15, 2018
	 * @version 1.0
	 */
	public static enum Major
	{
		/**
		 * Data is stored in column-major order.
		 */
		COLUMN,
		/**
		 * Data is stored in row-major order.
		 */
		ROW;
	}
	
	
	
	private static Major order;
	
	/**
	 * Checks if two tensor objects are of the same dimension.
	 * 
	 * @param t1  a  first tensor to check
	 * @param t2  a second tensor to check
	 * @return  {@code true} if the dimensions are equal
	 */
	public static boolean isomorph(Tensor t1, Tensor t2)
	{
		return Array.equals.of(t1.Dimensions(), t2.Dimensions());
	}

	/**
	 * Creates an identity {@code Tensor} of the specified size.
	 * 
	 * @param size   the size of the tensor
	 * @param order  the order of the tensor
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
	 * Creates an empty {@code Tensor} of the specified dimensions.
	 * 
	 * @param order  the tensor order
	 * @return  a new tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T create(int... order)
	{
		if(order.length == 1)
		{
			return (T) Vectors.create(order[0]);
		}
		
		if(order.length == 2)
		{
			return (T) Matrices.create(order[0], order[1]);
		}
		
		return (T) new Tensor(order);
	}
	
	/**
	 * Creates a random {@code Tensor} of the specified dimensions.
	 * 
	 * @param order  the tensor order
	 * @return  a random tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T random(int... order)
	{
		Tensor t = create(order);
		for(int i = 0; i < t.Size(); i++)
		{
			t.Data().set(i, Randomizer.randomFloat());
		}
		
		return (T) t;
	}
		
	
	/**
	 * Initializes the {@code Tensors} library.
	 * 
	 * @param order  a major data order
	 * 
	 * 
	 * @see Major
	 */
	public static void initialize(Major order)
	{
		Tensors.order = order;
	}
	
	/**
	 * Initializes the {@code Tensors} library.
	 */
	public static void initialize()
	{
		initialize(Major.COLUMN);
	}
	
	/**
	 * Returns the order of {@code Tensors}.
	 * 
	 * @return  the tensor order
	 * 
	 * 
	 * @see Major
	 */
	public static Major Order()
	{
		if(order == null)
		{
			initialize();
		}
		
		return order;
	}
	
	
	private Tensors()
	{
		// NOT APPLICABLE
	}
}