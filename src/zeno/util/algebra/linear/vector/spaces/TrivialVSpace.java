package zeno.util.algebra.linear.vector.spaces;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.vector.VSpace;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.Floats;

/**
 * The {@code TrivialVSpace} class defines a trivial vector space.
 * Its dimension equals zero, containing only the zero vector.
 *
 * @author Zeno
 * @since Apr 9, 2019
 * @version 1.0
 * 
 * 
 * @see VSpace
 */
public class TrivialVSpace extends VSpace
{
	private static final int ULPS = 3;
	
	
	private int dim;
	
	/**
	 * Creates a new {@code TrivialVSpace}.
	 * 
	 * @param dim  a space dimension
	 */
	public TrivialVSpace(int dim)
	{
		this.dim = dim;
	}
	
	
	@Override
	public Matrix Span()
	{
		return Matrices.create(dim, 0);
	}
	
	@Override
	public Matrix Complement()
	{
		return Matrices.identity(dim);
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
		return Floats.isZero(v.norm(), ULPS * dim);
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