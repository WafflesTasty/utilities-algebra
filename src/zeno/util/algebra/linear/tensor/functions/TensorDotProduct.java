package zeno.util.algebra.linear.tensor.functions;

import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.tools.Integers;
import zeno.util.tools.patterns.properties.operator.Operation;

/**
 * The {@code TensorDotProduct} class defines the standard tensor dot operation.
 * It naïvely loops over all values of the tensors in major order, multiplying
 * and adding them together in double precision. 
 * 
 * @author Zeno
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Float
 */
public class TensorDotProduct implements Operation<Float>
{
	private Tensor t1, t2;
	
	/**
	 * Creates a new {@code TensorDotProduct}.
	 * 
	 * @param t1  the  first tensor to multiply
	 * @param t2  the second tensor to multiply
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorDotProduct(Tensor t1, Tensor t2)
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	

	@Override
	public Float result()
	{
		float[] v1 = t1.Values();
		float[] v2 = t2.Values();
		
		double dot = 0d;
		for(int i = 0; i < v1.length; i++)
		{
			dot += v1[i] * v2[i];
		}
		
		return (float) dot;
	}
	
	@Override
	public int cost()
	{
		if(Tensors.isomorph(t1, t2))
		{
			return 2 * t1.Size() - 1;
		}
		
		return Integers.MAX_VALUE;
	}
}