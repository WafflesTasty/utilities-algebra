package waffles.utils.alg.poly.solvers;

import java.util.Iterator;

import waffles.utils.alg.lin.measure.vector.complex.Complex;
import waffles.utils.alg.poly.Polynomial;
import waffles.utils.alg.poly.PolynomialSolver;
import waffles.utils.alg.utilities.Algorithmic;
import waffles.utils.sets.queues.Queue;
import waffles.utils.sets.queues.wrapper.FIFOQueue;
import waffles.utils.tools.primitives.Array;
import waffles.utils.tools.primitives.Doubles;

/**
 * A {@code RootFinder} iterates over all roots in a {@code Polynomial}.
 *
 * @author Waffles
 * @since 27 Aug 2023
 * @version 1.0
 * 
 * 
 * @see PolynomialSolver
 * @see Iterator
 * @see Complex
 */
public class RootFinder implements PolynomialSolver, Iterator<Complex>
{
	/**
	 * The {@code Hints} interface defines settings for a {@code RootFinder}.
	 *
	 * @author Waffles
	 * @since 19 Sep 2025
	 * @version 1.1
	 *
	 * 
	 * @see Algorithmic
	 */
	public static interface Hints extends Algorithmic.Iterative, PolynomialSolver.Hints
	{
		// NOT APPLICABLE
	}


	private Hints hints;
	private Polynomial poly;
	private Queue<Complex> next;
	
	/**
	 * Creates a new {@code RootFinder}.
	 * 
	 * @param h  algorithm hints
	 * 
	 * 
	 * @see Hints
	 */
	public RootFinder(Hints h)
	{
		hints = h;
		next = new FIFOQueue<>();
		poly = Hints().Polynomial();
		poly = findNext();
	}
	
	/**
	 * Creates a new {@code RootFinder}.
	 * 
	 * @param p  a target polynomial
	 * 
	 * 
	 * @see Polynomial
	 */
	public RootFinder(Polynomial p)
	{
		this(() -> p.copy());
	}
	
	
	Polynomial findNext()
	{
		switch(poly.Degree())
		{
		case 0:
			break;
		case 1:
		{
			findLinear(poly);
			poly = new Polynomial(0d);
			break;
		}
		case 2:
		{
			findQuadratic(poly);
			poly = new Polynomial(0d);
			break;
		}			
		default:
			findHigher(poly); break;
		}
		
		return poly;
	}
	
	void findHigher(Polynomial p)
	{
		int deg = p.Degree();
		double err = Hints().Error();
		
		double[] bArr = new double[deg + 1];
		double[] fArr = new double[deg + 1];
		
		
		double uErr = 1;
		double vErr = 1;
		
		// Initial guesses for u and v.
		double u = p.get(deg-1) / p.get(deg);
		double v = p.get(deg-2) / p.get(deg);
		
		if(u == 0) u = 1;
		if(v == 0) v = 1;
		
		
		for(int i = 0; i < Hints().MaxLoops(); i++)
		{
			// Inaccurate quotients.
			for(int d = deg - 2; d >= 0; d--)
			{
				bArr[d] = p.get(d+2) - u * bArr[d+1] - v * bArr[d+2];
				fArr[d] =  bArr[d+2] - u * fArr[d+1] - v * fArr[d+2];
			}
			
			
			// Inaccurate first remainder.
			double c = p.get(1) - u * bArr[0] - v * bArr[1];
			double d = p.get(0) - v * bArr[0];
			
			// Inaccurate second remainder.
			double g = bArr[1] - u * fArr[0] - v * fArr[1];
			double h = bArr[0] - v * fArr[0];
			
			
			// Calculate new error values.
			double dnom = h * h - h * g * u + g * g * v;
			double vnum = d * h - d * g * u + c * g * v;
			double unum = c * h - d * g;
			
			uErr = unum / dnom;
			vErr = vnum / dnom;
			
			// Compute new divisor.
			u += uErr;
			v += vErr;

			
			// Check last error margin.
			if(Doubles.abs(uErr) < err
			&& Doubles.abs(vErr) < err)
			{
				break;
			}
		}

		
		poly = new Polynomial(Array.reverse.of(bArr));
		Polynomial q = new Polynomial(1, u, v);
		findQuadratic(q);
	}
	
	void findQuadratic(Polynomial p)
	{
		double c = p.get(0);
		double b = p.get(1);
		double a = p.get(2);
		
		
		if(b == 0)
		{
			if(a == 0)
			{
				// Either no or infinite solutions.
				return;
			}
			
			// Purely real roots.
			double val = -c / a;
			if(val > 0)
			{
				float val1 = (float) -Doubles.sqrt(val);
				float val2 = (float)  Doubles.sqrt(val);

				next.push(new Complex(val1, 0));
				next.push(new Complex(val2, 0));

				return;
			}
			
			// Purely imaginary roots.
			float val1 = (float) -Doubles.sqrt(-val);
			float val2 = (float)  Doubles.sqrt(-val);
			
			next.push(new Complex(0, val1));
			next.push(new Complex(0, val2));

			return;
		}
		
				
		double disc = b * b - 4 * a * c;
		if(disc == 0)
		{
			float val = (float) (-b / (2 * a));
			
			next.push(new Complex(val, 0));
			
			return;
		}
		
		if(disc > 0)
		{
			double sqrt = Doubles.sign(b) * Doubles.sqrt(disc);
			float val1 = (float) (-(b + sqrt) / (2 * a));
			float val2 = (float) (c / (a * val1));
			
			next.push(new Complex(val1, 0));
			next.push(new Complex(val2, 0));

			return;
		}
		
		double sqrt = Doubles.sqrt(-disc);
		float ima1 = (float) (-sqrt / (2 * a));
		float ima2 = (float) ( sqrt / (2 * a));
		float real = (float) (   -b / (2 * a));
		
		next.push(new Complex(real, ima1));
		next.push(new Complex(real, ima2));	
	}
	
	void findLinear(Polynomial p)
	{
		float coef0 = (float) p.get(0);
		float coef1 = (float) p.get(1);
		float val = - coef0 / coef1;
		
		next.push(new Complex(val, 0));
	}
	
		
	@Override
	public boolean hasNext()
	{
		return !next.isEmpty();
	}

	@Override
	public Complex next()
	{
		Complex c = next.pop();
		poly = findNext();
		return c;
	}

	@Override
	public Hints Hints()
	{
		return hints;
	}
}