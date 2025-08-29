package waffles.utils.alg.linear.solvers.ortho;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Identity;
import waffles.utils.alg.linear.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.linear.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.linear.solvers.Solver;
import waffles.utils.alg.linear.solvers.exact.types.LUCrout;
import waffles.utils.alg.linear.solvers.factor.QSFactor;
import waffles.utils.alg.utilities.Algorithmic.Iterative;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;

/**
 * The {@code ORNewton} algorithm computes a nearest orthogonal with a {@code QSFactor}
 * factorization method. This method computes the polar decomposition of a {@code Square}
 * matrix using Newton's method. The decomposition takes the form of {@code M = QS}
 * where Q is an {@code Orthogonal} matrix and S a {@code Symmetric} matrix.
 *
 * @author Waffles
 * @since 29 Aug 2025
 * @version 1.1
 *
 * 
 * @see <a href="https://epubs.siam.org/doi/abs/10.1137/0907079">Polar Decomposition</a>
 * @see Orthogonalizer
 * @see QSFactor
 */
public class ORNewton implements Orthogonalizer, QSFactor
{
	/**
	 * The {@code Hints} interface defines hints for an {@code ORNewton}.
	 *
	 * @author Waffles
	 * @since 24 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Iterative
	 * @see Solver
	 */
	@FunctionalInterface
	public static interface Hints extends Solver.Hints, Iterative
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
			if(Matrix().is(Orthogonal.Type()))
				return State.ORTHOGONAL;
			if(Matrix().is(Symmetric.Type()))
				return State.SYMMETRIC;
			if(Matrix().allows(Square.Type(), 0))
				return State.SQUARE;
			return State.INVALID;
		}
		
		
		@Override
		public default double Error()
		{
			return Doubles.pow(2, -16);
		}
	}
	
	/**
	 * A {@code State} defines the types of {@code ORNewton} algorithms.
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
		 * An orthogonal matrix is not decomposed at all.
		 */
		ORTHOGONAL,
		/**
		 * A symmetric matrix is not decomposed at all.
		 */
		SYMMETRIC,
		/**
		 * A square matrix is decomposed with Newton's method.
		 */
		SQUARE;
	}
	
	
	private Matrix s;
	private Matrix p, q;
	private LUCrout luc;
	private Hints hints;
		
	/**
	 * Creates a new {@code ORNewton}.
	 * 
	 * @param h  solver hints
	 */
	public ORNewton(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code ORNewton}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public ORNewton(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy();
			b.setOperator(m.Operator());
			return b.destroy();
		});
	}


	@Override
	public Matrix orthogonalize()
	{
		return Q();
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}

	@Override
	public Matrix Q()
	{
		if(q == null)
			q = factor();
		return q;
	}

	@Override
	public Matrix S()
	{
		if(s == null)
			q = factor();
		return s;
	}
	
	
	Matrix factorNewton()
	{
		float np = 0f;
		float nq = 0f;

		double err = Hints().Error();
		while(err * np <= nq)
		{
			p = q;
			luc = new LUCrout(p);
			s = luc.inverse().transpose();
			q = p.plus(s).times(0.5f);
			
			nq = q.minus(p).norm1();
			np = p.norm1();
		}
		
		
		q.setOperator(Orthogonal.Type());
		
		s = Hints().Matrix();
		s = q.transpose().times(s);
		s = s.plus(s.transpose()).times(0.5f);
		s.setOperator(Symmetric.Type());
		
		return q;
	}
	
	Matrix factor()
	{
		if(q == null)
		{
			q = Hints().Matrix();
			int c1 = q.Columns();
			int r1 = q.Rows();

			switch(Hints().State())
			{
			case ORTHOGONAL:
			{
				s = Matrices.identity(r1);
				s.setOperator(Identity.Type());
				
				break;
			}
			case SYMMETRIC:
			{
				s = q;
				q = Matrices.identity(c1);
				q.setOperator(Identity.Type());
				
				break;
			}
			case SQUARE:
				q = factorNewton(); break;
			case INVALID:
			default:
				break;
			}
		}

		return q;
	}
}