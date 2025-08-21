package waffles.utils.alg.linear.measure.tensor.ops;

import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorResize} operation resizes a {@code Tensor} to a new dimension.
 * 
 * @author Waffles
 * @since Jul 13, 2018
 * @version 1.0
 * 
 * 
 * @see Operation
 * @see Tensor
 */
public class TensorResize implements Operation<Tensor>
{
	private Tensor t1;
	private int[] dims;
	
	/**
	 * Creates a new {@code TensorResize}.
	 * 
	 * @param t1    a tensor to resize
	 * @param dims  a tensor dimension
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorResize(Tensor t1, int... dims)
	{
		this.dims = dims;
		this.t1 = t1;
	}
	

	@Override
	public Tensor result()
	{
		Tensor t2 = Tensors.create(dims);
		for(int[] crd : t1.Data().NZKeys())
		{
			if(t2.contains(crd))
			{
				float v1 = t1.get(crd);
				t2.set(v1, crd);
			}
		}
		
		return t2;
	}
	
	@Override
	public int cost()
	{
		return t1.Data().NZCount();
	}
}