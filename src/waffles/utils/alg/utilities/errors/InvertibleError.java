package waffles.utils.alg.utilities.errors;

/**
 * An {@code InvertibleError} is thrown when a {@code Matrix} is not invertible.
 *
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see RuntimeException
 */
public class InvertibleError extends RuntimeException
{
	private static final long serialVersionUID = -903270796920266285L;

	
	/**
	 * Creates a new {@code InvertibleError}.
	 */
	public InvertibleError()
	{
		super("The provided matrix is not invertible.");
	}
}