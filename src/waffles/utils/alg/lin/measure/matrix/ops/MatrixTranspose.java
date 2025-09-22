package waffles.utils.alg.lin.measure.matrix.ops;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code MatrixTranspose} computes a matrix transpose the naive way.
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
	private Matrix m1;
	
	/**
	 * Creates a new {@code MatrixTranspose}.
	 * 
	 * @param m1  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixTranspose(Matrix m1)
	{
		this.m1 = m1;
	}
	

	@Override
	public Matrix result()
	{
		int r1 = m1.Rows();
		int c1 = m1.Columns();
		
		Matrix m2 = Matrices.create(c1, r1);
		for(int c = 0; c < c1; c++)
		{
			for(int r = 0; r < r1; r++)
			{
				float val = m1.get(r, c);
				m2.set(val, c, r);
			}
		}

		return m2;
	}
	
	@Override
	public int cost()
	{
		int r = m1.Rows();
		int c = m1.Columns();

		return r * c;
	}
}