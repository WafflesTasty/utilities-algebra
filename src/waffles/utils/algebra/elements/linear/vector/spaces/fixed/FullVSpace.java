package waffles.utils.algebra.elements.linear.vector.spaces.fixed;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.spaces.VSpace;

/**
 * A {@code FullVSpace} defines a vector space of full rank.
 *
 * @author Waffles
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
	 * @param dim  a vector space dimension
	 */
	public FullVSpace(int dim)
	{
		this.dim = dim;
	}
	
	
	@Override
	public Matrix ColComplement()
	{
		return Matrices.create(dim, 0);
	}
	
	@Override
	public Matrix RowComplement()
	{
		return Matrices.create(dim, 0);
	}
	
	@Override
	public Matrix ColSpace()
	{
		return Matrices.identity(dim);
	}
	
	@Override
	public Matrix RowSpace()
	{
		return Matrices.identity(dim);
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