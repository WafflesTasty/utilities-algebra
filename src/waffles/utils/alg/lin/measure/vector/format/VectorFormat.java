package waffles.utils.alg.lin.measure.vector.format;

import java.util.Iterator;

import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.lang.tokens.format.Format;

/**
 * A {@code VectorFormat} defines a formatter for a {@code Vector}.
 *
 * @author Waffles
 * @since 19 Sep 2025
 * @version 1.1
 *
 * 
 * @see Format
 * @see Vector
 */
public class VectorFormat implements Format<Vector>
{
	/**
	 * Defines a default integer size.
	 */
	public static int DEF_INTEGER = 6;
	
	/**
	 * Defines a default fractional size.
	 */
	public static int DEF_FRACTION = 4;
	
	
	/**
	 * A {@code Descriptor} describes the data in a {@code Vector}.
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
		private int k;
		private Vector vec;
		
		/**
		 * Creates a new {@code Descriptor}.
		 * 
		 * @param v  a vector
		 * 
		 * 
		 * @see Vector
		 */
		public Descriptor(Vector v)
		{
			vec = v;
		}

		
		@Override
		public boolean hasNext()
		{
			return k < vec.Size();
		}

		@Override
		public String next()
		{
			String s = parse(vec.get(k++));
			return "[" + s + "]";
		}		
	}
	
	
	private int i, f;
	
	/**
	 * Creates a new {@code VectorFormat}.
	 * 
	 * @param i  an integer size
	 * @param f  a fractional size
	 */
	public VectorFormat(int i, int f)
	{
		this.i = i;
		this.f = f;
	}
	
	/**
	 * Creates a new {@code VectorFormat}.
	 */
	public VectorFormat()
	{
		this(DEF_INTEGER, DEF_FRACTION);
	}
	
	
	@Override
	public Iterable<String> describe(Vector v)
	{
		return () -> new Descriptor(v);
	}
	
	@Override
	public String parse(Vector v)
	{
		String s = "[ ";
		for(int k = 0; k < v.Size(); k++)
		{
			if(k > 0)
				s += ", ";
			s += parse(v.get(k));
		}
		
		s += "]";
		return s;
	}
	
	String parse(float v)
	{
		return String.format("%1$"+ (i + f + 1) +"." + f + "f", v);		
	}
}