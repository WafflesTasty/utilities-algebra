package waffles.utils.algebra.elements.linear.matrix.ops.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedRProduct} multiplies a banded matrix on the right.
 * The operation is optimized to skip zeroes outside the matrix bands.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class BandedRProduct implements Operation<Matrix>
{
	private Matrix b, m;
	
	/**
	 * Creates a new {@code BandedRProduct}.
	 * 
	 * @param b  a banded matrix
	 * @param m  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedRProduct(Matrix b, Matrix m)
	{
		this.b = b;
		this.m = m;
	}
	
	
	@Override
	public Matrix result()
	{
		int row1 = m.Rows();
		int row2 = b.Rows();
		int col2 = b.Columns();

		
		Banded band = (Banded) b.Operator();
		Matrix result = Matrices.create(row1, col2);
		for(int r = 0; r < row1; r++)
		{			
			for(int c = 0; c < col2; c++)
			{
				int dMin = Integers.max(c - band.UpperBand(), 0);
				int dMax = Integers.min(c + band.LowerBand(), row2-1);
				
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
		
		
		Banded band = (Banded) b.Operator();
		int b1 = c1 - band.LowerBand();
		int b2 = c1 - band.UpperBand();
		
		// Total cost of multiplication.
		return r1 * r2 * (2 * r2 - 1)
		// Minus the skipped lower band.
			 - b1 * r1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * r1 * (b2 - 1);
	}
}