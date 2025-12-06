package waffles.utils.alg.lin.measure.vector;

import waffles.utils.alg.lin.measure.matrix.Matrices;
import waffles.utils.alg.lin.measure.scalar.ScalarOps;
import waffles.utils.alg.lin.measure.tensor.TensorData;
import waffles.utils.alg.lin.measure.tensor.Tensors;
import waffles.utils.alg.lin.measure.vector.fixed.Vector2;
import waffles.utils.alg.lin.measure.vector.fixed.Vector3;
import waffles.utils.alg.lin.measure.vector.fixed.Vector4;
import waffles.utils.tools.Randomizer;

/**
 * The {@code Vectors} class defines static-access utilities for {@code Vector} objects.
 *
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.1
 */
public class Vectors
{	
	// VECTOR CREATION.
	
	/**
	 * Creates a zero {@code Vector}.
	 * 
	 * @param s  a vector size
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(int s)
	{
		switch(s)
		{
		case 4:
			return (V) new Vector4();
		case 3:
			return (V) new Vector3();
		case 2:
			return (V) new Vector2();
		default:
		{
			Vector v = new Vector(s);
			if(s == 1)
			{
				v.setOperator(ScalarOps.Type());
			}
			
			return (V) v;
		}
		}
	}
	
	/**
	 * Creates a real-valued {@code Vector}.
	 * 
	 * @param vals  a value set
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float... vals)
	{
		int s = vals.length;
		
		Vector v = create(s);
		for(int i = 0; i < s; i++)
		{
			v.set(vals[i], i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates a {@code Vector} with the specified value.
	 * 
	 * @param val  a constant value
	 * @param s    a vector size
	 * @return  a new vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V create(float val, int s)
	{
		Vector v = create(s);
		for(int i = 0; i < s; i++)
		{
			v.set(val, i);
		}
		
		return (V) v;
	}
	
	/**
	 * Creates a {@code Vector} with an existing data source.
	 * 
	 * @param data  a data source
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
		
		
		int r1 = data.Dimensions()[0];
		int c1 = data.Dimensions()[1];

		if(c1 != 1)
		{
			return null;
		}
		
		switch(r1)
		{
		case 4:
			return (V) new Vector4(data);
		case 3:
			return (V) new Vector3(data);
		case 2:
			return (V) new Vector2(data);
		default:
		{
			Vector v = new Vector(data);
			if(r1 == 1)
			{
				v.setOperator(ScalarOps.Type());
			}
			
			return (V) v;
		}
		}
	}
	
	/**
	 * Creates a concatenated {@code Vector} from a set.
	 * 
	 * @param set  a vector set
	 * @return     a concatenated vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V concat(Vector... set)
	{
		if(set.length == 1)
		{
			return (V) set[0];
		}
		
		
		int s1 = 0;
		for(Vector v : set)
		{
			s1 += v.Size();
		}
		
		
		int curr = 0;
		Vector v = Vectors.create(s1);
		for(int k = 0; k < set.length; k++)
		{
			int s2 = set[k].Size();
			
			for(int l = 0; l < s2; l++)
			{
				float w = set[k].get(l);
				v.set(w, curr);
				curr++;
			}
		}
		
		return (V) v;
	}
	
	/**
	 * Omits an index from a {@code Vector}.
	 * 
	 * @param <V>  a vector type
	 * @param v    a base vector
	 * @param k    a vector index
	 * @return  an omitted vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V omit(Vector v, int k)
	{
		return Tensors.omit(v, 0, k);
	}
	
	/**
	 * Creates a unit {@code Vector}.
	 * 
	 * @param <V>  a vector type
	 * @param k    a unit index
	 * @param n    a dimension
	 * @return  a unit vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V unit(int k, int n)
	{
		Vector v = create(n);
		v.set(1f, k);
		return (V) v;
	}
	
	
	// VECTOR RANDOMIZATION.
	
	/**
	 * Creates a random {@code Vector}.
	 * 
	 * @param rng  a randomizer
	 * @param s    a vector size
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
	 * Creates a random {@code Vector}.
	 * 
	 * @param s  a vector size
	 * @return  a random vector
	 * 
	 * 
	 * @see Vector
	 */
	public static <V extends Vector> V random(int s)
	{
		return random(Randomizer.Global(), s);
	}


	private Vectors()
	{
		// NOT APPLICABLE
	}
}