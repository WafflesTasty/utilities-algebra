package waffles.utils.alg.linear.measure.matrix.types.banded;

import waffles.utils.alg.linear.measure.matrix.Matrix;
import waffles.utils.alg.linear.measure.matrix.ops.square.banded.BandedAddition;
import waffles.utils.alg.linear.measure.matrix.ops.square.banded.BandedDotProduct;
import waffles.utils.alg.linear.measure.matrix.ops.square.banded.BandedLProduct;
import waffles.utils.alg.linear.measure.matrix.ops.square.banded.BandedRProduct;
import waffles.utils.alg.linear.measure.matrix.ops.square.banded.BandedScalar;
import waffles.utils.alg.linear.measure.matrix.types.Square;
import waffles.utils.alg.linear.measure.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code Banded} operator is used for matrices with a lower and upper band of zero values.
 * Both lower and upper band are expressed as positive integers, with a diagonal matrix
 * characterized as having a lower and upper band equal to zero.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Banded extends Square
{
	/**
	 * Returns the abstract {@code Banded} type.
	 * 
	 * @param bLower  a  lower band size
	 * @param bUpper  an upper band size
	 * @return  a type operator
	 */
	public static Banded Type(int bLower, int bUpper)
	{
		return new Banded()
		{
			@Override
			public Matrix Operable()
			{
				return null;
			}
			
			
			@Override
			public int LowerBand()
			{
				return bLower;
			}
			
			@Override
			public int UpperBand()
			{
				return bUpper;
			}
		};
	}
	
	/**
	 * Computes the band size of a {@code Matrix} below the diagonal.
	 * 
	 * @param m    a base matrix
	 * @param err  an error margin
	 * @return  a lower band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getLowerBand(Matrix m, float err)
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		int band = Integers.max(rows, cols) - 1;
		for(int i = rows - 1; i > 0; i--)
		{
			for(int j = 0; j < Integers.min(rows - i, cols); j++)
			{
				if(Floats.abs(m.get(i + j, j)) > err)
				{
					return band;
				}
			}

			band--;
		}
		
		return 0;
	}
	
	/**
	 * Computes the band size of a {@code Matrix} above the diagonal.
	 * 
	 * @param m    a base matrix
	 * @param err  an error margin
	 * @return  an upper band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getUpperBand(Matrix m, float err)
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		int band = Integers.max(rows, cols) - 1;
		for(int i = cols - 1; i > 0; i--)
		{
			for(int j = 0; j < Integers.min(cols - i, rows); j++)
			{
				if(Floats.abs(m.get(j, i + j)) > err)
				{
					return band;
				}
			}
			
			band--;
		}
		
		return 0;
	}

	/**
	 * A {@code Qualify} operation checks if a matrix is {@code Banded}.
	 *
	 * @author Waffles
	 * @since 22 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Operation
	 */
	public static class Qualify extends Square.Qualify
	{		
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param b1   a banded operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Banded
		 */
		public Qualify(Banded b1, float err)
		{
			super(b1, err);
		}

		
		@Override
		public Banded Operator()
		{
			return (Banded) super.Operator();
		}

		@Override
		public Boolean result()
		{
			if(!super.result())
			{
				return false;
			}
			
			
			float e1 = Error();
			Matrix m1 = Matrix();
			Banded b = Operator();

			return Banded.getLowerBand(m1, e1) <= b.LowerBand()
				&& Banded.getUpperBand(m1, e1) <= b.UpperBand();
		}
		
		@Override
		public int cost()
		{
			int r1 = Matrix().Rows();
			int c1 = Matrix().Columns();
			
			return super.cost() + r1 * (c1 - 1);
		}
	}

	
	@Override
	public default Operation<Tensor> Addition(Tensor t)
	{
		return new BandedAddition(Operable(), (Matrix) t);
	}
	
	@Override
	public default Operation<Float> DotProduct(Tensor t)
	{
		return new BandedDotProduct(Operable(), (Matrix) t);
	}
			
	@Override
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new BandedRProduct(Operable(), m);
	}
		
	@Override
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new BandedLProduct(Operable(), m);
	}
	
	@Override
	public default Operation<Tensor> Multiply(float v)
	{
		return new BandedScalar(Operable(), v);
	}
		
	
	@Override
	public default Banded instance(Tensor t)
	{
		return () -> (Matrix) t;
	}

	@Override
	public default Operation<Boolean> Allows(float e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Banded;
	}
				
	
	/**
	 * Returns the lower band of the {@code Banded}.
	 * 
	 * @return  a matrix lower band
	 */
	public default int LowerBand()
	{
		if(Operable() != null)
		{
			return Operable().Rows() - 1;
		}
		
		return Integers.MAX_VALUE;
	}
	
	/**
	 * Returns the upper band of the {@code Banded}.
	 * 
	 * @return  a matrix upper band
	 */
	public default int UpperBand()
	{
		if(Operable() != null)
		{
			return Operable().Columns() - 1;
		}
		
		return Integers.MAX_VALUE;
	}
}