package waffles.utils.alg.linear.measure.vector;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.MatrixOps;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.ops.TensorQualify;
import waffles.utils.alg.linear.measure.vector.ops.VectorNorm;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * The {@code VectorOps} interface implements {@code Operations} for vectors.
 * This extends {@code MatrixOps} with a few basic vector functionalities.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Tall
 */
public interface VectorOps extends Tall
{
	/**
	 * Returns the abstract type of the {@code VectorOps}.
	 * </br> The result of this method can be passed to a
	 * {@code Tensor} object to verify compatibility.
	 * 
	 * @return  a type operator
	 */
	public static VectorOps Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	/**
	 * A {@code Qualify} operation qualifies a {@code Vector}.
	 * This operation is used to check if a vector is allowed
	 * to operate through a {@code VectorOps} subtype.
	 *
	 * @author Waffles
	 * @since 23 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see TensorQualify
	 */
	public static class Qualify extends MatrixOps.Qualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a vector operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see VectorOps
		 */
		public Qualify(VectorOps o1, float err)
		{
			super(o1, err);
		}
		
		/**
		 * Returns the base {@code Matrix}.
		 * 
		 * @return  a base matrix
		 * 
		 * 
		 * @see Matrix
		 */
		public Vector Vector()
		{
			return Operator().Operable();
		}
		
		
		@Override
		public VectorOps Operator()
		{
			return (VectorOps) super.Operator();
		}
		
		@Override
		public Boolean result()
		{
			return Vector().is(VectorOps.Type());
		}
	}	
	

	/**
	 * Returns a vector 1-norm {@code Operation}.
	 * 
	 * @return  a norm operation
	 * 
	 * 
	 * @see Operation
	 */
	public default Operation<Float> Norm1()
	{
		return new VectorNorm(Operable());
	}
	
	
	@Override
	public default Operation<Boolean> Allows(float e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default VectorOps instance(Tensor t)
	{
		return () -> (Vector) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof VectorOps;
	}
	
	@Override
	public abstract Vector Operable();
}