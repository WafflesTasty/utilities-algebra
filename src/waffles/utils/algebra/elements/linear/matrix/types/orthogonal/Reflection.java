package waffles.utils.algebra.elements.linear.matrix.types.orthogonal;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.square.Involutory;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A matrix tagged with the {@code Reflection} operator is assumed to represent a reflection.
 * These are orthogonal matrices with one eigenvalue equal to -1 and all remaining ones equal to +1.
 * 
 * @author Waffles
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
	 * Returns the abstract type of the {@code Reflection} operator.
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
	 * Computes a reflection normal from a {@code Matrix}.
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
			float val = (1f - m.get(i, i)) / 2;
			n.set(Floats.sqrt(val), i);
		}
		
		return n;
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return Orthogonal.super.Inverse();
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
	public default boolean allows(Tensor t, int ulps)
	{
		if(matches(t))
		{
			return true;
		}
		
		if(Symmetric.Type().allows(t, ulps))
		{
			Vector n = normal((Matrix) t);
			Matrix id = n.times(n.transpose());
			id = id.times(2f).plus(t);
			
			return Identity.Type().allows(id, ulps);
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Reflection;
	}
}