package waffles.utils.alg.poly.parsers;

import waffles.utils.alg.poly.Polynomial;
import waffles.utils.lang.Characters;
import waffles.utils.lang.tokens.parsers.Parsable;
import waffles.utils.lang.tokens.parsers.basic.primitive.FloatParser;
import waffles.utils.lang.utilities.enums.Existence;

/**
 * A {@code PolynomialParser} parses a string into a {@code Polynomial}.
 *
 * @author Waffles
 * @since 18 Sep 2025
 * @version 1.1
 *
 * 
 * @see Polynomial
 * @see Parsable
 */
public class PolynomialParser implements Parsable<Polynomial>
{
	private static final Existence e = Existence.OBLIGATORY;

	private static enum State
	{
		COEFFICIENT,
		DEGREE;
	}
	
	
	private float val;
	private TermParser term;
	private FloatParser coef;
	private Polynomial ply;
	private State state;
	
	/**
	 * Creates a new {@code PolynomialParser}.
	 * 
	 * @param var  a variable character
	 */
	public PolynomialParser(char var)
	{
		state = State.COEFFICIENT;
		term = new TermParser(var);
		coef = new FloatParser();
		ply = new Polynomial();
	}


	@Override
	public boolean consume(Character c)
	{
		switch(state)
		{
		case COEFFICIENT:
		{
			if(Characters.isWhiteSpace(c))
				return true;
			if(coef.consume(c))
				return true;
			
			state = State.DEGREE;
			val = coef.generate();
			if(val == 0f)
			{
				val = 1f;
			}
			
			coef = new FloatParser(e);
		}
		case DEGREE:
		{
			if(Characters.isWhiteSpace(c))
				return true;
			if(term.consume(c))
				return true;
			
			state = State.COEFFICIENT;
			int deg = term.generate();
			ply.add(deg, val);
			term.reset();
			
			return consume(c);
		}
		default:
			return false;
		}
	}

	@Override
	public Polynomial generate()
	{
		switch(state)
		{
		case COEFFICIENT:
		{
			val = coef.generate();
			if(val == 0f)
			{
				val = 1f;
			}
			
			ply.add(0, val);
			break;
		}
		case DEGREE:
		{
			int deg = term.generate();
			ply.add(deg, val);
		}
		default:
			break;
		}
		
		return ply;
	}
	
	@Override
	public void reset()
	{
		coef = new FloatParser();
		ply = new Polynomial();
		term.reset();
	}
}
