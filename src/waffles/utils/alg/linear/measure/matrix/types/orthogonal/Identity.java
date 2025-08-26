package waffles.utils.alg.linear.measure.matrix.types.orthogonal;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.ops.square.SquareTrace;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.alg.linear.measure.matrix.types.banded.Diagonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Involutory;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.ops.TensorCopy;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A matrix tagged with the {@code Identity} operator is an identity matrix.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Involutory
 * @see Orthogonal
 * @see Diagonal
 */
public interface Identity extends Orthogonal, Diagonal, Involutory
{
	/**
	 * Returns the abstract {@code Identity} type.
	 * 
	 * @return  a type operator
	 */
	public static Identity Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Identity}.
	 *
	 * @author Waffles
	 * @since 22 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Banded
	 */
	public static class Qualify extends Banded.Qualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Identity
		 */
		public Qualify(Identity o1, double err)
		{
			super(o1, err);
		}

		
		@Override
		public Boolean result()
		{
			if(super.result())
			{
				double err = Error();
				Matrix m2 = Matrix();
				int c2 = m2.Columns();
				int r2 = m2.Rows();
				
				for(int r = 0; r < r2; r++)
				{
					float v1 = m2.get(r, r);
					if(err < Floats.abs(v1 - 1f))
					{
						return false;
					}
				}
				
				return true;
			}
			
			return false;
		}
		
		@Override
		public int cost()
		{
			return super.cost() + Matrix().Rows();
		}
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return new TensorCopy<>(Operable());
	}
	
	@Override
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new TensorCopy<>(m);
	}
	
	@Override
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new TensorCopy<>(m);
	}

	@Override
	public default Operation<Float> DotProduct(Tensor t)
	{
		return new SquareTrace((Matrix) t);
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}

		
	@Override
	public default Identity instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Identity;
	}
}