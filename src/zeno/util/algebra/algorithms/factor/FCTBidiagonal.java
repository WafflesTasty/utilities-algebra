package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.properties.Updateable;

/**
 * The {@code FCTBidiagonal} interface defines an algorithm that performs bidiagonal factorization.
 * Every matrix can be decomposed as {@code M = UBV*} where U is a matrix of orthogonal columns,
 * B is an upper bidiagonal matrix, and V is an orthogonal matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Updateable
 */
public interface FCTBidiagonal extends Updateable
{
	/**
	 * Returns the bidiagonal matrix B from the {@code FCTBidiagonal}.
	 * 
	 * @return  the bidiagonal matrix B
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix B();
	
	/**
	 * Returns the reduced orthogonal matrix U from the {@code FCTBidiagonal}.
	 * 
	 * @return  the orthogonal matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns the orthogonal matrix V from the {@code FCTBidiagonal}.
	 * 
	 * @return  the orthogonal matrix V
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix V();
}