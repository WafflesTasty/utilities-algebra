package waffles.utils.algebra.elements.linear.matrix.types.orthogonal;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.MatrixIdentity;
import waffles.utils.algebra.elements.linear.matrix.ops.square.SquareTrace;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.square.Involutory;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A matrix tagged with the {@code Identity} operator is an identity matrix.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Involutory
 * @see Orthogonal
 * @see Diagonal
 */
public interface Identity extends Orthogonal, Diagonal, Involutory
{
	/**
	 * Returns the abstract type of the {@code Identity} operator.
	 * 
	 * @return  a type operator
	 */
	public static Identity Type()
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
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new MatrixIdentity(m);
	}
	
	@Override
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new MatrixIdentity(m);
	}

	@Override
	public default Operation<Float> DotProduct(Tensor t)
	{
		return new SquareTrace((Matrix) t);
	}

		
	@Override
	public default Identity instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean allows(Tensor t, int ulps)
	{		
		return Diagonal.super.allows(t, ulps);
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Identity;
	}
}