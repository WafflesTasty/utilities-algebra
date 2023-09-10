package waffles.utils.algebra.elements.linear;

import waffles.utils.algebra.Function;
import waffles.utils.algebra.elements.linear.Affine.Factory;
import waffles.utils.algebra.elements.linear.matrix.Matrix;

/**
 * A {@code LinearMap} defines a function expressed through a matrix multiplication.
 * The corresponding matrix is dimension-dependent, with the intent to allow
 * the map to handle input/output of a variable dimension as well.
 *
 * @author Waffles
 * @since Feb 10, 2019
 * @version 1.1
 * 
 * 
 * @param <X>  a map source type
 * @param <Y>  a map target type
 * 
 * 
 * @see Function
 * @see Affine
 */
public interface LinearMap<X extends Affine, Y extends Affine> extends Function<X, Y>
{
	/**
	 * Returns the inverse matrix of the {@code LinearMap2}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  a square matrix inverse
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Inverse(int dim);
	
	/**
	 * Returns the matrix of the {@code LinearMap2}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  a square matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Matrix(int dim);


	@Override
	public default X unmap(Y val)
	{
		Matrix span = val.Span();
		Factory fct = val.Factory();
		Matrix map = Inverse(span.Rows());
		return (X) fct.create(map.times(span));
	}
	
	@Override
	public default Y map(X val)
	{
		Matrix span = val.Span();
		Factory fct = val.Factory();
		Matrix map = Matrix(span.Rows());
		return (Y) fct.create(map.times(span));
	}
}