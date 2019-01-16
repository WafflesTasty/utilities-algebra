package zeno.util.algebra.functions;

import zeno.util.algebra.Function;
import zeno.util.algebra.linear.matrix.Matrix;

/**
 * The {@code Composition} class composes two {@code Function} objects.
 *
 * @author Zeno
 * @since Jan 16, 2019
 * @version 1.0
 * 
 *
 * @param <X>  a source type
 * @param <Y>  an intermediate type
 * @param <Z>  a target type
 * @see Function
 */
public class Composition<X, Y, Z> implements Function<X, Z>
{
	/**
	 * The {@code Linear} class composes two {@code Linear Function} objects.
	 *
	 * @author Zeno
	 * @since Jan 16, 2019
	 * @version 1.0
	 * 
	 * @see Composition
	 * @see Function
	 * @see Matrix
	 */
	public static class Linear extends Composition<Matrix, Matrix, Matrix> implements Function.Linear
	{
		/**
		 * Creates a new {@code Linear Composition}.
		 * 
		 * @param f  a  first function
		 * @param g  a second function
		 */
		public Linear(Function.Linear f, Function.Linear g)
		{
			super(f, g);
		}

		
		@Override
		public Function.Linear F()
		{
			return (Function.Linear) super.F();
		}
		
		@Override
		public Function.Linear G()
		{
			return (Function.Linear) super.G();
		}
		
		
		@Override
		public Matrix Inverse()
		{
			return F().Inverse().times(G().Inverse());
		}

		@Override
		public Matrix Matrix()
		{
			return G().Matrix().times(F().Matrix());
		}
	}
	
	
	private Function<X, Y> f;
	private Function<Y, Z> g;
	
	/**
	 * Creates a new {@code Composition}.
	 * 
	 * @param f  a  first function to use
	 * @param g  a second function to use
	 * @see Function
	 */
	public Composition(Function<X, Y> f, Function<Y, Z> g)
	{
		this.f = f;
		this.g = g;
	}
	
	
	/**
	 * Returns the  first function of the {@code Composition}.
	 * 
	 * @return  a first function
	 * @see Function
	 */
	public Function<X, Y> F()
	{
		return f;
	}
	
	/**
	 * Returns the second function of the {@code Composition}.
	 * 
	 * @return  a second function
	 * @see Function
	 */
	public Function<Y, Z> G()
	{
		return g;
	}
	
	
	@Override
	public X unmap(Z val)
	{
		return f.unmap(g.unmap(val));
	}
	
	@Override
	public Z map(X val)
	{
		return g.map(f.map(val));
	}
}