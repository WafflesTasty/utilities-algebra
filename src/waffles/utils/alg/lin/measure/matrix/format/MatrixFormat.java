package waffles.utils.alg.lin.measure.matrix.format;

import java.util.Iterator;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.lang.tokens.format.Format;

/**
 * A {@code MatrixFormat} defines a formatter for a {@code Matrix}.
 *
 * @author Waffles
 * @since 19 Sep 2025
 * @version 1.1
 *
 * 
 * @see Format
 * @see Matrix
 */
public class MatrixFormat implements Format<Matrix>
{
	/**
	 * A {@code Descriptor} describes the data in a {@code Matrix}.
	 *
	 * @author Waffles
	 * @since 19 Sep 2025
	 * @version 1.1
	 *
	 * 
	 * @see Iterator
	 */
	public class Descriptor implements Iterator<String>
	{
		private int r;
		private Matrix mat;
		
		/**
		 * Creates a new {@code Descriptor}.
		 * 
		 * @param m  a matrix
		 * 
		 * 
		 * @see Matrix
		 */
		public Descriptor(Matrix m)
		{
			mat = m;
		}

		
		@Override
		public boolean hasNext()
		{
			return r < mat.Rows();
		}

		@Override
		public String next()
		{
			return mat.Row(r++).condense();
		}		
	}


	@Override
	public Iterable<String> describe(Matrix m)
	{
		return () -> new Descriptor(m);
	}

	@Override
	public String parse(Matrix m)
	{
		String s = "";
		for(String r : describe(m))
		{
			if(s.length() == 0)
				s += "[ ";
			else
				s += ", ";
			
			s += r;
		}
		
		return s;
	}
}