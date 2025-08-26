package waffles.utils.alg.linear.solvers.eigen.types;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.vector.Vector;
import waffles.utils.alg.linear.solvers.eigen.EigenPair;
import waffles.utils.alg.linear.solvers.eigen.EigenSolver;
import waffles.utils.alg.linear.solvers.factor.lu.LUCrout;
import waffles.utils.algebra.elements.linear.Matrices;
import waffles.utils.tools.primitives.Doubles;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code EVInverse} algorithm approximates eigenvectors through inverse iteration.
 *
 * @author Waffles
 * @since Jul 14, 2018
 * @version 1.0
 * 
 * 
 * @see EigenSolver
 */
public class ESInverse implements EigenSolver
{
	private Hints hints;
	private LUCrout slv;
	
	/**
	 * Creates a new {@code EVInverse}.
	 * 
	 * @param h  solver hints
	 * 
	 * 
	 * @see EigenSolver
	 */
	public ESInverse(Hints h)
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
	public ESInverse(Matrix m)
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
			
			Matrix s = Matrices.shift(m, -l);
			float norm = Floats.MAX_VALUE;
			slv = new LUCrout(s);
			
			
			int loops = 0;
			Vector v = w.normalize();
			double err = Doubles.pow(e, 4);
			while(err < norm)
			{
				// Iterate the vector.
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