package waffles.utils.alg.linear.measure.matrix.ops.square;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code SquareTrace} operation computes a square matrix trace.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 */
public class SquareTrace implements Operation<Float>
{
	private Matrix m1;
	
	/**
	 * Creates a new {@code SquareTrace}.
	 * 
	 * @param m1  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SquareTrace(Matrix m1)
	{
		this.m1 = m1;
	}
	

	@Override
	public Float result()
	{
		double v1 = 0d;
		for(int r = 0; r < m1.Rows(); r++)
		{
			float val = m1.get(r, r);
			v1 += val;
		}
		
		return (float) v1;
	}
	
	@Override
	public int cost()
	{
		int r1 = m1.Rows();
		int c1 = m1.Columns();
		
		if(r1 != c1)
		{
			return Integers.MAX_VALUE;
		}
		
		return r1;
	}
}