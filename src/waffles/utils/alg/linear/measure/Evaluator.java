package waffles.utils.alg.linear.measure;

/**
 * An {@code Evaluator} can evaluate a value and return a result.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 *
 *
 * @param <S>  a value type
 */
@FunctionalInterface
public interface Evaluator<S>
{
	/**
	 * Evaluates the {@code Evaluator}.
	 * 
	 * @param arg  an argument value
	 * @return  a result value
	 */
	public abstract S evaluate(S arg);
}