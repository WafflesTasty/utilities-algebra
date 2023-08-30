package waffles.utils.algebra.utilities.matrix;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Orthogonal;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Givens} class generates Givens rotation matrices.
 *
 * @author Waffles
 * @since 26 Aug 2023
 * @version 1.0
 */
public class Givens
{
	/**
	 * Creates a Givens rotation {@code Matrix}.
	 * A Givens rotation is designed to turn a single
	 * element under the diagonal of a matrix
	 * to zero by right-multiplication.
	 * 
	 * @param m  a coefficient matrix
	 * @param i  a row to eliminate
	 * @param j  a column to eliminate
	 * @return  a givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix Right(Matrix m, int i, int j)
	{
		Matrix gr = Matrices.identity(m.Columns());
		gr.setOperator(Orthogonal.Type());
		
		float a = m.get(i, i);
		float b = m.get(i, j);
		float[] r = rot(a, b);
				
		gr.set( r[0], i, i);
		gr.set(-r[1], i, j);
		gr.set( r[1], j, i);
		gr.set( r[0], j, j);
		
		return gr;
	}
	
	/**
	 * Creates a Givens rotation {@code Matrix}.
	 * A Givens rotation is designed to turn a single
	 * element under the diagonal of a matrix
	 * to zero by left-multiplication.
	 * 
	 * @param m  a coefficient matrix
	 * @param i  a row to eliminate
	 * @param j  a column to eliminate
	 * @return  a givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix Left(Matrix m, int i, int j)
	{
		Matrix gr = Matrices.identity(m.Rows());
		gr.setOperator(Orthogonal.Type());
		
		float a = m.get(j, j);
		float b = m.get(i, j);
		float[] r = rot(a, b);
				
		gr.set( r[0], i, i);
		gr.set(-r[1], i, j);		
		gr.set( r[1], j, i);
		gr.set( r[0], j, j);
		
		return gr;
	}
	
	
	private static float[] rot(float a, float b)
	{
		float c = 0f;
		float s = 0f;
		float r = 0f;
		
		if(Floats.isZero(b, 3))
		{
			c = a < 0 ? -1f : 1f;
			r = Floats.abs(a);
		}
		else
		if(Floats.isZero(a, 3))
		{
			s = Floats.sign(b);
			r = Floats.abs(b);
		}
		else
		if(Floats.abs(a) > Floats.abs(b))
		{
			float t = b / a;
			float u = Floats.sign(a) * Floats.sqrt(1 + t * t);
			
			c = 1 / u;
			s = c * t;
			r = a * u;
		}
		else
		{
			float t = a / b;
			float u = Floats.sign(b) * Floats.sqrt(1 + t * t);
			
			s = 1 / u;
			c = s * t;
			r = b * u;
		}

		return new float[]{c, s, r};
	}
	
	private Givens()
	{
		// NOT APPLICABLE
	}
}