package zeno.util.algebra.linear.matrix.types.orthogonal;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.functions.identity.IdentityDotProduct;
import zeno.util.algebra.linear.matrix.types.banded.Diagonal;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.patterns.Operator;
import zeno.util.tools.patterns.functions.Repetition;

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
 * @see Orthogonal
 * @see Diagonal
 */
public interface Identity extends Orthogonal, Diagonal
{
	/**
	 * Returns the abstract type of the {@code Identity Operator}.
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
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new Repetition<>(m);
	}
	
	@Override
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new Repetition<>(m);
	}

	@Override
	public default Operation<Float> dotproduct(Tensor t)
	{
		return new IdentityDotProduct((Matrix) t);
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
	public default Identity instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	
	@Override
	public default Matrix multiply(float val)
	{
		int rows = Operable().Rows();
		
		Matrix result = Matrices.create(rows, rows);
		for(int r = 0; r < rows; r++)
		{
			result.set(val, r, r);
		}
		
		return result;
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