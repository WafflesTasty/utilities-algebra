package zeno.util.algebra.linear.tensor.functions;

import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.primitives.Floats;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code TensorEquality} class defines the standard tensor equality operation.
 * It compares every pair of values in two tensors within an
 * acceptable range of machine epsilons.
 * 
 * @author Zeno
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
		if(Tensors.isomorph(t1, t2))
		{
			float[] v1 = t1.Values();
			float[] v2 = t2.Values();
			
			for(int i = 0; i < v1.length; i++)
			{
				if(!Floats.isEqual(v1[i], v2[i], iError))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public int cost()
	{
		if(Tensors.isomorph(t1, t2))
		{
			return t1.Size();
		}
		
		return Integers.MAX_VALUE;
	}
}