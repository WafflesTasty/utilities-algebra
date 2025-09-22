package waffles.utils.alg.lin.solvers.matrix.eigen.types;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.solvers.matrix.eigen.EigenPair;
import waffles.utils.alg.lin.solvers.matrix.eigen.EigenSolver;
import waffles.utils.alg.lin.solvers.matrix.exact.types.LUCrout;
import waffles.utils.alg.utilities.matrix.Rayleigh;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code ESPower} algorithm approximates eigenvectors through {@code Rayleigh} iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * 
 * @see EigenSolver
 */
public class ESRayleigh implements EigenSolver
{
	/**
	 * The {@code Hints} interface defines settings for an {@code ESRayleigh}.
	 *
	 * @author Waffles
	 * @since 26 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see EigenSolver
	 */
	@FunctionalInterface
	public static interface Hints extends EigenSolver.Hints
	{
		@Override
		public default double Error()
		{
			return Doubles.pow(2, -32);
		}
	}
	
	
	private Hints hints;
	private LUCrout slv;
	
	/**
	 * Creates a new {@code ESRayleigh}.
	 * 
	 * @param h  solver hints
	 * 
	 * 
	 * @see EigenSolver
	 */
	public ESRayleigh(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code ESRayleigh}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see EigenSolver
	 */
	public ESRayleigh(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy().destroy();
			if(m.is(Square.Type()))
				b.setOperator(Square.Type());
			return b;
		});
	}

	
	LUCrout ShiftedCrout(Matrix s)
	{
		return new LUCrout(new LUCrout.Hints()
		{
			@Override
			public Matrix Matrix()
			{
				return s;
			}

			@Override
			public double Error()
			{
				return ESRayleigh.this.Hints().Error();
			}
		});
	}
	
	@Override
	public EigenPair approx(EigenPair est)
	{
		if(Hints().State() == State.VALID)
		{
			Vector w = est.Key();
			float l = est.Value();
			
			double e = Hints().Error();
			Matrix m = Hints().Matrix();
			int lMax = Hints().MaxLoops();
			float norm = Floats.MAX_VALUE;
			
			
			int loops = 0;
			Matrix s = null;
			Vector v = w.normalize();
			double err = Doubles.pow(e, 4);
			while(err < norm)
			{
				// Iterate the vector.
				l = Rayleigh.coefficient(m, v);
				s = Matrices.shift(m, -l);
				slv = ShiftedCrout(s);
				w = slv.solve(v);
				
				// Calculate the error.
				float dot = v.dot(w);
				v = w.minus(v.times(dot));
				norm = v.normSqr();

				v = w.normalize();
				if(lMax <= ++loops)
					break;
			}
			
			return new EigenPair(v, l);
		}

		return null;
	}
	
	@Override
	public Hints Hints()
	{
		return hints;
	}
}