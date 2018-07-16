package zeno.util.algebra.linear.tensor;

import zeno.util.algebra.Dimensional;
import zeno.util.algebra.linear.Measurable;
import zeno.util.algebra.linear.tensor.data.Data;
import zeno.util.algebra.linear.tensor.data.Dense;
import zeno.util.algebra.linear.tensor.types.TensorOps;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.tools.patterns.Operable;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.patterns.Operator;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code Tensor} class defines an algebraic tensor using the standard dot product.
 * A tensor describes a multilinear transformation of any order and dimension. Its values
 * are stored in a {@code Data} object, which can be implemented densely or sparsely.
 * The basic tensor operations are all delegated to an {@code Operator} object.
 *
 * @author Zeno
 * @since Jul 4, 2018
 * @version 1.0
 * 
 * 
 * @see Dimensional
 * @see Measurable
 * @see Operable
 */
public class Tensor implements Dimensional, Measurable<Tensor>, Operable<Tensor>
{	
	private Data data;
	private Operator<Tensor> ops;

	/**
	 * Creates a new {@code Tensor}.
	 * This constructor assumes a default
	 * dense data object.
	 * 
	 * @param order  the tensor order
	 */
	public Tensor(int... order)
	{
		this(new Dense(order));
	}
	
	/**
	 * Creates a new {@code Tensor}.
	 * 
	 * @param dat  a data source
	 * @see Data
	 */
	public Tensor(Data dat)
	{
		ops = TensorOps.Type();
		data = dat;
	}
	
	
	/**
	 * Returns a value in the {@code Tensor}.
	 * 
	 * @param coords  a tensor coördinate
	 * @return  a tensor value
	 */
	public float get(int... coords)
	{
		return data.get(coords);
	}
	
	/**
	 * Changes the operator of the {@code Tensor}.
	 * 
	 * @param ops  a new operator
	 * 
	 *
	 * @see TensorOps
	 */
	public void setOperator(TensorOps ops)
	{
		this.ops = ops;
	}
	
	/**
	 * Changes a value in the {@code Tensor}.
	 * 
	 * @param val  a tensor value
	 * @param coords  a tensor coördinate
	 */
	public void set(float val, int... coords)
	{
		data.set(val, coords);
	}

	@Override
	public boolean equals(Tensor t, int ulps)
	{
		Operation<Boolean> eq1 =   Operator().equality(   t, ulps);
		Operation<Boolean> eq2 = t.Operator().equality(this, ulps);
		if(eq1.cost() < eq2.cost())
		{
			return eq1.result();
		}
		
		return eq2.result();
	}
	
	@Override
	public int[] Dimensions()
	{
		return Data().Dimensions();
	}
	
	
	/**
	 * Returns the data of the {@code Tensor}.
	 * 
	 * @return  the tensor data
	 * @see Data
	 */
	public Data Data()
	{
		return data;
	}

	/**
	 * Returns the diagonal of the {@code Tensor}.
	 * 
	 * @return  the main diagonal
	 * @see Vector
	 */
	public Vector Diagonal()
	{
		int size = Integers.min(Dimensions());
		Vector v = Vectors.create(size);
		for(int i = 0; i < size; i++)
		{
			int[] coords = new int[Order()];
			for(int k = 0; k < Order(); k++)
			{
				coords[k] = i;
			}
			
			v.set(get(coords), i);
		}
		
		return v;
	}

	/**
	 * Returns all values in the {@code Tensor}.
	 * 
	 * @return  the tensor values
	 */
	@Override
	public float[] Values()
	{
		return Data().Values();
	}
	
	/**
	 * Returns the trace of the {@code Tensor}.
	 * 
	 * @return  the tensor trace
	 */
	public float Trace()
	{
		return Operator().trace();
	}
	
			
	@Override
	public float dot(Tensor t)
	{
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Computing a dot product requires equal dimensions: ", this, t);
		}
		
		
		Operation<Float> dot1 =   Operator().dotproduct(t);
		Operation<Float> dot2 = t.Operator().dotproduct(this);
		if(dot1.cost() < dot2.cost())
		{
			return dot1.result();
		}
		
		return dot2.result();
	}
	
	@Override
	public TensorOps Operator()
	{
		return (TensorOps) ops.instance(this);
	}
	
	@Override
	public Tensor plus(Tensor t)
	{
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Computing a sum requires equal dimensions: ", this, t);
		}
		
		
		Operation<Tensor> add1 =   Operator().addition(t);
		Operation<Tensor> add2 = t.Operator().addition(this);
		if(add1.cost() < add2.cost())
		{
			return add1.result();
		}
		
		return add2.result();
	}
	
	@Override
	public Tensor times(float v)
	{
		return Operator().multiply(v);
	}
	
	@Override
	public Tensor instance()
	{
		return Tensors.create(Dimensions());
	}
	
	@Override
	public Tensor copy()
	{
		Tensor copy = Measurable.super.copy();
		copy.ops = ops.instance(null);
		copy.data = data.copy();
		return copy;
	}
}