package waffles.utils.alg.linear.measure.matrix;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.Additive;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.algebra.elements.linear.Affine;
import waffles.utils.algebra.elements.linear.matrix.MatrixOps;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * A {@code Matrix} is defined as a second order {@code Tensor}.
 * 
 * @author Waffles
 * @since Mar 16, 2016
 * @version 1.1
 * 
 * 
 * @see Affine
 * @see Tensor
 */
public class Matrix extends Tensor implements Affine
{		
	/**
	 * Creates a new {@code Matrix}.
	 * 
	 * @param d  a data object
	 * 
	 * 
	 * @see TensorData
	 */
	public Matrix(TensorData d)
	{
		super(d); setOperator(MatrixOps.Type());
	}
	
	/**
	 * Creates a new {@code Matrix}.
	 * 
	 * @param r  a row count
	 * @param c  a column count
	 */
	public Matrix(int r, int c)
	{
		super(r, c); setOperator(MatrixOps.Type());
	}
	
	
	/**
	 * Returns an absolute {@code Matrix}.
	 * 
	 * @return  an absolute matrix
	 */
	public Matrix absolute()
	{
		return Operator().Absolute().result();
	}
	
	/**
	 * Returns a transpose {@code Matrix}.
	 * 
	 * @return  the matrix transpose
	 */
	public Matrix transpose()
	{
		return Operator().Transpose().result();
	}
	
	
	/**
	 * Returns a {@code Matrix} product.
	 * 
	 * @param v  a vector to multiply
	 * @return   a matrix product
	 * 
	 * 
	 * @see Vector
	 */
	public Vector times(Vector v)
	{
		return (Vector) times((Matrix) v);
	}
	
	/**
	 * Returns a {@code Matrix} product.
	 * 
	 * @param m  a matrix to multiply
	 * @return   a matrix product
	 */
	public Matrix times(Matrix m)
	{
		int row2 = m.Rows();
		int col1 = Columns();
	
		if(col1 != row2)
		{
			throw new Tensors.DimensionError("Computing a matrix product requires compatible dimensions: ", this, m);
		}
		
		
		Operation<Matrix> mul1 =   Operator().RMultiplier(m);
		Operation<Matrix> mul2 = m.Operator().LMultiplier(this);
		if(mul1.cost() < mul2.cost())
		{
			return mul1.result();
		}

		return mul2.result();
	}
	
	
	/**
	 * Returns a resized {@code Matrix}.
	 * 
	 * @param r  a row count
	 * @param c  a column count
	 * @return  a resized matrix
	 */
	public Matrix resize(int r, int c)
	{
		return (Matrix) super.resize(r, c);
	}
	
	/**
	 * Returns a {@code Matrix} column.
	 * 
	 * @param c  a column index
	 * @return  a column vector
	 * 
	 * 
	 * @see Vector
	 */
	public Vector Column(int c)
	{
		Vector v = Vectors.create(Rows());
		for(int i = 0; i < Rows(); i++)
		{
			v.set(get(i, c), i);
		}
		
		return v;
	}
	
	/**
	 * Returns a {@code Matrix} row.
	 * 
	 * @param r  a row index
	 * @return  a row vector
	 * 
	 * 
	 * @see Vector
	 */
	public Vector Row(int r)
	{
		Vector v = Vectors.create(Columns());
		for(int j = 0; j < Columns(); j++)
		{
			v.set(get(r, j), j);
		}
		
		return v;
	}
	
	/**
	 * Returns a column count.
	 * 
	 * @return  a column count
	 */
	public int Columns()
	{
		return Dimensions()[1];
	}
	
	/**
	 * Returns a row count.
	 * 
	 * @return  a row count
	 */
	public int Rows()
	{
		return Dimensions()[0];
	}
	
	
	@Override
	public Matrix Span()
	{
		return this;
	}

	@Override
	public Factory Factory()
	{
		return mat -> mat;
	}
	
	@Override
	public MatrixOps Operator()
	{
		return (MatrixOps) super.Operator();
	}
		
	@Override
	public Matrix minus(Abelian a)
	{
		return (Matrix) super.minus(a);
	}
		
	@Override
	public Matrix hadamard(Tensor t)
	{
		return (Matrix) super.hadamard(t);
	}
		
	@Override
	public Matrix plus(Additive a)
	{
		return (Matrix) super.plus(a);
	}
	
	@Override
	public Matrix times(Float v)
	{
		return (Matrix) super.times(v);
	}
	
	@Override
	public Matrix normalize()
	{
		return (Matrix) super.normalize();
	}

	@Override
	public Matrix destroy()
	{
		return (Matrix) super.destroy();
	}
	
	@Override
	public Matrix copy()
	{
		return (Matrix) super.copy();
	}
}