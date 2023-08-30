package waffles.utils.algebra.elements.linear.matrix;

import waffles.utils.algebra.elements.linear.matrix.fixed.Matrix2x2;
import waffles.utils.algebra.elements.linear.matrix.fixed.Matrix3x3;
import waffles.utils.algebra.elements.linear.matrix.fixed.Matrix4x4;
import waffles.utils.algebra.elements.linear.matrix.types.orthogonal.Reflection;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.lang.Strings;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Matrices} class defines static-access utilities for {@code Matrix} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 */
public class Matrices
{	
	/**
	 * A {@code TypeError} is thrown when a {@code Matrix}
	 * does not match a required operator type.
	 *
	 * @author Waffles
	 * @since 25 Aug 2023
	 * @version 1.0
	 * 
	 * 
	 * @see RuntimeException
	 */
	public static class TypeError extends RuntimeException
	{
		private static final long serialVersionUID = 7576947725497069700L;

		/**
		 * Creates a new {@code TypeError}.
		 * 
		 * @param type  a matrix type
		 * 
		 * 
		 * @see MatrixOps
		 * @see Matrix
		 */
		public TypeError(MatrixOps type)
		{
			super(format(type));
		}
		
		static String format(MatrixOps type)
		{
			String format = "Invalid matrix type: ";
			String name = type.getClass().getSimpleName();
			format += Strings.replaceFirst(name, "\\$.*", "");
			format += " is required.";
			
			return format;
		}
	}
	
	/**
	 * An {@code InvertibleError} is thrown when a {@code Matrix}
	 * turns out to be singular.
	 *
	 * @author Waffles
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
		 * 
		 * 
		 * @see Matrix
		 */
		public InvertibleError(Matrix m)
		{
			super("The matrix " + m + " is not invertible.");
		}
	}
	
	/**
	 * A {@code PosDefiniteError} is thrown when a {@code Matrix}
	 * turns out not to be positive definite.
	 *
	 * @author Waffles
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
	 * A {@code TriangularError} is thrown when a {@code Matrix}
	 * turns out not to be either lower or upper triangular.
	 *
	 * @author Waffles
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
		 * 
		 * 
		 * @see Matrix
		 */
		public TriangularError(Matrix m)
		{
			super("The matrix " + m + " is not triangular.");
		}
	}
	
	
	/**
	 * Creates an identity {@code Matrix} of the given size.
	 * 
	 * @param size  a square matrix size
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
	 * Creates an empty {@code Matrix} of the give dimensions.
	 * 
	 * @param r  a matrix row count
	 * @param c  a matrix column count
	 * @return  a new matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M create(int r, int c)
	{
		if(c == 1)
		{
			return (M) Vectors.create(r);
		}
		
		if(c == r)
		{
			if(c == 2)
			{
				return (M) new Matrix2x2();
			}
			
			if(c == 3)
			{
				return (M) new Matrix3x3();
			}
			
			if(c == 4)
			{
				return (M) new Matrix4x4();
			}
		}
		
		return (M) new Matrix(r, c);
	}
	
	/**
	 * Creates a {@code Matrix} with the specified data object.
	 * 
	 * @param data  a matrix data
	 * @return  a new matrix
	 * 
	 * 
	 * @see TensorData
	 * @see Matrix
	 */
	public static <M extends Matrix> M create(TensorData data)
	{
		if(data.Order() != 2)
		{
			return null;
		}
		
		int rows = data.Dimensions()[0];
		int cols = data.Dimensions()[1];

		if(cols == 1)
		{
			return (M) Vectors.create(data);
		}
		
		if(cols == rows)
		{
			if(cols == 2)
			{
				return (M) new Matrix2x2(data);
			}
			
			if(cols == 3)
			{
				return (M) new Matrix3x3(data);
			}
			
			if(cols == 4)
			{
				return (M) new Matrix4x4(data);
			}
		}
		
		return (M) new Matrix(data);
	}
	
	/**
	 * Creates a concatenation of a set of {@code Matrix} objects.
	 * 
	 * @param mats  a set of matrices
	 * @return  a concatenated matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M concat(Matrix... mats)
	{
		int cols = 0;
		int rows = mats[0].Rows();
		for(Matrix m : mats)
		{
			cols += m.Columns();
		}
		
		
		int curr = 0;
		Matrix m = create(rows, cols);
		for(int i = 0; i < mats.length; i++)
		{
			// If a source matrix does not have equal rows...
			if(mats[i].Rows() != rows)
			{
				// Cancel the operation.
				throw new Tensors.DimensionError("Concatenation requires equal row count: ", mats);
			}
			
			
			int mRows = mats[i].Rows();
			int mCols = mats[i].Columns();
			// Otherwise, add its values to the target matrix.
			for(int c = 0; c < mCols; c++)
			{
				for(int r = 0; r < mRows; r++)
				{
					m.set(mats[i].get(r, c), r, curr);
				}
				
				curr++;
			}
		}
		
		return (M) m;
	}
	
	/**
	 * Creates a {@code Matrix} as a resized copy of another matrix.
	 * 
	 * @param m  a matrix to resize
	 * @param r  a row count
	 * @param c  a column count
	 * @return  a resized matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M resize(Matrix m, int r, int c)
	{
		return Tensors.resize(m, r, c);
	}
			
	/**
	 * Creates a random {@code Matrix} of the given dimensions.
	 * 
	 * @param rng  a value randomizer
	 * @param r  a matrix row count
	 * @param c  a matrix column count
	 * @return  a random matrix
	 * 
	 * 
	 * @see Randomizer
	 * @see Matrix
	 */
	public static <M extends Matrix> M random(Randomizer rng, int r, int c)
	{
		return Tensors.random(rng, r, c);
	}
	
	/**
	 * Creates a random {@code Matrix} of the given dimensions.
	 * 
	 * @param r  a matrix row count
	 * @param c  a matrix column count
	 * @return  a random matrix
	 * 
	 * 
	 * @see Randomizer
	 * @see Matrix
	 */
	public static <M extends Matrix> M random(int r, int c)
	{
		return Tensors.random(r, c);
	}
	
	/**
	 * Creates a generic reflection {@code Matrix}.
	 * 
	 * @param v  a reflection plane normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static <M extends Matrix> M reflection(Vector v)
	{
		Matrix ref = v.times(v.transpose()).times(2f / v.normSqr());
		ref = identity(v.Size()).minus(ref);
		ref.setOperator(Reflection.Type());
		return (M) ref;
	}
	
	/**
	 * Creates a generic diagonal {@code Matrix}.
	 * 
	 * @param v  a diagonal vector
	 * @return   a diagonal matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static <M extends Matrix> M diagonal(Vector v)
	{
		Matrix diag = create(v.Size(), v.Size());
		for(int i = 0; i < v.Size(); i++)
		{
			diag.set(v.get(i), i, i);
		}
		
		return (M) diag;
	}
	
	/**
	 * Prints a {@code Matrix} to the Java console.
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
				System.out.print(" " + standard(m.get(i, j)));
				if(j < m.Columns() - 1)
				{
					System.out.print(",");
				}
			}
			
			System.out.println("]");
		}
		
		System.out.println();
	}
	
	
	static String standard(float val)
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
	
	private Matrices()
	{
		// NOT APPLICABLE
	}
}