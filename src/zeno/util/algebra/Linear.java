package zeno.util.algebra;

import zeno.util.algebra.linear.matrix.Matrix;
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
	 * The {@code Composite} interface composes multiple {@code Linear Maps}.
	 *
	 * @author Zeno
	 * @since Feb 10, 2019
	 * @version 1.0
	 * 
	 *
	 * @param <X>  a source type
	 * @param <Y>  a target type
	 * 
	 * 
	 * @see Function.Composite
	 * @see Linear.Map
	 */
	@FunctionalInterface
	public static interface Composite<X, Y> extends Function.Composite<X, Y>, Map<X, Y>
	{
		@Override
		public abstract Linear.Map<?, ?>[] Functions();


		@Override
		public default Matrix Inverse()
		{
			Matrix inv = Functions()[0].Inverse();
			for(int i = 1; i < Functions().length; i++)
			{
				inv = inv.times(Functions()[i].Inverse());
			}
			
			return inv;
		}
		
		@Override
		public default Matrix Matrix()
		{
			Matrix mat = Functions()[0].Matrix();
			for(int i = 1; i < Functions().length; i++)
			{
				mat = Functions()[i].Matrix().times(mat);
			}

			return mat;
		}
	}
	
	/**
	 * The {@code Map} interface defines a linear map.
	 *
	 * @author Zeno
	 * @since Feb 10, 2019
	 * @version 1.0
	 * 
	 *
	 * @param <X>  a source type
	 * @param <Y>  a target type
	 * 
	 * 
	 * @see Function
	 */
	public static interface Map<X , Y> extends Function<X, Y>
	{
		/**
		 * Returns the inverse of the {@code Linear Map}.
		 * 
		 * @return  a transformation inverse
		 * 
		 * 
		 * @see Matrix
		 */
		public abstract Matrix Inverse();
		
		/**
		 * Returns the matrix of the {@code Linear Map}.
		 * 
		 * @return  a transformation matrix
		 * 
		 * 
		 * @see Matrix
		 */
		public abstract Matrix Matrix();
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