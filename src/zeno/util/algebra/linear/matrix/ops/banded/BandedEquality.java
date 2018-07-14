package zeno.util.algebra.linear.matrix.ops.banded;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;
import zeno.util.tools.primitives.Floats;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code Addition} class defines the standard matrix equality.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Matrix
 */
public class BandedEquality implements Operation<Boolean>
{
	private Matrix m1, m2;
	private int lband, uband;
	private int iError;
	
	/**
	 * Creates a new {@code Equality}.
	 * 
	 * @param m1  the  first matrix to add
	 * @param m2  the second matrix to add
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @param iError  an error margin
	 * @see Matrix
	 */
	public BandedEquality(Matrix m1, Matrix m2, int lband, int uband, int iError)
	{
		this.iError = iError;
		
		this.lband = lband;
		this.uband = uband;
		
		this.m1 = m1;
		this.m2 = m2;
	}
	

	@Override
	public Boolean result()
	{
		int row1 = m1.Rows();
		int row2 = m2.Rows();
		
		int col1 = m1.Columns();
		int col2 = m2.Columns();
	
		if(row1 != row2 || col1 != col2)
		{
			return null;
		}
		
		
		for(int r = 0; r < row1; r++)
		{
			int cMin = Integers.max(r - lband, 0);
			int cMax = Integers.min(r + uband, col1 - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				float v1 = m1.get(r, c);
				float v2 = m2.get(r, c);
				
				if(!Floats.isEqual(v1, v2, iError))
				{
					return false;
				}
			}
		}
		
		return true;
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