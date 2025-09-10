package waffles.utils.alg.linear.solvers.matrix.ranks.types;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.banded.Diagonal;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.Tall;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.matrix.factor.UVFactor;
import waffles.utils.alg.linear.solvers.matrix.factor.uv.UVBidiagonal;
import waffles.utils.alg.linear.solvers.matrix.ranks.RankReveal;
import waffles.utils.alg.linear.solvers.matrix.square.LeastSquares;
import waffles.utils.alg.linear.solvers.matrix.square.Spectral;
import waffles.utils.alg.utilities.Algorithmic.Iterative;
import waffles.utils.alg.utilities.matrix.Givens;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.algebra.elements.linear.Vectors;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code RRSVD} algorithm computes rank through {@code SVD} factorization.
 * This method factorizes a matrix {@code M = UEV*} where U, V are both (reduced)
 * {@code Orthogonal} matrices, and E a {@code Diagonal} singular value matrix.
 *
 * @author Waffles
 * @since Jul 10, 2018
 * @version 1.0 
 * 
 * 
 * @see <a href="https://epubs.siam.org/doi/abs/10.1137/0911052">James Demmel & William Kahan, "Accurate singular values of bidiagonal matrices."</a>
 * @see LeastSquares
 * @see RankReveal
 * @see Spectral
 * @see UVFactor
 */
public class RRSVD implements UVFactor, LeastSquares, RankReveal, Spectral
{
	/**
	 * The {@code Hints} interface defines hints for an {@code RRSVD}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Iterative
	 * @see UVFactor
	 */
	@FunctionalInterface
	public static interface Hints extends UVFactor.Hints, Iterative
	{
		/**
		 * Returns the state of the {@code Hints}.
		 * This determines which least squares algorithm
		 * will be used to solve the linear system.
		 * 
		 * @return  an algorithm state
		 * 
		 * 
		 * @see State
		 */
		public default State State()
		{
			if(Matrix().allows(Tall.Type(), 0))
				return State.TALL;
			return State.WIDE;
		}

		
		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * The {@code DiagonalHints} define hints for an {@code UVBidiagonal} solver.
	 *
	 * @author Waffles
	 * @since 02 Sep 2025
	 * @version 1.1
	 *
	 * 
	 * @see UVBidiagonal
	 */
	public class DiagonalHints implements UVBidiagonal.Hints
	{
		@Override
		public Matrix Matrix()
		{
			return Hints().Matrix();
		}
		
		@Override
		public double Error()
		{
			return Hints().Error();
		}
	}
			
	/**
	 * A {@code State} defines the types of {@code RRSVD} algorithms.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 */
	public static enum State
	{
		/**
		 * A tall matrix is factorized as upper bidiagonal.
		 */
		TALL,
		/**
		 * A wide matrix is factorized as lower bidiagonal.
		 */
		WIDE;
	}
	
	
	private int rank;
	private Hints hints;
	private UVBidiagonal uvb;
	private Matrix e, f;
	private Matrix u, v;
	private Vector sv;
	
	/**
	 * Creates a new {@code RRSVD}.
	 * 
	 * @param h  solver hints
	 */
 	public RRSVD(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code RRSVD}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public RRSVD(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}
	
	
	/**
	 * Returns the E matrix of the {@code RRSVD}.
	 * 
	 * @return  a singular value matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix E()
	{
		if(e == null)
			e = factor();
		return e;
	}
	
	/**
	 * Returns the F matrix of the {@code RRSVD}.
	 * This is the inverse of the E matrix.
	 * 
	 * @return  a singular value matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public Matrix F()
	{
		if(f == null)
		{
			int r1 = E().Rows();
			int c1 = E().Columns();
			
			f = Matrices.create(c1, r1);
			for(int k = 0; k < Integers.min(r1, c1); k++)
			{
				f.set(1f / e.get(k, k), k, k);
			}
		}
				
		return f;
	}

	
	@Override
	public Vector SingularValues()
	{
		if(e == null)
			e = factor();
		if(sv == null)
		{
			int r1 = E().Rows();
			int c1 = E().Columns();
			int d = Integers.min(r1, c1);
			
			sv = Vectors.create(d);
			for(int k = 0; k < d; k++)
			{
				sv.set(E().get(k, k), k);
			}
		}

		return sv;
	}
	
	@Override
	public <M extends Matrix> M approx(M b)
	{
		// Solve through substitution.
		Matrix x = U().transpose();
		x = F().times(x.times(b));
		return (M) V().times(x);
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}
	
	@Override
	public Matrix U()
	{
		if(u == null)
			e = factor();
		return u;
	}
	
	@Override
	public Matrix V()
	{
		if(v == null)
			e = factor();
		return v;
	}
	
	@Override
	public int rank()
	{
		if(e == null)
			e = factor();
		return rank;
	}
	
	
	int computeRank()
	{
		int r1 = e.Rows();
		int c1 = e.Columns();

		
		Vector v = SingularValues();
		for(int k = 0; k < Integers.min(r1, c1); k++)
		{
			rank++;

			float s = Floats.abs(v.get(k));
			if(s < Hints().Error())
			{
				return rank;
			}
		}
		
		return rank++;
	}
		
	boolean isDiagonal()
	{
		int r1 = E().Rows();
		int c1 = E().Columns();
		
		double err = Hints().Error();
		for(int k = 1; k < Integers.min(r1, c1); k++)
		{
			float l = E().get(k, k-1);
			float u = E().get(k-1, k);
			
			if(err < Floats.abs(l)
			|| err < Floats.abs(u))
				return false;
		}
		
		return true;
	}
	
	Matrix factorTall()
	{
		int r1 = e.Rows();
		int c1 = e.Columns();
	
		// For every diagonal in the base matrix...
		for(int k = 1; k < Integers.min(r1, c1); k++)
		{
			float x = e.get(k-1, k-1);
			float y = e.get(k-1, k-0);

			// If the upper diagonal is non-zero...
			if(Hints().Error() < Floats.abs(y))
			{
				// ...perform a right Givens rotation.
				Matrix rg = Givens.right(e, k);
				
				v = v.times(rg);
				e = e.times(rg);
			}

			float z = e.get(k-0, k-1);

			// If the lower diagonal is non-zero...
			if(Hints().Error() < Floats.abs(z))
			{
				// ...perform a left Givens rotation.
				Matrix lg = Givens.left(e, k);
				Matrix lt = lg.transpose();
				
				u = u.times(lt);
				e = lg.times(e);
			}
		}
		
		return e;
	}
	
	Matrix factorWide()
	{
		int r1 = e.Rows();
		int c1 = e.Columns();
		
		// For every diagonal in the base matrix...
		for(int k = 1; k < Integers.min(r1, c1); k++)
		{
			float x = e.get(k-1, k-1);
			float z = e.get(k-0, k-1);

			// If the lower diagonal is non-zero...
			if(Hints().Error() < Floats.abs(z))
			{
				// ...perform a left Givens rotation.
				Matrix lg = Givens.left(e, k);
				Matrix lt = lg.transpose();
				
				u = u.times(lt);
				e = lg.times(e);
			}

			float y = e.get(k-1, k-0);
			
			// If the upper diagonal is non-zero...
			if(Hints().Error() < Floats.abs(y))
			{
				// ...perform a right Givens rotation.
				Matrix rg = Givens.right(e, k);
				
				v = v.times(rg);
				e = e.times(rg);
			}
		}
		
		return e;
	}
	
	Matrix reduceTall()
	{
		int r1 = e.Rows();
		int c1 = e.Columns();
		

		Matrix sgn = Matrices.identity(c1);
		sgn.setOperator(Diagonal.Type());
		for(int k = 0; k < c1; k++)
		{
			if(e.get(k, k) < 0f)
			{
				sgn.set(-1f, k, k);
			}
		}
		
		// Singular values have to be positive.		
		e = e.times(sgn);
		v = v.times(sgn);
		
		
		rank = computeRank();
		if(e.allows(Square.Type(), 0) || !Hints().isReduced())
		{
			u.setOperator(Orthogonal.Type());
			v.setOperator(Orthogonal.Type());
		}
		else
		{			
			e = e.resize(c1, c1);
			v.setOperator(Orthogonal.Type());
			u = u.resize(r1, c1);
		}

		return e;
	}
	
	Matrix reduceWide()
	{
		int r1 = e.Rows();
		int c1 = e.Columns();
		

		Matrix sgn = Matrices.identity(r1);
		sgn.setOperator(Diagonal.Type());
		for(int k = 0; k < r1; k++)
		{
			if(e.get(k, k) < 0f)
			{
				sgn.set(-1f, k, k);
			}
		}
		
		// Singular values have to be positive.		
		e = sgn.times(e);
		u = u.times(sgn);
		
		
		rank = computeRank();
		if(e.allows(Square.Type(), 0) || !Hints().isReduced())
		{
			u.setOperator(Orthogonal.Type());
			v.setOperator(Orthogonal.Type());
		}
		else
		{
			e = e.resize(r1, r1);
			u.setOperator(Orthogonal.Type());
			v = v.resize(c1, r1);
		}

		return e;
	}
	
	Matrix factor()
	{
		if(e == null)
		{
			uvb = new UVBidiagonal(new DiagonalHints());
			u = uvb.U(); v = uvb.V();
			e = uvb.B();


			for(int i = 0; i < Hints().MaxLoops(); i++)
			{
				switch(Hints().State())
				{
				case TALL:
					e = factorTall(); break;
				case WIDE:
					e = factorWide(); break;
				default:
					break;
				}

				if(isDiagonal())
					break;
			}

			switch(Hints().State())
			{
			case TALL:
				e = reduceTall(); break;
			case WIDE:
				e = reduceWide(); break;
			default:
				break;
			}
		}

		return e;
	}

}