package zeno.util.algebra.constants;

/**
 * The {@code Extreme} enumeration defines two numerical extremes.
 * 
 * @author Zeno
 * @since Oct 16, 2014
 * @version 1.0
 */
public enum Extreme
{
	/**
	 * The minimum extreme.
	 */
	MIN("minimum"),
	/**
	 * The maximum extreme.
	 */
	MAX("maximum");

	
	private String name;
	
	private Extreme(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}