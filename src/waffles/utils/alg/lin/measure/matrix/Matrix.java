package waffles.utils.alg.lin.measure.matrix;

import waffles.utils.alg.Abelian;
import waffles.utils.alg.lin.measure.matrix.format.MatrixFormat;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.tensor.TensorData;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.alg.lin.measure.vector.Vectors;
import waffles.utils.alg.utilities.affine.Affine;
import waffles.utils.alg.utilities.errors.DimensionError;
import waffles.utils.lang.tokens.Token;
import waffles.utils.lang.tokens.format.Format;
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
 * @see Vector
 * @see Token
 */
public class Matrix extends Tensor implements Affine, Token
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
	 * Returns a transposed {@code Matrix}.
	 * 
	 * @return  the matrix transpose
	 */
	public Matrix transpose()
	{
		return Operator().Transpose().result();
	}
	
	/**
	 * Returns a {@code Matrix-Vector} product.
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
			throw new DimensionError("Computing a matrix product requires compatible dimensions: ", this, m);
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
	 * Returns a {@code Matrix} 1-norm.
	 * 
	 * @return  a 1-norm
	 */
	public float norm1()
	{
		return Operator().Norm1().result();
	}
	
	/**
	 * Prints the {@code Matrix} to console.
	 */
	public void print()
	{
		for(String s : describe())
		{
			System.out.println(s);
		}
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
	public Format<?> Formatter()
	{
		return new MatrixFormat();
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
	public Matrix plus(Abelian a)
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
	public Matrix absolute()
	{
		return (Matrix) super.absolute();
	}
	
	
	@Override
	public Matrix instance()
	{
		return (Matrix) super.instance();
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