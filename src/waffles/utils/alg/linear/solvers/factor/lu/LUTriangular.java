package waffles.utils.alg.linear.solvers.factor.lu;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.banded.Banded;
import waffles.utils.alg.linear.measure.matrix.types.banded.Diagonal;
import waffles.utils.alg.linear.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.linear.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.linear.solvers.Solver;
import waffles.utils.alg.linear.solvers.exact.LinearSystem;
import waffles.utils.alg.linear.solvers.factor.LUFactor;
import waffles.utils.alg.utilities.errors.InvertibleError;
import waffles.utils.alg.utilities.errors.TypeError;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code LUTriangular} algorithm solves exact linear systems through substitution.
 * This solver fails if anything other than a {@code Triangular} matrix is used.
 *
 * @author Waffles
 * @since Jul 9, 2018
 * @version 1.1
 * 
 * 
 * @see LinearSystem
 * @see LUFactor
 */
public class LUTriangular implements LinearSystem, LUFactor
{	
	/**
	 * The {@code Hints} interface defines hints for an {@code LUTriangular}.
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
		 * This determines which triangular algorithm
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
				return State.DIAGONAL;
			if(Matrix().is(LowerTriangular.Type()))
				return State.LOWER_TRIANGULAR;
			if(Matrix().is(UpperTriangular.Type()))
				return State.UPPER_TRIANGULAR;
			return State.INDETERMINATE;
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code LUTriangular} algorithms.
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
		 * A lower triangular matrix is solved with forward substitution.
		 */
		LOWER_TRIANGULAR,
		/**
		 * An upper triangular matrix is solved with backward substitution.
		 */
		UPPER_TRIANGULAR,
		/**
		 * A diagonal matrix is solved with scalar substitution.
		 */
		DIAGONAL;
	}
	
	
	private Float det;
	private Matrix inv;
	private Matrix l, u;
	private Hints hints;
	private State state;
	
	/**
	 * Creates a new {@code LUTriangular}.
	 * 
	 * @param h  solver hints
	 */
	public LUTriangular(Hints h)
	{
		state = h.State();
		hints = h;
	}
	
	/**
	 * Creates a new {@code LUTriangular}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public LUTriangular(Matrix m)
	{
		this(() -> m);
	}
	
			
	@Override
	public <M extends Matrix> M solve(M b)
	{
		switch(State())
		{
		case DIAGONAL:
			return (M) scalar(b);
		case LOWER_TRIANGULAR:
			return (M) forward(b);
		case UPPER_TRIANGULAR:
			return (M) backward(b);
		case INDETERMINATE:
		case INVALID:
		default:
			return null;
		}
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
					LowerTriangular.Type(),
					UpperTriangular.Type(),
					Diagonal.Type()
				);
			}

			return canInvert();
		}
		
		return false;
	}
		
	@Override
	public float determinant()
	{
		if(det == null)
		{
			double d = 1d;
			Matrix mat = Hints().Matrix();
			for(int r = 0; r < mat.Rows(); r++)
			{
				d *= mat.get(r, r);
			}
			
			det = (float) d;
		}
		
		return det;
	}
	
	@Override
	public Matrix inverse()
	{
		if(inv == null)
		{
			Matrix mat = Hints().Matrix();
			inv = LinearSystem.super.inverse();
			inv.setOperator(mat.Operator());
		}
		
		return inv;
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
			Matrix a = Hints().Matrix();
			
			int c = a.Columns();
			switch(State())
			{
			case DIAGONAL:
			case LOWER_TRIANGULAR:
				l = a.copy();
			case UPPER_TRIANGULAR:
				l = Matrices.identity(c);
			case INDETERMINATE:
			case INVALID:
			default:
				l = Matrices.identity(0);
			}
			
			l.setOperator(LowerTriangular.Type());
		}
		
		return l;
	}
	
	@Override
	public Matrix U()
	{
		if(u == null)
		{
			Matrix a = Hints().Matrix();
			
			int c = a.Columns();
			switch(State())
			{
			case DIAGONAL:
			case UPPER_TRIANGULAR:
				u = a.copy();
			case LOWER_TRIANGULAR:
				u = Matrices.identity(c);
			case INDETERMINATE:
			case INVALID:
			default:
				u = Matrices.identity(0);
			}
			
			u.setOperator(UpperTriangular.Type());
		}
		
		return u;
	}
	
					
	Matrix backward(Matrix b)
	{
		double e = Hints().Error();
		Matrix a = Hints().Matrix();
		
		int c1 = a.Columns();
		int c2 = b.Columns();
		int r1 = a.Rows();


		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int k = 0; k < c2; k++)
		{
			// ...perform backward substitution.
			for(int i = r1 - 1; i >= 0; i--)
			{	
				float v = a.get(i, i);
				// If a diagonal element is zero...
				if(Floats.abs(v) <= e)
				{
					// ...the matrix is not invertible.
					throw new InvertibleError();
				}
				
				
				float w = 0;
				for(int j = i + 1; j < c1; j++)
				{
					w += a.get(i, j) * x.get(j, k);
				}
				
				v = (x.get(i, k) - w) / v;
				x.set(v, i, k);
			}
		}
			
		return x;
	}
	
	Matrix forward(Matrix b)
	{
		double e = Hints().Error();
		Matrix a = Hints().Matrix();
		
		int c2 = b.Columns();
		int r1 = a.Rows();


		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int c = 0; c < c2; c++)
		{
			// ...perform forward substitution.
			for(int r = 0; r < r1; r++)
			{
				float v = a.get(r, r);
				// If a diagonal element is zero...
				if(Floats.abs(v) <= e)
				{
					// ...the matrix is not invertible.
					throw new InvertibleError();
				}
				
				
				float w = 0;
				for(int s = 0; s < r; s++)
				{
					w += a.get(r, s) * x.get(s, c);
				}
				
				v = (x.get(r, c) - w) / v;
				x.set(v, r, c);
			}
		}
		
		return x;
	}
	
	Matrix scalar(Matrix b)
	{
		double e = Hints().Error();
		Matrix a = Hints().Matrix();
		
		int c2 = b.Columns();
		int r1 = a.Rows();

		
		Matrix x = b.copy();
		// For each column in the solution matrix...
		for(int c = 0; c < c2; c++)
		{
			// ...perform matrix scaling.
			for(int r = 0; r < r1; r++)
			{
				float v = a.get(r, r);
				// If a diagonal element is zero...
				if(Floats.abs(v) <= e)
				{
					// ...the matrix is not invertible.
					throw new InvertibleError();
				}
				
				v = x.get(r, c) / v;
				x.set(v, r, c);
			}
		}
		
		return x;
	}
	
	State State()
	{
		if(state == State.INDETERMINATE)
		{
			double e = Hints().Error();
			Matrix a = Hints().Matrix();
			Banded ops = Banded.Type(a, e);
			
			state = State.INVALID;
			if(ops instanceof LowerTriangular)
				state = State.LOWER_TRIANGULAR;
			if(ops instanceof UpperTriangular)
				state = State.UPPER_TRIANGULAR;
			if(ops instanceof Diagonal)
				state = State.DIAGONAL;
		}

		return state;
	}
}