package zeno.util.algebra.vectors.fixed;

import zeno.util.algebra.Floats;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.Vector;

/**
 * The {@code Vector2} class defines a two-dimensional vector.
 *
 * @since Mar 21, 2016
 * @author Zeno
 * 
 * @see Vector
 */
public class Vector2 extends Vector
{
	/**
	 * Contains the two-dimensional x-axis unit vector.
	 */
    public static final Vector2 X_AXIS = new Vector2(1, 0);
    /**
     * Contains the two-dimensional y-axis unit vector.
     */
    public static final Vector2 Y_AXIS = new Vector2(0, 1);
	
        
    /**
	 * Checks whether three {@code Vector2}'s are colinear.
	 * 
	 * @param a  the first vector to check
	 * @param b  the second vector to check
	 * @param c  the third vector to check
	 * @return {@code true} if colinear
	 */
	public static boolean isColinear(Vector2 a, Vector2 b, Vector2 c)
	{		
		return Floats.isZero(getRotation(a, b, c));
	}
	
	/**
	 * Calculates the rotation between three points.
	 * <br> Depending on the return value, the following holds true:
	 * <ul>
	 * <li> Positive: the points turn counter-clockwise </li>
	 * <li> Negative: the points turn clockwise </li>
	 * <li> Zero: the points are colinear
	 * </ul> 
	 * @param a  the first point to check
	 * @param b  the second point to check
	 * @param c  the third point to check
	 * @return  the points' rotation
	 */
	public static float getRotation(Vector2 a, Vector2 b, Vector2 c)
	{	
		return getRotation(a.X(), a.Y(), b.X(), b.Y(), c.X(), c.Y());
	}
    	
	/**
	 * Creates a new {@code Vector2} from polar co�rdinates.
	 * 
	 * @param len  the length of the vector
	 * @param ang  the angle of the vector
	 * @return  a two-dimensional vector
	 */
	public static Vector2 fromPolar(float len, float ang)
	{
		float x = len * Floats.cos(ang);
		float y = len * Floats.sin(ang);
		return new Vector2(x, y);
	}
	
	/**
	 * Calculates the rotation between three points.
	 * <br> Depending on the return value, the following holds true:
	 * <ul>
	 * <li> Positive: the points turn counter-clockwise </li>
	 * <li> Negative: the points turn clockwise </li>
	 * <li> Zero: the points are colinear
	 * </ul> 
	 * @param val  an array of six co�rdinates for three points
	 * @return  the points' rotation
	 */
	public static float getRotation(float... val)
	{
		float dx1 = val[2] - val[0];
		float dy1 = val[3] - val[1];
		float dx2 = val[4] - val[0];
		float dy2 = val[5] - val[1];

		return dx1 * dy2 - dx2 * dy1;
	}
	
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param x  the vector's x-co�rdinate
	 * @param y  the vector's y-co�rdinate
	 */
	public Vector2(float x, float y)
	{
		super(2);
		setX(x);
		setY(y);
	}
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param val  a co�rdinate value
	 */
	public Vector2(float val)
	{
		this(val, val);
	}
	
	/**
	 * Creates a new {@code Vector2}.
	 */
	public Vector2()
	{
		super(2);
	}
	
		
	/**
	 * Changes the x-co�rdinate of the {@code Vector2}.
	 * 
	 * @param x  a new x-co�rdinate
	 */
	public void setX(float x)
	{
		set(0, x);
	}
	
	/**
	 * Changes the y-co�rdinate of the {@code Vector2}.
	 * 
	 * @param y  a new y-co�rdinate
	 */
	public void setY(float y)
	{
		set(1, y);
	}
	
	
	/**
	 * Returns the perpdot product with a {@code Vector2}.
	 * </br> This value equals the z-co�rdinate of the cross
	 * product between the two vectors.
	 * 
	 * @param v  a vector to use
	 * @return  the perpdot product
	 */
	public float perpdot(Vector2 v)
	{
		return X() * v.Y() - Y() * v.X();
	}
	
	/**
	 * Returns the x-axis angle of the {@code Vector2}.
	 * 
	 * @return  the vector's angle
	 */
	public float getAngle()
	{
		return Floats.atan2(X(), Y());
	}
	
	/**
	 * Returns the x-co�rdinate of the {@code Vector2}.
	 * 
	 * @return  the vector's x-co�rdinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-co�rdinate of the {@code Vector2}.
	 * 
	 * @return  the vector's y-co�rdinate
	 */
	public float Y()
	{
		return get(1);
	}

		
	/**
	 * Performs angular interpolation with a {@code Vector2}.
	 * 
	 * @param v  a vector to interpolate with
	 * @param alpha  an interpolation alpha
	 * @return an interpolated vector
	 */
	public Vector2 alerp(Vector2 v, float alpha)
    {
		float a1 = getAngle();
		float r1 = getLength();
		float a2 = v.getAngle();
		float r2 = v.getLength();
		
		float a = a1 * (1 - alpha) + a2 * alpha;
		float r = r1 * (1 - alpha) + r2 * alpha;
	
		return new Vector2(r * Floats.cos(a), r * Floats.sin(a));
    }
	
	@Override
	public Vector2 lerp(IVector v, float alpha)
	{
		return (Vector2) super.lerp(v, alpha);
	}
	
	@Override
	public Vector2 projectTo(IVector norm)
	{
		return (Vector2) super.projectTo(norm);
	}
		
	@Override
	public Vector2 add(IMatrix m)
	{
		return (Vector2) super.add(m);
	}
	
	@Override
	public Vector2 times(float s)
	{
		return (Vector2) super.times(s);
	}
		
	@Override
	public Vector2 normalize()
	{
		return (Vector2) super.normalize();
	}
	
	@Override
	public Vector2 copy()
	{
		return (Vector2) super.copy();
	}
}