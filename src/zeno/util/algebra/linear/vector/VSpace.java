package zeno.util.algebra.linear.vector;

import zeno.util.algebra.algorithms.lsquares.LSQSVD;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.Floats;
import zeno.util.tools.patterns.properties.Copyable;
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
 * @see Copyable
 */
public class VSpace implements Copyable<VSpace>, Inaccurate<VSpace>
{
	/**
	 * Defines a full vector space for static access.
	 * 
	 * @param dim  a vector coördinate dimension
	 * @return  a full vector space
	 */
	public static final VSpace full(int dim)
	{
		return new Full(dim);
	}
	
	/**
	 * Defines the trivial vector space for static access.
	 * 
	 * @param dim  a vector coördinate dimension 
	 * @return  a trivial vector space
	 */
	public static final VSpace trivial(int dim)
	{
		return new Trivial(dim);
	}
		
	
	private static class Trivial extends VSpace
	{
		private int dim;
		
		protected Trivial(int dim)
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
	
	private static class Full extends VSpace
	{
		private int dim;
		
		protected Full(int dim)
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
	

	private static final int ULPS = 3;
	
	
	private LSQSVD svd;
	
	/**
	 * Creates a new {@code VSpace}.
	 */
	protected VSpace()
	{
		// NOT APPLICABLE
	}
	
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
		svd = new LSQSVD(m, ULPS);
		
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
	 * Returns an intersection of the {@code VSpace}.
	 * 
	 * @param s  a space to intersect with
	 * @return  a space intersection
	 */
	public VSpace intersect(VSpace s)
	{
		Matrix m = new VSpace(Span(), s.Span()).RowComplement();
		m = Matrices.resize(m, Dimension(), m.Columns());
		return new VSpace(Span().times(m));
	}

	/**
	 * Returns a direct sum with the {@code VSpace}.
	 * 
	 * @param s  a space to sum with
	 * @return  a space sum
	 */
	public VSpace add(VSpace s)
	{
		return new VSpace(Span(), s.Span());
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
		return Dimension() == add(s).Dimension()
			&& Dimension() >= s.Dimension();
	}


	
	@Override
	public boolean equals(VSpace s, int ulps)
	{
		return Dimension() == add(s).Dimension()
			&& Dimension() == s.Dimension();
	}
	
	private Matrix RowComplement()
	{
		return svd.NullSpace();
	}

		
	@Override
	public VSpace instance()
	{
		return new VSpace();
	}

	@Override
	public VSpace copy()
	{
		VSpace copy = Copyable.super.copy();
		copy.svd = new LSQSVD(Span());
		return copy;
	}
}