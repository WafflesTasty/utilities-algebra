package zeno.util.algebra;

import zeno.util.algebra.linear.matrix.Matrix;

/**
 * The {@code Function} interface defines a functional map.
 *
 * @author Zeno
 * @since Dec 18, 2018
 * @version 1.0
 * 
 *
 * @param <X>  a source type
 * @param <Y>  a target type
 */
public interface Function<X, Y>
{
	/**
	 * The {@code Linear} interface defines an abstract linear function.
	 * It is represented by a matrix multiplication, and its inverse.
	 *
	 * @author Zeno
	 * @since Dec 18, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see Function
	 * @see Matrix
	 */
	public static interface Linear extends Function<Matrix, Matrix>
	{
		/**
		 * Returns the inverse of the {@code Linear Function}.
		 * 
		 * @return  a transformation inverse
		 * @see Matrix
		 */
		public abstract Matrix Inverse();
		
		/**
		 * Returns the matrix of the {@code Linear Function}.
		 * 
		 * @return  a transformation matrix
		 * @see Matrix
		 */
		public abstract Matrix Matrix();

			
		@Override
		public default Matrix unmap(Matrix val)
		{
			return Inverse().times(val);
		}
		
		@Override
		public default Matrix map(Matrix val)
		{
			return Matrix().times(val);
		}
	}
	
	
	/**
	 * Maps a target to its source {@code Function}.
	 * 
	 * @param val  a target element
	 * @return     a source element
	 */
	public abstract X unmap(Y val);
	
	/**
	 * Maps a source to its target {@code Function}.
	 * 
	 * @param val  a source element
	 * @return     a target element
	 */
	public abstract Y map(X val);
}