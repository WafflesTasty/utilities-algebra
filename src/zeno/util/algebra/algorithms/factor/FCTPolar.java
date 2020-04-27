package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.algorithms.Orthogonalize;
import zeno.util.algebra.linear.matrix.Matrix;

/**
 * The {@code FCTPolar} interface defines an algorithm that performs polar factorization.
 * Every matrix can be decomposed as {@code M = QP} where Q is an orthogonal matrix
 * and P is a positive-semidefinite Hermitian matrix.
 *
 * @author Zeno
 * @since 27 Apr 2020
 * @version 1.0
 * 
 * 
 * @see Orthogonalize
 */
public interface FCTPolar extends Orthogonalize
{
	/**
	 * Returns the orthogonal matrix Q from the {@code FCTPolar}.
	 * 
	 * @return  the orthogonal matrix Q
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Q();
	
	/**
	 * Returns the Hermitian matrix P from the {@code FCTPolar}.
	 * 
	 * @return  the hermitian matrix P
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix P();
	
	
	@Override
	public default Matrix NearestOrthogonal()
	{
		return Q();
	}
}