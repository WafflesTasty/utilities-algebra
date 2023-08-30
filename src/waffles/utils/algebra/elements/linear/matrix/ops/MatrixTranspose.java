package waffles.utils.algebra.elements.linear.matrix.ops;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code MatrixTranspose} computes the transpose of a matrix the naive way.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class MatrixTranspose implements Operation<Matrix>
{
	private Matrix m;
	
	/**
	 * Creates a new {@code MatrixTranspose}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixTranspose(Matrix m)
	{
		this.m = m;
	}
	

	@Override
	public Matrix result()
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		Matrix result = Matrices.create(cols, rows);
		for(int c = 0; c < cols; c++)
		{
			for(int r = 0; r < rows; r++)
			{
				float val = m.get(r, c);
				result.set(val, c, r);
			}
		}

		return result;
	}
	
	@Override
	public int cost()
	{
		int r = m.Rows();
		int c = m.Columns();

		return r * c;
	}
}