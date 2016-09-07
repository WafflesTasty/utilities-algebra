package zeno.util.algebra.vectors;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.fixed.Vector2;
import zeno.util.algebra.vectors.fixed.Vector3;
import zeno.util.algebra.vectors.fixed.Vector4;
import zeno.util.tools.Randomizer;

/**
 * The {@code IVector} class defines the base of a floating-point vector.
 *
 * @since Mar 16, 2016
 * @author Zeno
 * 
 * @see IMatrix
 */
public abstract class IVector extends IMatrix
{
	/**
	 * Creates a null vector with the specified size.
	 * <br> Depending on dimensions, a subclass may be used:
	 * <ul>
	 * <li> 1x2 returns a {@code Vector2}. </li>
	 * <li> 1x3 returns a {@code Vector3}. </li>
	 * <li> 1x4 returns a {@code Vector4}. </li>
	 * </ul>
	 * 
	 * @param size  the size of the vector
	 * @return  a new null vector
	 */
	public static IVector createVector(int size)
	{
		if(size == 4)
		{
			return new Vector4();
		}
		
		if(size == 3)
		{
			return new Vector3();
		}
		
		if(size == 2)
		{
			return new Vector2();
		}
		
		return new Vector(size);
	}

	/**
	 * Creates a random vector with the specified size.
	 * 
	 * @param size  the size of the vector
	 * @return  a random vector
	 */
	public static IVector randomVector(int size)
	{
		IVector v = createVector(size);
		for(int i = 0; i < size; i++)
		{
			v.set(i, Randomizer.randomFloat(-1, 1));
		}
		
		return v;
	}
	
	
	/**
	 * Returns a normalized {@code IVector}.
	 * 
	 * @return  a normalized vector
	 */
	public IVector normalize()
	{
		float length = getLength();
		if(length == 0 || length == 1)
		{
			return copy();
		}
		
		return times(1f / length);
	}
	
	/**
	 * Returns the projection of the {@code IVector}.
	 * 
	 * @param norm  the planar norm to project to
	 * @return  the orthogonal projection
	 */
	public IVector projectTo(IVector norm)
	{
		return add(norm.times(-dot(norm) / norm.getLengthSquared()));
	}
	
	/**
	 * Changes a single value of the {@code IVector}.
	 * 
	 * @param i  the component index
	 * @param val  a vector value
	 */
	protected void set(int i, float val)
	{
		set(0, i, val);
	}
	
	/**
	 * Returns a single value of the {@code IVector}.
	 * 
	 * @param i  the component index
	 * @return  a vector value
	 */
	protected float get(int i)
	{
		return get(0, i);
	}
	
	
	/**
	 * Performs linear interpolation to an {@code IVector}.
	 * 
	 * @param v  a vector to interpolate with
	 * @param alpha  an interpolation alpha
	 * @return an interpolated vector
	 */
	public IVector lerp(IVector v, float alpha)
    {
		IVector v1 = times(1 - alpha);
		IVector v2 = v.times(alpha);
		return v1.add(v2);
    }
	
	/**
	 * Returns the distance to another {@code IVector}.
	 * 
	 * @param v  a vector to measure to
	 * @return  the vector distance
	 */
	public float distanceTo(IVector v)
	{
		return add(v.times(-1)).getLength();
	}
					
	/**
	 * Returns the square length of the {@code IVector}.
	 * 
	 * @return  the vector's squared length
	 */
	public float getLengthSquared()
	{
		return dot(this);
	}
	
	/**
	 * Returns the dot product with a {@code IVector}.
	 * 
	 * @param v  a vector to calculate with
	 * @return  the vector dot product
	 */
	public float dot(IVector v)
	{
		int size = getSize();
		if(size != v.getSize())
		{
			return Floats.NaN;
		}
		
		float prod = 0;
		for(int i = 0; i < size; i++)
		{
			prod += get(i) * v.get(i);
		}
		
		return prod;
	}
	
	/**
	 * Returns the length of the {@code IVector}.
	 * 
	 * @return  the vector's length
	 */
	public float getLength()
	{
		return Floats.sqrt(getLengthSquared());
	}
	
	/**
	 * Returns the size of the {@code IVector}.
	 * 
	 * @return  the vector's size
	 */
	public int getSize()
	{
		return getRows();
	}


	@Override
	public IVector times(float s)
	{
		return (IVector) super.times(s);
	}
	
	@Override
	public IVector add(IMatrix m)
	{
		return (IVector) super.add(m);
	}
	
	@Override
	public IVector copy()
	{
		return (IVector) super.copy();
	}
}