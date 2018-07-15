package zeno.util.algebra.linear.matrix.operators.square;

import zeno.util.algebra.attempt4.linear.Matrix;
import zeno.util.algebra.attempt4.linear.Tensor;
import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.operations.banded.BandedAddition;
import zeno.util.algebra.linear.matrix.operations.banded.BandedDotProduct;
import zeno.util.algebra.linear.matrix.operations.banded.BandedEquality;
import zeno.util.algebra.linear.matrix.operations.banded.BandedLProduct;
import zeno.util.algebra.linear.matrix.operations.banded.BandedRProduct;
import zeno.util.algebra.linear.matrix.operators.Square;
import zeno.util.tools.patterns.Operation;
import zeno.util.tools.patterns.Operator;
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
 * @param <M>  the type of the underlying matrix
 * @see Matrix
 * @see Square
 */
public interface Banded<M extends Matrix> extends Square<M>
{
	/**
	 * Returns the abstract type of the {@code Banded Operator}.
	 * 
	 * @param lband  the lower band of the type
	 * @param uband  the upper band of the type
	 * @return  a type operator
	 */
	public static Banded<?> Type(int lband, int uband)
	{
		return new Banded<Matrix>()
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
	public default Operation<Float> dotproduct(M matrix)
	{
		return new BandedDotProduct(Operable(), matrix, LowerBand(), UpperBand());
	}
	
	@Override
	public default Operation<Boolean> equality(M matrix, int ulps)
	{
		int lband = matrix.Rows() - 1;
		int uband = matrix.Columns() - 1;
		
		Operator<?> op = matrix.Operator();
		if(op instanceof Banded)
		{
			lband = Integers.max(LowerBand(), ((Banded<?>) op).LowerBand());
			uband = Integers.max(UpperBand(), ((Banded<?>) op).UpperBand());
		}
		
		return new BandedEquality(Operable(), matrix, lband, uband, ulps);
	}
		
	@Override
	public default Operation<M> LMultiplier(M matrix)
	{
		return new BandedRProduct<>(matrix, Operable(), LowerBand(), UpperBand());
	}
	
	@Override
	public default Operation<M> RMultiplier(M matrix)
	{
		return new BandedLProduct<>(Operable(), matrix, LowerBand(), UpperBand());
	}

	@Override
	public default Operation<M> addition(M matrix)
	{
		return new BandedAddition<>(matrix, Operable(), LowerBand(), UpperBand());
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
			return ((Banded<?>) op).LowerBand() <= LowerBand()
				&& ((Banded<?>) op).UpperBand() <= UpperBand();
		}

		return false;
	}
	
	@Override
	public default Banded<M> instance(Tensor t)
	{
		return () ->
		{
			return (M) t;
		};
	}
	
	@Override
	public default M multiply(float val)
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
		
		return (M) result;
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