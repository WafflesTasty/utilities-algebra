package waffles.utils.algebra.elements.linear.matrix.ops.diagonal;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code DiagonalInverse} is an inverse operation on diagonal matrices.
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
	private Matrix m;
	
	/**
	 * Creates a new {@code DiagonalInverse}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public DiagonalInverse(Matrix m)
	{
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
		
		
		for(int i = 0; i < rows; i++)
		{
			if(Floats.isZero(m.get(i, i), 3))
			{
				throw new Matrices.InvertibleError(m);
			}
		
			float val = 1f / m.get(i, i);
			result.set(val, i, i);
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		return m.Rows();
	}
}