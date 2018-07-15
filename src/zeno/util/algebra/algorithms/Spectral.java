package zeno.util.algebra.algorithms;

import zeno.util.algebra.linear.vector.Vector;
import zeno.util.tools.generic.properties.Updateable;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code Spectral} interface defines a solver for matrix singular values.
 *
 * @author Zeno
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * 
 * @see Updateable
 */
public interface Spectral extends Updateable
{	
	/**
	 * Returns the singular values of the {@code Spectral}.
	 * 
	 * @return  the singular values
	 * 
	 * 
	 * @see Vector
	 */
	public abstract Vector SingularValues();

	
	/**
	 * Returns the euclidian norm of the {@code Spectral}.
	 * 
	 * @return  the euclidian matrix norm
	 */
	public default float euclidian()
	{
		return Floats.max(SingularValues().Values());
	}
	
	/**
	 * Returns the frobenius norm of the {@code Spectral}.
	 * 
	 * @return  the frobenius matrix norm
	 */
	public default float frobenius()
	{
		return SingularValues().norm();
	}
	
	/**
	 * Returns the value of the {@code Spectral}.
	 * This equals the condition number of a matrix.
	 * 
	 * @return  a condition number
	 */
	public default float condition()
	{
		Vector e = SingularValues();
		float eMin = Floats.min(e.Values());
		float eMax = Floats.max(e.Values());
		return eMax / eMin;
	}
}