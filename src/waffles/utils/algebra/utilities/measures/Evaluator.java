package waffles.utils.algebra.utilities.measures;

/**
 * An {@code Evaluator} is capable of evaluating a value and returning a result.
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