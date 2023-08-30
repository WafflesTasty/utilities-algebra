package waffles.utils.algebra.elements.linear.matrix.ops.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedLProduct} multiplies a banded matrix on the left.
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
public class BandedLProduct implements Operation<Matrix>
{
	private Matrix b, m;
	
	/**
	 * Creates a new {@code BandedLProduct}.
	 * 
	 * @param b  a banded matrix
	 * @param m  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedLProduct(Matrix b, Matrix m)
	{
		this.b = b;
		this.m = m;
	}
	
	
	@Override
	public Matrix result()
	{
		int row1 = b.Rows();
		int col1 = b.Columns();
		int col2 = m.Columns();


		Banded band = (Banded) b.Operator();
		Matrix result = Matrices.create(row1, col2);
		for(int r = 0; r < row1; r++)
		{
			int dMin = Integers.max(r - band.LowerBand(), 0);
			int dMax = Integers.min(r + band.UpperBand(), col1-1);
			
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
		
		return result;
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
		
		
		Banded band = (Banded) b.Operator();
		int b1 = c1 - band.LowerBand();
		int b2 = c1 - band.UpperBand();
		
		// Total cost of multiplication.
		return c1 * c2 * (2 * c1 - 1)
		// Minus the skipped lower band.
			 - b1 * c2 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * c2 * (b2 - 1);
	}
}