package waffles.utils.alg.linear.measure.matrix.ops.square.banded;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedRProduct} multiplies a {@code Banded} matrix on the right.
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
	private Matrix b1, m1;
	
	/**
	 * Creates a new {@code BandedRProduct}.
	 * 
	 * @param b1  a banded matrix
	 * @param m1  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedRProduct(Matrix b1, Matrix m1)
	{
		this.b1 = b1;
		this.m1 = m1;
	}
	
	
	@Override
	public Matrix result()
	{
		Banded b = (Banded) b1.Operator();
		
		int r1 = m1.Rows();
		int r2 = b1.Rows();
		
		int c1 = m1.Columns();
		int c2 = b1.Columns();

		Matrix m2 = Matrices.create(r1, c2);
		for(int r = 0; r < r1; r++)
		{			
			for(int c = 0; c < c2; c++)
			{
				int dMin = Integers.max(c - b.UpperBand(), 0);
				int dMax = Integers.min(c + b.LowerBand(), r2 - 1);
				
				
				double v = 0;
				for(int d = dMin; d <= dMax; d++)
				{
					double v1 = m1.get(r, d);
					double v2 = b1.get(d, c);
					
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
		
		
		int r1 = m1.Rows();
		int r2 = b1.Rows();
		
		int c1 = m1.Columns();
		int c2 = b1.Columns();

		int b1 = c1 - b.LowerBand();
		int b2 = c1 - b.UpperBand();
		
		// Total cost of multiplication.
		return r1 * r2 * (2 * r2 - 1)
		// Minus the skipped lower band.
			 - b1 * r1 * (b1 - 1)
		// Minus the skipped upper band.
			 - b2 * r1 * (b2 - 1);
	}
}