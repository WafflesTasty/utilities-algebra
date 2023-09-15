package waffles.utils.algebra.elements.linear.matrix.ops;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code MatrixAbs} operation computes the absolute value of a {@code Matrix}.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Matrix
 */
public class MatrixAbs implements Operation<Matrix>
{
	private Matrix m;
	
	/**
	 * Creates a new {@code MatrixAbs}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public MatrixAbs(Matrix m)
	{
		this.m = m;
	}
	

	@Override
	public Matrix result()
	{
		int rows = m.Rows();
		int cols = m.Columns();


		Matrix n = m;
		if(!n.isDestructible())
		{
			n = Matrices.create(rows, cols);
		}
		
		
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				float val = Floats.abs(m.get(r, c));
				n.set(val, r, c);
			}
		}

		return n;
	}
	
	@Override
	public int cost()
	{
		int c = m.Data().NZCount();
		
		if(m.isDestructible())
			return c;
		return 2 * c;
	}
}