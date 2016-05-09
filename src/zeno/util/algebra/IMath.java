package zeno.util.algebra;

/**
 * The {@code IMath} class defines basic math for integer values.
 *
 * @author Zeno
 * @since May 4, 2016
 */
public final class IMath
{
	// Parsing
	
	/**
	 * Parses a string to a integer value.
	 * 
	 * @param text  a string to parse
	 * @return  a parsed integer
	 * @see String
	 */
	public static int parse(String text)
	{
		return Integer.parseInt(text);
	}
	
	
	private IMath()
	{
		// NOT APPLICABLE
	}
}