package waffles.utils.alg.lin.measure.tensor;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.vector.Vectors;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Array;

/**
 * The {@code Tensors} class defines static-access utilities for {@code Tensor} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.1
 */
public final class Tensors
{
	// TENSOR CREATION.
	
	/**
	 * Creates a zero {@code Tensor}.
	 * 
	 * @param ord  a tensor order
	 * @return  a new tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T create(int... ord)
	{
		switch(ord.length)
		{
		case 2:
			return (T) Matrices.create(ord[0], ord[1]);
		case 1:
			return (T) Vectors.create(ord[0]);
		default:
			return (T) new Tensor(ord);
		}
	}
	
	/**
	 * Creates a {@code Tensor} with an existing data source.
	 * 
	 * @param data  a data source
	 * @return  a new tensor
	 * 
	 * 
	 * @see TensorData
	 * @see Tensor
	 */
	public static <T extends Tensor> T create(TensorData data)
	{
		switch(data.Order())
		{
		case 2:
			return (T) Matrices.create(data);
		case 1:
			return (T) Vectors.create(data);
		default:
			return (T) new Tensor(data);
		}
	}
	
	/**
	 * Creates a square identity {@code Tensor}.
	 * 
	 * @param dim  a tensor dimension
	 * @param ord  a tensor order
	 * @return  an identity tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T identity(int dim, int ord)
	{
		int[] dims = new int[ord];
		for(int o = 0; o < ord; o++)
		{
			dims[o] = dim;
		}
		
		
		Tensor t = create(dims);
		for(int d = 0; d < dim; d++)
		{
			int[] crd = new int[ord];
			for(int o = 0; o < ord; o++)
			{
				crd[o] = d;
			}
			
			t.set(1f, crd);
		}
		
		return (T) t;
	}

	/**
	 * Omits a dimension from a {@code Tensor}.
	 * 
	 * @param <T>  a tensor type
	 * @param t    a base tensor
	 * @param dim  a tensor dimension
	 * @param idx  a tensor index
	 * @return  an omitted tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T omit(Tensor t, int dim, int idx)
	{
		int[] dims = t.Dimensions();
		dims = Array.copy.of(dims);
		dims[dim] = dims[dim] - 1;

		Tensor o = create(dims);
		for(int[] crd : o.Data().Keys())
		{
			if(crd[dim] < idx)
				o.set(t.get(crd), crd);
			else
			{
				int[] cro = Array.copy.of(crd);
				
				cro[dim] = cro[dim] + 1;
				o.set(t.get(cro), crd);
			}
		}
		
		return (T) o;
	}
	

	// TENSOR RANDOMIZATION.
	
	/**
	 * Creates a random {@code Tensor}.
	 * 
	 * @param rng  a randomizer
	 * @param ord  a tensor order
	 * @return  a random tensor
	 * 
	 * 
	 * @see Randomizer
	 * @see Tensor
	 */
	public static <T extends Tensor> T random(Randomizer rng, int... ord)
	{
		Tensor t = create(ord);
		for(int[] crd : t.Data().Keys())
		{
			t.set(rng.randomFloat() * 1024f, crd);
		}
		
		return (T) t;
	}
	
	/**
	 * Creates a random {@code Tensor}.
	 * 
	 * @param ord  a tensor order
	 * @return  a random tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public static <T extends Tensor> T random(int... ord)
	{
		return random(Randomizer.Global(), ord);
	}

	
	// TENSOR CHECKS.

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