package zeno.util.algebra.linear.matrix.operators;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.MatrixOps;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Tall} interface defines an operator for tall matrices.
 * Matrices tagged with this operator are assumed to have more
 * rows than they have columns.
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
public interface Tall<M extends Matrix> extends MatrixOps<M>
{
	/**
	 * Returns the abstract type of the {@code Tall Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Tall<?> Type()
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
			return rows >= cols;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Tall;
	}
					
	@Override
	public default Tall<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
}