package zeno.util.tests;

import zeno.util.algebra.analysis.Polynomial;
import zeno.util.algebra.analysis.RootFinder;
import zeno.util.algebra.vectors.complex.Complex;
import zeno.util.tools.Randomizer;

/**
 * The {@code PolynomialTest} class defines a test case for the {@code Polynomial} implementation.
 *
 * @author Zeno
 * @since May 5, 2016
 * @see Polynomial
 * @see RootFinder
 */
public class PolynomialTest
{
	private static Polynomial poly1 = new Polynomial(0, 1, 0, 1, 0, 1, 0, 1);
	private static Polynomial poly2 = new Polynomial(6, 11, -33, -33, 11, 6);
	
	/**
	 * Executes the {Polynomial} test.
	 * 
	 * @param args  optional arguments
	 * @see String
	 */
	public static void main(String[] args)
	{
		Randomizer.initialize();

		multiplication();
		representation();
		rootfinding();
		accessors();
		division();
		parsing();
		copying();
	}
	
			
	private static void multiplication()
	{
		System.out.println("--------------");
		System.out.println("Multiplication");
		System.out.println("--------------");
		System.out.println();
		
		System.out.println("Polynomial multiplication:");
		System.out.println("Poly 1 times poly 2:");
		System.out.println(poly1.times(poly2));
		System.out.println("Poly 2 times poly 1:");
		System.out.println(poly2.times(poly1));
		System.out.println();
		
		System.out.println("Number multiplication:");
		for(int i = 0; i < 5; i++)
		{
			int val = Randomizer.randomInt(0, 10);
			System.out.println("Multiply by " + val + ":");
			System.out.println(poly2.times(val));
		}
		System.out.println();
		
		System.out.println("Term multiplication:");
		for(int i = 0; i < 5; i++)
		{
			int deg = Randomizer.randomInt(0, 10);
			int val = Randomizer.randomInt(0, 10);
			
			System.out.println("Multiply by [" + deg + "] " + val + ":");
			System.out.println(poly2.times(deg, val));
		}
		System.out.println();
	}
	
	private static void representation()
	{
		System.out.println("--------------");
		System.out.println("Representation");
		System.out.println("--------------");
		System.out.println();
		
		System.out.print("Polynomial 1: ");
		System.out.println(poly1);
		System.out.print("Polynomial 2: ");
		System.out.println(poly2);
		System.out.println();
	}
		
	private static void rootfinding()
	{
		System.out.println("------------");
		System.out.println("Root Finding");
		System.out.println("------------");
		System.out.println();
		
		Complex[] roots;
		
		System.out.println("Roots 1: ");
		roots = poly1.ComplexRoots();
		for(Complex root : roots)
		{
			System.out.println(root);
		}
		System.out.println();
		
		System.out.println("Roots 2: ");
		roots = poly2.ComplexRoots();
		for(Complex root : roots)
		{
			System.out.println(root);
		}
		System.out.println();
	}

	private static void accessors()
	{
		System.out.println("---------");
		System.out.println("Accessors");
		System.out.println("---------");
		System.out.println();
		
		int deg1 = poly1.Degree();
		int deg2 = poly2.Degree();
		
		System.out.print("Degree 1: ");
		System.out.println(deg1);
		System.out.print("Degree 2: ");
		System.out.println(deg2);
		System.out.println();
		
		System.out.println("Coëfficients 1:");
		for(int i = 0; i <= deg1; i++)
		{
			float val = poly1.getValue(i);
			System.out.println("[" + i + "] " + val);
		}
		System.out.println();
		
		System.out.println("Coëfficients 2:");
		for(int i = 0; i <= deg2; i++)
		{
			float val = poly2.getValue(i);
			System.out.println("[" + i + "] " + val);
		}
		System.out.println();
		
		System.out.println("Substitutions 1:");
		for(int i = 0; i < 5; i++)
		{
			int val = Randomizer.randomInt(0, 10);
			float subs = poly1.substitute(val);
			System.out.println("x = " + val + " : " + subs);
		}
		System.out.println();
		
		System.out.println("Substitutions 2:");
		for(int i = 0; i < 5; i++)
		{
			int val = Randomizer.randomInt(0, 10);
			float subs = poly2.substitute(val);
			System.out.println("x = " + val + " : " + subs);
		}
		System.out.println();
	}
	
	private static void division()
	{
		System.out.println("--------");
		System.out.println("Division");
		System.out.println("--------");
		System.out.println();
		
		System.out.println("Poly 1 / poly 2:");
		Polynomial[] res1 = poly1.divide(poly2);
		System.out.println("Quotient: " + res1[0]);
		System.out.println("Remainder: " + res1[1]);
		
		System.out.println("Poly 2 / poly 1:");
		Polynomial[] res2 = poly2.divide(poly1);
		System.out.println("Quotient: " + res2[0]);
		System.out.println("Remainder: " + res2[1]);
		System.out.println();
	}
	
	private static void copying()
	{
		System.out.println("-------");
		System.out.println("Copying");
		System.out.println("-------");
		System.out.println();
		
		System.out.print("Polynomial 1: ");
		System.out.println(poly1.copy());
		System.out.print("Polynomial 2: ");
		System.out.println(poly2.copy());
		System.out.println();
	}
	
	private static void parsing()
	{
		System.out.println("-------");
		System.out.println("Parsing");
		System.out.println("-------");
		System.out.println();
		
		String text = "x^2 - 2 x^7 + 3 + x^4 + 2 x^7 - 2 + 3 x^3 - 4 x^3";
		Polynomial poly = Polynomial.parse(text);
		
		System.out.println("Original: " + text);
		System.out.println("Parsed: " + poly);
		System.out.println();
	}
}