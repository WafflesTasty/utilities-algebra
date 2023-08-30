package waffles.utils.algebra.elements.linear.matrix.types.orthogonal;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A matrix tagged with the {@code Orthogonal} operator has an inverse equal to its transpose.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Orthogonal extends Square
{
	/**
	 * Returns the abstract type of the {@code Orthogonal} operator.
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
	public default Operation<Matrix> Inverse()
	{
		return Transpose();
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
	public default boolean allows(Tensor t, int ulps)
	{		
		if(matches(t))
		{
			return true;
		}
		
		if(Square.Type().allows(t, ulps))
		{
			Matrix m = (Matrix) t;
			Matrix id = m.times(m).transpose();
			return Identity.Type().allows(id, ulps);
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Orthogonal;
	}
}