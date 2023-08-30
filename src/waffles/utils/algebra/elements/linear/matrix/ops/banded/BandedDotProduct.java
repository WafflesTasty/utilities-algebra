package waffles.utils.algebra.elements.linear.matrix.ops.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedDotProduct} computes the dot product of a matrix with a banded matrix.
 * The operation is optimized to skip zeroes outside the matrix bands.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Float
 */
public class BandedDotProduct implements Operation<Float>
{
	private Matrix b, m;
	
	/**
	 * Creates a new {@code BandedDotProduct}.
	 * 
	 * @param b  a banded matrix
	 * @param m  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedDotProduct(Matrix b, Matrix m)
	{
		this.b = b;
		this.m = m;
	}
	
	
	@Override
	public Float result()
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		
		double dot = 0d;
		Banded band = (Banded) b.Operator();
		for(int r = 0; r < rows; r++)
		{
			int cMin = Integers.max(r - band.LowerBand(), 0);
			int cMax = Integers.min(r + band.UpperBand(), cols-1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				double v1 = m.get(r, c);
				double v2 = b.get(r, c);
				dot += v1 * v2;
			}
		}
		
		return (float) dot;
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
		
		
		Banded band = (Banded) b.Operator();
		int b1 = c1 - band.LowerBand();
		int b2 = c1 - band.UpperBand();
		
		// Total cost of multiplication.
		return 2 * r1 * c1 - 1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * (b2 - 1);
	}
}