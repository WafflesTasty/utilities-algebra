package waffles.utils.alg.lin.solvers.matrix.eigen.types;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.solvers.matrix.eigen.EigenPair;
import waffles.utils.alg.lin.solvers.matrix.eigen.EigenSolver;
import waffles.utils.alg.utilities.matrix.Rayleigh;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code ESPower} algorithm approximates eigenvectors through power iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * 
 * @see EigenSolver
 */
public class ESPower implements EigenSolver
{
	private Hints hints;
	
	/**
	 * Creates a new {@code EVInverse}.
	 * 
	 * @param h  solver hints
	 * 
	 * 
	 * @see EigenSolver
	 */
	public ESPower(Hints h)
	{
		hints = h;
	}
	
	/**
	 * Creates a new {@code EVInverse}.
	 * 
	 * @param m  a base matrix
	 * 
	 * 
	 * @see EigenSolver
	 */
	public ESPower(Matrix m)
	{
		this(() ->
		{
			Matrix b = m.copy().destroy();
			if(m.is(Square.Type()))
				b.setOperator(Square.Type());
			return b;
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
			Vector v = w.normalize();
			double err = Doubles.pow(e, 4);
			while(err < norm)
			{
				// Iterate the vector.
				w = m.times(v);
				
				// Calculate the error.
				float dot = v.dot(w);
				v = w.minus(v.times(dot));
				norm = v.normSqr();

				v = w.normalize();
				if(lMax <= ++loops)
					break;
			}
			
			l = Rayleigh.coefficient(m, v);
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