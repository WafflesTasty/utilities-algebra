package waffles.utils.alg.reals.ranges.parser;

import waffles.utils.alg.reals.cuts.Cut;
import waffles.utils.alg.reals.ranges.Range;
import waffles.utils.alg.reals.ranges.Ranges;
import waffles.utils.lang.Characters;
import waffles.utils.lang.tokens.parsers.Parsable;
import waffles.utils.lang.tokens.parsers.basic.primitive.FloatParser;

/**
 * A {@code RangeParser} parses a {@code Range} from a string.
 *
 * @author Waffles
 * @since 20 Sep 2025
 * @version 1.1
 *
 * 
 * @see Parsable
 * @see Range
 */
public class RangeParser implements Parsable<Range>
{
	enum State
	{
		INITIAL,
		VALUE1,
		VALUE2;
	}
	
	
	private Cut c1, c2;
	private FloatParser val;
	private boolean isBefore;
	private State state;
	
	/**
	 * Creates a new {@code RangeParser}.
	 */
	public RangeParser()
	{
		val = new FloatParser();
		state = State.INITIAL;
	}


	@Override
	public Range generate()
	{
		return Ranges.create(c1, c2);
	}
	
	@Override
	public boolean consume(Character c)
	{
		if(Characters.isWhiteSpace(c))
			return true;
		
		switch(state)
		{
		case INITIAL:
		{
			if(c == '(' || c == '[')
			{
				isBefore = c == '[';
				state = State.VALUE1;
				return true;
			}
				
			return false;
		}
		case VALUE1:
		{
			if(val.consume(c))
				return true;

			float v = val.generate();
			c1 = isBefore ? Cut.Before(v) : Cut.After(v);
			if(c == ',')
			{
				val.reset();
				state = State.VALUE2;
				return true;
			}
			
			return false;
		}
		case VALUE2:
		{
			if(val.consume(c))
				return true;

			float v = val.generate();
			if(c == ')' || c == ']')
			{
				isBefore = c == ')';
				c2 = isBefore ? Cut.Before(v) : Cut.After(v);
				return true;
			}
		}
		default:
			return false;
		}
	}

	@Override
	public void reset()
	{
		state = State.INITIAL;
		c1 = null; c2 = null;
		val.reset();
	}
}