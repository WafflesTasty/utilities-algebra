package waffles.utils.alg.linear.measure.matrix;

import waffles.utils.alg.linear.measure.matrix.ops.MatrixProduct;
import waffles.utils.alg.linear.measure.matrix.ops.MatrixTranspose;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.alg.linear.measure.tensor.TensorOps;
import waffles.utils.alg.linear.measure.tensor.ops.TensorQualify;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * The {@code MatrixOps} interface implements {@code Operations} for matrices.
 * Depending on the layout and contents of matrices, most methods have a variety
 * of ways to be implemented, each providing their own benefits and drawbacks.
 * Different subtypes of {@code MatrixOps} can provide indicators to which
 * benefits or drawbacks the underlying matrix may provide.
 * 
 * Be aware that operators only serve as indicators to a matrix's layout. They don't
 * actually have any capability to enforce it. Making sure a matrix's contents corresponds
 * to its operator type is a task left to the developer. Failing to do so may lead to
 * unexpected results when requesting results of various algorithms.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see TensorOps
 */
@FunctionalInterface
public interface MatrixOps extends TensorOps
{	
	/**
	 * Returns the abstract type of the {@code MatrixOps}.
	 * </br> The result of this method can be passed to a
	 * {@code Tensor} object to verify compatibility.
	 * 
	 * @return  a type operator
	 */
	public static MatrixOps Type()
	{
		return () -> null;
	}
	
	/**
	 * A {@code Qualify} operation qualifies a {@code Matrix}.
	 * This operation is used to check if a matrix is allowed
	 * to operate through a {@code MatrixOps} subtype.
	 *
	 * @author Waffles
	 * @since 23 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see TensorQualify
	 */
	public static class Qualify extends TensorQualify
	{
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see MatrixOps
		 */
		public Qualify(MatrixOps o1, float err)
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
		public Matrix Matrix()
		{
			return Operator().Operable();
		}
		
		
		@Override
		public MatrixOps Operator()
		{
			return (MatrixOps) super.Operator();
		}
		
		@Override
		public Boolean result()
		{
			return Matrix().is(MatrixOps.Type());
		}
	}
		
		
	/**
	 * Returns a left multiplication {@code Operation}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a multiply operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new MatrixProduct(m, Operable());
	}
	
	/**
	 * Returns a right multiplication {@code Operation}.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a multiply operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new MatrixProduct(Operable(), m);
	}

	/**
	 * Returns a matrix transpose {@code Operation}.
	 * 
	 * @return  a transpose operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<Matrix> Transpose()
	{
		return new MatrixTranspose(Operable());
	}

		
	@Override
	public default Operation<Boolean> Allows(float e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default MatrixOps instance(Tensor t)
	{
		return () -> (Matrix) t;
	}	
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof MatrixOps;
	}

	@Override
	public abstract Matrix Operable();
}