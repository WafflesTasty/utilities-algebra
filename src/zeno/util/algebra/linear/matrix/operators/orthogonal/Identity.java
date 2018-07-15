package zeno.util.algebra.linear.matrix.operators.orthogonal;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.operations.identity.IdentityDotProduct;
import zeno.util.algebra.linear.matrix.operators.banded.Diagonal;
import zeno.util.algebra.linear.matrix.operators.square.Orthogonal;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.patterns.Operator;
import zeno.util.tools.patterns.ops.Repetition;

/**
 * The {@code Identity} interface defines an operator for identity matrices.
 * Matrices tagged with this operator are assumed to have zeroes everywhere
 * and ones on the diagonal.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see Orthogonal
 * @see Diagonal
 * @see Matrix
 */
public interface Identity<M extends Matrix> extends Orthogonal<M>, Diagonal<M>
{
	/**
	 * Returns the abstract type of the {@code Identity Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Identity<?> Type()
	{
		return () ->
		{
			return null;
		};
	}

	
	@Override
	public default Operation<M> LMultiplier(M matrix)
	{
		return new Repetition<>(matrix);
	}
	
	@Override
	public default Operation<M> RMultiplier(M matrix)
	{
		return new Repetition<>(matrix);
	}

	@Override
	public default Operation<Float> dotproduct(M matrix)
	{
		return new IdentityDotProduct(matrix);
	}
	
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Identity;
	}
	
	@Override
	public default boolean matches(Tensor t, int ulps)
	{
		return Diagonal.super.matches(t, ulps);
	}

	@Override
	public default Identity<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	
	@Override
	public default M multiply(float val)
	{
		int rows = Operable().Rows();
		
		Matrix result = Matrices.create(rows, rows);
		for(int r = 0; r < rows; r++)
		{
			result.set(val, r, r);
		}
		
		return (M) result;
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