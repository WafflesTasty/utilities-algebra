package zeno.util.algebra.linear.matrix.types;

import zeno.util.algebra.algorithms.lsquares.LSQHouseHolder;
import zeno.util.algebra.algorithms.solvers.SLVCrout;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.functions.MatrixProduct;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.types.TensorOps;
import zeno.util.tools.patterns.properties.operable.Operation;

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
 * @see TensorOps
 * @see Matrix
 */
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
	public default MatrixOps instance(Tensor m)
	{
		return () ->
		{
			return (Matrix) m;
		};
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
	 * Returns the pseudoinverse of a {@code Matrix}.
	 * 
	 * @return  a pseudoinverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix pseudoinverse()
	{
		return new LSQHouseHolder(Operable()).pseudoinverse();
	}

	/**
	 * Returns the transpose of a {@code Matrix}.
	 * 
	 * @return  a transpose matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix transpose()
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

		return m;
	}
	
	/**
	 * Returns the inverse of a {@code Matrix}.
	 * 
	 * @return  an inverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix inverse()
	{
		return new SLVCrout(Operable()).inverse();
	}
}