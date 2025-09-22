package waffles.utils.alg.lin.measure.matrix;

import waffles.utils.alg.lin.measure.matrix.fixed.Matrix2x2;
import waffles.utils.alg.lin.measure.matrix.fixed.Matrix3x3;
import waffles.utils.alg.lin.measure.matrix.fixed.Matrix4x4;
import waffles.utils.alg.lin.measure.matrix.types.orthogonal.Reflection;
import waffles.utils.alg.lin.measure.tensor.TensorData;
import waffles.utils.alg.lin.measure.tensor.Tensors;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.measure.vector.Vectors;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code Matrices} class defines static-access utilities for {@code Matrix} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 */
public class Matrices
{	
	// MATRIX CREATION.
	
	/**
	 * Creates a zero {@code Matrix}.
	 * 
	 * @param r  a row count
	 * @param c  a column count
	 * @return  a new matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M create(int r, int c)
	{
		if(c == 1)
			return (M) Vectors.create(r);
		if(c == r)
		{
			switch(c)
			{
			case 2:
				return (M) new Matrix2x2();
			case 3:
				return (M) new Matrix3x3();
			case 4:
				return (M) new Matrix4x4();
			default:
				break;
			}
		}
		
		return (M) new Matrix(r, c);
	}
	
	/**
	 * Creates a {@code Matrix} with an existing data source.
	 * 
	 * @param data  a data source
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
		
		
		int r1 = data.Dimensions()[0];
		int c1 = data.Dimensions()[1];

		if(c1 == 1)
		{
			return (M) Vectors.create(data);
		}
		
		if(r1 == c1)
		{
			switch(c1)
			{
			case 4:
				return (M) new Matrix4x4(data);
			case 3:
				return (M) new Matrix3x3(data);
			case 2:
				return (M) new Matrix2x2(data);
			default:
				break;
			}
		}
		
		return (M) new Matrix(data);
	}
	
	/**
	 * Creates a concatenated {@code Matrix} from a set.
	 * 
	 * @param set  a matrix set
	 * @return     a concatenated matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M concat(Matrix... set)
	{
		if(set.length == 1)
		{
			return (M) set[0];
		}
		
		
		int r1 = 0, c1 = 0;
		for(Matrix m : set)
		{
			r1 = Integers.max(r1, m.Rows());
			c1 += m.Columns();
		}
		
		
		int curr = 0;
		Matrix m = create(r1, c1);
		for(int k = 0; k < set.length; k++)
		{
			int r2 = set[k].Rows();
			int c2 = set[k].Columns();
			
			for(int r = 0; r < r2; r++)
			{
				for(int c = 0; c < c2; c++)
				{
					float v = set[k].get(r, c);
					m.set(v, r, curr);
				}
				
				curr++;
			}
		}
		
		return (M) m;
	}
	
	
	/**
	 * Omits a column from a {@code Matrix}.
	 * 
	 * @param m  a base matrix
	 * @param k  a column index
	 * @return  an omitted matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M omitColumn(Matrix m, int k)
	{
		return Tensors.omit(m, 1, k);
	}
	
	/**
	 * Omits a row from a {@code Matrix}.
	 * 
	 * @param m  a base matrix
	 * @param k  a row index
	 * @return  an omitted matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M omitRow(Matrix m, int k)
	{
		return Tensors.omit(m, 0, k);
	}
	
	/**
	 * Creates a square identity {@code Matrix}.
	 * 
	 * @param d  a matrix dimension
	 * @return  an identity matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M identity(int d)
	{
		return Tensors.identity(d, 2);
	}
	
	
	// MATRIX TRANSFORMATIONS.
	
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
		Matrix d = create(v.Size(), v.Size());
		for(int k = 0; k < v.Size(); k++)
		{
			d.set(v.get(k), k, k);
		}
		
		return (M) d;
	}
	
	/**
	 * Creates a generic reflection {@code Matrix}.
	 * 
	 * @param n  a reflection normal
	 * @return   a reflection matrix
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static <M extends Matrix> M reflection(Vector n)
	{
		float s = 2f / n.normSqr();
		Matrix ref = n.times(n.transpose());
		ref = identity(n.Size()).minus(ref.times(s));
		ref.setOperator(Reflection.Type());
		return (M) ref;
	}
		
	/**
	 * Creates an identity shifted {@code Matrix}.
	 * 
	 * @param <M>  a matrix type
	 * @param m    a base matrix
	 * @param s    a shift value
	 * @return  a shifted matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M shift(Matrix m, float s)
	{
		int r = m.Rows();
		int c = m.Columns();
		Matrix n = m.copy();
		
		for(int k = 0; k < Integers.min(r, c); k++)
		{
			float v = n.get(k, k);
			n.set(v + s, k, k);
		}
		
		return (M) n;
	}
	
	
	// MATRIX RANDOMIZATION.
	
	/**
	 * Creates a random {@code Matrix}.
	 * 
	 * @param rng  a randomizer
	 * @param r    a row count
	 * @param c    a column count
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
	 * Creates a random {@code Matrix}.
	 * 
	 * @param r  a row count
	 * @param c  a column count
	 * @return  a random matrix
	 * 
	 * 
	 * @see Matrix
	 */
	public static <M extends Matrix> M random(int r, int c)
	{
		return random(Randomizer.Global(), r, c);
	}


	private Matrices()
	{
		// NOT APPLICABLE
	}
}