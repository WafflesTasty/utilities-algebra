package waffles.utils.algebra.elements.linear.matrix.types.square;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.MatrixIdentity;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A matrix tagged with the {@code Involutory} operator is equal to its inverse.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Involutory extends Square
{
	/**
	 * Returns the abstract type of the {@code Involutory} operator.
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
	public default Operation<Matrix> Inverse()
	{
		return new MatrixIdentity(Operable());
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
	public default boolean allows(Tensor t, int ulps)
	{
		if(matches(t))
		{
			return true;
		}
		
		if(Square.Type().allows(t, ulps))
		{
			Matrix prod = ((Matrix) t).times((Matrix) t);
			return Identity.Type().allows(prod, ulps);
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Involutory;
	}
}