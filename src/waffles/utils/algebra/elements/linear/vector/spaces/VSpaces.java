package waffles.utils.algebra.elements.linear.vector.spaces;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.spaces.fixed.FullVSpace;
import waffles.utils.algebra.elements.linear.vector.spaces.fixed.TrivialVSpace;

/**
 * The {@code VSpaces} class defines static-access utilities for {@code VSpace} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 */
public final class VSpaces
{
	/**
	 * Defines the full vector space through static access.
	 * 
	 * @param dim  a vector space dimension
	 * @return     a full ranked vector space
	 * 
	 * 
	 * @see VSpace
	 */
	public static VSpace full(int dim)
	{
		return new FullVSpace(dim);
	}
	
	/**
	 * Defines the trivial vector space through static access.
	 * 
	 * @param dim  a vector space dimension
	 * @return     an empty ranked vector space
	 */
	public static VSpace trivial(int dim)
	{
		return new TrivialVSpace(dim);
	}

	/**
	 * Creates a new vector space through static access.
	 * 
	 * @param mats  a set of matrices
	 * @return      a vector space
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
	 * Defines a vector space that expands into a new dimension.
	 * The coordinate order changes and can increase dimension as well.
	 * 
	 * @param s    a space to expand
	 * @param dim  a vector space dimension
	 * @return  a new expanded vector space
	 * 
	 * 
	 * @see VSpace
	 */
	public static VSpace expand(VSpace s, int dim)
	{
		Matrix span = s.ColSpace();
		int cols = span.Columns();
		int rows = span.Rows();
		
		if(rows >= dim)
			span = Matrices.resize(span, dim, cols);
		else
		{
			span = Matrices.resize(span, dim, dim + cols - rows);
			for(int i = cols; i < dim + cols - rows; i++)
			{
				span.set(1f, i, i);
			}
		}
				
		return new VSpace(span);
	}
	
	/**
	 * Defines a vector space that occupies a new dimension.
	 * The coordinate order changes but the dimension does not.
	 * 
	 * @param s    a space to occupy
	 * @param dim  a vector space dimension
	 * @return  a new occupying vector space
	 * 
	 * 
	 * @see VSpace
	 */
	public static VSpace occupy(VSpace s, int dim)
	{
		Matrix span = s.ColSpace();
		int cols = span.Columns();
		
		span = Matrices.resize(span, dim, cols);
		return new VSpace(span);
	}
	
	
	private VSpaces()
	{
		// NOT APPLICABLE
	}
}