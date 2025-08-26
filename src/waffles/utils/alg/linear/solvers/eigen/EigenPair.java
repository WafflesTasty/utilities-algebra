package waffles.utils.alg.linear.solvers.eigen;

import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.algebra.elements.linear.Vectors;
import waffles.utils.sets.utilities.keymaps.Pair;

/**
 * An {@code EigenPair} defines an eigenvector and corresponding value as a {@code Pair}.
 *
 * @author Waffles
 * @since 26 Aug 2025
 * @version 1.1
 *
 * 
 * @see Pair
 */
public class EigenPair extends Pair.Mutable<Vector, Float>
{
	/**
	 * Creates a new {@code EigenPair}.
	 * 
	 * @param d  a vector dimension
	 * @param l  an eigen value
	 */
	public EigenPair(int d, Float l)
	{
		this(Vectors.create(d), l);
	}
	
	/**
	 * Creates a new {@code EigenPair}.
	 * 
	 * @param v  an eigen vector
	 * @param l  an eigen value
	 * 
	 * 
	 * @see Vector
	 */
	public EigenPair(Vector v, Float l)
	{
		super(v, l);
	}

	/**
	 * Creates a new {@code EigenPair}.
	 * 
	 * @param v  an eigen vector
	 * 
	 * 
	 * @see Vector
	 */
	public EigenPair(Vector v)
	{
		this(v, 0f);
	}
}