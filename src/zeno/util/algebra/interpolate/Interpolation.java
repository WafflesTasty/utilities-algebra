package zeno.util.algebra.interpolate;

import zeno.util.tools.actions.TimedAction;

/**
 * The {@code Interpolation} class defines an action that gradually interpolates a value.
 * Use the {@link #getValue()} method to get the current value.
 *
 * @author Zeno
 * @since Apr 7, 2016
 * @see TimedAction
 */
public class Interpolation extends TimedAction
{		
	private Curve curve;
	private int step, maxstep;
	
	/**
	 * Creates a new {@code GradientAction}.
	 * 
	 * @param ival  an update interval
	 */
	public Interpolation(long ival)
	{
		super(ival);
		curve = (val, min, max) ->
		{
			return Curve.linear(val, min, max);
		};
	}
	
	/**
	 * Creates a new {@code GradientAction}.
	 * <br> The default interval is 16ms.
	 */
	public Interpolation()
	{
		super();
		curve = (val, min, max) ->
		{
			return Curve.linear(val, min, max);
		};
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
	 * Starts the {@code GradientAction}.
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
		return curve.valueAt(step, 0, maxstep);
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