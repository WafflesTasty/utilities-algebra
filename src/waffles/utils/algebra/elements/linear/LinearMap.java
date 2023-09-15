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
 * @see Function
 * @see Affine
 */
//public interface LinearMap<X extends Affine, Y extends Affine> extends Function<X, Y>
public interface LinearMap extends Function<Affine, Affine>
{
	/**
	 * Returns the inverse matrix of the {@code LinearMap}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  a square matrix inverse
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Inverse(int dim);
	
	/**
	 * Returns the matrix of the {@code LinearMap}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  a square matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Matrix(int dim);


	@Override
	public default Affine unmap(Affine val)
	{
		Matrix span = val.Span();
		Factory fct = val.Factory();
		
		Matrix map = Inverse(span.Rows());
		return fct.create(map.times(span));
	}
	
	@Override
	public default Affine map(Affine val)
	{
		Matrix span = val.Span();
		Factory fct = val.Factory();
		
		Matrix map = Matrix(span.Rows());
		return fct.create(map.times(span));
	}
}