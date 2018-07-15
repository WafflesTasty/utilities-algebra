package zeno.util.algebra.linear.matrix.operators;

import zeno.util.algebra.algorithms.solvers.SLVCrout;
import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.MatrixOps;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Square} interface defines an operator for square matrices.
 * Matrices tagged with this operator are assumed to have as many
 * rows as they have columns.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see MatrixOps
 * @see Matrix
 */
public interface Square<M extends Matrix> extends Tall<M>, Wide<M>
{
	/**
	 * Returns the abstract type of the {@code Square Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Square<?> Type()
	{
		return () ->
		{
			return null;
		};
	}

	
	@Override
	public default boolean matches(Tensor t, int ulps)
	{
		if(t instanceof Matrix)
		{
			int rows = ((Matrix) t).Rows();
			int cols = ((Matrix) t).Columns();
			return rows == cols;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Square;
	}
		
	@Override
	public default Square<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}

	@Override
	public default M inverse()
	{
		return (M) new SLVCrout(Operable()).inverse();
	}
}