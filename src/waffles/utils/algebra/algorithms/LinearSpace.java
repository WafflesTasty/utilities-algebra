package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code LinearSpace} interface defines a solver for matrix subspaces.
 * Each matrix defines a row space, column space, and null space for which a basis can be found.
 * The row space and null space are necessarily complementary vector spaces.
 * 
 * @author Waffles
 * @since Apr 7, 2019
 * @version 1.0
 */
public interface LinearSpace
{	
	/**
	 * Returns the row space of the {@code LinearSpace}.
	 * 
	 * @return  a vector row space
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix RowSpace();

	/**
	 * Returns the column space of the {@code LinearSpace}.
	 * 
	 * @return  a vector column space
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix ColSpace();
	
	
	/**
	 * Returns the row complement of the {@code LinearSpace}.
	 * 
	 * @return  a vector row complement
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix RowComplement();

	/**
	 * Returns the column complement of the {@code LinearSpace}.
	 * 
	 * @return  a vector column complement
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix ColComplement();
}