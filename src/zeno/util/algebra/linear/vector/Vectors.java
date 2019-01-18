package zeno.util.algebra.linear.vector;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.algebra.linear.vector.fixed.Vector4;
import zeno.util.tools.helper.Array;

/**
 * The {@code Vectors} class defines basic {@code Vector} operations.
 *
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 */
public class Vectors
{
	/**
	 * Splits an array into equal size {@code Vectors}.
	 * 
	 * @param count  the amount of vectors to create
	 * @param vals  the values to split
	 * @return  a list of vectors
	 * 
	 * 
	 * @see Vector
	 */
	public static Vector[] split(int count, float... vals)
	{
		Vector[] result = new Vector[count];
		
		int size = vals.length / count;
		for(int i = 0; i < count; i++)
		{
			int iMin = size * (i + 0);
			int iMax = size * (i + 1);
			
			float[] iVals = Array.copy.of(vals, iMin, iMax);
			result[i] = Vectors.create(iVals);
		}
		
		return result;
	}
	
	/**
	 * Calculates the elementwise multiplication {@code Vector}.
	 * 
	 * @param v1  a first  vector to use
	 * @param v2  a second vector to use
	 * @return  an elementwise multiplication vector
	 * @see Vector
	 */
	public static <V extends Vector> V eMult(Vector v1, Vector v2)
	{
		return Tensors.eMult(v1, v2);
	}
	
	/**
	 * Resizes a {@code Vector} to the specified size.
	 * 
	 * @param v  a vector to resize
	 * @param size  a vector size
	 * @return  a resized vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V resize(Vector v, int size)
	{
		return Matrices.resize(v, size, 1);
	}
		
	/**
	 * Creates a constant {@code Vector} of the specified size.
	 * 
	 * @param val  a default vector value
	 * @param size  a vector size
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float val, int size)
	{
		Vector v = create(size);
		for(int i = 0; i < size; i++)
		{
			v.set(val, i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates a {@code Vector} with the specified values.
	 * 
	 * @param vals  a value array
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float... vals)
	{
		Vector v = Vectors.create(vals.length);
		for(int i = 0; i < vals.length; i++)
		{
			v.set(vals[i], i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates an empty {@code Vector} of the specified size.
	 * 
	 * @param size  a vector size
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(int size)
	{
		if(size == 4)
		{
			return (V) new Vector4();
		}
		
		if(size == 3)
		{
			return (V) new Vector3();
		}
		
		if(size == 2)
		{
			return (V) new Vector2();
		}
		
		return (V) new Vector(size);
	}
	
	/**
	 * Creates a random {@code Vector} of the specified size.
	 * 
	 * @param size  a vector size
	 * @return  a random vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V random(int size)
	{
		return Tensors.random(size);
	}
}
