package zeno.util.algebra.linear.vector.spaces;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.vector.VSpace;
import zeno.util.algebra.linear.vector.Vector;

/**
 * The {@code FullVSpace} class defines an entire vector space.
 * Its dimension equals the amount of coördinates being used.
 *
 * @author Zeno
 * @since Apr 9, 2019
 * @version 1.0
 * 
 * 
 * @see VSpace
 */
public class FullVSpace extends VSpace
{
	private int dim;
	
	/**
	 * Creates a new {@code FullVSpace}.
	 * 
	 * @param dim  a space dimension
	 */
	public FullVSpace(int dim)
	{
		this.dim = dim;
	}
	
	
	@Override
	public Matrix Span()
	{
		return Matrices.identity(dim);
	}
	
	@Override
	public Matrix Complement()
	{
		return Matrices.create(dim, 0);
	}
	
	@Override
	public VSpace intersect(VSpace s)
	{
		return s;
	}
	
	@Override
	public VSpace add(VSpace s)
	{
		return this;
	}
	
	@Override
	public int Dimension()
	{
		return dim;
	}
	
	
	@Override
	public boolean contains(Vector v)
	{
		return true;
	}
	
	@Override
	public boolean intersects(VSpace s)
	{
		return true;
	}
	
	@Override
	public boolean contains(VSpace s)
	{
		return true;
	}
}