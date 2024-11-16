package waffles.utils.algebra;

/**
 * A {@code Partition} defines a section of the real number line.
 * These sections occur either below or above a real number value.
 *
 * @author Waffles
 * @since 16 Nov 2024
 * @version 1.1
 */
public interface Partition
{
	/**
	 * Checks if the {@code Partition} is above a value.
	 * 
	 * @param val  a real value
	 * @return  {@code true} if the partition is above
	 */
	public default boolean isAbove(float val)
	{
		return compareTo(val) == +1;
	}
	
	/**
	 * Checks if the {@code Partition} is below a value.
	 * 
	 * @param val  a real value
	 * @return  {@code true} if the partition is below
	 */
	public default boolean isBelow(float val)
	{
		return compareTo(val) == -1;
	}

	/**
	 * Compares the {@code Partition} to a value.
	 * This should return -1, 0, or +1, as the partition
	 * is below, around, or above the given value.
	 * 
	 * @param val  a real value
	 * @return  a comparable value
	 */
	public abstract int compareTo(float val);
}