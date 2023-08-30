package waffles.utils.algebra.algorithms;

import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Spectral} interface defines a solver for singular values.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 */
public interface Spectral
{	
	/**
	 * Returns the singular values of the {@code Spectral}.
	 * 
	 * @return  a singular value vector
	 * 
	 * 
	 * @see Vector
	 */
	public abstract Vector SingularValues();

	
	/**
	 * Returns the euclidian norm of the {@code Spectral}.
	 * 
	 * @return  a euclidian matrix norm
	 */
	public default float euclidian()
	{
		TensorData data = SingularValues().Data();
		return Floats.max(data.Array());
	}
	
	/**
	 * Returns the frobenius norm of the {@code Spectral}.
	 * 
	 * @return  a frobenius matrix norm
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
		TensorData data = SingularValues().Data();
		float eMin = Floats.min(data.Array());
		float eMax = Floats.max(data.Array());
		return eMax / eMin;
	}
}