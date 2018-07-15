package zeno.util.algebra.counters;

import java.util.Iterator;

import zeno.util.tools.primitives.Integers;

/**
 * The {@code IntegerCounter} class creates a counter for integer values.
 *
 * @since May 5, 2016
 * @author Zeno
 * 
 * 
 * @see Iterator
 * @see Integer
 */
public class IntegerCounter implements Iterator<Integer>
{
	private int next;
	
	/**
	 * Creates a new {@code IntegerCounter}.
	 */
	public IntegerCounter()
	{
		next = 0;
	}
	
	@Override
	public boolean hasNext()
	{
		return next != Integers.MAX_VALUE;
	}

	@Override
	public Integer next()
	{
		return next++;
	}
}