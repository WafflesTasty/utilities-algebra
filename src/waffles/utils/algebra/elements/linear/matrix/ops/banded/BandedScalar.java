package waffles.utils.algebra.elements.linear.matrix.ops.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedScalar} is a scalar operation optimized for banded matrices.
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
	private Matrix m;
	private float mul;
	
	/**
	 * Creates a new {@code BandedScalar}.
	 * 
	 * @param m    a matrix to multiply
	 * @param mul  a scalar multiple
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedScalar(Matrix m, float mul)
	{
		this.mul = mul;
		this.m = m;
	}
	

	@Override
	public Matrix result()
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		Matrix result = m;
		if(!m.isDestructible())
		{
			result = Matrices.create(rows, cols);
		}


		Banded band = (Banded) m.Operator();
		for(int r = 0; r < rows; r++)
		{
			int cMin = Integers.max(r - band.LowerBand(), 0);
			int cMax = Integers.min(r + band.UpperBand(), cols-1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				float val = m.get(r, c) * mul;
				result.set(val, r, c);
			}
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		int r = m.Rows();
		int c = m.Columns();

		return r * c;
	}
}