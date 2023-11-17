package waffles.utils.algebra.elements.linear.tensor.ops;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code TensorEquality} compares two tensors for equality operation.
 * It compares every pair of values in two tensors within an
 * acceptable range of machine epsilons.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Boolean
 */
public class TensorEquality implements Operation<Boolean>
{
	private Tensor t1, t2;
	private int iError;
	
	/**
	 * Creates a new {@code TensorEquality}.
	 * 
	 * @param t1  the  first tensor to check
	 * @param t2  the second tensor to check
	 * @param iError  an error margin
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorEquality(Tensor t1, Tensor t2, int iError)
	{
		this.iError = iError;
		
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public Boolean result()
	{
		Tensor t3 = Tensors.create(t1.Dimensions());
		for(int[] coord : t2.Data().NZKeys())
		{
			float v1 = t1.get(coord);
			float v2 = t2.get(coord);
			
			t3.set(v1 - v2, coord);
		}
		
		for(int[] coord : t3.Data().NZKeys())
		{
			if(!Floats.isZero(t3.get(coord), iError))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int cost()
	{
		int c1 = t1.Data().NZCount();
		int c2 = t2.Data().NZCount();
		
		if(!t1.isDestructible())
			return c1 + c2;
		return c2;
	}
}