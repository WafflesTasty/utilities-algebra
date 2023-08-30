package waffles.utils.algebra.elements.linear.vector.spaces.fixed;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.spaces.VSpace;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code TrivialVSpace} defines a vector space of empty rank.
 *
 * @author Waffles
 * @since Apr 9, 2019
 * @version 1.0
 * 
 * 
 * @see VSpace
 */
public class TrivialVSpace extends VSpace
{
	private int dim;
	
	/**
	 * Creates a new {@code TrivialVSpace}.
	 * 
	 * @param dim  a space dimension
	 */
	public TrivialVSpace(int dim)
	{
		super(Matrices.create(dim, 0));
		this.dim = dim;
	}
	
	
	@Override
	public Matrix ColComplement()
	{
		return Matrices.identity(dim);
	}
	
	@Override
	public Matrix RowComplement()
	{
		return Matrices.identity(dim);
	}
	
	@Override
	public Matrix ColSpace()
	{
		return Matrices.create(dim, 0);
	}
	
	@Override
	public Matrix RowSpace()
	{
		return Matrices.create(dim, 0);
	}
	
	
	@Override
	public VSpace intersect(VSpace s)
	{
		return this;
	}
	
	@Override
	public VSpace add(VSpace s)
	{
		return s;
	}
	
	@Override
	public int Dimension()
	{
		return 0;
	}
	
	
	@Override
	public boolean contains(Vector v)
	{
		return Floats.isZero(v.norm(), 3 * dim);
	}
	
	@Override
	public boolean intersects(VSpace s)
	{
		return false;
	}
	
	@Override
	public boolean contains(VSpace s)
	{
		return s.Dimension() == 0;
	}
}