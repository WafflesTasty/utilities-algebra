package waffles.utils.alg.lin.solvers.matrix.ranks;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.solvers.matrix.factor.UVFactor;
import waffles.utils.tools.primitives.Integers;

/**
 * An {@code RRUVFactor} algorithm computes rank through an {@code UVFactor}.
 *
 * @author Waffles
 * @since 07 Dec 2025
 * @version 1.1
 * 
 * 
 * @see RankReveal
 * @see UVFactor
 */
public interface RRUVFactor extends RankReveal, UVFactor
{
	/**
	 * Returns the complement of the {@code RRUVFactor}.
	 * 
	 * @return  a complement matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix Complement()
	{
		int c1 = Hints().Matrix().Columns();
		int r1 = Hints().Matrix().Rows();
		int m = Integers.min(r1, c1);

		// Create the column complement matrix...
		Matrix b = Matrices.create(r1, m - rank());
		for(int c = rank(); c < m; c++)
		{
			// ...as the last r - rank columns of U.
			for(int r = 0; r < r1; r++)
			{
				float u = U().get(r, c);
				b.set(u, r, c - rank());
			}
		}
		
		return b;
	}
	
	/**
	 * Returns the cokernel of the {@code RRUVFactor}.
	 * 
	 * @return  a cokernel matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix Cokernel()
	{
		int c1 = Hints().Matrix().Columns();
		int r1 = Hints().Matrix().Rows();

		// Create the kernel matrix...
		Matrix b = Matrices.create(c1, rank());
		for(int c = 0; c < rank(); c++)
		{
			// ...as the first rank columns of V.
			for(int r = 0; r < r1; r++)
			{
				float v = V().get(r, c);
				b.set(v, r, c);
			}
		}
		
		return b;
	}	
	
	/**
	 * Returns the kernel of the {@code RRUVFactor}.
	 * 
	 * @return  a kernel matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix Kernel()
	{
		int c1 = Hints().Matrix().Columns();
		int r1 = Hints().Matrix().Rows();

		// Create the kernel matrix...
		Matrix b = Matrices.create(c1, c1 - rank());
		for(int c = rank(); c < c1; c++)
		{
			// ...as the last r - rank columns of V.
			for(int r = 0; r < r1; r++)
			{
				float v = V().get(r, c);
				b.set(v, r, c - rank());
			}
		}
		
		return b;
	}
	
	/**
	 * Returns the basis of the {@code RRUVFactor}.
	 * 
	 * @return  a basis matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public default Matrix Basis()
	{
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();

		// Create the column basis matrix...
		Matrix b = Matrices.create(r1, rank());
		for(int c = 0; c < rank(); c++)
		{
			// ...as the first rank columns of U.
			for(int r = 0; r < r1; r++)
			{
				float u = U().get(r, c);
				b.set(u, r, c);
			}
		}
		
		return b;
	}
}