package waffles.utils.alg.linear.measure.matrix.types.shaped;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.MatrixOps;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code Tall} operator is used for matrices with no more columns than rows.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see MatrixOps
 */
public interface Tall extends MatrixOps
{
	/**
	 * Returns the abstract {@code Tall} type.
	 * 
	 * @return  a type operator
	 */
	public static Tall Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Tall}.
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
		 * @see Tall
		 */
		public Qualify(Tall o1, float err)
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
				return c1 <= r1;
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
		return t.Operator() instanceof Tall;
	}
	
	@Override
	public default Operation<Boolean> Allows(float e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default Tall instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
}