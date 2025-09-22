package waffles.utils.alg.lin.measure.matrix.types.orthogonal;

import waffles.utils.alg.lin.measure.matrix.Matrix;
import waffles.utils.alg.lin.measure.matrix.MatrixOps;
import waffles.utils.alg.lin.measure.matrix.types.shaped.square.Involutory;
import waffles.utils.alg.lin.measure.matrix.types.shaped.square.Symmetric;
import waffles.utils.alg.lin.measure.tensor.Tensor;
import waffles.utils.alg.lin.measure.vector.Vector;
import waffles.utils.tools.patterns.operator.Operation;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Reflection} operator is for matrices which represent a reflection.
 * These are orthogonal matrices with one eigenvalue equal to -1 and
 * all remaining ones equal to +1.
 * 
 * @author Waffles
 * @since Jul 15, 2018
 * @version 1.0
 * 
 *
 * @see Involutory
 * @see Orthogonal
 * @see Symmetric
 */
public interface Reflection extends Involutory, Orthogonal, Symmetric
{
	/**
	 * Returns the abstract {@code Reflection} type.
	 * 
	 * @return  a type operator
	 */
	public static Reflection Type()
	{
		return () -> null;
	}
	
	/**
	 * Computes a reflection normal from a {@code Matrix}.
	 * 
	 * @param m  a reflection matrix
	 * @return   a reflection normal
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public static Vector normal(Matrix m)
	{
		int rows = m.Rows();
		
		Vector n = new Vector(rows);
		for(int r = 0; r < rows; r++)
		{
			float v = 1f - m.get(r, r);
			n.set(Floats.sqrt(v / 2), r);
		}
		
		return n;
	}
	
	/**
	 * A {@code Qualify} operation checks if a matrix is a {@code Reflection}.
	 *
	 * @author Waffles
	 * @since 22 Aug 2025
	 * @version 1.1
	 *
	 * 
	 * @see Symmetric
	 */
	public static class Qualify extends Symmetric.Qualify
	{
		private static MatrixOps ID = Identity.Type();
		
		
		private Operation<Boolean> id;
		
		/**
		 * Creates a new {@code Qualify}.
		 * 
		 * @param o1   a matrix operator
		 * @param err  an error margin
		 * 
		 * 
		 * @see Reflection
		 */
		public Qualify(Reflection o1, double err)
		{
			super(o1, err);
			
			Matrix m1 = o1.Operable();
			MatrixOps o2 = ID.instance(m1);
			
			id = o2.Allows(err);
		}

		
		@Override
		public Boolean result()
		{
			if(super.result())
			{
				double e = Error();
				Vector n = normal(Matrix());
				Matrix m2 = n.times(n.transpose());
				m2 = m2.times(2f).plus(Matrix());
				MatrixOps o2 = ID.instance(m2);
				
				return o2.Allows(e).result();
			}
			
			return false;
		}
		
		@Override
		public int cost()
		{
			int r1 = Matrix().Rows();
			int c1 = Matrix().Columns();
			
			// Cost from the parent operation...
			return super.cost()
			// ...plus the cost from the id matrix...
				+ 3 * r1 * (c1 + 1)
			// ...plus the cost from the id check.
				+ id.cost();
		}
	}
	
	
	@Override
	public default Operation<Matrix> Inverse()
	{
		return Orthogonal.super.Inverse();
	}
	
	@Override
	public default Operation<Boolean> Allows(double e)
	{
		return new Qualify(this, e);
	}
	
	@Override
	public default Reflection instance(Tensor t)
	{
		return () -> (Matrix) t;
	}
	
	@Override
	public default boolean matches(Tensor t)
	{
		return t.Operator() instanceof Reflection;
	}
}