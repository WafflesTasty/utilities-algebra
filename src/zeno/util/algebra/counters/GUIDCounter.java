package zeno.util.algebra.counters;

import java.util.Iterator;

import zeno.util.algebra.Longs;

/**
 * The {@code GUIDCounter} class creates a counter for prefixed long values.
 *
 * @since May 5, 2016
 * @author Zeno
 * 
 * @see Iterator
 * @see String
 */
public class GUIDCounter implements Iterator<String>
{
	private long next;
	private String prefix;
	
	/**
	 * Creates a new {@code GUIDCounter}.
	 * 
	 * @param pref  a string prefix
	 * @see String
	 */
	public GUIDCounter(String pref)
	{
		prefix = pref;
		next = 0;
	}
	
	@Override
	public boolean hasNext()
	{
		return next != Longs.MAX_VALUE;
	}

	@Override
	public String next()
	{
		return prefix + (next++);
	}
}