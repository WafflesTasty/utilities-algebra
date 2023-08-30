package waffles.utils.algebra.algorithms.orthogonal;

import waffles.utils.algebra.algorithms.factoring.FCTPolarized;
import waffles.utils.algebra.algorithms.solvers.SLVCrout;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code ORTNewton} algorithm computes a polar decomposition using {@code Newton's method}.
 * This method iteratively computes the orthogonal matrix Q up to convergence.
 *
 * @author Waffles
 * @since 27 Apr 2020
 * @version 1.0 
 * 
 * 
 * @see FCTPolarized
 */
public class ORTNewton implements FCTPolarized
{
	private Matrix q, p;
	private Matrix base;
	
	/**
	 * Creates a new {@code ORTNewton}.
	 * It requires a matrix marked as square.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public ORTNewton(Matrix m)
	{
		base = m;
	}
	

	private void decompose()
	{
		// If the matrix is not square...
		if(!base.is(Square.Type()))
		{
			// A Hessenberg factorization cannot be computed.
			throw new Matrices.TypeError(Square.Type());
		}
		
		
		newton();
	}
	
	private float maxColumnL1(Matrix m)
	{
		float val = 0f;
		for(int c = 0; c < m.Columns(); c++)
		{
			Vector v = m.Column(0);
			val = Floats.max(val, v.norm1());
		}
		
		return val;
	}
	
	private void newton()
	{
		Matrix m = base.copy();
		float n1, n2 = 0f;
		
		do
		{
			q = m;
			
			// Compute the next iterative step.
			Matrix r = new SLVCrout(m).inverse();
			m = m.plus(r.transpose()).times(0.5f);
			
			// Compute the convergence norms.
			n1 = maxColumnL1(m.minus(q));
			n2 = maxColumnL1(q);
		}
		while(!Floats.isLower(n1, n2, 3));
	}
	

	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(q == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		// Assign the type of the matrix Q.
		q.setOperator(Orthogonal.Type());
		return q;
	}

	@Override
	public Matrix P()
	{
		// If no decomposition has been made yet...
		if(q == null)
		{
			// Decompose the matrix.
			decompose();
		}
		
		
		// If matrix P hasn't been computed yet...
		if(p == null)
		{
			// Create the permutation matrix P.
			p = Q().transpose().times(base);
			p = p.plus(p.transpose()).times(0.5f);
		}
		
		return p;
	}
}