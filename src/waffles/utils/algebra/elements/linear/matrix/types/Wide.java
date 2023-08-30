package waffles.utils.algebra.elements.linear.matrix.types;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.MatrixOps;
import waffles.utils.algebra.elements.linear.tensor.Tensor;

/**
 * A matrix tagged with the {@code Tall} operator is assumed to have more columns than rows.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see MatrixOps
 */
public interface Wide extends MatrixOps
{
	/**
	 * Returns the abstract type of the {@code Wide} operator.
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
	public default Wide instance(Tensor t)
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
		
		
		if(t instanceof Matrix)
		{
			int rows = ((Matrix) t).Rows();
			int cols = ((Matrix) t).Columns();
			
			return rows <= cols;
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Wide;
	}
}