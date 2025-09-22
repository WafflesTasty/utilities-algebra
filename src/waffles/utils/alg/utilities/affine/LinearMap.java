package waffles.utils.alg.utilities.affine;

import waffles.utils.alg.Function;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.utilities.affine.Affine.Factory;

/**
 * A {@code LinearMap} maps {@code Affine} objects through matrix multiplication.
 * The corresponding matrix is dimension-dependent, with the intent to allow
 * the map to handle input and output of a variable dimension.
 *
 * @author Waffles
 * @since Feb 10, 2019
 * @version 1.1
 * 
 * 
 * @see Function
 * @see Affine
 */
public interface LinearMap extends Function<Affine, Affine>
{
	/**
	 * Returns the inverse matrix of the {@code LinearMap}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  an inverse matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix Inverse(int dim);
	
	/**
	 * Returns the similar matrix of the {@code LinearMap}.
	 * 
	 * @param dim  a matrix dimension
	 * @return  a similar matrix
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