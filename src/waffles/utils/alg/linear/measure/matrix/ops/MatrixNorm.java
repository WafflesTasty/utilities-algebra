package waffles.utils.alg.linear.measure.matrix.ops;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Doubles;

/**
 * A {@code MatrixNorm} operation computes the 1-norm of a {@code Matrix}.
 * This norm equals the largest sum of absolute values in each column.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 */
public class MatrixNorm implements Operation<Float>
{
	private Matrix m1;
	
	/**
	 * Creates a new {@code MatrixNorm}.
	 * 
	 * @param m1  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixNorm(Matrix m1)
	{
		this.m1 = m1;
	}
	

	@Override
	public Float result()
	{
		int r1 = m1.Rows();
		int c1 = m1.Columns();
		
		
		double norm = 0d;
		for(int c = 0; c < c1; c++)
		{
			double nc = 0d;
			for(int r = 0; r < r1; r++)
			{
				float v1 = m1.get(r, c);
				nc += Doubles.abs(v1);
			}
			
			if(norm < nc)
			{
				norm = nc;
			}
		}
		
		return (float) norm;
	}
	
	@Override
	public int cost()
	{
		int r1 = m1.Rows();
		int c1 = m1.Columns();
		return c1 * (r1 - 1);
	}
}