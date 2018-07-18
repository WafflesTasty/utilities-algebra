package zeno.util.algebra.linear.matrix.types.orthogonal;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.properties.operator.Operator;

/**
 * The {@code Orthogonal} interface defines an operator for orthogonal matrices.
 * Matrices tagged with this operator are assumed to have an inverse that
 * equals their transpose.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Orthogonal extends Square
{
	/**
	 * Returns the abstract type of the {@code Orthogonal Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Orthogonal Type()
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
			Matrix prod = ((Matrix) t).times(((Matrix) t).transpose());
			return prod.is(Identity.Type(), ulps);
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Orthogonal;
	}
	
	@Override
	public default Orthogonal instance(Tensor t)
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
		return transpose();
	}
}