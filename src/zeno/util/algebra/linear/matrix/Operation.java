package zeno.util.algebra.linear.matrix;

/**
 * The {@code Operation} interface defines an abstract matrix operation.
 * By using interfaces to delegate matrix operations, different matrix types
 * can implement different optimizations for common matrix functions.
 * 
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * @param <O>  the type of the operation result
 */
public interface Operation<O>
{		
	/**
	 * Returns the result of the {@code Operation}.
	 * 
	 * @return  the operation result
	 */
	public abstract O result();

	/**
	 * Returns the cost of the {@code Operation}.
	 * 
	 * @return  the operation cost
	 */
	public abstract int cost();
}