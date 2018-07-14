package zeno.util.algebra.linear.matrix.ops;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code DotProduct} class defines the standard dot product.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Float
 */
public class DotProduct implements Operation<Float>
{
	private Matrix m1, m2;
	
	/**
	 * Creates a new {@code DotProduct}.
	 * 
	 * @param m1  the  first matrix to multiply
	 * @param m2  the second matrix to multiply
	 * @see Matrix
	 */
	public DotProduct(Matrix m1, Matrix m2)
	{
		this.m1 = m1;
		this.m2 = m2;
	}
	

	@Override
	public Float result()
	{
		int row1 = m1.Rows();
		int row2 = m2.Rows();
		
		int col1 = m1.Columns();
		int col2 = m2.Columns();
	
		if(row1 != row2 || col1 != col2)
		{
			return null;
		}
		
		
		double dot = 0d;
		for(int r = 0; r < row1; r++)
		{
			for(int c = 0; c < col2; c++)
			{
				double v1 = m1.get(r, c);
				double v2 = m2.get(r, c);
				dot += v1 * v2;
			}
		}
		
		return (float) dot;
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
		
		
		return 2 * r1 * c1 - 1;
	}
}