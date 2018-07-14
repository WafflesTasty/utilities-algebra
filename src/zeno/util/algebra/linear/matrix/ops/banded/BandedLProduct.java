package zeno.util.algebra.linear.matrix.ops.banded;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code BandedLProduct} class defines a matrix multiplication with a banded matrix on the left.
 * The multiplication is optimized to skip zero values in the left matrix.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Matrix
 */
public class BandedLProduct implements Operation<Matrix>
{
	private Matrix m1, m2;
	private int lband, uband;
	
	/**
	 * Creates a new {@code BandedLProduct}.
	 * 
	 * @param m1  the  first matrix to multiply
	 * @param m2  the second matrix to multiply
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @see Matrix
	 */
	public BandedLProduct(Matrix m1, Matrix m2, int lband, int uband)
	{
		this.lband = lband;
		this.uband = uband;
		
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
			
		if(col1 != row2)
		{
			return null;
		}
		
		
		Matrix result = Matrices.create(row1, col2);
		for(int r = 0; r < row1; r++)
		{
			int dMin = Integers.max(r - lband, 0);
			int dMax = Integers.min(r + uband, col1 - 1);
			
			for(int c = 0; c < col2; c++)
			{
				double val = 0;
				for(int d = dMin; d <= dMax; d++)
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
		int r2 = m2.Rows();
		
		int c1 = m1.Columns();
		int c2 = m2.Columns();
			
		if(c1 != r2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		int b1 = c1 - lband;
		int b2 = c1 - uband;
		
		// Total cost of multiplication.
		return c1 * c2 * (2 * c1 - 1)
		// Minus the skipped lower band.
			 - b1 * c2 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * c2 * (b2 - 1);
	}
}