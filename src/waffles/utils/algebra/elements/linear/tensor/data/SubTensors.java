package waffles.utils.algebra.elements.linear.tensor.data;

import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.sets.utilities.iterators.IndexKeys;
import waffles.utils.tools.primitives.Array;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code SubTensors} defines tensor data through a one-dimensional array of subtensors.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see TensorData
 */
public class SubTensors implements TensorData
{		
	private static int[] SubCoord(Order ord, int... coord)
	{
		if(ord == Order.COL_MAJOR)
			return Array.remove.from(coord, coord.length-1);
		return Array.remove.from(coord, 0);
	}
	
	private static int MainCoord(Order ord, int... coord)
	{
		if(ord == Order.COL_MAJOR)
			return coord[coord.length-1];
		return coord[0];
	}
	
	
	private int[] dims;
	private Order order;
	private Tensor[] subset;
	
	/**
	 * Creates a new {@code TensorArray}.
	 * 
	 * @param ord   an index order
	 * @param dims  a tensor dimension
	 */
	public SubTensors(Order ord, int... dims)
	{
		this(new Tensor[MainCoord(ord, dims)], ord, dims);
	}

	SubTensors(Tensor[] set, Order ord, int... dims)
	{
		this.subset = set;
		this.order = ord;
		this.dims = dims;
	}
	
	
	private int ZeroError()
	{
		int[] crdSub = SubCoord(order, dims);
		return Array.product.of(crdSub);
	}
	
	@Override
	public Float remove(int... coord)
	{
		int crdMain = MainCoord(order, coord);
		int[] crdSub = SubCoord(order, coord);
		
		float prev = 0f;
		Tensor t = subset[crdMain];
		if(t != null)
		{
			prev = t.get(crdSub);
			t.set(0f, crdSub);
			
			if(Floats.isZero(t.normSqr(), ZeroError()))
			{
				subset[crdMain] = null;
			}
		}
		
		return prev;
	}

	@Override
	public Float put(Float val, int... coord)
	{
		int crdMain = MainCoord(order, coord);
		int[] crdSub = SubCoord(order, coord);
		
		
		Tensor t = subset[crdMain];
		if(t == null)
		{
			t = Tensors.create(SubCoord(order, dims));
		}

		
		float prev = t.get(crdSub);
		t.set(val, crdSub);
		return prev;
	}

	@Override
	public Float get(int... coord)
	{
		int crdMain = MainCoord(order, coord);
		int[] crdSub = SubCoord(order, coord);
		
		Tensor t = subset[crdMain];
		if(t != null)
		{
			return t.get(crdSub);
		}
		
		return 0f;
	}

	@Override
	public void clear()
	{
		subset = new Tensor[MainCoord(order, dims)];
	}
	
	
	@Override
	public Iterable<int[]> NZIndex()
	{
		return () -> new IndexKeys(this);
	}

	@Override
	public SubTensors instance()
	{
		return new SubTensors(order, Dimensions());
	}
	
	@Override
	public SubTensors copy()
	{
		Tensor[] set = Array.copy.of(subset);
		return new SubTensors(set, order, dims);
	}
	
	@Override
	public int NZCount()
	{
		return Count();
	}
}