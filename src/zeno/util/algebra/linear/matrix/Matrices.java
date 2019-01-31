package zeno.util.algebra.linear.matrix;

import zeno.util.algebra.linear.matrix.fixed.Matrix2x2;
import zeno.util.algebra.linear.matrix.fixed.Matrix3x3;
import zeno.util.algebra.linear.matrix.fixed.Matrix4x4;
import zeno.util.algebra.linear.matrix.types.orthogonal.Orthogonal;
import zeno.util.algebra.linear.matrix.types.orthogonal.Reflection;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.tools.Floats;

/**
 * The {@code Matrices} class defines basic {@code Matrix} operations.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 */
public class Matrices
{	
	/**
	 * The {@code InvertibleError} defines an error thrown when
	 * a {@code Matrix} turns out to be singular.
	 *
	 * @author Zeno
	 * @since Jul 13, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class InvertibleError extends RuntimeException
	{
		private static final long serialVersionUID = -1165264740808510034L;

		/**
		 * Creates a new {@code InvertibleError}.
		 * 
		 * @param m  a target matrix
		 * @see Matrix
		 */
		public InvertibleError(Matrix m)
		{
			super("The matrix " + m + " is not invertible.");
		}
	}
	
	/**
	 * The {@code PosDefiniteError} defines an error thrown when
	 * a {@code Matrix} turns out not to be positive definite.
	 *
	 * @author Zeno
	 * @since Jul 13, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class PosDefiniteError extends RuntimeException
	{
		private static final long serialVersionUID = -1165264740808510034L;

		/**
		 * Creates a new {@code PosDefiniteError}.
		 * 
		 * @param m  a target matrix
		 * @see Matrix
		 */
		public PosDefiniteError(Matrix m)
		{
			super("The matrix " + m + " is not positive definite.");
		}
	}
		
	/**
	 * The {@code TriangularError} defines an error thrown when
	 * a {@code Matrix} turns out not to be triangular.
	 *
	 * @author Zeno
	 * @since Jul 13, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class TriangularError extends RuntimeException
	{
		private static final long serialVersionUID = -1165264740808510034L;

		/**
		 * Creates a new {@code TriangularError}.
		 * 
		 * @param m  a target matrix
		 * @see Matrix
		 */
		public TriangularError(Matrix m)
		{
			super("The matrix " + m + " is not triangular.");
		}
	}
	
	/**
	 * The {@code SymmetricError} defines an error thrown when
	 * a {@code Matrix} turns out not to be symmetric.
	 *
	 * @author Zeno
	 * @since Jul 13, 2018
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class SymmetricError extends RuntimeException
	{
		private static final long serialVersionUID = -1165264740808510034L;

		/**
		 * Creates a new {@code SymmetricError}.
		 * 
		 * @param m  a target matrix
		 * @see Matrix
		 */
		public SymmetricError(Matrix m)
		{
			super("The matrix " + m + " is not triangular.");
		}
	}

	
	
	/**
	 * Calculates the elementwise multiplication {@code Matrix}.
	 * 
	 * @param m1  a first  matrix to use
	 * @param m2  a second matrix to use
	 * @return  an elementwise multiplication matrix
	 * @see Matrix
	 */
	public static <M extends Matrix> M eMult(Matrix m1, Matrix m2)
	{
		return Tensors.eMult(m1, m2);
	}
	
	/**
	 * Resizes a {@code Matrix} to the specified dimensions.
	 * 
	 * @param m  a matrix to resize
	 * @param rows  a matrix row count
	 * @param cols  a matrix column count
	 * @return  a resized matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M resize(Matrix m, int rows, int cols)
	{
		Matrix n = create(rows, cols);
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				// Separate check depends on whether the
				// old or the new matrix is the larger one.
				if(i < m.Rows() && j < m.Columns())
				{
					n.set(m.get(i, j), i, j);
				}
			}
		}
		
		return (M) n;
	}
	
	/**
	 * Creates an empty {@code Matrix} of the specified dimensions.
	 * 
	 * @param rows  a matrix row count
	 * @param cols  a matrix column count
	 * @return  a new matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M create(int rows, int cols)
	{
		if(cols == 1)
		{
			return (M) Vectors.create(rows);
		}
		
		if(cols == rows)
		{
			if(cols == 2)
			{
				return (M) new Matrix2x2();
			}
			
			if(cols == 3)
			{
				return (M) new Matrix3x3();
			}
			
			if(cols == 4)
			{
				return (M) new Matrix4x4();
			}
		}
		
		return (M) new Matrix(rows, cols);
	}
	
	/**
	 * Creates a random {@code Matrix} of the specified dimensions.
	 * 
	 * @param rows  a matrix row count
	 * @param cols  a matrix column count
	 * @return  a random matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M random(int rows, int cols)
	{
		return Tensors.random(rows, cols);
	}
	
	/**
	 * Creates an identity {@code Matrix} of the specified size.
	 * 
	 * @param size  the size of the matrix
	 * @return  an identity matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M identity(int size)
	{
		return Tensors.identity(size, 2);
	}


	/**
	 * Creates a right-hand side Givens rotation {@code Matrix}.
	 * This rotation is designed to turn a single element above
	 * the diagonal of a matrix to zero by right-multiplication.
	 * 
	 * @param m  a target matrix
	 * @param i  the row of the element to eliminate
	 * @param j  the column of the element to eliminate
	 * @return  a right-hand side Givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix rightGivens(Matrix m, int i, int j)
	{
		Matrix gr = Matrices.identity(m.Rows());
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
	 * Creates a left-hand side Givens rotation {@code Matrix}.
	 * This rotation is designed to turn a single element under
	 * the diagonal of a matrix to zero by left-multiplication.
	 * 
	 * @param m  a target matrix
	 * @param i  the row of the element to eliminate
	 * @param j  the column of the element to eliminate
	 * @return  a left-hand side Givens rotation
	 * 
	 * 
	 * @see Matrix
	 */
	public static Matrix leftGivens(Matrix m, int i, int j)
	{
		Matrix gr = Matrices.identity(m.Columns());
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

	/**
	 * Creates a Householder reflection {@code Matrix}.
	 * A Householder reflection is designed to project
	 * all components of a vector to zero, except
	 * for one.
	 * 
	 * @param v  a vector to reflect
	 * @param i  the component to keep
	 * @return  a householder reflection
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Matrix houseHolder(Vector v, int i)
	{
		Vector x = v.copy();
		float iErr = (x.get(i) < 0 ? -1 : 1);
		x.set(x.get(i) + iErr * x.norm(), i);
		return reflection(x);
	}
	
	/**
	 * Creates a generic reflection {@code Matrix}.
	 * 
	 * @param v  a reflection plane normal
	 * @return  a reflection matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Matrix reflection(Vector v)
	{
		Matrix ref = v.times(v.transpose()).times(2f / v.normSqr());
		ref = identity(v.Size()).minus(ref);
		ref.setOperator(Reflection.Type());
		return ref;
	}
	
	/**
	 * Creates a generic diagonal {@code Matrix}.
	 * 
	 * @param v  a diagonal vector
	 * @return  a diagonal matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Matrix diagonal(Vector v)
	{
		Matrix dia = Matrices.create(v.Size(), v.Size());
		for(int i = 0; i < v.Size(); i++)
		{
			dia.set(v.get(i), i, i);
		}
		
		return dia;
	}
	
	/**
	 * Prints a {@code Matrix} to console.
	 * 
	 * @param m  a matrix to print
	 * 
	 * 
	 * @see Matrix
	 */
	public static void print(Matrix m)
	{
		for(int i = 0; i < m.Rows(); i++)
		{
			System.out.print("[");
			for(int j = 0; j < m.Columns(); j++)
			{
				System.out.print(" " + parse(m.get(i, j)));
				if(j < m.Columns() - 1)
				{
					System.out.print(",");
				}
			}
			
			System.out.println("]");
		}
		
		System.out.println();
	}


	static float[] rot(float a, float b)
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

	static String parse(float val)
	{
		float round = Floats.round(val, 3);
		String result = String.valueOf(round);
		
		if(round >= 0)
		{
			result = " " + result;
		}
		
		if(Floats.abs(round) < 10)
		{
			result = " " + result;
		}
		
		int length = result.length();
		for(int i = 0; i < 10 - length; i++)
		{
			result += "0";
		}

		return result;
	}
}