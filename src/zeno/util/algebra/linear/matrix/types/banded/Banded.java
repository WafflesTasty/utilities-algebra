package zeno.util.algebra.linear.matrix.types.banded;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.Matrix;
import zeno.util.algebra.linear.matrix.functions.banded.BandedAddition;
import zeno.util.algebra.linear.matrix.functions.banded.BandedDotProduct;
import zeno.util.algebra.linear.matrix.functions.banded.BandedEquality;
import zeno.util.algebra.linear.matrix.functions.banded.BandedLProduct;
import zeno.util.algebra.linear.matrix.functions.banded.BandedRProduct;
import zeno.util.algebra.linear.matrix.types.Square;
import zeno.util.algebra.linear.tensor.Tensor;
import zeno.util.tools.patterns.properties.operable.Operation;
import zeno.util.tools.patterns.properties.operable.Operator;
import zeno.util.tools.primitives.Floats;
import zeno.util.tools.primitives.Integers;

/**
 * The {@code Banded} interface defines an operator for banded matrices.
 * Matrices tagged with this operator are assumed to have a lower
 * and upper band of zero values.
 * 
 * @author Zeno
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Square
 */
public interface Banded extends Square
{
	/**
	 * Returns the abstract type of the {@code Banded Operator}.
	 * 
	 * @param lband  the lower band of the type
	 * @param uband  the upper band of the type
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
	 * @param m  a matrix to check
	 * @param ulps  an error margin
	 * @return  the lower band size
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
	 * @param m  a matrix to check
	 * @param ulps  an error margin
	 * @return  the upper band size
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
	public default Operation<Float> dotproduct(Tensor t)
	{
		return new BandedDotProduct(Operable(), (Matrix) t, LowerBand(), UpperBand());
	}
			
	@Override
	public default Operation<Matrix> LMultiplier(Matrix m)
	{
		return new BandedRProduct(m, Operable(), LowerBand(), UpperBand());
	}
	
	@Override
	public default Operation<Boolean> equality(Tensor t, int ulps)
	{
		int lband = ((Matrix) t).Rows() - 1;
		int uband = ((Matrix) t).Columns() - 1;
		
		Operator<?> op = t.Operator();
		if(op instanceof Banded)
		{
			lband = Integers.max(LowerBand(), ((Banded) op).LowerBand());
			uband = Integers.max(UpperBand(), ((Banded) op).UpperBand());
		}
		
		return new BandedEquality(Operable(), (Matrix) t, lband, uband, ulps);
	}
	
	@Override
	public default Operation<Matrix> RMultiplier(Matrix m)
	{
		return new BandedLProduct(Operable(), m, LowerBand(), UpperBand());
	}

	@Override
	public default Operation<Tensor> addition(Tensor t)
	{
		return new BandedAddition((Matrix) t, Operable(), LowerBand(), UpperBand());
	}
	
	
	@Override
	public default Matrix multiply(float val)
	{
		Matrix m = Operable();
		
		int cols = m.Columns();
		int rows = m.Rows();
	
		
		Matrix result = Matrices.create(rows, cols);
		for(int r = 0; r < rows; r++)
		{
			int cMin = Integers.max(r - LowerBand(), 0);
			int cMax = Integers.min(r + UpperBand(), cols - 1);
			
			for(int c = cMin; c <= cMax; c++)
			{
				result.set(m.get(r, c) * val, r, c);
			}
		}
		
		return result;
	}
	
	@Override
	public default boolean matches(Tensor t, int ulps)
	{
		if(t.is(Square.Type()))
		{
			return Banded.getLowerBand((Matrix) t, ulps) <= LowerBand()
				&& Banded.getUpperBand((Matrix) t, ulps) <= UpperBand();
		}
		
		return false;
	}
	
	@Override
	public default boolean matches(Operator<Tensor> op)
	{
		if(op instanceof Banded)
		{
			return ((Banded) op).LowerBand() <= LowerBand()
				&& ((Banded) op).UpperBand() <= UpperBand();
		}

		return false;
	}
		
	@Override
	public default Banded instance(Tensor t)
	{
		return () ->
		{
			return (Matrix) t;
		};
	}
		
	
	/**
	 * Returns the lower band of the {@code Banded}.
	 * 
	 * @return  the matrix lower band
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
	 * @return  the matrix upper band
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