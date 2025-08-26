package waffles.utils.alg.linear.solvers.factor.lu;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Wide;
import waffles.utils.alg.linear.solvers.Solver;
import waffles.utils.alg.linear.solvers.exact.LinearSystem;
import waffles.utils.alg.linear.solvers.factor.PLUFactor;
import waffles.utils.alg.utilities.errors.InvertibleError;
import waffles.utils.alg.utilities.errors.TypeError;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code LUCrout} algorithm solves exact linear systems using {@code Crout's method}.
 * This method is a variant of {@code Gauss elimination} that decomposes a matrix {@code M = PLU},
 * where P is a permutation matrix, L a lower triangular matrix, and U an upper triangular matrix.
 * Note that non-square matrices can also be decomposed: one of L or U will not be square.
 * {@code Crout's method} is designed to leave the matrix U with a unit diagonal.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see LinearSystem
 * @see PLUFactor
 */
public class LUCrout implements LinearSystem, PLUFactor
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LUCrout}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Solver
	 */
	@FunctionalInterface
	public static interface Hints extends Solver.Hints
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * This determines which Crout's algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(LowerTriangular.Type()))
				return State.LOWER_TRIANGULAR;
			if(Matrix().is(UpperTriangular.Type()))
				return State.UPPER_TRIANGULAR;
			if(Matrix().allows(Square.Type(), 0))
				return State.GENERIC;
			return State.INVALID;
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code LUCrout} algorithms.
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
		 * A lower triangular matrix is not decomposed at all.
		 */
		LOWER_TRIANGULAR,
		/**
		 * An upper triangular matrix is decomposed with a simplified method.
		 */
		UPPER_TRIANGULAR,
		/**
		 * A generic matrix is decomposed with Crout's method.
		 */
		GENERIC;
	}
	
	
	private Float det;
	private Matrix l, u;
	private Matrix p, mat;
	private Hints hints;
		
	/**
	 * Creates a new {@code LUCrout}.
	 * 
	 * @param h  solver hints
	 */
	public LUCrout(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code LUCrout}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LUCrout(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy().destroy();
			if(m.is(LowerTriangular.Type()))
				b.setOperator(LowerTriangular.Type());
			if(m.is(UpperTriangular.Type()))
				b.setOperator(UpperTriangular.Type());
			return b;
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
				return LUCrout.this.Hints().Error();
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
	public float determinant()
	{
		mat = factor();
		if(det == null)
		{
			det = 1f;
			for(int r = 0; r < mat.Rows(); r++)
			{
				det *= mat.get(r, r);
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
	public Matrix P()
	{
		if(p == null)
		{
			mat = factor();

			int r1 = Hints().Matrix().Rows();
			int c1 = Hints().Matrix().Columns();
			
			// Create the permutation matrix P.
			p = Matrices.create(r1, r1);
			// Assign the type of the matrix P.
			p.setOperator(Orthogonal.Type());
			// Copy from the decomposed matrix.
			for(int r = 0; r < r1; r++)
			{
				Float v = mat.get(r, c1);
				p.set(1f, r, v.intValue());
			}
		}
		
		return p;
	}
	
	@Override
	public Matrix L()
	{
		if(l == null)
		{
			mat = factor();

			int r1 = Hints().Matrix().Rows();
			int c1 = Hints().Matrix().Columns();
			
			if(r1 <= c1)
			{
				l = Matrices.create(r1, r1);
				l.setOperator(LowerTriangular.Type());
			}
			else
			{
				l = Matrices.create(r1, c1);
				l.setOperator(Tall.Type());
			}
								
			// Copy from the decomposed matrix.
			for(int c = 0; c < c1; c++)
			{
				for(int r = c; r < r1; r++)
				{
					float v = mat.get(r, c);
					l.set(v, r, c);
				}
			}
		}
		
		return l;
	}

	@Override
	public Matrix U()
	{
		if(u == null)
		{
			mat = factor();

			int r1 = Hints().Matrix().Rows();
			int c1 = Hints().Matrix().Columns();

			if(r1 >= c1)
			{	
				u = Matrices.create(c1, c1);
				u.setOperator(UpperTriangular.Type());
			}
			else
			{
				u = Matrices.create(r1, c1);
				u.setOperator(Wide.Type());
			}
								
			// Copy from the decomposed matrix.
			for(int r = 0; r < r1; r++)
			{
				for(int c = r; c < c1; c++)
				{
					if(r == c)
						u.set(1f, r, c);
					else
					{
						float v = mat.get(r, c);
						u.set(v, r, c);
					}
				}
			}
		}
		
		return u;
	}
	
	
	void swap(int i, int j)
	{
		int c1 = Hints().Matrix().Columns();
		// Swap the rows i and j of the base matrix.
		for(int c = 0; c < c1 + 1; c++)
		{
			float curr = mat.get(i, c);
			mat.set(mat.get(j, c), i, c);
			mat.set(curr, j, c);
		}
	}
	
	Matrix factorCrout()
	{
		det = 1f;
		
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();

		// For each column in the base matrix...
		for(int c = 0; c < Integers.min(r1, c1); c++)
		{
			double vMax = 0; int iMax = c;
			
			// ...eliminate its subdiagonal values.
			for(int r = c; r < r1; r++)
			{
				double v = 0f;
				for(int s = 0; s < c; s++)
				{
					double v1 = mat.get(r, s);
					double v2 = mat.get(s, c);
					
					v += v1 * v2;
				}

				v = mat.get(r, c) - v;
				mat.set((float) v, r, c);
				
				// Leave the largest value as the next pivot.
				if(Doubles.abs(v) > vMax)
				{
					vMax = Doubles.abs(v);
					iMax = r;
				}
			}
			
			
			// If the next pivot is zero...
			if(vMax <= Hints().Error())
			{
				// ...the matrix is not invertible.
				throw new InvertibleError();
			}
			
			// Pivot the next row.
			if(c != iMax)
			{
				swap(c, iMax);
				det = -det;
			}
			
			
			float vc = mat.get(c, c);
			// Eliminate a row of superdiagonal values.
			for(int d = c + 1; d < c1; d++)
			{
				double v = 0d;
				for(int t = 0; t < c; t++)
				{
					double v1 = mat.get(c, t);
					double v2 = mat.get(t, d);
					
					v += v1 * v2;
				}
				
				v = (mat.get(c, d) - v) / vc;
				mat.set((float) v, c, d);
			}
			
			det *= vc;
		}
		
		return mat;
	}

	Matrix factorUpper()
	{
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();
		
		// For each row in the matrix...
		for(int r = 0; r < r1; r++)
		{
			float dr = mat.get(r, r);
			// Divide the diagonal element.
			for(int c = r + 1; c < c1; c++)
			{
				float val = mat.get(r, c) / dr;
				mat.set(val, r, c);
			}
		}
		
		return mat;
	}
	
	Matrix factor()
	{
		if(mat == null)
		{
			mat = Hints().Matrix();
			int c1 = mat.Columns();
			int r1 = mat.Rows();


			// Add a permutation column.
			mat = mat.resize(r1, c1 + 1);
			for(int r = 0; r < r1; r++)
			{
				mat.set(r, r, c1);
			}
			
			switch(Hints().State())
			{
			case GENERIC:
				mat = factorCrout(); break;
			case UPPER_TRIANGULAR:
				mat = factorUpper(); break;
			case LOWER_TRIANGULAR:
			case INVALID:
			default:
				break;
			}
		}

		return mat;
	}
}