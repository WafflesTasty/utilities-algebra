package waffles.utils.alg.linear.measure.matrix.types.shaped;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.MatrixOps;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code Tall} operator is used for matrices with no more rows than columns.
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
	 * Returns the abstract {@code Wide} type.
	 * 
	 * @return  a type operator
	 */
	public static Wide Type()
	{
		return () -> null;
	}
				
	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Wide}.
	 *
	 * @author Waffles
	 * @since 22 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see MatrixOps
	 */
	public static class Qualify extends MatrixOps.Qualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Wide
		 */
		public Qualify(Wide o1, double err)
		{
			super(o1, err);
		}

		
		@Override
		public Boolean result()
		{
			if(super.result())
			{
				int c1 = Matrix().Columns();
				int r1 = Matrix().Rows();
				return r1 <= c1;
			}

			return false;
		}
		
		@Override
		public int cost()
		{
			return 1;
		}
	}
	
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Wide;
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default Wide instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
}