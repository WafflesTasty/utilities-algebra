package waffles.utils.alg.lin.solvers.matrix.ranks.types;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.lin.solvers.matrix.MatrixSolver;
import waffles.utils.alg.lin.solvers.matrix.exact.LinearSystem;
import waffles.utils.alg.lin.solvers.matrix.exact.types.LUTriangular;
import waffles.utils.alg.lin.solvers.matrix.factor.PLUQFactor;
import waffles.utils.alg.lin.solvers.matrix.ranks.RankReveal;
import waffles.utils.alg.utilities.errors.TypeError;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRGauss} algorithm solves exact linear systems using {@code Gauss's method}.
 * This method is a variant of {@code Gaussian elimination} that decomposes a matrix {@code PMQ = LU},
 * where P,Q are permutation matrices, L a lower triangular matrix, and U an upper triangular matrix.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see LinearSystem
 * @see PLUQFactor
 * @see RankReveal
 */
public class RRGauss implements LinearSystem, PLUQFactor, RankReveal
{
	/**
	 * Defines the default error of an {@code RRGauss}.
	 */
	public static double DEF_ERROR = Doubles.pow(2, -32);
	
	/**
	 * The {@code Hints} interface defines hints for an {@code RRGauss}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see MatrixSolver
	 */
	@FunctionalInterface
	public static interface Hints extends MatrixSolver.Hints
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * This determines which Gauss's algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().allows(Square.Type(), 0))
				return State.VALID;
			return State.INVALID;
		}
		
		
		@Override
		public default double Error()
		{
			return DEF_ERROR;
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code RRGauss} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * An invalid matrix cannot be used.
		 */
		INVALID,
		/**
		 * A square matrix is decomposed.
		 */
		VALID;
	}
	
	
	private Float det;
	private Matrix l, u;
	private Matrix p, q, m;
	private Integer rank;
	private Hints hints;
		
	/**
	 * Creates a new {@code RRGauss}.
	 * 
	 * @param h  solver hints
	 */
	public RRGauss(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code RRGauss}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRGauss(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}

	

	LUTriangular LUTriangular(Matrix m)
	{
		return new LUTriangular(new LUTriangular.Hints()
		{
			@Override
			public Matrix Matrix()
			{
				return m;
			}
			
			@Override
			public double Error()
			{
				return RRGauss.this.Hints().Error();
			}
		});
	}
	
	@Override
	public <M extends Matrix> M solve(M b)
	{
		M x = (M) P().times(b);
		// Solve through substitution.
		x = LUTriangular(L()).solve(x);
		x = LUTriangular(U()).solve(x);
		x = (M) Q().times(x);
		return x;
	}
	
	@Override
	public boolean canSolve(Matrix b)
	{
		if(LinearSystem.super.canSolve(b))
		{
			if(Hints().State() == State.INVALID)
			{
				throw new TypeError
				(
					Square.Type()
				);
			}

			return canInvert();
		}
		
		return false;
	}

	@Override
	public boolean canInvert()
	{
		Matrix a = Hints().Matrix();
		
		int c1 = a.Columns();
		int r1 = a.Rows();
		
		
		return a.allows(Square.Type(), 0)
			&& rank < r1;
	}
	
	@Override
	public float determinant()
	{
		m = factor();
		if(det == null)
		{
			det = 1f;
			for(int r = 0; r < m.Rows(); r++)
			{
				det *= m.get(r, r);
			}
		}

		return det;
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}

	@Override
	public int rank()
	{
		if(rank == null)
			m = factor();
		return rank;
	}
	
	@Override
	public Matrix P()
	{
		if(p == null)
			m = factor();
		return p;
	}
	
	@Override
	public Matrix L()
	{
		if(l == null)
			m = factor();
		return l;
	}

	@Override
	public Matrix U()
	{
		if(u == null)
			m = factor();
		return u;
	}

	@Override
	public Matrix Q()
	{
		if(q == null)
			m = factor();
		return q;
	}
	
	
	void swapCols(int i, int j)
	{
		int r1 = Hints().Matrix().Rows();
		// Swap the columns i and j of the base matrix.
		for(int r = 0; r < r1 + 1; r++)
		{
			float curr = m.get(r, i);
			m.set(m.get(r, j), r, i);
			m.set(curr, r, j);
		}
	}
	
	void swapRows(int i, int j)
	{
		int c1 = Hints().Matrix().Columns();
		// Swap the rows i and j of the base matrix.
		for(int c = 0; c < c1 + 1; c++)
		{
			float curr = m.get(i, c);
			m.set(m.get(j, c), i, c);
			m.set(curr, j, c);
		}
	}
		
	Matrix factorGauss()
	{
		det = 1f;
		
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();
		
		rank = Integers.min(r1, c1);

		// For each dimension in the base matrix...
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			double vMax = 0;
			int rMax = k, cMax = k;

			// ...find the best pivot.
			for(int c = k; c < c1; c++)
			{
				for(int r = k; r < r1; r++)
				{
					float val = m.get(r, c);
					if(Floats.abs(val) > vMax)
					{
						vMax = Floats.abs(val);
						cMax = c; rMax = r;
					}
				}
			}
			
			// If the pivot is negligible...
			if(vMax < Hints().Error())
			{
				// ...finalize the rank.
				rank = k;
				return reduceGauss();
			}
			
			// Pivot the next column.
			if(cMax != k)
			{
				swapCols(cMax, k);
				det = -det;
			}
			
			// Pivot the next row.
			if(rMax != k)
			{
				swapRows(rMax, k);
				det = -det;
			}				
			
			
			float p = m.get(k, k);
			// For each row below the diagonal...
			for(int r = k + 1; r < r1; r++)
			{
				// Divide by the pivot.
				float q = m.get(r, k) / p;
				m.set(q, r, k);
				
				// ...eliminate its superdiagonal values.
				for(int c = k + 1; c < c1; c++)
				{
					float v1 = m.get(r, c);
					float v2 = m.get(k, c);
					float v3 = v1 - q * v2;
					
					m.set(v3, r, c);
				}
			}			
			
			det *= p;
		}
		
		return reduceGauss();
	}
	
	Matrix reduceGauss()
	{
		int r1 = m.Rows() - 1;
		int c1 = m.Columns() - 1;
		
		p = Matrices.create(r1, r1);
		l = Matrices.create(r1, r1);
		u = Matrices.create(c1, c1);
		q = Matrices.create(c1, c1);
		
		p.setOperator(Orthogonal.Type());
		l.setOperator(LowerTriangular.Type());
		u.setOperator(UpperTriangular.Type());
		q.setOperator(Orthogonal.Type());
		
		for(int r = 0; r < r1; r++)
		{
			Float v1 = m.get(r, c1);
			Float v2 = m.get(r1, r);
			
			p.set(1f, r, v1.intValue());
			q.set(1f, v2.intValue(), r);
			
			for(int c = 0; c < c1; c++)
			{
				float v3 = m.get(r, c);
				if(r > c)
					l.set(v3, r, c);
				else
				{
					u.set(v3, r, c);
					if(r == c)
					{
						l.set(1f, r, c);
					}
				}
			}
		}
		
		return m;
	}

	Matrix factor()
	{
		if(m == null)
		{
			m = Hints().Matrix();
			int c1 = m.Columns();
			int r1 = m.Rows();


			// Add a permutation column.
			m = m.resize(r1 + 1, c1 + 1);
			for(int k = 0; k < Integers.max(r1, c1); k++)
			{
				if(k < r1) m.set(k, k, c1);
				if(k < c1) m.set(k, r1, k);
			}
			
			switch(Hints().State())
			{
			case VALID:
				m = factorGauss(); break;
			case INVALID:
			default:
				break;
			}
		}

		return m;
	}
}