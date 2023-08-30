package waffles.utils.algebra.algorithms.factoring.hholder;

import waffles.utils.algebra.algorithms.factoring.FCTHessenberg;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.matrix.types.banded.Tridiagonal;
import waffles.utils.algebra.elements.linear.matrix.types.banded.upper.UpperHessenberg;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Identity;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.algebra.elements.linear.matrix.types.square.Symmetric;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.utilities.matrix.Householder;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code FCTHessenbergHH} algorithm performs a {@code Hessenberg} factorization.
 * This algorithm applies {@code Householder} transformations to induce zeroes in a matrix.
 * 
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see FCTHessenberg
 */
public class FCTHessenbergHH implements FCTHessenberg
{
	private Matrix mat;
	private Matrix q, h;
	
	
	private Matrix base;
	
	/**
	 * Creates a new {@code FCTHessenbergHH}.
	 * It requires a matrix marked as square.
	 * Otherwise, an exception is thrown
	 * during decomposition.
	 * 
	 * @param m  a coefficient matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public FCTHessenbergHH(Matrix m)
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

	
		// If the coefficient matrix is orthogonal...
		if(base.is(Orthogonal.Type()))
		{
			// Skip Householder's method.
			skipOrthogonal();
		}
		// If the coefficient matrix is Hessenberg...
		else if(base.is(UpperHessenberg.Type()))
		{
			// Skip Householder's method.
			skipHessenberg();
		}
		else
		{
			// Otherwise, decompose with Householder's method.
			mat = base.copy();
			houseHolder();	
		}
	}
		
	private void skipHessenberg()
	{
		// Define dimensions.
		int cols = base.Columns();	
				
		// Skip Householder's method.
		mat = base.copy();
		mat.setOperator(UpperHessenberg.Type());
		
		q = Matrices.identity(cols);
		q.setOperator(Identity.Type());
	}
	
	private void skipOrthogonal()
	{
		// Define dimensions.
		int rows = base.Rows();
				
		// Skip Householder's method.
		q = base.copy();
		q.setOperator(Orthogonal.Type());
		
		mat = Matrices.identity(rows);
		mat.setOperator(Identity.Type());
	}
		
	private void houseHolder()
	{
		// Define dimensions.
		int size = base.Rows();	
		
		
		// Create the orthogonal matrix Q.
		q = Matrices.identity(size);
		// Assign the type of the matrix Q.
		q.setOperator(Identity.Type());
		
		
		// For every subdiagonal element in the matrix...
		for(int j = 1; j < size - 1; j++)
		{
			// Create the reflection normal.
			Vector vk = mat.Column(j - 1);
			for(int i = 0; i < j; i++)
			{
				vk.set(0f, i);
			}
			
			// Create the reflection matrix.
			Matrix hh = Householder.Matrix(vk, j);
			// Reflect the target matrix.
			mat = hh.times(mat).times(hh);
			q = hh.times(q);
		}
	}
		
	
	@Override
	public Matrix H()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// If H has not been computed yet...
		if(h == null)
		{
			// Define dimensions.
			int rows = base.Rows();
			int cols = base.Columns();	
			
			
			boolean isTridiagonal = false;
			// Create the Hessenberg matrix H.
			h = Matrices.create(rows, cols);
			// Assign the type of the matrix H.
			if(!base.is(Symmetric.Type()))
				h.setOperator(UpperHessenberg.Type());
			else
			{
				h.setOperator(Tridiagonal.Type());
				isTridiagonal = true;
			}

			
			// Copy from the decomposed matrix.
			for(int i = 0; i < rows; i++)
			{
				int jMin = Integers.max(i - 1, 0);
				int jMax = Integers.min(i + 2, cols);
				
				jMax = isTridiagonal ? jMax : cols;
				for(int j = jMin; j < jMax; j++)
				{
					h.set(mat.get(i, j), i, j);
				}
			}
		}
				
		return h;
	}

	@Override
	public Matrix Q()
	{
		// If no decomposition has been made yet...
		if(mat == null)
		{			
			// Decompose the matrix.
			decompose();
		}
		
		// Assign the type of matrix Q.
		q.setOperator(Orthogonal.Type());
		
		return q;
	}
}