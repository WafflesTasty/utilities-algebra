package waffles.utils.alg.lin.measure.matrix.types.banded;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.diag.DiagonalInverse;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerBidiagonal;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperBidiagonal;
import waffles.utils.alg.lin.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.measure.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code Diagonal} operator assumes a matrix to have zeroes everywhere
 * except for the main diagonal. This is equivalent to having
 * both a lower and upper band of 0.
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
	 * Returns the abstract {@code Diagonal} type.
	 * 
	 * @return  a type operator
	 */
	public static Diagonal Type()
	{
		return () -> null;
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
		int s = Integers.min(t.Dimensions());
		
		Vector v = Vectors.create(s);
		for(int i = 0; i < s; i++)
		{
			int[] crd = new int[t.Order()];
			for(int k = 0; k < t.Order(); k++)
			{
				crd[k] = i;
			}
			
			v.set(t.get(crd), i);
		}
		
		return v;
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return new DiagonalInverse(Operable());
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return LowerBidiagonal.super.Allows(e);
	}

	
	@Override
	public default Diagonal instance(Tensor t)
	{
		return () -> (Matrix) t;
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