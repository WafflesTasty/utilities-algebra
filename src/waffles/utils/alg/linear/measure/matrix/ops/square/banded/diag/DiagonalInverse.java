package waffles.utils.alg.linear.measure.matrix.ops.square.banded.diag;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code DiagonalInverse} operation computes an inverse {@code Diagonal} matrix.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class DiagonalInverse implements Operation<Matrix>
{
	private Matrix d1;
	
	/**
	 * Creates a new {@code DiagonalInverse}.
	 * 
	 * @param d1  a diagonal matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public DiagonalInverse(Matrix d1)
	{
		this.d1 = d1;
	}
	

	@Override
	public Matrix result()
	{
		int r1 = d1.Rows();
		int c1 = d1.Columns();

		Matrix m2 = d1.instance();
		for(int r = 0; r < r1; r++)
		{
			if(Floats.isZero(d1.get(r, r), 3))
			{
				throw new Matrices.InvertibleError(d1);
			}
		
			float v1 = d1.get(r, r);
			m2.set(1f / v1, r, r);
		}
		
		return m2;
	}
	
	@Override
	public int cost()
	{
		int r1 = d1.Rows();
		int c1 = d1.Columns();
		
		return Integers.min(r1, c1);
	}
}