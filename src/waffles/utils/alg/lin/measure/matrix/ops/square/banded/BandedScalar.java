package waffles.utils.alg.lin.measure.matrix.ops.square.banded;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.banded.Banded;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedScalar} operation multiplies a {@code Banded} matrix with a scalar.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class BandedScalar implements Operation<Tensor>
{
	private Matrix b1;
	private float s1;
	
	/**
	 * Creates a new {@code BandedScalar}.
	 * 
	 * @param b1  a banded matrix
	 * @param s1  a scalar value
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedScalar(Matrix b1, float s1)
	{
		this.b1 = b1;
		this.s1 = s1;
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
				m2.set(v1 * s1, r, c);
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

		// Total cost of multiplication.
		return r1 * c1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1) / 2
		// Minus the skipped upper band.
			 - b2 * (b2 - 1) / 2;
	}
}