package zeno.util.algebra.linear.factor;

import zeno.util.algebra.attempt4.linear.mat.Matrices;
import zeno.util.algebra.attempt4.linear.mat.Matrix;

/**
 * The {@code FCTCholesky} interface defines an algorithm that performs Cholesky factorization.
 * Every symmetric matrix can be decomposed as {@code M = U*U} where U is an upper triangular
 * matrix. This also equals a triangular factorization {@code PM = LU} where L = U*
 * and P is the identity matrix.
 *
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0
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
	public default Matrix L()
	{
		return U().transpose();
	}
}