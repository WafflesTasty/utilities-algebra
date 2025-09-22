package waffles.utils.alg.utilities.matrix;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Orthogonal;
import waffles.utils.alg.lin.measure.vector.fixed.Vector2;
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
	 * element above the diagonal of a matrix
	 * to zero by right-multiplication.
	 * 
	 * @param m  a base matrix
	 * @param k  a diagonal to eliminate
	 * @return   a right givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix right(Matrix m, int k)
	{
		Matrix gr = Matrices.identity(m.Columns());
		gr.setOperator(Orthogonal.Type());

		float x = m.get(k-1, k-1);
		float y = m.get(k-1, k-0);
		
		Vector2 r = trig(x, y);
				
		gr.set(+r.get(0), k-1, k-1);
		gr.set(-r.get(1), k-1, k-0);
		gr.set(+r.get(1), k-0, k-1);
		gr.set(+r.get(0), k-0, k-0);
		
		return gr;
	}
	
	/**
	 * Creates a Givens rotation {@code Matrix}.
	 * A Givens rotation is designed to turn a single
	 * element left of the diagonal of a matrix
	 * to zero by left-multiplication.
	 * 
	 * @param m  a base matrix
	 * @param k  a diagonal to eliminate
	 * @return   a left givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix left(Matrix m, int k)
	{
		Matrix gl = Matrices.identity(m.Rows());
		gl.setOperator(Orthogonal.Type());

		float x = m.get(k-1, k-1);
		float y = m.get(k-0, k-1);
		
		Vector2 r = trig(x, y);
				
		gl.set(+r.get(0), k-1, k-1);
		gl.set(+r.get(1), k-1, k-0);
		gl.set(-r.get(1), k-0, k-1);
		gl.set(+r.get(0), k-0, k-0);
		
		return gl;
	}
	
	/**
	 * Creates a {@code Vector2} with trig values.
	 * 
	 * @param x  an x-coordinate
	 * @param y  an y-coordinate
	 * @return   a trig vector
	 * 
	 * 
	 * @see Vector2
	 */
	public static Vector2 trig(float x, float y)
	{
		float c = 0f, s = 0f;
		if(Floats.abs(x) < Floats.abs(y))
		{
			float t = x / y;
			float u = Floats.sign(y) * Floats.sqrt(1 + t * t);
			
			s = 1 / u;
			c = s * t;			
		}
		else
		{
			float t = y / x;
			float u = Floats.sign(x) * Floats.sqrt(1 + t * t);
			
			c = 1 / u;
			s = c * t;
		}

		return new Vector2(c, s);
	}
	

	private Givens()
	{
		// NOT APPLICABLE
	}
}