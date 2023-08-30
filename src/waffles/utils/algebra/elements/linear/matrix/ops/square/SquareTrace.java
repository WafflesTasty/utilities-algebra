package waffles.utils.algebra.elements.linear.matrix.ops.square;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code SquareTrace} operation computes the trace of a square matrix.
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
	private Matrix m;
	
	/**
	 * Creates a new {@code SquareTrace}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SquareTrace(Matrix m)
	{
		this.m = m;
	}
	

	@Override
	public Float result()
	{
		double result = 0d;
		for(int i = 0; i < m.Rows(); i++)
		{
			float val = m.get(i, i);
			result += val;
		}
		
		return (float) result;
	}
	
	@Override
	public int cost()
	{
		return m.Rows();
	}
}