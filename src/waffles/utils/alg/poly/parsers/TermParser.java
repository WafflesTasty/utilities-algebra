package waffles.utils.alg.poly.parsers;

import waffles.utils.lang.tokens.parsers.Parsable;
import waffles.utils.lang.tokens.parsers.basic.primitive.IntegerParser;

/**
 * A {@code TermParser} parses a variable term for a {@code PolynomialParser}.
 *
 * @author Waffles
 * @since 18 Sep 2025
 * @version 1.1
 *
 * 
 * @see Parsable
 * @see Integer
 */
public class TermParser implements Parsable<Integer>
{
	private static final char HAT = '^';
	
	private static enum State
	{
		VARIABLE,
		EXPONENT,
		DEGREE;
	}
	
	
	private char var;
	private State state;
	private IntegerParser e;
	
	/**
	 * Creates a new {@code TermParser}.
	 * 
	 * @param v  a variable character
	 */
	public TermParser(char v)
	{
		e = new IntegerParser();
		state = State.VARIABLE;
		var = v;
	}
	
	/**
	 * Creates a new {@code TermParser}.
	 * The default variable character is 'X'.
	 */
	public TermParser()
	{
		this('X');
	}
	
	
	@Override
	public boolean consume(Character c)
	{
		switch(state)
		{
		case VARIABLE:
		{
			if(c == var)
			{
				state = State.EXPONENT;
				return true;
			}
			
			return false;
		}
		case EXPONENT:
		{
			if(c == HAT)
			{
				state = State.DEGREE;
				return true;
			}
			
			return false;
		}
		case DEGREE:
			return e.consume(c);
		default:
			return false;
		}
	}

	@Override
	public Integer generate()
	{
		switch(state)
		{
		case DEGREE:
			return e.generate();
		case EXPONENT:
			return 1;
		case VARIABLE:
		default:
			return 0;
		}
	}
	
	@Override
	public void reset()
	{
		state = State.VARIABLE;
		e.reset();
	}
}