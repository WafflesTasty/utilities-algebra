package zeno.util.algebra.algorithms;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.manipulators.Adaptable;

/**
 * The {@code LinearSpace} interface defines a solver for matrix subspaces.
 * Each matrix defines a row space, column space, and null space for which a basis can be found.
 * The row space and null space are necessarily eachother's complement.
 * 
 * @author Zeno
 * @since Apr 7, 2019
 * @version 1.0
 * 
 * 
 * @see Adaptable
 */
public interface LinearSpace extends Adaptable
{	
	/**
	 * Returns a row space of the {@code LinearSpace}.
	 * The matrix columns form a basis for the row space.
	 * 
	 * @return  a row space matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix RowSpace();

	/**
	 * Returns a column space of the {@code LinearSpace}.
	 * The matrix columns form a basis for the column space.
	 * 
	 * @return  a column space matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix ColumnSpace();
	
	/**
	 * Returns a null transpose of the {@code LinearSpace}.
	 * The matrix columns form a basis for the null transpose.
	 * 
	 * @return  a transpose null space
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix NullTranspose();
	
	/**
	 * Returns a null space of the {@code LinearSpace}.
	 * The matrix columns form a basis for the null space.
	 * 
	 * @return  a null space matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix NullSpace();
}