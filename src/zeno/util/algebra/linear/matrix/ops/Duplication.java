package zeno.util.algebra.linear.matrix.ops;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.linear.matrix.Operation;

/**
 * The {@code Duplication} class defines a standard matrix copier.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Operation
 * @see Matrix
 */
public class Duplication implements Operation<Matrix>
{
	private Matrix m;
	
	/**
	 * Creates a new {@code Duplication}.
	 * 
	 * @param m  a matrix to copy
	 * @see Matrix
	 */
	public Duplication(Matrix m)
	{
		this.m = m;
	}
	
	
	@Override
	public Matrix result()
	{
		return m.copy();
	}
	
	@Override
	public int cost()
	{
		return 0;
	}
}