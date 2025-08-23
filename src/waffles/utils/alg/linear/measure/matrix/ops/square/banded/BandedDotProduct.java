package waffles.utils.alg.linear.measure.matrix.ops.square.banded;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedDotProduct} computes a dot product with a {@code Banded} matrix.
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
	private Matrix b1, m1;
	
	/**
	 * Creates a new {@code BandedDotProduct}.
	 * 
	 * @param b1  a banded matrix
	 * @param m1  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedDotProduct(Matrix b1, Matrix m1)
	{
		this.b1 = b1;
		this.m1 = m1;
	}
	
	
	@Override
	public Float result()
	{
		Banded b = (Banded) b1.Operator();
		
		int c1 = b1.Columns();
		int r1 = b1.Rows();
		
		
		double dot = 0d;
		for(int r = 0; r < r1; r++)
		{
			int cMin = Integers.max(r - b.LowerBand(), 0);
			int cMax = Integers.min(r + b.UpperBand(), c1 - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				double v1 = m1.get(r, c);
				double v2 = b1.get(r, c);
				
				dot += v1 * v2;
			}
		}
		
		return (float) dot;
	}
	
	@Override
	public int cost()
	{
		Banded b = (Banded) b1.Operator();
		
		
		int r1 = b1.Rows();
		int c1 = b1.Columns();

		int b1 = c1 - b.LowerBand();
		int b2 = c1 - b.UpperBand();
		
		// Total cost of multiplication.
		return 2 * r1 * c1 - 1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * (b2 - 1);
	}
}