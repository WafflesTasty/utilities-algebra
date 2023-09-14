package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * The {@code LinearSpace} interface defines a solver for matrix subspaces.
 * In addition to a null space it also defines complementary row spaces.
 * 
 * @author Waffles
 * @since Apr 7, 2019
 * @version 1.1
 * 
 * 
 * @see NullSpace
 */
public interface LinearSpace extends NullSpace
{	
	/**
	 * Returns the row space of the {@code LinearSpace}.
	 * 
	 * @return  a row space
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix RowSpace();

	/**
	 * Returns the row complement of the {@code LinearSpace}.
	 * 
	 * @return  a row space complement
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix RowComplement();
}