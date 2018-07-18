package zeno.util.algebra.linear.matrix.functions.banded;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.Integers;
import zeno.util.tools.patterns.properties.operator.Operation;

/**
 * The {@code BandedAddition} class defines the banded matrix sum.
 * The addition is optimized to skip zeroes outside the matrix bands.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class BandedAddition implements Operation<Tensor>
{
	private Matrix m, b;
	private int lband, uband;
	
	/**
	 * Creates a new {@code BandedAddition}.
	 * 
	 * @param m  a matrix to add
	 * @param b  a banded matrix to add
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @see Matrix
	 */
	public BandedAddition(Matrix m, Matrix b, int lband, int uband)
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
	
		if(row1 != row2 || col1 != col2)
		{
			return null;
		}
		
		
		Matrix result = m.copy();
		for(int r = 0; r < row1; r++)
		{
			int cMin = Integers.max(r - lband, 0);
			int cMax = Integers.min(r + uband, col1 - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				result.set(m.get(r, c) + b.get(r, c), r, c);
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
		int c2 = b.Columns();
	
		if(r1 != r2 || c1 != c2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		int b1 = c1 - lband;
		int b2 = c1 - uband;
		
		// Total cost of addition.
		return r1 * c1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1) / 2
		// Minus the skipped upper band.
			 - b2 * (b2 - 1) / 2;
	}
}