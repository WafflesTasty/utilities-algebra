package waffles.utils.algebra.elements.linear.tensor;

import waffles.util.sets.DimensionalSet;
import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.linear.Angular;
import waffles.utils.algebra.elements.linear.tensor.data.TensorArray;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.tools.patterns.operator.Operable;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.patterns.semantics.Copyable;
import waffles.utils.tools.patterns.semantics.Inaccurate;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Tensor} class defines an algebraic tensor using the standard dot product.
 * A tensor describes a multilinear transformation of any order and dimension. Its values
 * are stored in a {@code TensorData} object, which can be implemented densely or sparsely.
 * The basic tensor operations are all delegated to an {@code TensorOps} object.
 *
 * @author Waffles
 * @since Jul 4, 2018
 * @version 1.0
 * 
 * 
 * @see DimensionalSet
 * @see Inaccurate
 * @see Operable
 * @see Angular
 * @see Tensor
 */
public class Tensor implements Angular, Copyable<Tensor>, DimensionalSet<Float>, Inaccurate<Tensor>, Operable<Tensor>
{	
	private TensorData data;
	private TensorOps operator;
	private boolean isDestructible;

	/**
	 * Creates a new {@code Tensor}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Tensor(TensorData d)
	{
		operator = TensorOps.Type();
		data = d;
	}
	
	/**
	 * Creates a new {@code Tensor}.
	 * This constructor assumes a default
	 * dense data object.
	 * 
	 * @param order  the tensor order
	 */
	public Tensor(int... order)
	{
		this(new TensorArray(order));
	}
	
	
	/**
	 * Changes a single value in the {@code Tensor}.
	 * 
	 * @param val     a tensor value
	 * @param coords  a tensor coordinate
	 */
	public void set(float val, int... coords)
	{
		Data().put(val, coords);
	}

	/**
	 * Sets the destructibility of the {@code Tensor}.
	 * 
	 * @param destroy  a destructible state
	 */
	public void setDestructible(boolean destroy)
	{
		isDestructible = destroy;
	}
	
	/**
	 * Changes the operator of the {@code Tensor}.
	 * 
	 * @param ops  a tensor operator
	 * 
	 * 
	 * @see TensorOps
	 */
	public void setOperator(TensorOps ops)
	{
		operator = ops;
	}
	
	/**
	 * Checks destructibility of the {@code Tensor}.
	 * 
	 * @return  {@code true} if the data is obsolete
	 */
	public boolean isDestructible()
	{
		return isDestructible;
	}
	
	/**
	 * Returns the data of the {@code Tensor}.
	 * 
	 * @return  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public TensorData Data()
	{
		return data;
	}
	

	@Override
	public Boolean equals(Tensor t, int ulps)
	{
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Checking equality requires equal dimensions: ", this, t);
		}
		
		
		Operation<Boolean> eq1 =   Operator().Equality(   t, ulps);
		Operation<Boolean> eq2 = t.Operator().Equality(this, ulps);
		if(eq1.cost() < eq2.cost())
		{
			return eq1.result();
		}
		
		return eq2.result();
	}
	
	@Override
	public Float get(int... coords)
	{
		return Data().get(coords);
	}
	
	@Override
	public int[] Dimensions()
	{
		return Data().Dimensions();
	}
	
	
	@Override
	public float dot(Angular a)
	{
		Tensor t = (Tensor) a;
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Computing a dot product requires equal dimensions: ", this, t);
		}
		
		
		Operation<Float> dot1 =   Operator().DotProduct(t);
		Operation<Float> dot2 = t.Operator().DotProduct(this);
		if(dot1.cost() < dot2.cost())
		{
			return dot1.result();
		}
		
		return dot2.result();
	}
	
	@Override
	public Tensor plus(Additive a)
	{
		Tensor t = (Tensor) a;
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Computing a sum requires equal dimensions: ", this, t);
		}
		
		
		Operation<Tensor> add1 =   Operator().Addition(t);
		Operation<Tensor> add2 = t.Operator().Addition(this);
		if(add1.cost() < add2.cost())
		{
			return add1.result();
		}
		
		return add2.result();
	}

	@Override
	public Tensor times(Float v)
	{
		return Operator().Multiply(v).result();
	}

	@Override
	public TensorOps Operator()
	{
		return (TensorOps) operator.instance(this);
	}
	
	@Override
	public Tensor normalize()
	{
		float norm = norm();
		if(Floats.isEqual(norm, 0f, 3)
		|| Floats.isEqual(norm, 1f, 3))
		{
			return copy();
		}
		
		return times(1f / norm);
	}
	
	@Override
	public Tensor instance()
	{
		return Tensors.create(Dimensions());
	}
	
	@Override
	public Tensor copy()
	{
		if(isDestructible())
		{
			return Tensors.create(data);
		}

		TensorData copy = data.copy();
		return Tensors.create(copy);
	}
}