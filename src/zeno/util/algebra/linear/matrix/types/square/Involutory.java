package zeno.util.algebra.linear.matrix.types.square;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.matrix.types.orthogonal.Identity;
import zeno.util.algebra.linear.tensor.Tensor;
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
 * @see Square
 */
public interface Involutory extends Square
{
	/**
	 * Returns the abstract type of the {@code Involutory Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Involutory Type()
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
	public default Involutory instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	
	@Override
	public default Matrix pseudoinverse()
	{
		return inverse();
	}
	
	@Override
	public default Matrix inverse()
	{
		return Operable();
	}
}