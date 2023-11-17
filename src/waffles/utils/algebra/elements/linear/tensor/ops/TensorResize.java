package waffles.utils.algebra.elements.linear.tensor.ops;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code TensorResize} resizes a tensor to a new dimension.
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
	private Tensor t;
	private int[] dims;
	
	/**
	 * Creates a new {@code TensorResize}.
	 * 
	 * @param t     a tensor to resize
	 * @param dims  a tensor dimension
	 * 
	 * 
	 * @see Tensor
	 */
	public TensorResize(Tensor t, int[] dims)
	{
		this.dims = dims;
		this.t = t;
	}
	

	@Override
	public Tensor result()
	{
		Tensor result = Tensors.create(dims);

		
		TensorData iData = t.Data();
		TensorData oData = result.Data();
		
		for(int[] coord : iData.NZKeys())
		{
			if(oData.contains(coord))
			{
				float val = t.get(coord);
				result.set(val, coord);
			}
		}
		
		return result;
	}
	
	@Override
	public int cost()
	{
		return t.Data().NZCount();
	}
}