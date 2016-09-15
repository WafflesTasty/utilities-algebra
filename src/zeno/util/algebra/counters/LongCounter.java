package zeno.util.algebra.counters;

import java.util.Iterator;

import zeno.util.algebra.Longs;

/**
 * The {@code LongCounter} class creates a counter for long values.
 *
 * @since May 5, 2016
 * @author Zeno
 * 
 * @see Iterator
 * @see Long
 */
public class LongCounter implements Iterator<Long>
{
	private long next;
	
	/**
	 * Creates a new {@code LongCounter}.
	 */
	public LongCounter()
	{
		next = 0;
	}
	
	@Override
	public boolean hasNext()
	{
		return next != Longs.MAX_VALUE;
	}

	@Override
	public Long next()
	{
		return next++;
	}
}