package waffles.utils.alg.lin.measure.matrix.ops.square.banded;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.banded.Banded;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedAddition} computes a sum with a {@code Banded} matrix.
 * The operation is optimized to skip zeroes outside the matrix bands.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class BandedAddition implements Operation<Tensor>
{
	private Matrix b1, m1;
	
	/**
	 * Creates a new {@code BandedAddition}.
	 * 
	 * @param b1  a banded matrix
	 * @param m1  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedAddition(Matrix b1, Matrix m1)
	{
		this.b1 = b1;
		this.m1 = m1;
	}
	

	@Override
	public Matrix result()
	{
		Banded b = (Banded) b1.Operator();
		
		
		int r1 = b1.Rows();
		int c1 = b1.Columns();
		
		Matrix m2 = b1.copy();
		for(int r = 0; r < r1; r++)
		{
			int cMin = Integers.max(r - b.LowerBand(), 0);
			int cMax = Integers.min(r + b.UpperBand(), c1 - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				float v1 = b1.get(r, c);
				float v2 = m1.get(r, c);
				
				m2.set(v1 + v2, r, c);
			}
		}
		
		return m2;
	}
	
	@Override
	public int cost()
	{
		Banded b = (Banded) b1.Operator();
		
		
		int r1 = b1.Rows();
		int c1 = b1.Columns();

		int b1 = c1 - b.LowerBand();
		int b2 = c1 - b.UpperBand();
		
		// Total cost of addition.
		return r1 * c1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1) / 2
		// Minus the skipped upper band.
			 - b2 * (b2 - 1) / 2;
	}
}