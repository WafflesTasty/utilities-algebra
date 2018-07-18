package zeno.util.algebra.linear.matrix.functions.banded;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;
import zeno.util.tools.patterns.properties.operator.Operation;

/**
 * The {@code BandedEquality} class defines the banded matrix equality.
 * The equality is optimized to skip values outside the matrix bands.
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
	private Matrix m, b;
	private int lband, uband;
	private int iError;
	
	/**
	 * Creates a new {@code BandedEquality}.
	 * 
	 * @param m  a matrix to check
	 * @param b  a banded matrix to check
	 * @param lband  a  lower band value
	 * @param uband  an upper band value
	 * @param iError  an error margin
	 * @see Matrix
	 */
	public BandedEquality(Matrix m, Matrix b, int lband, int uband, int iError)
	{
		this.iError = iError;
		
		this.lband = lband;
		this.uband = uband;
		
		this.m = m;
		this.b = b;
	}
	

	@Override
	public Boolean result()
	{
		int row1 = m.Rows();
		int row2 = b.Rows();
		
		int col1 = m.Columns();
		int col2 = b.Columns();
	
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
				float v1 = m.get(r, c);
				float v2 = b.get(r, c);
				
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
		
		// Total cost of multiplication.
		return 2 * r1 * c1 - 1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * (b2 - 1);
	}
}