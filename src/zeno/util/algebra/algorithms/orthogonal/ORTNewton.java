package zeno.util.algebra.algorithms.orthogonal;

import zeno.util.algebra.algorithms.factor.FCTPolar;
import zeno.util.algebra.algorithms.solvers.SLVCrout;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Floats;

/**
 * The {@code ORTNewton} class performs polar decomposition using {@code Newton's method}.
 * This method iteratively computes the orthogonal matrix Q up to convergence.
 *
 * @author Zeno
 * @since 27 Apr 2020
 * @version 1.0 
 * 
 * 
 * @see FCTPolar
 */
public class ORTNewton implements FCTPolar
{
	private static final int ULPS = 3;
	
	
	private Matrix mat;
	private Matrix q, p;	
	private int iError;
	
	/**
	 * Creates a new {@code ORTNewton}.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public ORTNewton(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code ORTNewton}.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public ORTNewton(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}
	

	@Override
	public boolean needsUpdate()
	{
		return q == null;
	}

	@Override
	public void requestUpdate()
	{
		q = p = null;
	}
	
	private void decompose()
	{
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// The decomposition cannot be computed.
			throw new Tensors.DimensionError("Polar decomposition requires a square matrix: ", mat);
		}
		
		
		Matrix m = mat.copy();
		float n1, n2 = 0f;
		
		do
		{
			q = m;
			
			// Compute the next iteration.
			Matrix r = new SLVCrout(m).inverse();
			m = m.plus(r.transpose()).times(0.5f);
			
			// Compute the convergence norms.
			n1 = Matrices.ColMax(m.minus(q));
			n2 = Matrices.ColMax(q);
		}
		while(!Floats.isLower(n1, n2, iError));
	}
	

	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform polar factorization.
			decompose();
		}
		
		return q;
	}

	@Override
	public Matrix P()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform polar factorization.
			decompose();
		}
		
		
		// If matrix P hasn't been computed yet...
		if(p == null)
		{
			// Compute the matrix.
			p = Q().transpose().times(mat);
			p = p.plus(p.transpose()).times(0.5f);
		}
		
		return p;
	}
}