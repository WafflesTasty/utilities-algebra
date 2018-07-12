package zeno.util.algebra.linear.algo.factor;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.tools.generic.properties.Updateable;

/**
 * The {@code FCTSingular} interface defines an algorithm that performs SVD factorization.
 * Every matrix can be decomposed as {@code M = UEV*} where U is an orthogonal matrix,
 * E a diagonal matrix of singular values, and V an orthogonal matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * @see Updateable
 */
public interface FCTSingular extends Updateable
{
	/**
	 * Returns the diagonal matrix E from the {@code FCTSingular}.
	 * 
	 * @return  the diagonal matrix E
	 * @see Matrix
	 */
	public abstract Matrix E();
	
	/**
	 * Returns the orthogonal matrix U from the {@code FCTSingular}.
	 * 
	 * @return  the orthogonal matrix U
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns the orthogonal matrix V from the {@code FCTSingular}.
	 * 
	 * @return  the orthogonal matrix V
	 * @see Matrix
	 */
	public abstract Matrix V();
}