package zeno.util.algebra.linear.vector;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.vector.spaces.FullVSpace;
import zeno.util.algebra.linear.vector.spaces.TrivialVSpace;

/**
 * The {@code VSpaces} class defines basic {@code VSpace} operations.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 */
public final class VSpaces
{
	/**
	 * Defines a full vector space for static access.
	 * 
	 * @param dim  a vector coördinate dimension
	 * @return  a full vector space
	 */
	public static VSpace full(int dim)
	{
		return new FullVSpace(dim);
	}
	
	/**
	 * Defines the trivial vector space for static access.
	 * 
	 * @param dim  a vector coördinate dimension 
	 * @return  a trivial vector space
	 */
	public static VSpace trivial(int dim)
	{
		return new TrivialVSpace(dim);
	}

	/**
	 * Creates a vector space from static access.
	 * 
	 * @param mats  a matrix set to span
	 * @return  a vector space
	 * 
	 * 
	 * @see Matrix
	 * @see VSpace
	 */
	public static VSpace create(Matrix... mats)
	{
		Matrix m = Matrices.concat(mats);
		
		int dim = m.Rows();
		VSpace s = new VSpace(m);
		if(s.Dimension() == 0)
			return trivial(dim);
		if(s.Dimension() == dim)
			return full(dim);
		return s;
	}
	
	
	/**
	 * Defines a vector space that expands into a new coördinate count.
	 * The amount of coördinates change and can increase dimension as well.
	 * 
	 * @param s  a space to expand
	 * @param coords  a coördinate count to use
	 * @return  a new expanded vector space
	 * 
	 * 
	 * @see VSpace
	 */
	public static VSpace expand(VSpace s, int coords)
	{
		Matrix span = s.Span();
		int cols = span.Columns();
		int rows = span.Rows();
		
		if(rows >= coords)
			span = Matrices.resize(span, coords, cols);
		else
		{
			span = Matrices.resize(span, coords, coords + cols - rows);
			for(int i = cols; i < coords + cols - rows; i++)
			{
				span.set(1f, i, i);
			}
		}
				
		return new VSpace(span);
	}
	
	/**
	 * Defines a vector space that occupies a new coördinate count.
	 * The amount of coördinates change but this does not increase dimension.
	 * 
	 * @param s  a space to occupy
	 * @param coords  a coördinate count to use
	 * @return  a new occupying vector space
	 * 
	 * 
	 * @see VSpace
	 */
	public static VSpace occupy(VSpace s, int coords)
	{
		Matrix span = s.Span();
		int cols = span.Columns();
		
		span = Matrices.resize(span, coords, cols);
		return new VSpace(span);
	}
	
	
	private VSpaces()
	{
		// NOT APPLICABLE
	}

}