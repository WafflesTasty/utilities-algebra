package zeno.util.algebra.linear.matrix.operators.square;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.Square;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Symmetric} interface defines an operator for symmetric matrices.
 * Matrices tagged with this operator are assumed to equal their transpose.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see Matrix
 * @see Square
 */
public interface Symmetric<M extends Matrix> extends Square<M>
{
	/**
	 * Returns the abstract type of the {@code Symmetric Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Symmetric<?> Type()
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
	public default Symmetric<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	@Override
	public default M transpose()
	{
		return Operable();
	}
}