package zeno.util.algebra.linear.matrix.functions.banded;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code BandedLProduct} class defines the banded matrix multiplication on the right.
 * The multiplication is optimized to skip zero values in the right matrix.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class BandedRProduct implements Operation<Matrix>
{
	private Matrix m, b;
	private int lband, uband;
	
	/**
	 * Creates a new {@code BandedRProduct}.
	 * 
	 * @param m  a matrix to multiply
	 * @param b  a banded matrix to multiply
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @see Matrix
	 */
	public BandedRProduct(Matrix m, Matrix b, int lband, int uband)
	{
		this.lband = lband;
		this.uband = uband;
		
		this.m = m;
		this.b = b;
	}
	
	
	@Override
	public Matrix result()
	{
		int row1 = m.Rows();
		int row2 = b.Rows();
		
		int col1 = m.Columns();
		int col2 = b.Columns();
			
		if(col1 != row2)
		{
			return null;
		}
		
		
		Matrix result = Matrices.create(row1, col2);
		for(int r = 0; r < row1; r++)
		{			
			for(int c = 0; c < col2; c++)
			{
				int dMin = Integers.max(c - uband, 0);
				int dMax = Integers.min(c + lband, row2 - 1);
				
				double val = 0;
				for(int d = dMin; d <= dMax; d++)
				{
					double v1 = m.get(r, d);
					double v2 = b.get(d, c);
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
		int r1 = m.Rows();
		int r2 = b.Rows();
		
		int c1 = m.Columns();
			
		if(c1 != r2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		int b1 = r2 - lband;
		int b2 = r2 - uband;
		
		// Total cost of multiplication.
		return r1 * r2 * (2 * r2 - 1)
		// Minus the skipped lower band.
			 - b1 * r1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * r1 * (b2 - 1);
	}
}