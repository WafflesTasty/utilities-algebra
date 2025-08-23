package waffles.utils.alg.linear.measure.matrix.types.shaped.square;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.MatrixOps;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.ops.TensorCopy;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * An {@code Involutory} operator is for matrices which equal their inverse.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Involutory extends Square
{
	/**
	 * Returns the abstract {@code Involutory} type.
	 * 
	 * @return  a type operator
	 */
	public static Involutory Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Involutory}.
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
		private static MatrixOps ID = Identity.Type();


		private Operation<Matrix> mp;
		private Operation<Boolean> id;
		
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Involutory
		 */
		public Qualify(Involutory o1, float err)
		{
			super(o1, err);
			
			Matrix m1 = o1.Operable();
			
			MatrixOps o2 = m1.Operator();
			MatrixOps o3 = ID.instance(m1);
			
			mp = o2.LMultiplier(m1);
			id = o3.Allows(err);
		}

		
		@Override
		public Boolean result()
		{
			if(super.result())
			{
				float e = Error();
				Matrix m2 = mp.result();
				MatrixOps o2 = ID.instance(m2);
				
				return o2.Allows(e).result();
			}
			
			return false;
		}
		
		@Override
		public int cost()
		{
			return super.cost() + id.cost() + mp.cost();
		}
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return new TensorCopy<>(Operable());
	}
	
	@Override
	public default Operation<Boolean> Allows(float e)
	{
		return new Qualify(this, e);
	}
			
	@Override
	public default Involutory instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
		
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Involutory;
	}
}