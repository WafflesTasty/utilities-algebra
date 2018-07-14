package zeno.util.algebra.linear.alg;

import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.tools.generic.properties.Updateable;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Condition} interface defines a solver for matrix condition values.
 *
 * @author Zeno
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * @see Updateable
 */
public interface Condition extends Updateable
{	
	/**
	 * Returns the singular values of the {@code Condition}.
	 * 
	 * @return  the singular values
	 * @see Vector
	 */
	public abstract Vector SingularValues();
	
	
	/**
	 * Returns the value of the {@code Condition}.
	 * This equals the condition number of a matrix.
	 * 
	 * @return  a condition number
	 */
	public default float value()
	{
		Vector e = SingularValues();
		float eMin = Floats.min(e.Values());
		float eMax = Floats.max(e.Values());
		return eMax / eMin;
	}
	
	/**
	 * Returns the norm of the {@code Condition}.
	 * This equals the frobenius norm of a matrix.
	 * 
	 * @return  a matrix norm
	 */
	public default float norm()
	{
		return SingularValues().norm();
	}
}