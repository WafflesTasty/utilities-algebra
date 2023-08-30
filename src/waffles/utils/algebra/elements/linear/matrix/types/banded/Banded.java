package waffles.utils.algebra.elements.linear.matrix.types.banded;

import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.matrix.ops.banded.BandedAddition;
import waffles.utils.algebra.elements.linear.matrix.ops.banded.BandedDotProduct;
import waffles.utils.algebra.elements.linear.matrix.ops.banded.BandedLProduct;
import waffles.utils.algebra.elements.linear.matrix.ops.banded.BandedRProduct;
import waffles.utils.algebra.elements.linear.matrix.ops.banded.BandedScalar;
import waffles.utils.algebra.elements.linear.matrix.types.Square;
import waffles.utils.algebra.elements.linear.tensor.Tensor;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A matrix tagged with the {@code Banded} operator is assumed to have a lower and upper band of zero values.
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
	 * Returns the abstract type of the {@code Banded} operator.
	 * 
	 * @param lband  a  lower band size
	 * @param uband  an upper band size
	 * @return  a type operator
	 */
	public static Banded Type(int lband, int uband)
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
				return lband;
			}
			
			@Override
			public int UpperBand()
			{
				return uband;
			}
		};
	}
	
	/**
	 * Computes the band size of a matrix below the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param ulps  an error margin
	 * @return  a lower band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getLowerBand(Matrix m, int ulps)
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		int band = Integers.max(rows, cols) - 1;
		for(int i = rows - 1; i > 0; i--)
		{
			for(int j = 0; j < Integers.min(rows - i, cols); j++)
			{
				if(!Floats.isZero(m.get(i + j, j), ulps))
				{
					return band;
				}
			}

			band--;
		}
		
		return 0;
	}
	
	/**
	 * Computes the band size of a matrix above the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param ulps  an error margin
	 * @return  an upper band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getUpperBand(Matrix m, int ulps)
	{
		int rows = m.Rows();
		int cols = m.Columns();
		
		int band = Integers.max(rows, cols) - 1;
		for(int i = cols - 1; i > 0; i--)
		{
			for(int j = 0; j < Integers.min(cols - i, rows); j++)
			{
				if(!Floats.isZero(m.get(j, i + j), ulps))
				{
					return band;
				}
			}
			
			band--;
		}
		
		return 0;
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
	public default Operation<Tensor> Multiply(float val)
	{
		return new BandedScalar(Operable(), val);
	}
	
	
	@Override
	public default Banded instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
	
	@Override
	public default boolean allows(Tensor t, int ulps)
	{
		if(t.is(Square.Type()))
		{
			return Banded.getLowerBand((Matrix) t, ulps) <= LowerBand()
				&& Banded.getUpperBand((Matrix) t, ulps) <= UpperBand();
		}
		
		return false;
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