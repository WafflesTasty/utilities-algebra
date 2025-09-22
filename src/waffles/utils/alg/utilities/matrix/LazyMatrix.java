package waffles.utils.alg.utilities.matrix;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.tools.patterns.Computable;
import waffles.utils.tools.patterns.properties.values.LazyValue;

/**
 * A {@code LazyMatrix} keeps track of a matrix, only updating when needed.
 * An integer parameter can be provided, which determines the dimension of the matrix.
 * If no other changes occurred, an update only happens when the dimension differs.
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