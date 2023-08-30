package waffles.utils.algebra.elements.linear.matrix;

import waffles.utils.algebra.elements.linear.matrix.ops.MatrixProduct;
import waffles.utils.algebra.elements.linear.matrix.ops.MatrixTranspose;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.TensorOps;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.patterns.operator.Operator;

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
 * @see Operator
 * @see Tensor
 */
@FunctionalInterface
public interface MatrixOps extends TensorOps
{	
	/**
	 * Returns the abstract type of the {@code MatrixOps}.
	 * 
	 * @return  a type operator
	 */
	public static MatrixOps Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public abstract Matrix Operable();
	
	@Override
	public default MatrixOps instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}	
	
	@Override
	public default boolean allows(Tensor obj, int ulps)
	{
		if(!matches(obj))
		{
			return obj.Order() == 2;
		}
		
		return true;
	}
	
	@Override
	public default boolean matches(Tensor obj)
	{
		return obj.Operator() instanceof MatrixOps;
	}
	
	
	/**
	 * Returns a left multiplication {@code Operation}.
	 * 
	 * @param m  a matrix to left multiply
	 * @return  a multiplication operation
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
	 * @param m  a matrix to right multiply
	 * @return  a multiplication operation
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
}