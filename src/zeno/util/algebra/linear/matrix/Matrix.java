package zeno.util.algebra.linear.matrix;

import zeno.util.algebra.linear.matrix.types.MatrixOps;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.algebra.linear.tensor.Tensors;
import zeno.util.algebra.linear.tensor.data.Data;
import zeno.util.algebra.linear.vector.Vector;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.tools.patterns.properties.operable.Operation;

/**
 * The {@code Matrix} class provides a default implementation for
 * algebraic matrices using the standard dot product. A matrix describes
 * a linear relation for tensors of the second order.
 * 
 * @author Zeno
 * @since Mar 16, 2016
 * @version 1.0
 * 
 * 
 * @see Tensor
 */
public class Matrix extends Tensor
{	
	/**
	 * Creates a new {@code Matrix}.
	 * 
	 * @param d  a data source
	 * @see Data
	 */
	public Matrix(Data d)
	{
		super(d); setOperator(MatrixOps.Type());
//		ops = Generic.Type();
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
//		ops = Generic.Type();
	}

	
	/**
	 * Returns the inverse of the {@code Matrix}.
	 * 
	 * @return  a matrix inverse
	 */
	public Matrix inverse()
	{
		if(is(Square.Type()))
		{
			return Operator().inverse();
		}
		
		throw new Tensors.DimensionError("Computing the inverse requires a square matrix: ", this);
	}

	/**
	 * Returns a matrix product of the {@code Matrix}.
	 * 
	 * @param m  a matrix to multiply with
	 * @return  a matrix multiplication
	 */
	public Matrix times(Matrix m)
	{
		int row2 = m.Rows();
		int col1 = Columns();
	
		if(col1 != row2)
		{
			throw new Tensors.DimensionError("Computing a matrix product requires compatible dimensions: ", this, m);
		}
		
		
		Operation<Matrix> multi1 =   Operator().RMultiplier(m);
		Operation<Matrix> multi2 = m.Operator().LMultiplier(this);
		if(multi1.cost() < multi2.cost())
		{
			return multi1.result();
		}

		return multi2.result();
	}
		
	/**
	 * Returns the pseudoinverse of the {@code Matrix}.
	 * 
	 * @return  a matrix pseudoinverse
	 */
	public Matrix pseudoinverse()
	{
		return Operator().pseudoinverse();
	}
		
	/**
	 * Returns the transpose of the {@code Matrix}.
	 * 
	 * @return  the matrix transpose
	 */
	public Matrix transpose()
	{
		return Operator().transpose();
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
	 * Returns the column count of the {@code Matrix}.
	 * 
	 * @return  the column count
	 */
	public int Columns()
	{
		return Dimensions()[1];
	}
	
	/**
	 * Returns the row count of the {@code Matrix}.
	 * 
	 * @return  the row count
	 */
	public int Rows()
	{
		return Dimensions()[0];
	}
	
	
	@Override
	public MatrixOps Operator()
	{
		return (MatrixOps) super.Operator();
	}
	
	@Override
	public Matrix minus(Tensor t)
	{
		return (Matrix) super.minus(t);
	}
		
	@Override
	public Matrix times(float val)
	{
		return (Matrix) super.times(val);
	}
	
	@Override
	public Matrix plus(Tensor t)
	{
		return (Matrix) super.plus(t);
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