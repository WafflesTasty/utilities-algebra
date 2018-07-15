package zeno.util.algebra.attempt4.linear;

import zeno.util.algebra.Dimensional;
import zeno.util.algebra.Measurable;
import zeno.util.algebra.attempt4.linear.data.Data;
import zeno.util.algebra.attempt4.linear.data.Dense;
import zeno.util.algebra.attempt4.linear.vec.Vector;
import zeno.util.algebra.attempt4.linear.vec.Vectors;
import zeno.util.algebra.linear.tensor.TensorOps;
import zeno.util.algebra.linear.tensor.Tensors;
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
 * @see Dimensional
 * @see Measurable
 * @see Operable
 */
public class Tensor implements Operable<Tensor>, Dimensional<Tensor>, Measurable<Tensor>
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
	
	
	
	@Override
	public boolean equals(Tensor t, int ulps)
	{
		Operation<Boolean> eq1 =   ((TensorOps<Tensor>) Operator()).equality(   t, ulps);
		Operation<Boolean> eq2 = ((TensorOps<Tensor>) t.Operator()).equality(this, ulps);
		if(eq1.cost() < eq2.cost())
		{
			return eq1.result();
		}
		
		return eq2.result();
	}

	@Override
	public Operator Operator()
	{
		return ops.instance(this);
	}
	
	public void setOperator(Operator o)
	{
		ops = o;
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
	 * Returns all values in the {@code Tensor}.
	 * 
	 * @return  the tensor values
	 */
	@Override
	public float[] Values()
	{
		return Data().toArray();
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
			
			v.set(Data().get(coords), i);
		}
		
		return v;
	}
			
	/**
	 * Returns the trace of the {@code Tensor}.
	 * 
	 * @return  the tensor trace
	 */
	public float Trace()
	{
		return ((TensorOps<Tensor>) Operator()).trace();
	}
	
	
	@Override
	public int[] Dimensions()
	{
		return Data().Dimensions();
	}
	
	@Override
	public float dot(Tensor t)
	{
		Operation<Float> dot1 = ((TensorOps<Tensor>) Operator()).dotproduct(t);
		Operation<Float> dot2 = ((TensorOps<Tensor>) t.Operator()).dotproduct(this);
		if(dot1.cost() < dot2.cost())
		{
			return dot1.result();
		}
		
		return dot2.result();
	}

	@Override
	public Tensor times(float val)
	{
		return ((TensorOps<Tensor>) Operator()).multiply(val);
	}
	
	@Override
	public Tensor plus(Tensor t)
	{
		Operation<Tensor> add1 = ((TensorOps<Tensor>) Operator()).addition(t);
		Operation<Tensor> add2 = ((TensorOps<Tensor>) t.Operator()).addition(this);
		if(add1.cost() < add2.cost())
		{
			return add1.result();
		}
		
		return add2.result();
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