package zeno.util.algebra.linear.vector;

import zeno.util.algebra.algorithms.rankreveal.RRSVD;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.properties.Inaccurate;

/**
 * The {@code VSpace} class defines a standard euclidian vector space.
 * Vector spaces are immutable objects designed to produce various bases,
 * and allow for intersection and containment checks.
 *
 * @author Zeno
 * @since Apr 8, 2019
 * @version 1.0
 * 
 * 
 * @see Inaccurate
 */
public class VSpace implements Inaccurate<VSpace>
{
	private static final int ULPS = 3;
	
	
	private RRSVD svd;
	
	/**
	 * Creates a new {@code VSpace}.
	 * 
	 * @param m  a target matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public VSpace(Matrix m)
	{
		svd = new RRSVD(m, ULPS);
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
	 * Creates a new {@code VSpace}.
	 * 
	 * @param vecs  a set of vectors
	 * 
	 * 
	 * @see Vector
	 */
	public VSpace(Vector... vecs)
	{
		this(Matrices.fromCols(vecs));
	}
		
	
	/**
	 * Returns a span for the {@code VSpace}.
	 * Making use of SVD, the span vectors are orthonormal.
	 * 
	 * @return  a vector span
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix Span()
	{
		return svd.ColumnSpace();
	}
	
	/**
	 * Returns a row span for the {@code VSpace}.
	 * Making use of SVD, the span vectors are orthonormal.
	 * 
	 * @return  a row span
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix RowSpan()
	{
		return svd.RowSpace();
	}
	
	/**
	 * Returns a complement for the {@code VSpace}.
	 * Making use of SVD, the span vectors are orthonormal.
	 * 
	 * @return  a vector complement
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix Complement()
	{
		return svd.NullTranspose();
	}
	
	/**
	 * Returns a row complement for the {@code VSpace}.
	 * Making use of SVD, the span vectors are orthonormal.
	 * 
	 * @return  a row vector complement
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix RowComplement()
	{
		return svd.NullSpace();
	}
	
	/**
	 * Returns an intersection of the {@code VSpace}.
	 * 
	 * @param s  a space to intersect with
	 * @return  an intersected space
	 */
	public VSpace intersect(VSpace s)
	{
		Matrix m = add(s).RowComplement();
		m = Matrices.resize(m, Dimension(), m.Columns());
		return new VSpace(Span().times(m));
	}

	/**
	 * Returns vector coördinates in the {@code VSpace}.
	 * These are relative to the space basis.
	 * 
	 * @param v  a vector to coördinate
	 * @return  vector coördinates
	 */
	public Vector coordinates(Vector v)
	{
		return svd.solve(v);
	}
	
	/**
	 * Returns a direct sum with the {@code VSpace}.
	 * 
	 * @param s  a space to sum with
	 * @return  an added space
	 */
	public VSpace add(VSpace s)
	{
		return VSpaces.create(Span(), s.Span());
	}
	
	/**
	 * Returns the dimension of the {@code VSpace}.
	 * 
	 * @return  the space dimension
	 */
	public int Dimension()
	{
		return svd.rank();
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
		return svd.canSolve(v);
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
}