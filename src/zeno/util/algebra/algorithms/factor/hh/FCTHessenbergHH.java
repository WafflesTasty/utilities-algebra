package zeno.util.algebra.algorithms.factor.hh;

import zeno.util.algebra.algorithms.factor.FCTHessenberg;
import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.operators.Square;
import zeno.util.algebra.linear.matrix.operators.banded.Tridiagonal;
import zeno.util.algebra.linear.matrix.operators.banded.upper.UpperHessenberg;
import zeno.util.algebra.linear.matrix.operators.orthogonal.Identity;
import zeno.util.algebra.linear.matrix.operators.square.Orthogonal;
import zeno.util.algebra.linear.matrix.operators.square.Symmetric;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code FCTHessenbergHH} class performs a {@code Hessenberg} factorization.
 * This algorithm applies {@code Householder} transformations to induce zeroes in a matrix.
 * 
 * @author Zeno
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTHessenberg
 */
public class FCTHessenbergHH implements FCTHessenberg
{
	private static final int ULPS = 3;
	
	
	private Matrix q, h;
	private Matrix mat, c;
	private int iError;
	
	/**
	 * Creates a new {@code FCTHessenbergHH}.
	 * This algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTHessenbergHH(Matrix m)
	{
		this(m, ULPS);
	}
	
	/**
	 * Creates a new {@code FCTHessenbergHH}.
	 * This algorithm requires a square matrix.
	 * Otherwise, an exception will be thrown during the process.
	 * 
	 * @param m  a coëfficient matrix
	 * @param ulps  an error margin
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTHessenbergHH(Matrix m, int ulps)
	{
		iError = ulps;
		mat = m;
	}

	
	@Override
	public boolean needsUpdate()
	{
		return c == null;
	}
	
	@Override
	public void requestUpdate()
	{
		c = q = h = null;
	}
		
	private void houseHolder()
	{
		// Copy the target matrix.
		c = mat.copy();
				
		// Matrix dimensions.
		int size = mat.Rows();	
		
		
		// Create the orthogonal matrix Q.
		q = Matrices.identity(size);
		// Assign the type of the matrix Q.
		q.setOperator(Identity.Type());
		
		
		// For every subdiagonal element in the matrix...
		for(int j = 1; j < size - 1; j++)
		{
			// Create the reflection normal.
			Vector vk = c.Column(j - 1);
			for(int i = 0; i < j; i++)
			{
				vk.set(0f, i);
			}
			
			// Create the reflection matrix.
			Matrix hh = Matrices.houseHolder(vk, j);
			// Reflect the target matrix.
			c = hh.times(c).times(hh);
			q = hh.times(q);
		}
	}
	
	private void decompose()
	{
		// If the matrix is not square...
		if(!mat.is(Square.Type()))
		{
			// A Hessenberg factorization cannot be computed.
			throw new Tensors.DimensionError("Hessenberg factorization requires a square matrix: ", mat);
		}
		
						
		// Matrix dimensions.
		int rows = mat.Rows();
		int cols = mat.Columns();		
				
		// If the matrix is orthogonal...
		if(mat.is(Orthogonal.Type(), iError))
		{
			// Skip Householder's method.
			q = mat.copy();
			q.setOperator(Orthogonal.Type());
			c = Matrices.identity(rows);
			c.setOperator(Identity.Type());
			return;
		}
		
		// If the matrix is Hessenberg...
		if(mat.is(UpperHessenberg.Type(), iError))
		{
			// Skip Householder's method.
			c = mat.copy();
			c.setOperator(UpperHessenberg.Type());
			q = Matrices.identity(cols);
			q.setOperator(Identity.Type());
			return;
		}
		
		
		// Otherwise, perform Householder's method.
		houseHolder();
	}
	
	
	@Override
	public Matrix H()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Hessenberg factorization.
			decompose();
		}
		
		// If H has not been computed yet...
		if(h == null)
		{
			// Matrix dimensions.
			int rows = mat.Rows();
			int cols = mat.Columns();	
			
			
			// Create the Hessenberg matrix H.
			h = Matrices.create(rows, cols);
			
			boolean isTridiagonal = false;
			// Assign the type of the matrix H.
			if(!mat.is(Symmetric.Type(), iError))
				h.setOperator(UpperHessenberg.Type());
			else
			{
				h.setOperator(Tridiagonal.Type());
				isTridiagonal = true;
			}

			
			// Copy the elements from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				int jMin = Integers.max(i - 1, 0);
				int jMax = Integers.min(i + 2, cols);
				
				jMax = isTridiagonal ? jMax : cols;
				for(int j = jMin; j < jMax; j++)
				{
					h.set(c.get(i, j), i, j);
				}
			}
		}
				
		return h;
	}

	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(needsUpdate())
		{
			// Perform Hessenberg factorization.
			decompose();
		}
		
		// Assign the type of matrix Q.
		q.setOperator(Orthogonal.Type());
		
		return q;
	}
}