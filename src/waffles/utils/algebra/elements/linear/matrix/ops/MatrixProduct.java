package waffles.utils.algebra.elements.linear.matrix.ops;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code MatrixProduct} computes the product of two matrices the naive way.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class MatrixProduct implements Operation<Matrix>
{
	private Matrix m1, m2;
	
	/**
	 * Creates a new {@code MatrixProduct}.
	 * 
	 * @param m1  a  first matrix
	 * @param m2  a second matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixProduct(Matrix m1, Matrix m2)
	{
		this.m1 = m1;
		this.m2 = m2;
	}
	

	@Override
	public Matrix result()
	{
		int row1 = m1.Rows();		
		int col1 = m1.Columns();
		int col2 = m2.Columns();

		
		Matrix result = Matrices.create(row1, col2);
		for(int r = 0; r < row1; r++)
		{
			for(int c = 0; c < col2; c++)
			{
				double val = 0;
				for(int d = 0; d < col1; d++)
				{
					double v1 = m1.get(r, d);
					double v2 = m2.get(d, c);
					val += v1 * v2;
				}
				
				result.set((float) val, r, c);
			}
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		int r1 = m1.Rows();
		int r2 = m2.Rows();
		
		int c1 = m1.Columns();
		int c2 = m2.Columns();
	
		if(c1 != r2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		return r1 * c2 * (2 * c1 - 1);
	}
}