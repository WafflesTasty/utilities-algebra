package waffles.utils.algebra.elements.linear.vector.spaces;

import waffles.utils.algebra.algorithms.LinearSpace;
import waffles.utils.algebra.algorithms.rankreveal.RRSVD;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Tall;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.patterns.semantics.Inaccurate;

/**
 * A {@code VSpace} defines a real-valued euclidian vector space.
 * Vector spaces are immutable objects designed to produce various bases,
 * and allow for intersection and containment checks.
 *
 * @author Waffles
 * @since Apr 8, 2019
 * @version 1.0
 * 
 * 
 * @see LinearSpace
 * @see Inaccurate
 */
public class VSpace implements LinearSpace, Inaccurate<VSpace>
{
	private RRSVD rrSVD;
	private Matrix base;
	
	/**
	 * Creates a new {@code VSpace}.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public VSpace(Matrix m)
	{
		if(!Tall.Type().allows(m, 0))
			base = m.transpose();
		else
			base = m;
		if(!base.is(Tall.Type()))
			base.setOperator(Tall.Type());
		
		rrSVD = new RRSVD(base);
	}
	
	/**
	 * Creates a new {@code VSpace}.
	 * 
	 * @param mats  a set of matrices
	 * 
	 * 
	 * @see Matrix
	 */
	public VSpace(Matrix... mats)
	{
		this(Matrices.concat(mats));
	}

	
	/**
	 * Returns an intersection of the {@code VSpace}.
	 * 
	 * @param s  a space to intersect
	 * @return  an intersected space
	 */
	public VSpace intersect(VSpace s)
	{
		Matrix m = add(s).RowComplement();
		m = Matrices.resize(m, Dimension(), m.Columns());
		return new VSpace(ColSpace().times(m));
	}

	/**
	 * Returns vector coordinates in the {@code VSpace}.
	 * These are relative to the given vector basis.
	 * 
	 * @param v  a vector
	 * @return   a vector coordinate
	 * 
	 * 
	 * @see Vector
	 */
	public Vector coordinates(Vector v)
	{
		if(!Tall.Type().allows(base, 0))
			return rrSVD.preApprox(v);
		return rrSVD.approx(v);
	}
	
	/**
	 * Returns a direct sum with the {@code VSpace}.
	 * 
	 * @param s  a space to sum with
	 * @return  a direct sum space
	 */
	public VSpace add(VSpace s)
	{
		return VSpaces.create(ColSpace(), s.ColSpace());
	}
	
	/**
	 * Returns the dimension of the {@code VSpace}.
	 * 
	 * @return  the space dimension
	 */
	public int Dimension()
	{
		return rrSVD.rank();
	}
	
	
	/**
	 * Checks if the {@code VSpace} contains a vector.
	 * 
	 * @param v  a vector to check
	 * @return  {@code true} if the vector is contained
	 * 
	 * 
	 * @see Vector
	 */
	public boolean contains(Vector v)
	{
		return rrSVD.canSolve(v);
	}
	
	/**
	 * Checks if the {@code VSpace} intersects a space.
	 * 
	 * @param s  a space to intersect with
	 * @return  {@code true} if the spaces intersect
	 */
	public boolean intersects(VSpace s)
	{
		return intersect(s).Dimension() > 0; 
	}
	
	/**
	 * Checks if the {@code VSpace} contains a space.
	 * 
	 * @param s  a space to check
	 * @return  {@code true} if the space is contained
	 */
	public boolean contains(VSpace s)
	{
		return Dimension() == add(s).Dimension();
	}


	@Override
	public boolean equals(VSpace s, int ulps)
	{
		return Dimension() == add(s).Dimension()
			&& Dimension() == s.Dimension();
	}
	
	@Override
	public Matrix ColComplement()
	{
		if(!Tall.Type().allows(base, 0))
			return rrSVD.RowComplement();
		return rrSVD.ColComplement();
	}
	
	@Override
	public Matrix RowComplement()
	{
		if(!Tall.Type().allows(base, 0))
			return rrSVD.ColComplement();
		return rrSVD.RowComplement();
	}
	
	@Override
	public Matrix ColSpace()
	{
		if(!Tall.Type().allows(base, 0))
			return rrSVD.RowSpace();
		return rrSVD.ColSpace();
	}
	
	@Override
	public Matrix RowSpace()
	{
		if(!Tall.Type().allows(base, 0))
			return rrSVD.ColSpace();
		return rrSVD.RowSpace();
	}
}