package zeno.util.algebra;

/**
 * The {@code BMath} class defines basic math for boolean values.
 *
 * @author Zeno
 * @since May 4, 2016
 */
public final class BMath
{
	// Parsing
	
	/**
	 * Parses a string to a boolean value.
	 * 
	 * @param text  a string to parse
	 * @return  a parsed boolean
	 * @see String
	 */
	public static boolean parse(String text)
	{
		return Boolean.parseBoolean(text);
	}
		
	
	private BMath()
	{
		// NOT APPLICABLE
	}
}