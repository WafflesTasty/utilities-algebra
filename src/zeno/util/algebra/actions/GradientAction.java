package zeno.util.algebra.actions;

import zeno.util.algebra.interfaces.Interpolation;
import zeno.util.tools.actions.TimedAction;

/**
 * The {@code GradientAction} class defines an action that gradually interpolates a value.
 *
 * @author Zeno
 * @since Apr 7, 2016
 * @see TimedAction
 */
public class GradientAction extends TimedAction
{		
	private int step, maxstep;
	private Interpolation curve;
	
	/**
	 * Creates a new {@code GradientAction}.
	 * 
	 * @param ival  an update interval
	 */
	public GradientAction(long ival)
	{
		super(ival);
		curve = (val, min, max) ->
		{
			return Interpolation.linear(val, min, max);
		};
	}
	
	/**
	 * Creates a new {@code GradientAction}.
	 * <br> The default interval is 16ms.
	 */
	public GradientAction()
	{
		super();
		curve = (val, min, max) ->
		{
			return Interpolation.linear(val, min, max);
		};
	}


	/**
	 * Changes the {@code Interpolation} curve.
	 * 
	 * @param curve  a new curve
	 * @see Interpolation
	 */
	public void setCurve(Interpolation curve)
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
	public float getGradient()
	{
		return curve.interpolate(step, 0, maxstep);
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