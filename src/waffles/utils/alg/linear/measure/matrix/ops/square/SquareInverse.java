package waffles.utils.alg.linear.measure.matrix.ops.square;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.algebra.algorithms.solvers.SLVCrout;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code SquareInverse} operation computes a matrix inverse.
 * By default, Crout's method for linear problems is used.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class SquareInverse implements Operation<Matrix>
{
	private Matrix m1;
	
	/**
	 * Creates a new {@code SquareInverse}.
	 * 
	 * @param m1  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SquareInverse(Matrix m1)
	{
		this.m1 = m1;
	}
	

	@Override
	public Matrix result()
	{
		return new SLVCrout(m1).inverse();
	}
	
	@Override
	public int cost()
	{
		int r = m1.Rows();
		int c = m1.Columns();

		return 4 * r * c * r;
	}
}