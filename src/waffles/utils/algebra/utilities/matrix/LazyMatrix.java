package waffles.utils.algebra.utilities.matrix;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.tools.patterns.properties.LazyValue;
import waffles.utils.tools.patterns.semantics.Computable;

/**
 * A {@code LazyMatrix} keeps track of a matrix, only updating when needed.
 *
 * @author Waffles
 * @since 11 Sep 2023
 * @version 1.0
 * 
 * 
 * @see LazyValue
 * @see Matrix
 */
public class LazyMatrix extends LazyValue<Integer, Matrix>
{
	/**
	 * Creates a new {@code LazyMatrix}.
	 * 
	 * @param c  a computable
	 * 
	 * 
	 * @see Computable
	 * @see Matrix
	 */
	public LazyMatrix(Computable<Integer, Matrix> c)
	{
		super(c);
	}
	
	/**
	 * Creates a new {@code LazyMatrix}.
	 */
	public LazyMatrix()
	{
		// NOT APPLICABLE
	}
}