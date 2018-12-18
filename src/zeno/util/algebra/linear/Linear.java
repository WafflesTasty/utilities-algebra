package zeno.util.algebra.linear;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.tools.patterns.Function;
import zeno.util.tools.patterns.properties.Copyable;

/**
 * The {@code Linear} interface defines an element in a linear space.
 *
 * @author Zeno
 * @since Apr 28, 2018
 * @version 1.0
 * 
 * 
 * @param <L>  the type of the element
 * @see Copyable
 */
public interface Linear<L extends Linear<L>> extends Copyable<L>
{	
	/**
	 * The {@code Map} interface defines an abstract linear map.
	 * It is represented by a matrix multiplication, and optionally its inverse.
	 *
	 * @author Zeno
	 * @since Dec 18, 2018
	 * @version 1.0
	 * 
	 *
	 * @param <X>  a source type
	 * @param <Y>  a target type
	 * @see Function
	 * @see Matrix
	 */
	public static interface Map<X extends Matrix, Y extends Matrix> extends Function<X, Y>
	{
		/**
		 * Returns the inverse of the {@code Linear Map}.
		 * 
		 * @return  a transformation inverse
		 * @see Matrix
		 */
		public abstract Matrix Inverse();
		
		/**
		 * Returns the matrix of the {@code Linear Map}.
		 * 
		 * @return  a transformation matrix
		 * @see Matrix
		 */
		public abstract Matrix Matrix();
		
		
		@Override
		public default X unmap(Y val)
		{
			return (X) Inverse().times(val);
		}
		
		@Override
		public default Y map(X val)
		{
			return (Y) Matrix().times(val);
		}
	}
	
	
	/**
	 * Returns the {@code Linear}'s product with a scalar value.
	 * 
	 * @param val  a value to multiply
	 * @return  the scalar product
	 */
	public abstract L times(float val);
	
	/**
	 * Returns the {@code Linear}'s addition with another element.
	 * 
	 * @param element  an element to add
	 * @return  the sum element
	 */
	public abstract L plus(L element);
	
	/**
	 * Returns the {@code Linear}'s difference with an element.
	 * 
	 * @param element  an element to subtract
	 * @return  the difference element
	 */
	public default L minus(L element)
	{
		return plus(element.times(-1));
	}
}