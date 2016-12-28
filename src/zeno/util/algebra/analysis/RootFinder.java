package zeno.util.algebra.analysis;

import java.util.ArrayList;
import java.util.List;

import zeno.util.algebra.tensors.vectors.complex.Complex;
import zeno.util.tools.Array;
import zeno.util.tools.Messenger;
import zeno.util.tools.primitives.Floats;

/**
 * The {@code RootFinder} class uses {@code Bairstow's method}
 * to calculate roots of a {@code Polynomial}.
 *
 * @since May 5, 2016
 * @author Zeno
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Bairstow's_method">Bairstow&rsquo;s Method</a>
 * @see Messenger
 */
public class RootFinder extends Messenger
{
	private static final int DEF_ITERATIONS = 1024;
	
	
	private Polynomial poly;
	private List<Complex> roots;
	private final int maxiterations;
	
	/**
	 * Creates a new {@code RootFinder}.
	 * 
	 * @param iterations  a max iteration count
	 */
	public RootFinder(int iterations)
	{
		maxiterations = iterations;
	}
	
	/**
	 * Creates a new {@code RootFinder}.
	 */
	public RootFinder()
	{
		this(DEF_ITERATIONS);
	}
	
	
	/**
	 * Finds the real roots of a {@code Polynomial}.
	 * 
	 * @param p  a polynomial to check
	 * @return  the polynomial's real roots
	 * @see Polynomial
	 */
	public float[] findRealRoots(Polynomial p)
	{
		findRoots(p);
		
		for(int i = 0; i < roots.size(); i++)
		{
			Complex root = roots.get(i);
			if(!root.isReal())
			{
				roots.remove(i);
				i--;
			}
		}
		
		float[] array = new float[roots.size()];
		for(int i = 0; i < roots.size(); i++)
		{
			Complex root = roots.get(i);
			array[i] = root.Real();
		}
		
		return array;
	}

	/**
	 * Finds the complex roots of a {@code Polynomial}.
	 * 
	 * @param p  a polynomial to check
	 * @return  the polynomial's complex roots
	 * @see Polynomial
	 * @see Complex
	 */
	public Complex[] findComplexRoots(Polynomial p)
	{
		findRoots(p);
		
		Complex[] array = new Complex[roots.size()];
		for(int i = 0; i < roots.size(); i++)
		{
			array[i] = roots.get(i);
		}
		
		return array;
	}
		
	
	private float disc(double a, double b, double c)
	{
		return (float) (b * b - 4 * a * c);
	}
	
	private void findQuadratic(Polynomial p)
	{
		sendMessage("Solving a second degree polynomial: " + p);
		
		float c = p.getValue(0);
		float b = p.getValue(1);
		float a = p.getValue(2);
		
		
		// Special case: b = 0.
		
		if(b == 0)
		{
			if(a == 0)
			{
				// Either no or infinite solutions.
				
				return;
			}
			
			// Pure real roots.
			
			float val = -c / a;
			if(val > 0)
			{
				float val1 = -Floats.sqrt(val);
				float val2 =  Floats.sqrt(val);
				
				Complex root1 = new Complex(val1, 0);
				Complex root2 = new Complex(val2, 0);
				sendMessage("Found root: " + root1);
				sendMessage("Found root: " + root2);
				roots.add(root1);
				roots.add(root2);
				return;
			}
			
			// Pure imaginary roots.
			
			float val1 = -Floats.sqrt(-val);
			float val2 =  Floats.sqrt(-val);
			
			Complex root1 = new Complex(0, val1);
			Complex root2 = new Complex(0, val2);
			sendMessage("Found root: " + root1);
			sendMessage("Found root: " + root2);
			roots.add(root1);
			roots.add(root2);
			return;
		}
		
				
		float disc = disc(a, b, c);
		sendMessage("Discriminant: " + disc);
		
		if(disc == 0)
		{
			float val = - b / (2 * a);
			
			Complex root = new Complex(val, 0);
			sendMessage("Found root: " + root);
			roots.add(root);
			return;
		}
		
		if(disc > 0)
		{
			float sqrt = Floats.sign(b) * Floats.sqrt(disc);
			float val1 = -(b + sqrt) / (2 * a);
			float val2 = c / (a * val1);
			
			Complex root1 = new Complex(val1, 0);
			Complex root2 = new Complex(val2, 0);
			sendMessage("Found root: " + root1);
			sendMessage("Found root: " + root2);
			roots.add(root1);
			roots.add(root2);
			return;
		}
		
		float sqrt = Floats.sqrt(-disc);
		float ima1 = - sqrt / (2 * a);
		float ima2 = + sqrt / (2 * a);
		float real = - b / (2 * a);
		
		Complex root1 = new Complex(real, ima1);
		Complex root2 = new Complex(real, ima2);
		sendMessage("Found root: " + root1);
		sendMessage("Found root: " + root2);
		roots.add(root1);
		roots.add(root2);
		return;
	}

	private void findLinear(Polynomial p)
	{
		sendMessage("Solving first degree polynomial: " + p);
		
		float coef0 = p.getValue(0);
		float coef1 = p.getValue(1);
		float val = - coef0 / coef1;
		
		Complex root = new Complex(val, 0);
		sendMessage("Found root: " + root);
		roots.add(root);
	}
	
	private void findHigher(Polynomial p)
	{				
		sendMessage("Solving higher degree polynomial: " + p);
		
		int deg = p.Degree();
		float[] coef = p.Coefficients();
		float[] bArr = new float[deg + 1];
		float[] fArr = new float[deg + 1];
		
		
		float uErr = 1;
		float vErr = 1;
		
		// Initial guesses for u and v.
		float u = coef[deg - 1] / coef[deg];
		float v = coef[deg - 2] / coef[deg];
		
		if(u == 0) u = 1;
		if(v == 0) v = 1;
		
		
		for(int i = 0; i < maxiterations; i++)
		{
			// Approximate quotients.
			for(int d = deg - 2; d >= 0; d--)
			{
				bArr[d] = coef[d+2] - u * bArr[d+1] - v * bArr[d+2];
				fArr[d] = bArr[d+2] - u * fArr[d+1] - v * fArr[d+2];
			}
			
			
			// Approximate first remainder.
			float c = coef[1] - u * bArr[0] - v * bArr[1];
			float d = coef[0] - v * bArr[0];
			
			// Approximate second remainder.
			float g = bArr[1] - u * fArr[0] - v * fArr[1];
			float h = bArr[0] - v * fArr[0];
			
			
			// Calculate new error values.
			float dnom = h * h - g * h * u + g * g * v;
			float vnum = d * h - d * g * u + c * g * v;
			float unum = c * h - d * g;
			
			uErr = unum / dnom;
			vErr = vnum / dnom;
			
			
			// Calculate new divisor.
			u += uErr;
			v += vErr;

			// Check last error margin.
			if(uErr == 0 && vErr == 0)
			{
				sendMessage("Iterations: " + i);
				break;
			}
		}

		
		Polynomial result = new Polynomial(1, u, v);
		poly = new Polynomial(Array.reverse.of(bArr));
		
		sendMessage("Divisor: " + result);
		sendMessage("Remainder: " + poly);
		
		findQuadratic(result);
	}

	private void findRoots(Polynomial p)
	{
		poly = p.copy();
		roots = new ArrayList<>();
		
		while(poly.Degree() > 0)
		{
			if(poly.Degree() == 1)
			{
				findLinear(poly);
				break;
			}
			
			if(poly.Degree() == 2)
			{
				findQuadratic(poly);
				break;
			}

			findHigher(poly);
		}
	}
}