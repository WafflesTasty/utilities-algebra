package zeno.util.algebra.linear.matrix.types.orthogonal;

import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.square.Involutory;
import zeno.util.algebra.linear.matrix.types.square.Symmetric;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.patterns.Operator;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Reflection} interface defines an operator for reflection matrices.
 * Matrices tagged with this operator are assumed to represent reflections.
 * These are orthogonal matrices with one eigenvalue equal to -1 and
 * all remaining eigenvalues equal to 1.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Involutory
 * @see Orthogonal
 * @see Symmetric
 */
public interface Reflection extends Involutory, Orthogonal, Symmetric
{
	/**
	 * Returns the abstract type of the {@code Reflection Operator}.
	 * 
	 * @return  a type operator
	 */
	public static Reflection Type()
	{
		return () ->
		{
			return null;
		};
	}
	
	/**
	 * Computes the reflection normal from a {@code Matrix}.
	 * 
	 * @param m  a reflection matrix
	 * @return   a reflection normal
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Vector normal(Matrix m)
	{
		int rows = m.Rows();
		
		Vector n = new Vector(rows);
		for(int i = 0; i < rows; i++)
		{
			float val = (1 - m.get(i, i)) / 2;
			n.set(Floats.sqrt(val), i);
		}
		
		return n;
	}
	
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		return op instanceof Reflection;
	}

	@Override
	public default boolean matches(Tensor t, int ulps)
	{
		if(t.is(Symmetric.Type(), ulps))
		{
			Vector n = normal((Matrix) t);
			Matrix ref = n.times(n.transpose()).times(2f).plus(t);
			return ref.is(Identity.Type(), ulps);
		}
		
		return false;
	}
	
	@Override
	public default Reflection instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	
	@Override
	public default Matrix pseudoinverse()
	{
		return inverse();
	}

	@Override
	public default Matrix inverse()
	{
		return Operable();
	}
}