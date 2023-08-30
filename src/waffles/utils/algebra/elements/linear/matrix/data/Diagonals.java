package waffles.utils.algebra.elements.linear.matrix.data;

import java.util.Iterator;

import waffles.utils.algebra.elements.linear.tensor.data.TensorData;
import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.primitives.Array;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Diagonals} defines matrix data through an array of diagonal vectors.
 *
 * @author Waffles
 * @since 24 Aug 2023
 * @version 1.0
 * 
 * 
 * @see TensorData
 */
public class Diagonals implements TensorData
{
	private int rows, cols;
	private Vector[] diags;
	
	/**
	 * Creates a new {@code Diagonals}.
	 * 
	 * @param rows  a matrix row count
	 * @param cols  a matrix column count
	 */
	public Diagonals(int rows, int cols)
	{
		this(new Vector[rows + cols - 1], rows, cols);
	}

	
	Diagonals(Vector[] diags, int rows, int cols)
	{
		this.diags = diags;
		this.rows = rows;
		this.cols = cols;
	}

	static int Coord(int r, int c)
	{
		if(r > c)
			return c;
		return r;
	}
	
	static int Diagonal(int r, int c)
	{
		if(r > c)
			return 1 - 2 * (c-r+1);
		return 2 * (c-r);
	}
	
	int Size(int r, int c)
	{
		if(r > c)
			return Coord(rows-1, c-r + rows-1) + 1;
		return Coord(r-c + cols-1, cols-1) + 1;
	}


	@Override
	public Float remove(int... coord)
	{
		int r = coord[0], c = coord[1];
		int diag = Diagonal(r, c);
		int crd  = Coord(r, c);

		
		float prev = 0f;
		Vector v = diags[diag];
		if(v != null)
		{
			prev = v.get(crd);
			v.set(0f, crd);
			
			if(Floats.isZero(v.normSqr(), v.Size()))
			{
				diags[diag] = null;
			}
		}
		
		return prev;
	}

	@Override
	public Float put(Float val, int... coord)
	{
		int r = coord[0], c = coord[1];
		int diag = Diagonal(r, c);
		int crd  = Coord(r, c);
		
		
		Vector v = diags[diag];
		if(v == null)
		{
			v = Vectors.create(Size(r, c));
		}

		
		float prev = v.get(crd);
		v.set(val, crd);
		return prev;
	}

	@Override
	public Float get(int... coord)
	{
		int r = coord[0], c = coord[1];
		int diag = Diagonal(r, c);
		int crd  = Coord(r, c);
		
		
		Vector v = diags[diag];
		if(v != null)
		{
			return v.get(crd);
		}
		
		return 0f;
	}

	@Override
	public void clear()
	{
		diags = new Vector[rows + cols - 1];
	}
	
	
	class NZValues implements Iterator<int[]>
	{
		private int diag, crd;
		
		public NZValues()
		{
			while(diags[diag] == null)
			{
				diag++;
				if(diag == diags.length)
				{
					break;
				}
			}
		}

		
		@Override
		public boolean hasNext()
		{
			return diag < diags.length;
		}

		@Override
		public int[] next()
		{
			int r, c;
			if(diag % 2 == 1)
			{
				r = 1 + crd + (diag-1) / 2;
				c = crd;
			}
			else
			{
				r = crd;
				c = crd + diag / 2;
			}
			
			
			crd++;
			if(crd == Size(r, c))
			{
				crd = 0; diag++;
				while(diags[diag] == null)
				{
					diag++;
					if(diag == diags.length)
					{
						break;
					}
				}
			}
			
			return new int[]{r, c};
		}
		
	}
	
	@Override
	public Iterable<int[]> NZIndex()
	{
		return () -> new NZValues();
	}

	@Override
	public Diagonals instance()
	{
		return new Diagonals(rows, cols);
	}
	
	@Override
	public Diagonals copy()
	{
		Vector[] copy = Array.copy.of(diags);
		return new Diagonals(copy, rows, cols);
	}

	@Override
	public int NZCount()
	{
		int count = 0;
		for(int i = 0; i < diags.length; i++)
		{
			Vector v = diags[i];
			if(v != null)
			{
				count += v.Size();
			}
		}
		
		return count;
	}
}