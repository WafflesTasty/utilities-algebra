package waffles.utils.algebra.algorithms.factoring;

import waffles.utils.algebra.algorithms.Orthogonalize;
import waffles.utils.algebra.algorithms.Spectral;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Diagonal;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.vector.Vector;

/**
 * The {@code FCTSingularVals} interface defines an algorithm that performs SVD factorization.
 * Every matrix can be decomposed as {@code M = UEV*} where U is a matrix with orthogonal
 * columns, E a diagonal matrix of singular values, and V an orthogonal matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0
 * 
 * 
 * @see Orthogonalize
 * @see Spectral
 */
public interface FCTSingularVals extends Orthogonalize, Spectral
{
	/**
	 * Returns a diagonal matrix E of the {@code FCTSingular}.
	 * 
	 * @return  a diagonal matrix E
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix E();
	
	/**
	 * Returns a reduced orthogonal matrix U of the {@code FCTSingular}.
	 * 
	 * @return  an orthogonal matrix U
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix U();
	
	/**
	 * Returns a orthogonal matrix V of the {@code FCTSingular}.
	 * 
	 * @return  an orthogonal matrix V
	 * 
	 * 
	 * @see Matrix
	 */
	public abstract Matrix V();

	
	@Override
	public default Matrix NearestOrthogonal()
	{
		Matrix o = U().times(V().transpose());
		if(Square.Type().allows(o, 0))
		{
			o.setOperator(Orthogonal.Type());
		}
		
		return o;
	}
	
	@Override
	public default Vector SingularValues()
	{
		return Diagonal.of(E());
	}
}