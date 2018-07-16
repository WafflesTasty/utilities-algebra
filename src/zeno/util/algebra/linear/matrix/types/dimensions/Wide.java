package zeno.util.algebra.linear.matrix.types.dimensions;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.MatrixOps;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.Operator;

/**
 * The {@code Wide} interface defines an operator for wide matrices.
 * Matrices tagged with this operator are assumed to have less
 * rows than they have columns.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see MatrixOps
 */
public interface Wide extends MatrixOps
{
	/**
	 * Returns the abstract type of the {@code Wide Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Wide Type()
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
			return rows <= cols;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Wide;
	}
			
	@Override
	public default Wide instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
}