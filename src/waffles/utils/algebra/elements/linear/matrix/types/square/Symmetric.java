package waffles.utils.algebra.elements.linear.matrix.types.square;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.MatrixIdentity;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A matrix tagged with the {@code Symmetric} operator is equal to its transpose.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Symmetric extends Square
{
	/**
	 * Returns the abstract type of the {@code Symmetric Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Symmetric Type()
	{
		return () ->
		{
			return null;
		};
	}

				
	@Override
	public default Symmetric instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}

	@Override
	public default Operation<Matrix> Transpose()
	{
		return new MatrixIdentity(Operable());
	}
	
	@Override
	public default boolean allows(Tensor t, int ulps)
	{		
		if(matches(t))
		{
			return true;
		}
		
		if(t.is(Square.Type()))
		{
			Matrix m = ((Matrix) t).transpose();
			return t.equals(m, ulps);
		}

		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Symmetric;
	}
}