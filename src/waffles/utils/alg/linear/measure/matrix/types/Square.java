package waffles.utils.alg.linear.measure.matrix.types;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.MatrixOps;
import waffles.utils.alg.linear.measure.matrix.ops.square.SquareInverse;
import waffles.utils.alg.linear.measure.matrix.ops.square.SquareTrace;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Wide;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code Square} operator is used for matrices with as many rows as columns.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Tall
 * @see Wide
 */
public interface Square extends Tall, Wide
{
	/**
	 * Returns the abstract {@code Square} type.
	 * 
	 * @return  a type operator
	 */
	public static Square Type()
	{
		return () -> null;
	}

	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Square}.
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
		 * @see Square
		 */
		public Qualify(Square o1, double err)
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
				return r1 == c1;
			}

			return false;
		}
		
		@Override
		public int cost()
		{
			return 1;
		}
	}
	
		
	/**
	 * Returns a matrix inverse {@code Operation}.
	 * 
	 * @return  an inverse operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<Matrix> Inverse()
	{
		return new SquareInverse(Operable());
	}

	/**
	 * Returns a matrix trace {@code Operation}.
	 * 
	 * @return  a trace operation
	 * 
	 * 
	 * @see Operation
	 */
	public default Operation<Float> Trace()
	{
		return new SquareTrace(Operable());
	}
	
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Square;
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}
	
	@Override
 	public default Square instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
}