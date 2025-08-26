package waffles.utils.alg.linear.measure.tensor;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.Angular;
import waffles.utils.alg.linear.measure.tensor.data.TensorArray;
import waffles.utils.alg.utilities.Inaccurate;
import waffles.utils.algebra.elements.linear.Tensors;
import waffles.utils.sets.indexed.IndexedSet;
import waffles.utils.tools.patterns.operator.Operable;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.patterns.properties.counters.data.Persistible;
import waffles.utils.tools.patterns.properties.values.Copyable;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Tensor} class defines an algebraic tensor using the standard dot product.
 * A tensor describes a multilinear transformation of any order and dimension. The tensor
 * values are stored in an implementation of the {@code TensorData} interface. By default,
 * a {@code TensorArray} is used. The algorithms for all basic operations are delegated
 * to its accompanying {@code TensorOps} type implementation.
 *
 * @author Waffles
 * @since Jul 4, 2018
 * @version 1.1
 * 
 * 
 * @see Copyable
 * @see Operable
 * @see Persistible
 * @see Inaccurate
 * @see IndexedSet
 * @see Angular
 */
public class Tensor implements Angular, Copyable<Tensor>, Inaccurate<Tensor>, IndexedSet<Float>, Operable<Tensor>, Persistible<TensorData>
{		
	private TensorData data;
	private TensorOps operator;
	private boolean isDestructible;

	/**
	 * Creates a new {@code Tensor}.
	 * 
	 * @param d  a data source
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
	 * This tensor stores its data in
	 * a {@code TensorArray} instance.
	 * 
	 * @param order  a tensor order
	 */
	public Tensor(int... order)
	{
		this(new TensorArray(order));
	}
	
	
	/**
	 * Checks destructibility of the {@code Tensor}.
	 * 
	 * @return  {@code true} if the tensor can be destroyed
	 */
	public boolean isDestructible()
	{
		return isDestructible;
	}
		
	/**
	 * Changes a single value in the {@code Tensor}.
	 * 
	 * @param val   a tensor value
	 * @param crds  a tensor coordinate
	 */
	public void set(float val, int... crds)
	{
		Data().put(val, crds);
	}
	
	/**
	 * Checks a type operator for the {@code Tensor}.
	 * 
	 * @param ops  a type operator
	 * @param e  an error margin
	 * @return  {@code true} if the operator is allowed
	 * 
	 * 
	 * @see TensorOps
	 */
	public boolean allows(TensorOps ops, double e)
	{
		// Check this one-liner the fuck out.
		return ops.instance(this).Allows(e).result();
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
	 * Creates a resized {@code Tensor}.
	 * 
	 * @param dims  a tensor dimension
	 * @return  a resized tensor
	 */
	public Tensor resize(int... dims)
	{
		return Operator().Resize(dims).result();
	}
	
	/**
	 * Computes a Hadamard {@code Tensor}.
	 * This is the element-wise product of
	 * isomorph tensors, i.e. tensors
	 * with equal dimensions.
	 * 
	 * @param t  a tensor
	 * @return   a hadamard product
	 */
	public Tensor hadamard(Tensor t)
	{
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Computing a Hadamard product requires equal dimensions: ", this, t);
		}
		
		
		Operation<Tensor> mul1 =   Operator().Hadamard(t);
		Operation<Tensor> mul2 = t.Operator().Hadamard(this);
		if(mul1.cost() < mul2.cost())
		{
			return mul1.result();
		}
		
		return mul2.result();
	}

	/**
	 * Returns an absolute {@code Tensor}.
	 * 
	 * @return  an absolute tensor
	 */
	public Tensor absolute()
	{
		return Operator().Absolute().result();
	}
	
	/**
	 * Destroys the {@code Tensor}.
	 * The tensor is rendered destructible,
	 * and then returned. This is useful
	 * when chaining operations.
	 * 
	 * @return  a destrucible tensor
	 * 
	 * 
	 * @see Tensor
	 */
	public Tensor destroy()
	{
		isDestructible = true;
		return this;
	}
	
	
	@Override
	public Float get(int... crds)
	{
		return Data().get(crds);
	}

	@Override
	public Boolean equals(Tensor t, float e)
	{
		if(!Tensors.isomorph(this, t))
		{
			throw new Tensors.DimensionError("Checking equality requires equal dimensions: ", this, t);
		}
		
		
		Operation<Boolean> eq1 =   Operator().Equality(   t, e);
		Operation<Boolean> eq2 = t.Operator().Equality(this, e);
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
	
	
	@Override
	public TensorData Data()
	{
		return data;
	}
	
	@Override
	public TensorOps Operator()
	{
		return (TensorOps) operator.instance(this);
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
	public Tensor minus(Abelian a)
	{
		return (Tensor) Angular.super.minus(a);
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
		return Operator().Copy().result();
	}
}