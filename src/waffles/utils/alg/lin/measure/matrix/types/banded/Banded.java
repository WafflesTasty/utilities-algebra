package waffles.utils.alg.lin.measure.matrix.types.banded;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.BandedAddition;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.BandedDotProduct;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.BandedLProduct;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.BandedRProduct;
import waffles.utils.alg.lin.measure.matrix.ops.square.banded.BandedScalar;
import waffles.utils.alg.lin.measure.matrix.types.Square;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerBidiagonal;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerHessenberg;
import waffles.utils.alg.lin.measure.matrix.types.banded.lower.LowerTriangular;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperBidiagonal;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperHessenberg;
import waffles.utils.alg.lin.measure.matrix.types.banded.upper.UpperTriangular;
import waffles.utils.alg.lin.measure.tensor.Tensor;
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
	 * Returns an abstract {@code Banded} type.
	 * 
	 * @param m  a base matrix
	 * @param e  an error margin
	 * @return  a type operator
	 * 
	 * 
	 * @see Matrix
	 */
	public static Banded Type(Matrix m, double e)
	{
		int lBand = getLowerBand(m, e);
		int uBand = getUpperBand(m, e);
		
		return Type(lBand, uBand);
	}
	
	/**
	 * Returns an abstract {@code Banded} type.
	 * 
	 * @param lBand  a  lower band size
	 * @param uBand  an upper band size
	 * @return  a type operator
	 */
	public static Banded Type(int lBand, int uBand)
	{
		switch(lBand)
		{
		case 0:
		{
			switch(uBand)
			{
			case 0:
				return (Diagonal) () -> null;
			case 1:
				return (UpperBidiagonal) () -> null;
			default:
				return (UpperTriangular) () -> null;
			}
		}
		case 1:
		{
			switch(uBand)
			{
			case 0:
				return (LowerBidiagonal) () -> null;
			case 1:
				return (Tridiagonal) () -> null;
			default:
				return (UpperHessenberg) () -> null;
			}
		}
		default:
		{
			switch(uBand)
			{
			case 0:
				return (LowerTriangular) () -> null;
			case 1:
				return (LowerHessenberg) () -> null;
			default:
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
						return lBand;
					}
					
					@Override
					public int UpperBand()
					{
						return uBand;
					}
				};
			}
		}
		}
	}
	
	
	/**
	 * Computes the band size of a {@code Matrix} below the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param e  an error margin
	 * @return  a lower band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getLowerBand(Matrix m, double e)
	{
		return getLowerBand(m, e, 0);
	}
	
	/**
	 * Computes the band size of a {@code Matrix} below the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param e  an error margin
	 * @param min  a minimum band
	 * @return  a lower band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getLowerBand(Matrix m, double e, int min)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		int b = Integers.max(r1, c1) - 1;
		for(int r = r1 - 1; r > 0; r--)
		{
			int cMax = Integers.min(r1 - r, c1);
			for(int c = 0; c < cMax; c++)
			{
				float v = m.get(r + c, c);
				if(e < Floats.abs(v))
				{
					return b;
				}
			}

			if(--b <= min)
				break;
		}
		
		return b;
	}
	
	/**
	 * Computes the band size of a {@code Matrix} above the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param e  an error margin
	 * @param min  a minimum band
	 * @return  an upper band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getUpperBand(Matrix m, double e, int min)
	{
		int r1 = m.Rows();
		int c1 = m.Columns();
		
		int b = Integers.max(r1, c1) - 1;
		for(int c = c1 - 1; c > 0; c--)
		{
			int rMax = Integers.min(c1 - c, r1);
			for(int r = 0; r < rMax; r++)
			{
				float v = m.get(r, r + c);
				if(e < Floats.abs(v))
				{
					return b;
				}
			}

			if(--b <= min)
				break;
		}
		
		return b;
	}

	/**
	 * Computes the band size of a {@code Matrix} above the diagonal.
	 * 
	 * @param m  a base matrix
	 * @param e  an error margin
	 * @return  an upper band size
	 * 
	 * 
	 * @see Matrix
	 */
	public static int getUpperBand(Matrix m, double e)
	{
		return getUpperBand(m, e, 0);
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
		public Qualify(Banded b1, double err)
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
			
			
			double e = Error();
			Matrix m = Matrix();
			Banded b = Operator();

			return Banded.getLowerBand(m, e) <= b.LowerBand()
				&& Banded.getUpperBand(m, e) <= b.UpperBand();
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
	public default Operation<Boolean> Allows(double e)
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