package waffles.utils.alg;

import waffles.utils.alg.utilities.groups.Addition;
import waffles.utils.alg.utilities.groups.Subtraction;

/**
 * An {@code Abelian} defines a closed addition and subtraction.
 *
 * @author Waffles
 * @since 30 Aug 2023
 * @version 1.0
 * 
 * 
 * @see Subtraction
 * @see Addition
 */
public interface Abelian extends Addition<Abelian>, Subtraction<Abelian>
{
	// NOT APPLICABLE
}