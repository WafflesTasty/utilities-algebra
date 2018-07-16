package zeno.util.algebra.linear.matrix.types.square;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.properties.operable.Operator;

/**
 * The {@code Symmetric} interface defines an operator for symmetric matrices.
 * Matrices tagged with this operator are assumed to equal their transpose.
 * 
 * @author Zeno
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
	public default boolean matches(Tensor t, int ulps)
	{		
		if(t.is(Square.Type()))
		{
			Matrix tpos = ((Matrix) t).transpose();
			return t.equals(tpos, ulps);
		}

		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Symmetric;
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
	public default Matrix transpose()
	{
		return Operable();
	}
}