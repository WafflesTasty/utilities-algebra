package waffles.utils.algebra.algorithms.optimize;

import waffles.utils.algebra.algorithms.Optimizer;
import waffles.utils.algebra.elements.linear.matrix.Matrices;
import waffles.utils.algebra.elements.linear.matrix.Matrix;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.lang.enums.Extreme;
import waffles.utils.tools.primitives.Array;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code LCLOptimizer} algorithm optimizes a linearly constrained linear problem.
 * A positive vector {@code x} is computed which min/maxes {@code c.x} s.t. {@code Ax = b}.
 * 
 * @author Waffles
 * @since 06 Jan 2021
 * @version 1.0
 * 
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Simplex_algorithm">Simplex Algorithm - Wikipedia</a>
 */
public class LCLOptimizer implements Optimizer<Vector>
{
	private Matrix con;
	private Vector obj;
	
	private Matrix mat;
	private int[] base;
	
	/**
	 * Creates a new {@code LCLOptimizer}.
	 * 
	 * @param con  a constraint matrix
	 * @param obj  an objective vector
	 * 
	 * 
	 * @see Matrix
	 * @see Vector
	 */
	public LCLOptimizer(Matrix con, Vector obj)
	{
		this.con = con;
		this.obj = obj;
	}

	@Override
	public Vector optimize(Extreme ex)
	{
		base = Phase1Base();
		mat = Phase1Tableau();

		// Construct the phase 1 simplex tableau.
		if(ex == Extreme.MAX)
		{
			for(int c = 0; c < mat.Columns(); c++)
			{
				float val = mat.get(con.Rows(), c);
				mat.set(-val, con.Rows(), c);
			}
		}

		for(int r = 0; r < con.Rows(); r++)
		{
			basify(r, base[r]);
		}


		float value = 0f;
		Vector y = optimize(1);
		// Solve phase 1 with simplex.
		for(int c = con.Columns() - 1; c < y.Size(); c++)
		{
			value += y.get(c);
		}

		// If phase 1 reached a non-zero minimum...
		if(value > 0f)
		{
			// No solution exists.
			return null;
		}

		
		// Remove artificial basis elements.
		for(int r = 0; r < base.length; r++)
		{
			if(con.Columns() - 1 <= base[r])
			{
				base[r] = findColumn(r);
				basify(r, base[r]);
			}
		}
		
		// Solve phase 2 with simplex.
		mat = Phase2Tableau();
		return optimize(2);
	}
	
	@Override
	public boolean isFeasible()
	{
		base = Phase1Base();
		mat = Phase1Tableau();

		// Construct the phase 1 simplex tableau.
		for(int r = 0; r < con.Rows(); r++)
		{
			basify(r, base[r]);
		}


		float value = 0f;
		Vector y = optimize(1);
		// Solve phase 1 with simplex.
		for(int c = con.Columns() - 1; c < y.Size(); c++)
		{
			value += y.get(c);
		}

		return value <= 0f;
	}


	private boolean isOptimal()
	{
		for(int c = 0; c < mat.Columns() - 1; c++)
		{
			if(!Array.contents.has(base, c))
			{
				if(mat.get(mat.Rows() - 1, c) < 0)
				{
					return false;
				}
			}
		}
		
		return true;
	}

	private Vector optimize(int phase)
	{
		while(!isOptimal())
		{
			int col = findColumn();
			int row = findRow(col);

			basify(row, col);
			base[row] = col;
		}
		
		Vector x = Vectors.create(mat.Columns()-1);
		for(int r = 0; r < base.length; r++)
		{
			float val = mat.get(r, mat.Columns()-1);
			x.set(val, base[r]);
		}
		
		return x;
	}
		
	private void basify(int row, int col)
	{
		float pivot = mat.get(row, col);
		
		for(int r = 0; r < mat.Rows(); r++)
		{
			float mult = mat.get(r, col);
			for(int c = 0; c < mat.Columns(); c++)
			{
				float val = 0f;
				if(r != row)
				{
					val += mat.get(row, c) * mult;
				}
				
				val = mat.get(r, c) - val / pivot;
				mat.set(val, r, c);
			}
		}
		
		for(int c = 0; c < mat.Columns(); c++)
		{
			float val = mat.get(row, c) / pivot;
			mat.set(val, row, c);
		}
	}
				
	private int findColumn(int row)
	{
		int col = -1;
		
		float max = Floats.MIN_VALUE;
		for(int c = 0; c < con.Columns() - 1; c++)
		{
			float dist = Floats.abs(mat.get(row, c));
			if(max < dist)
			{
				max = dist;
				col = c;
			}
		}
		
		return col;
	}
	
	private int findRow(int col)
	{
		int row = -1;
		
		float min = Floats.MAX_VALUE;
		for(int r = 0; r < con.Rows(); r++)
		{
			if(mat.get(r, col) < 0f) continue;
			float ratio = mat.get(r, mat.Columns() - 1) / mat.get(r, col);
			if(ratio < min)
			{
				min = ratio;
				row = r;
			}
		}
		
		return row;
	}
	
	private int findColumn()
	{
		int col = -1;

		float min = Floats.MAX_VALUE;
		for(int c = 0; c < mat.Columns() - 1; c++)
		{
			if(mat.get(mat.Rows() - 1, c) < min)
			{
				min = mat.get(mat.Rows() - 1, c);
				col = c;
			}
		}
		
		return col;
	}
	
		
	private Matrix Phase1Tableau()
	{
		int rows = con.Rows() + 2;
		int cols = con.Rows() + con.Columns();		
		// Construct the phase 1 simplex tableau.
		Matrix tab = Matrices.create(rows, cols);
		
		// Add the constraint matrix to the tableau.
		for(int r = 0; r < con.Rows(); r++)
		{
			tab.set(1f, r, r + con.Columns() - 1);
			tab.set(con.get(r, con.Columns() - 1), r, cols-1);
			for(int c = 0; c < con.Columns() - 1; c++)
			{
				tab.set(con.get(r, c), r, c);
			}
		}
		
		// Add the objective functions in the bottom rows.
		for(int c = 0; c < cols - 1; c++)
		{
			if(c < con.Columns() - 1)
				tab.set(obj.get(c), rows - 2, c);
			else if(c < cols - 1)
				tab.set(1f, rows - 1, c);
		}
		
		return tab;
	}
	
	private Matrix Phase2Tableau()
	{
		int rows = con.Rows() + 1;
		int cols = con.Columns();
		
		// Construct the phase 2 simplex tableau.
		Matrix tab = Matrices.create(rows, cols);
		
		for(int r = 0; r < rows; r++)
		{
			float rhs = mat.get(r, mat.Columns() - 1);
			tab.set(rhs, r, cols - 1);
			
			for(int c = 0; c < cols - 1; c++)
			{
				float val = mat.get(r, c);
				tab.set(val, r, c);
			}
		}
		
		return tab;
	}
		
	private int[] Phase1Base()
	{
		int[] cols = new int[con.Rows()];
		for(int r = 0; r < con.Rows(); r++)
		{
			cols[r] = r + con.Columns() - 1;
		}
		
		return cols;
	}
}