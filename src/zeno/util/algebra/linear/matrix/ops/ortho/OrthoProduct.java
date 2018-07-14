package zeno.util.algebra.linear.matrix.ops.ortho;

import zeno.util.algebra.attempt4.linear.mat.Matrix;
import zeno.util.algebra.attempt4.linear.types.Orthogonal;
import zeno.util.algebra.linear.matrix.ops.Product;

/**
 * The {@code OrthoProduct} class defines the orthogonal matrix multiplication.
 *
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @see Product
 */
public class OrthoProduct extends Product
{
	/**
	 * Creates a new {@code OrthoProduct}.
	 * 
	 * @param m1  the  first matrix to multiply
	 * @param m2  the second matrix to multiply
	 * @see Matrix
	 */
	public OrthoProduct(Matrix m1, Matrix m2)
	{
		super(m1, m2);
	}

	@Override
	public Matrix result()
	{
		Matrix result = super.result();
		result.setType(Orthogonal.Type());
		return result;
	}
}