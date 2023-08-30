package waffles.utils.algebra.elements.linear.matrix.ops;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code MatrixIdentity} is an identity operation on matrix objects.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class MatrixIdentity implements Operation<Matrix>
{
	private Matrix m;
	
	/**
	 * Creates a new {@code MatrixTranspose}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixIdentity(Matrix m)
	{
		this.m = m;
	}
	

	@Override
	public Matrix result()
	{
		if(!m.isDestructible())
		{
			return m.copy();
		}
		
		return m;
	}
	
	@Override
	public int cost()
	{
		int r = m.Rows();
		int c = m.Columns();

		if(!m.isDestructible())
			return r * c;
		return 0;
	}
}