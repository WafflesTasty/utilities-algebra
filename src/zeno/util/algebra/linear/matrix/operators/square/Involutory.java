package zeno.util.algebra.linear.matrix.operators.square;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operators.Square;
import zeno.util.algebra.linear.matrix.operators.orthogonal.Identity;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Involutory} interface defines an operator for involutory matrices.
 * Matrices tagged with this operator are assumed to be their own inverse.
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
public interface Involutory<M extends Matrix> extends Square<M>
{
	/**
	 * Returns the abstract type of the {@code Involutory Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Involutory<?> Type()
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
			Matrix prod = ((Matrix) t).times((Matrix) t);
			return prod.is(Identity.Type(), ulps);
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Involutory;
	}
		
	@Override
	public default Involutory<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	
	@Override
	public default M pseudoinverse()
	{
		return inverse();
	}
	
	@Override
	public default M inverse()
	{
		return Operable();
	}
}