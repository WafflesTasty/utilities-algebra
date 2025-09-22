package waffles.utils.alg.reals;

import waffles.utils.lang.utilities.patterns.moments.Momentary;

/**
 * A {@code Partition} defines a section of the real number line.
 * These sections occur below or above a real number value.
 *
 * @author Waffles
 * @since 16 Nov 2024
 * @version 1.1
 */
public interface Partition extends Momentary<Float>
{
	/**
	 * Checks if the {@code Partition} contains a value.
	 * 
	 * @param v  a floating-point value
	 * @return  {@code true} if the value is contained
	 */
	public default boolean contains(Float v)
	{
		return compareTo(v) == 0;
	}
}