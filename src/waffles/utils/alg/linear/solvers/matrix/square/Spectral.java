package waffles.utils.alg.linear.solvers.matrix.square;

import waffles.utils.alg.linear.measure.tensor.TensorData;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Spectral} algorithm computes singular values of a matrix.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 */
@FunctionalInterface
public interface Spectral
{	
	/**
	 * Computes a singular value {@code Vector}.
	 * 
	 * @return  singular values
	 * 
	 * 
	 * @see Vector
	 */
	public abstract Vector SingularValues();

	
	/**
	 * Computes a {@code Matrix} condition number.
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
	
	/**
	 * Computes a {@code Matrix} euclidian norm.
	 * 
	 * @return  a euclidian norm
	 */
	public default float euclidian()
	{
		TensorData data = SingularValues().Data();
		return Floats.max(data.Array());
	}
	
	/**
	 * Computes a {@code Matrix} frobenius norm.
	 * 
	 * @return  a frobenius norm
	 */
	public default float frobenius()
	{
		return SingularValues().norm();
	}
}