package zeno.util.algebra.curves;

import zeno.util.algebra.curves.curves.LinearCurve;
import zeno.util.tools.actions.TimedAction;

/**
 * The {@code Interpolation} class defines an action that gradually interpolates a value.
 * Use the {@link #getValue()} method to get the current value.
 *
 * @since Apr 7, 2016
 * @author Zeno
 * 
 * @see TimedAction
 */
public class Interpolation extends TimedAction
{		
	private Curve curve;
	private float step, maxstep;
	
	/**
	 * Creates a new {@code Interpolation}.
	 * 
	 * @param ival  an update interval
	 */
	public Interpolation(long ival)
	{
		super(ival); curve = new LinearCurve();
	}
	
	/**
	 * Creates a new {@code Interpolation}.
	 * <br> The default interval is 16ms.
	 */
	public Interpolation()
	{
		super(); curve = new LinearCurve();
	}


	/**
	 * Changes the {@code Interpolation} curve.
	 * 
	 * @param curve  a new curve
	 * @see Curve
	 */
	public void setCurve(Curve curve)
	{
		this.curve = curve;
	}
	
	/**
	 * Changes the maximum steps of the action.
	 * 
	 * @param maxsteps  the maximum steps
	 */
	public void setSteps(int maxsteps)
	{
		maxstep = maxsteps;
		step = maxsteps;
	}
	
	/**
	 * Starts the {@code Interpolation}.
	 */
	public void startAction()
	{
		step = 0;
	}
	
	
	/**
	 * Indicates whether the interpolation is finished.
	 * 
	 * @return  {@code true} if the action is finished
	 */
	public boolean isFinished()
	{
		return step == maxstep;
	}
	
	/**
	 * Returns the current interpolation gradient.
	 * 
	 * @return  the current gradient
	 */
	public float getValue()
	{
		return curve.getValue(step / maxstep);
	}
	
	
	@Override
	public void onTick()
	{
		if(step < maxstep)
		{
			step++;
		}
	}
}