package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.algorithms.Spectral;
import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Vector;

/**
 * The {@code FCTSingular} interface defines an algorithm that performs SVD factorization.
 * Every matrix can be decomposed as {@code M = UEV*} where U is a matrix with orthogonal
 * columns, E a diagonal matrix of singular values, and V an orthogonal matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Spectral
 */
public interface FCTSingular extends Spectral
{
	/**
	 * Returns the diagonal matrix E from the {@code FCTSingular}.
	 * 
	 * @return  the diagonal matrix E
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix E();
	
	/**
	 * Returns the reduced orthogonal matrix U from the {@code FCTSingular}.
	 * 
	 * @return  the orthogonal matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns the orthogonal matrix V from the {@code FCTSingular}.
	 * 
	 * @return  the orthogonal matrix V
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix V();

	
	@Override
	public default Vector SingularValues()
	{
		return E().Diagonal();
	}
}