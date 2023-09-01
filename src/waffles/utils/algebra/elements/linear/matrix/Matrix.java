package waffles.utils.algebra.elements.linear.matrix;

import waffles.utils.algebra.Additive;
import waffles.utils.algebra.elements.Abelian;
import waffles.utils.algebra.elements.linear.Affine;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.algebra.elements.linear.tensor.Tensors;
import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.operator.Operation;

/**
 * The {@code Matrix} class defines a floating-point matrix derived from a {@code Tensor}.
 * The basic matrix operations are all delegated to a {@code MatrixOps} object.
 * 
 * @author Waffles
 * @since Mar 16, 2016
 * @version 1.0
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
	 * @param rows  a row count
	 * @param cols  a column count
	 */
	public Matrix(int rows, int cols)
	{
		super(rows, cols); setOperator(MatrixOps.Type());
	}

	
	/**
	 * Returns the transpose of the {@code Matrix}.
	 * 
	 * @return  the matrix transpose
	 */
	public Matrix transpose()
	{
		return Operator().Transpose().result();
	}
	
	/**
	 * Returns a vector product of the {@code Matrix}.
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
	 * Returns a matrix product of the {@code Matrix}.
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
	 * Returns a column vector in the {@code Matrix}.
	 * 
	 * @param j  a column index
	 * @return  a matrix column
	 * @see Vector
	 */
	public Vector Column(int j)
	{
		Vector v = Vectors.create(Rows());
		for(int i = 0; i < Rows(); i++)
		{
			v.set(get(i, j), i);
		}
		
		return v;
	}
	
	/**
	 * Returns a row vector in the {@code Matrix}.
	 * 
	 * @param i  a row index
	 * @return  a matrix row
	 * @see Vector
	 */
	public Vector Row(int i)
	{
		Vector v = Vectors.create(Columns());
		for(int j = 0; j < Columns(); j++)
		{
			v.set(get(i, j), j);
		}
		
		return v;
	}
	
	/**
	 * Returns a column count of the {@code Matrix}.
	 * 
	 * @return  a column count
	 */
	public int Columns()
	{
		return Dimensions()[1];
	}
	
	/**
	 * Returns a row count of the {@code Matrix}.
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
	public Matrix times(Float val)
	{
		return (Matrix) super.times(val);
	}
	
	@Override
	public Matrix plus(Additive a)
	{
		return (Matrix) super.plus(a);
	}
	
	@Override
	public Matrix normalize()
	{
		return (Matrix) super.normalize();
	}

	@Override
	public Matrix copy()
	{
		return (Matrix) super.copy();
	}
}