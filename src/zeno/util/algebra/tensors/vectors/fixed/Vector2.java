package zeno.util.algebra.tensors.vectors.fixed;

import zeno.util.algebra.tensors.vectors.Vector;
import zeno.util.tools.primitives.Floats;

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
	 * Creates a new {@code Vector2} from polar coördinates.
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
	 * @param val  an array of six coördinates for three points
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
		set(x, 0);
	}
	
	/**
	 * Changes the y-coördinate of the {@code Vector2}.
	 * 
	 * @param y  a new y-coördinate
	 */
	public void setY(float y)
	{
		set(y, 1);
	}
		
	/**
	 * Returns the x-axis angle of the {@code Vector2}.
	 * 
	 * @return  the vector's angle
	 */
	public float XAngle()
	{
		return Floats.atan2(X(), Y());
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
	 * Performs angular interpolation with a {@code Vector}.
	 * 
	 * @param v  a vector to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return an interpolated vector
	 */
	public Vector2 alerp(Vector2 v, float alpha)
    {
		float r1 = norm(); float a2 = v.XAngle();
		float r2 = v.norm(); float a1 = XAngle();
			
		float a = a1 * (1 - alpha) + a2 * alpha;
		float r = r1 * (1 - alpha) + r2 * alpha;
	
		return new Vector2(r * Floats.cos(a), r * Floats.sin(a));
    }
	
	/**
	 * Performs linear interpolation on the {@code Vector}.
	 * 
	 * @param v  a vector to interpolate to
	 * @param alpha  an interpolation alpha
	 * @return  an interpolated vector
	 */
	public Vector2 lerp(Vector2 v, float alpha)
    {
		return (Vector2) super.lerp(v, alpha);
    }

	/**
	 * Projects the {@code Vector} to a hyperplane.
	 * 
	 * @param plane  a plane to project to
	 * @return  the projected vector
	 */
	public Vector2 projectTo(Vector2 plane)
	{
		return (Vector2) super.projectTo(plane);
	}

	/**
	 * Returns the {@code Vector}'s perpdot product.
	 * This value equals the z-coördinate of the cross
	 * product between the two vectors.
	 * 
	 * @param v  a vector to multiply
	 * @return  the perpdot product
	 */
	public float perpdot(Vector2 v)
	{
		return X() * v.Y() - Y() * v.X();
	}
	
	/**
	 * Returns the {@code Vector}'s subtraction.
	 * 
	 * @param v  a vector to subtract
	 * @return  the difference vector
	 */
	public Vector2 minus(Vector2 v)
	{
		return (Vector2) super.minus(v);
	}
	
	/**
	 * Returns the {@code Vector}'s sum.
	 * 
	 * @param v  a vector to add
	 * @return  the sum vector
	 */
	public Vector2 plus(Vector2 v)
	{
		return (Vector2) super.plus(v);
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