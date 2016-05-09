package zeno.util.algebra.vectors.fixed;

import zeno.util.algebra.FMath;
import zeno.util.algebra.matrices.IMatrix;
import zeno.util.algebra.vectors.IVector;
import zeno.util.algebra.vectors.Vector;
import zeno.util.algebra.vectors.complex.Complex;

/**
 * The {@code Vector2} class defines a two-dimensional vector.
 *
 * @author Zeno
 * @since Mar 21, 2016
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
		return FMath.isZero(getRotation(a, b, c));
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
		float dx1 = b.X() - a.X();
		float dy1 = b.Y() - a.Y();
		float dx2 = c.X() - a.X();
		float dy2 = c.Y() - a.Y();

		return dx1 * dy2 - dx2 * dy1;
	}
    	
	/**
	 * Creates a new {@code Vector2} from polar coördinates.
	 * 
	 * @param len  the length of the vector
	 * @param ang  the angle of the vector
	 * @return  a two-dimensional vector
	 */
	public static Vector2 fromPolar(float len, float ang)
	{
		float x = len * FMath.cos(ang);
		float y = len * FMath.sin(ang);
		return new Vector2(x, y);
	}
		
	
	/**
	 * Creates a new {@code Vector2}.
	 * 
	 * @param x  the vector's x-coördinate
	 * @param y  the vector's y-coördinate
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
	 * @param val  a coördinate value
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
	 * Changes the x-coördinate of the {@code Vector2}.
	 * 
	 * @param x  a new x-coördinate
	 */
	public void setX(float x)
	{
		set(0, x);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector2}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(1, y);
	}
	
	/**
	 * Casts the {@code Vector2} to a {@code Complex}.
	 * 
	 * @return  a complex number
	 * @see Complex
	 */
	public Complex toComplex()
	{
		return new Complex(X(), Y());
	}
	
	/**
	 * Returns the x-axis angle of the {@code Vector2}.
	 * 
	 * @return  the vector's angle
	 */
	public float getAngle()
	{
		return FMath.atan2(X(), Y());
	}
	
	/**
	 * Returns the x-coördinate of the {@code Vector2}.
	 * 
	 * @return  the vector's x-coördinate
	 */
	public float X()
	{
		return get(0);
	}
	
	/**
	 * Returns the y-coördinate of the {@code Vector2}.
	 * 
	 * @return  the vector's y-coördinate
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
	
		return new Vector2(r * FMath.cos(a), r * FMath.sin(a));
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