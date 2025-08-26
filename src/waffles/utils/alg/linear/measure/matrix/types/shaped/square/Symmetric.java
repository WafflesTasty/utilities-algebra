package waffles.utils.alg.linear.measure.matrix.types.shaped.square;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.ops.TensorCopy;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Symmetric} operator is for matrices which equal their transpose.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Symmetric extends Square
{
	/**
	 * Returns the abstract {@code Symmetric} type.
	 * 
	 * @return  a type operator
	 */
	public static Symmetric Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Symmetric}.
	 *
	 * @author Waffles
	 * @since 22 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Square
	 */
	public static class Qualify extends Square.Qualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Symmetric
		 */
		public Qualify(Symmetric o1, double err)
		{
			super(o1, err);
		}

		
		@Override
		public Boolean result()
		{
			if(super.result())
			{
				double err = Error();
				Matrix m1 = Matrix();
				
				int c1 = m1.Columns();
				int r1 = m1.Rows();
				
				
				for(int r = 0; r < r1; r++)
				{
					for(int c = 0; c < r; c++)
					{
						float v1 = m1.get(r, c);
						float v2 = m1.get(c, r);
						
						if(err < Floats.abs(v1 - v2))
						{
							return false;
						}
					}
				}
				
				return true;
			}
			
			return false;
		}
		
		@Override
		public int cost()
		{
			int r1 = Matrix().Rows();
			int c1 = Matrix().Columns();
			
			return super.cost() + r1 * (c1 - 1) / 2;
		}
	}
	

	@Override
	public default Operation<Matrix> Transpose()
	{
		return new TensorCopy<>(Operable());
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default Symmetric instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Symmetric;
	}
}