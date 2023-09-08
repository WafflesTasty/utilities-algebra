package waffles.utils.algebra.elements.linear.vector;

import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector3;
import waffles.utils.algebra.elements.linear.vector.fixed.Vector4;
import waffles.utils.tools.Randomizer;

/**
 * The {@code Vectors} class defines static-access utilities for {@code Vector} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 */
public class Vectors
{
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
	 * Creates a {@code Vector} with the specified values.
	 * 
	 * @param vals  a value set
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float... vals)
	{
		Vector v = create(vals.length);
		for(int i = 0; i < vals.length; i++)
		{
			v.set(vals[i], i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates a {@code Vector} with the specified value.
	 * 
	 * @param val  a vector value
	 * @param dim  a vector dimension
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float val, int dim)
	{
		Vector v = create(dim);
		for(int i = 0; i < dim; i++)
		{
			v.set(val, i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates a {@code Vector} with the specified data object.
	 * 
	 * @param data  a vector data
	 * @return  a new vector
	 * 
	 * 
	 * @see TensorData
	 * @see Vector
	 */
	public static <V extends Vector> V create(TensorData data)
	{
		if(data.Order() != 2)
		{
			return null;
		}
		
		
		int rows = data.Dimensions()[0];
		int cols = data.Dimensions()[1];

		if(cols != 1)
		{
			return null;
		}
		
		
		if(rows == 4)
		{
			return (V) new Vector4(data);
		}
		
		if(rows == 3)
		{
			return (V) new Vector3(data);
		}
		
		if(rows == 2)
		{
			return (V) new Vector2(data);
		}
		
		return (V) new Vector(data);
	}
	
	/**
	 * Creates a {@code Vector} as a resized copy of another vector.
	 * 
	 * @param v  a vector to resize
	 * @param s  a vector size
	 * @return  a resized vector
	 * 
	 * 
	 * @see Matrix
	 */
	public static <V extends Vector> V resize(Vector v, int s)
	{
		return Matrices.resize(v, s, 1);
	}
	
	/**
	 * Creates a random {@code Vector} of the given size.
	 * 
	 * @param rng  a value randomizer
	 * @param s  a vector size
	 * @return  a random vector
	 * 
	 * 
	 * @see Randomizer
	 * @see Vector
	 */
	public static <V extends Vector> V random(Randomizer rng, int s)
	{
		return Matrices.random(rng, s, 1);
	}
	
	/**
	 * Creates a {@code Vector} as another vector with an index dropped.
	 * 
	 * @param v  a vector to resize
	 * @param k  an index to drop
	 * @return  a resized vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V drop(Vector v, int k)
	{
		Vector w = create(v.Size()-1);
		for(int i = 0; i < v.Size()-1; i++)
		{
			if(k <= i)
				w.set(v.get(i+1), i);
			else
				w.set(v.get(i+0), i);
		}
		
		return (V) w;
	}
	
	/**
	 * Creates a random {@code Vector} of the given size.
	 * 
	 * @param s  a vector size
	 * @return  a random vector
	 * 
	 * 
	 * @see Randomizer
	 * @see Vector
	 */
	public static <M extends Matrix> M random(int s)
	{
		return Matrices.random(s, 1);
	}
		
	
	private Vectors()
	{
		// NOT APPLICABLE
	}
}