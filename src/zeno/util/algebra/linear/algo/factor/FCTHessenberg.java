package zeno.util.algebra.linear.algo.factor;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.tools.generic.properties.Updateable;

/**
 * The {@code FCTHessenberg} interface defines an algorithm that performs Hessenberg factorization.
 * Every square matrix can be decomposed as {@code M = Q*HQ} where Q is an orthogonal matrix,
 * and H a Hessenberg matrix. If M is symmetric, then H will be tridiagonal.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * @see Updateable
 */
public interface FCTHessenberg extends Updateable
{
	/**
	 * Returns the Hessenberg matrix H from the {@code FCTHessenberg}.
	 * 
	 * @return  the hessenberg matrix H
	 * @see Matrix
	 */
	public abstract Matrix H();
	
	/**
	 * Returns the orthogonal matrix Q from the {@code FCTHessenberg}.
	 * 
	 * @return  the orthogonal matrix Q
	 * @see Matrix
	 */
	public abstract Matrix Q();
}