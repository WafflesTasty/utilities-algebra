package waffles.utils.algebra.elements.linear.matrix.types.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.diagonal.DiagonalInverse;
import waffles.utils.algebra.elements.linear.matrix.types.banded.lower.LowerBidiagonal;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperBidiagonal;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code Diagonal} matrix is assumed to have zeroes everywhere except
 * for the main diagonal. This is equivalent to having
 * a lower and upper band of 0.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see LowerBidiagonal
 * @see UpperBidiagonal
 * @see Symmetric
 */
public interface Diagonal extends LowerBidiagonal, UpperBidiagonal, Symmetric
{
	/**
	 * Returns the abstract type of the {@code Diagonal} operator.
	 * 
	 * @return  a type operator
	 */
	public static Diagonal Type()
	{
		return () ->
		{
			return null;
		};
	}

	/**
	 * Returns a diagonal {@code Vector} of a given {@code Tensor}.
	 * 
	 * @param t  a tensor
	 * @return   a diagonal vector
	 * 
	 * 
	 * @see Tensor
	 * @see Vector
	 */
	public static Vector of(Tensor t)
	{
		int size = Integers.min(t.Dimensions());
		Vector v = Vectors.create(size);
		for(int i = 0; i < size; i++)
		{
			int[] coords = new int[t.Order()];
			for(int k = 0; k < t.Order(); k++)
			{
				coords[k] = i;
			}
			
			v.set(t.get(coords), i);
		}
		
		return v;
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return new DiagonalInverse(Operable());
	}

	@Override
	public default boolean allows(Tensor t, int ulps)
	{
		return LowerBidiagonal.super.allows(t, ulps);
	}
	
	@Override
	public default Diagonal instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Diagonal;
	}
	
	@Override
	public default int LowerBand()
	{
		return 0;
	}
	
	@Override
	public default int UpperBand()
	{
		return 0;
	}
}