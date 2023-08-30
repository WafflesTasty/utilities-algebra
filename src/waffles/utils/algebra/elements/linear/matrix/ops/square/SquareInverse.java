package waffles.utils.algebra.elements.linear.matrix.ops.square;

import waffles.utils.algebra.algorithms.solvers.SLVCrout;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code SquareInverse} computes the inverse of a square matrix using Crout's method.
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
	private Matrix m;
	
	/**
	 * Creates a new {@code SquareInverse}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public SquareInverse(Matrix m)
	{
		this.m = m;
	}
	

	@Override
	public Matrix result()
	{
		return new SLVCrout(m).inverse();
	}
	
	@Override
	public int cost()
	{
		int r = m.Rows();
		int c = m.Columns();

		return r * c;
	}
}