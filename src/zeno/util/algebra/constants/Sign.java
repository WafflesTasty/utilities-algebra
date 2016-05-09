package zeno.util.algebra.constants;

/**
 * The {@code Sign} enum defines the three possible signs of a numerical value.
 * 
 * @author Zeno
 * @since Oct 16, 2104
 */
public enum Sign
{
	/**
	 * The negative sign.
	 */
	NEGATIVE(-1),
	/**
	 * The positive sign.
	 */
	POSITIVE(1),
	/**
	 * The neutral sign.
	 */
	ZERO(0);

	
	/**
	 * Returns the {@code Sign} of a value.
	 * 
	 * @param value  a value to check the sign for
	 * @return  the value's sign
	 */
	public static Sign of(float value)
	{
		if(value < 0)
			return NEGATIVE;
		
		if(value > 0)
			return POSITIVE;
		
		return ZERO;
	}
	
	
	private int value;
	
	private Sign(int value)
	{
		this.value = value;
	}
	
	/**
	 * Returns the value of the {@code Sign}.
	 * 
	 * @return the sign's value
	 */
	public int getValue()
	{
		return value;
	}
}