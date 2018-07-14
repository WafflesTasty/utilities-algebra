package zeno.util.algebra.linear.matrix.ops;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code Addition} class defines the standard matrix sum.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Matrix
 */
public class Addition implements Operation<Matrix>
{
	private Matrix m1, m2;
	
	/**
	 * Creates a new {@code Addition}.
	 * 
	 * @param m1  the  first matrix to add
	 * @param m2  the second matrix to add
	 * @see Matrix
	 */
	public Addition(Matrix m1, Matrix m2)
	{
		this.m1 = m1;
		this.m2 = m2;
	}
	

	@Override
	public Matrix result()
	{
		int row1 = m1.Rows();
		int row2 = m2.Rows();
		
		int col1 = m1.Columns();
		int col2 = m2.Columns();
	
		if(row1 != row2 || col1 != col2)
		{
			return null;
		}
		
		
		Matrix result = Matrices.create(row1, col1);
		for(int r = 0; r < row1; r++)
		{
			for(int c = 0; c < col2; c++)
			{
				result.set(m1.get(r, c) + m2.get(r, c), r, c);
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
	
		if(r1 != r2 || c1 != c2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		return r1 * c1;
	}
}