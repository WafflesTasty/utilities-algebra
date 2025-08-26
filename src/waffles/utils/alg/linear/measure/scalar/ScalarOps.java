package waffles.utils.alg.linear.measure.scalar;

import waffles.utils.alg.linear.measure.matrix.types.banded.Diagonal;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.measure.vector.VectorOps;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * The {@code ScalarOps} interface implements {@code Operations} for scalars.
 * This extends {@code VectorOps} with the {@code Diagonal} type.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see VectorOps
 * @see Diagonal
 */
public interface ScalarOps extends VectorOps, Diagonal
{	
	/**
	 * Returns the abstract type of the {@code ScalarOps}.
	 * </br> The result of this method can be passed to a
	 * {@code Tensor} object to verify compatibility.
	 * 
	 * @return  a type operator
	 */
	public static ScalarOps Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation qualifies a {@code Vector}.
	 * This operation is used to check if a vector is allowed
	 * to operate through a {@code ScalarOps} subtype.
	 *
	 * @author Waffles
	 * @since 23 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see VectorOps
	 */
	public static class Qualify extends VectorOps.Qualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a vector operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see ScalarOps
		 */
		public Qualify(ScalarOps o1, double err)
		{
			super(o1, err);
		}


		@Override
		public ScalarOps Operator()
		{
			return (ScalarOps) super.Operator();
		}
		
		@Override
		public Boolean result()
		{
			return Vector().is(ScalarOps.Type());
		}
	}
	
	
	@Override
	public default ScalarOps instance(Tensor t)
	{
		return () -> (Vector) t;
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}

	@Override
	public default boolean matches(Tensor t)
	{
		return Diagonal.super.matches(t);
	}
}