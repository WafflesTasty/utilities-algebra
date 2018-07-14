package zeno.util.algebra.linear.matrix.ops.banded;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code BandedDotProduct} class defines a dot product with a banded matrix.
 * The dot product is optimized to skip zero values in a matrix.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Float
 */
public class BandedDotProduct implements Operation<Float>
{
	private Matrix m1, m2;
	private int lband, uband;
	
	/**
	 * Creates a new {@code BandedDotProduct}.
	 * 
	 * @param m1  the  first matrix to multiply
	 * @param m2  the second matrix to multiply
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @see Matrix
	 */
	public BandedDotProduct(Matrix m1, Matrix m2, int lband, int uband)
	{
		this.lband = lband;
		this.uband = uband;
		
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
			int cMin = Integers.max(r - lband, 0);
			int cMax = Integers.min(r + uband, col1 - 1);
			
			for(int c = cMin; c <= cMax; c++)
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
		
		
		int b1 = c1 - lband;
		int b2 = c1 - uband;
		
		// Total cost of multiplication.
		return 2 * r1 * c1 - 1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * (b2 - 1);
	}
}