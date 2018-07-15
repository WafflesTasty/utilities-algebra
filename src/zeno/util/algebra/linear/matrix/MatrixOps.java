package zeno.util.algebra.linear.matrix;

import zeno.util.algebra.algorithms.lsquares.LSQHouseHolder;
import zeno.util.algebra.algorithms.solvers.SLVCrout;
import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.operations.MatrixProduct;
import zeno.util.algebra.linear.tensor.TensorOps;
import zeno.util.tools.patterns.Operation;

/**
 * The {@code MatrixOps} implements {@code Operations} for matrices.
 * Depending on the layout and contents of matrices, most methods have a variety
 * of ways to be implemented, each providing their own benefits and drawbacks.
 * The {@code MatrixOps} interface serves as a indicator to which benefits
 * or drawbacks the underlying matrix may provide.
 * 
 * Be aware that operators only serve as indicators to a matrix's layout. They don't
 * actually have any capability to enforce it. Making sure a matrix's contents corresponds
 * to its operator type is a task left to the developer. Failing to do so may lead to
 * unexpected results when requesting results of various algorithms.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @param <M>  the type of the underlying matrix
 * @see TensorOps
 * @see Matrix
 */
public interface MatrixOps<M extends Matrix> extends TensorOps<M>
{	
	/**
	 * Returns the abstract type of the {@code MatrixOps}.
	 * 
	 * @return  a type operator
	 */
	public static MatrixOps<?> Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	
	@Override
	public default MatrixOps<M> instance(Tensor m)
	{
		return () ->
		{
			return (M) m;
		};
	}	
	
	/**
	 * Returns a left multiplication {@code Operation}.
	 * 
	 * @param matrix  a matrix to left multiply
	 * @return  a multiplication operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<M> LMultiplier(M matrix)
	{
		return new MatrixProduct<>(matrix, Operable());
	}
	
	/**
	 * Returns a right multiplication {@code Operation}.
	 * 
	 * @param matrix  a matrix to right multiply
	 * @return  a multiplication operation
	 * 
	 * 
	 * @see Operation
	 * @see Matrix
	 */
	public default Operation<M> RMultiplier(M matrix)
	{
		return new MatrixProduct<>(Operable(), matrix);
	}

	
	/**
	 * Returns the pseudoinverse of a {@code Matrix}.
	 * 
	 * @return  a pseudoinverse matrix
	 */
	public default M pseudoinverse()
	{
		return (M) new LSQHouseHolder(Operable()).pseudoinverse();
	}

	/**
	 * Returns the transpose of a {@code Matrix}.
	 * 
	 * @return  a transpose matrix
	 */
	public default M transpose()
	{
		int rows = Operable().Rows();
		int cols = Operable().Columns();
		
		Matrix m = Matrices.create(cols, rows);
		for(int c = 0; c < cols; c++)
		{
			for(int r = 0; r < rows; r++)
			{
				float val = Operable().get(r, c);
				m.set(val, c, r);
			}
		}

		return (M) m;
	}
	
	/**
	 * Returns the inverse of a {@code Matrix}.
	 * 
	 * @return  an inverse matrix
	 */
	public default M inverse()
	{
		return (M) new SLVCrout(Operable()).inverse();
	}
}