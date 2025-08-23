package waffles.utils.alg.linear.measure.matrix.ops.square.banded;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedLProduct} multiplies a {@code Banded} matrix on the left.
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
	private Matrix b1, m1;
	
	/**
	 * Creates a new {@code BandedLProduct}.
	 * 
	 * @param b1  a banded matrix
	 * @param m1  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedLProduct(Matrix b1, Matrix m1)
	{
		this.b1 = b1;
		this.m1 = m1;
	}
	
	
	@Override
	public Matrix result()
	{
		Banded b = (Banded) b1.Operator();
		
		
		int r1 = b1.Rows();
		int r2 = m1.Rows();
		
		int c1 = b1.Columns();
		int c2 = m1.Columns();

		Matrix m2 = Matrices.create(r1, c2);
		for(int r = 0; r < r1; r++)
		{
			int dMin = Integers.max(r - b.LowerBand(), 0);
			int dMax = Integers.min(r + b.UpperBand(), c1 - 1);
			
			for(int c = 0; c < c2; c++)
			{
				double v = 0;
				for(int d = dMin; d <= dMax; d++)
				{
					double v1 = b1.get(r, d);
					double v2 = m1.get(d, c);
					
					v += v1 * v2;
				}
				
				m2.set((float) v, r, c);
			}
		}
		
		return m2;
	}
	
	@Override
	public int cost()
	{
		Banded b = (Banded) b1.Operator();
		
		
		int r1 = b1.Rows();
		int r2 = m1.Rows();
		
		int c1 = b1.Columns();
		int c2 = m1.Columns();

		int b1 = c1 - b.LowerBand();
		int b2 = c1 - b.UpperBand();
		
		// Total cost of multiplication.
		return c1 * c2 * (2 * c1 - 1)
		// Minus the skipped lower band.
			 - b1 * c2 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * c2 * (b2 - 1);
	}
}