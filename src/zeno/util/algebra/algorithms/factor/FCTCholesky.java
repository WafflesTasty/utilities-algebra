package zeno.util.algebra.algorithms.factor;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;

/**
 * The {@code FCTCholesky} interface defines an algorithm that performs Cholesky factorization.
 * Every symmetric matrix can be decomposed as {@code M = U*U} where U is an upper triangular
 * matrix. This also equals a triangular factorization {@code PMQ = LU} where L = U*
 * and P, Q are identity matrices.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see FCTTriangular
 */
public interface FCTCholesky extends FCTTriangular
{	
	@Override
	public default Matrix P()
	{
		return Matrices.identity(U().Columns());
	}
	
	@Override
	public default Matrix Q()
	{
		return Matrices.identity(U().Rows());
	}
	
	@Override
	public default Matrix L()
	{
		return U().transpose();
	}
}