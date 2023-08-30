package waffles.utils.algebra.elements.linear.matrix.ops.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Banded;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code BandedAddition} computes the sum of a matrix with a banded matrix.
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
	private Matrix b, m;
	
	/**
	 * Creates a new {@code BandedAddition}.
	 * 
	 * @param b  a banded matrix
	 * @param m  a matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public BandedAddition(Matrix b, Matrix m)
	{
		this.b = b;
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


		Banded band = (Banded) b.Operator();
		for(int r = 0; r < rows; r++)
		{
			int cMin = Integers.max(r - band.LowerBand(), 0);
			int cMax = Integers.min(r + band.UpperBand(), cols - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				float val = b.get(r, c) + m.get(r, c);
				result.set(val, r, c);
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
		int c2 = b.Columns();
	
		if(r1 != r2 || c1 != c2)
		{
			return Integers.MAX_VALUE;
		}
		
		
		Banded band = (Banded) b.Operator();
		int b1 = c1 - band.LowerBand();
		int b2 = c1 - band.UpperBand();
		
		// Total cost of addition.
		return r1 * c1
		// Minus the skipped lower band.
			 - b1 * (b1 - 1) / 2
		// Minus the skipped upper band.
			 - b2 * (b2 - 1) / 2;
	}
}