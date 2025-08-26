package waffles.utils.alg.linear.solvers.factor.lu;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.alg.linear.measure.matrix.types.banded.Diagonal;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.linear.solvers.Solver;
import waffles.utils.alg.linear.solvers.exact.LinearSystem;
import waffles.utils.alg.linear.solvers.factor.LUFactor;
import waffles.utils.alg.utilities.errors.TypeError;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code LUCholesky} algorithm solves exact linear systems using {@code Cholesky's method}.
 * This method is a variant of {@code Gauss elimination} that works exclusively for {@code Symmetric}
 * matrices {@code M = R*R} where {@code R} is an upper triangular matrix. This method cuts computation
 * time roughly in half. It decomposes a matrix {@code M = LU = U*U}, where U is an upper triangular
 * matrix. Note that {@code L = U*} in this case is a lower triangular matrix as well.
 * 
 * @author Waffles
 * @since Jul 6, 2018
 * @version 1.1
 * 
 * 
 * @see LinearSystem
 * @see LUFactor
 */
public class LUCholesky implements LinearSystem, LUFactor
{
	/**
	 * The {@code Hints} interface defines hints for an {@code LUCholesky}.
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
		 * This determines which Cholesky algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().is(Diagonal.Type()))
				return State.SIMPLIFIED;
			if(Matrix().is(Symmetric.Type()))
				return State.CHOLESKY;
			return State.INDETERMINATE;
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code LUCholesky} algorithms.
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
		 * An indeterminate matrix is validated against the other types.
		 */
		INDETERMINATE,
		/**
		 * A diagonal matrix is decomposed with a simplified method.
		 */
		SIMPLIFIED,
		/**
		 * A symmetric matrix is decomposed with Cholesky's method.
		 */
		CHOLESKY;
	}
	
	
	private Float det;
	private Matrix mat;
	private Matrix l, u;
	private Hints hints;
	private State state;
		
	/**
	 * Creates a new {@code LUCholesky}.
	 * 
	 * @param h  solver hints
	 */
	public LUCholesky(Hints h)
	{
		state = h.State();
		hints = h;
	}
	
	/**
	 * Creates a new {@code LUCholesky}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LUCholesky(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy().destroy();
			if(m.is(Symmetric.Type()))
				b.setOperator(Symmetric.Type());
			if(m.is(Diagonal.Type()))
				b.setOperator(Diagonal.Type());
			return b;
		});
	}

	
	@Override
	public <M extends Matrix> M solve(M b)
	{
		M x = (M) b;
		// Solve through substitution.
		x = new LUTriangular(L()).solve(x);
		x = new LUTriangular(U()).solve(x);
		
		return x;
	}
	
	@Override
	public boolean canSolve(Matrix b)
	{
		if(LinearSystem.super.canSolve(b))
		{
			if(State() == State.INVALID)
			{
				throw new TypeError
				(
					Symmetric.Type()
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
			double d = 1d;
			for(int r = 0; r < mat.Rows(); r++)
			{
				double v = mat.get(r, r);
				d *= v * v;
			}
			
			det = (float) d;
		}

		return det;
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}
	
	@Override
	public Matrix L()
	{
		if(l == null)
		{
			mat = factor();

			int r1 = Hints().Matrix().Rows();
			int c1 = Hints().Matrix().Columns();
			
			
			l = Matrices.create(r1, c1);
			l.setOperator(LowerTriangular.Type());
			
			// Copy from the decomposed matrix.
			for(int r = 0; r < r1; r++)
			{
				for(int c = r; c < c1; c++)
				{
					float v = mat.get(r, c);
					l.set(v, c, r);
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

			
			u = Matrices.create(r1, c1);
			u.setOperator(UpperTriangular.Type());
								
			// Copy from the decomposed matrix.
			for(int r = 0; r < r1; r++)
			{
				for(int c = r; c < c1; c++)
				{
					float v = mat.get(r, c);
					u.set(v, r, c);
				}
			}
		}
		
		return u;
	}
	
	
	Matrix factorSimplified()
	{
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();
		
		// For each row in the matrix...
		for(int r = 0; r < r1; r++)
		{
			// Square root the diagonal.
			float dr = mat.get(r, r);
			dr = Floats.sqrt(dr);
			mat.set(dr, r, r);
		}
		
		return mat;
	}
	
	Matrix factorCholesky()
	{
		double err = Hints().Error();
		int r1 = Hints().Matrix().Rows();
		int c1 = Hints().Matrix().Columns();

		// For each row in the base matrix...
		for(int r = 0; r < r1; r++)
		{
			float d = mat.get(r, r);
			// If the diagonal is negative...
			if(d < err)
			{
				// ...the matrix is not positive definite.
				throw new TypeError(Symmetric.Type());
			}
			
			// For each row below the diagonal...
			for(int s = r + 1; s < r1; s++)
			{
				// Eliminate a superdiagonal column.
				double m = mat.get(r, s) / d;
				for(int c = s; c < c1; c++)
				{
					double v1 = mat.get(s, c);
					double v2 = mat.get(r, c);
					
					float v = (float) (v1 - v2 * m);
					mat.set(v, s, c);
				}
			}
			
			double sqrt = Doubles.sqrt(d);
			// For each row below the diagonal...
			for(int c = r; c < c1; c++)
			{
				// Divide the diagonal element.
				float v = mat.get(r, c);
				v = (float) (v / sqrt);
				mat.set(v, r, c);
			}
		}
		
		return mat;
	}
	
	Matrix factor()
	{
		if(mat == null)
		{
			mat = Hints().Matrix();
			switch(State())
			{
			case SIMPLIFIED:
				mat = factorSimplified(); break;
			case CHOLESKY:
				mat = factorCholesky(); break;
			case INVALID:
			default:
				break;
			}
		}

		return mat;
	}

	State State()
	{
		if(state == State.INDETERMINATE)
		{
			double e = Hints().Error();
			Matrix a = Hints().Matrix();
			Banded ops = Banded.Type(a, e);
			
			state = State.INVALID;
			if(ops instanceof Diagonal)
				state = State.SIMPLIFIED;
			else
			{
				if(a.allows(Symmetric.Type(), e))
					state = State.CHOLESKY;
			}
		}

		return state;
	}
}