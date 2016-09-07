package zeno.util.algebra;

/**
 * The {@code Booleans} class defines basic math for boolean values.
 *
 * @since May 4, 2016
 * @author Zeno
 */
public final class Booleans
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
	
	
	private Booleans()
	{
		// NOT APPLICABLE
	}
}