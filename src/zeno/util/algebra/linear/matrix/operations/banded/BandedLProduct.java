package zeno.util.algebra.linear.matrix.operations.banded;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code BandedLProduct} class defines the banded matrix multiplication on the left.
 * The multiplication is optimized to skip zero values in the left matrix.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @param <M>  the type of the result
 * @see Operation
 * @see Matrix
 */
public class BandedLProduct<M extends Matrix> implements Operation<M>
{
	private Matrix b, m;
	private int lband, uband;
	
	/**
	 * Creates a new {@code BandedLProduct}.
	 * 
	 * @param b  a banded matrix to multiply
	 * @param m  a matrix to multiply
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @see Matrix
	 */
	public BandedLProduct(Matrix b, Matrix m, int lband, int uband)
	{
		this.lband = lband;
		this.uband = uband;
		
		this.b = b;
		this.m = m;
	}
	
	
	@Override
	public M result()
	{
		int row1 = b.Rows();
		int row2 = m.Rows();
		
		int col1 = b.Columns();
		int col2 = m.Columns();
			
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
					double v1 = b.get(r, d);
					double v2 = m.get(d, c);
					val += v1 * v2;
				}
				
				result.set((float) val, r, c);
			}
		}
		
		return (M) result;
	}
	
	@Override
	public int cost()
	{
		int r2 = m.Rows();
		
		int c1 = b.Columns();
		int c2 = m.Columns();
			
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