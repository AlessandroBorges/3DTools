package tools3d.navigation;

import java.util.ArrayList;
import java.util.Iterator;

import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.WakeupCondition;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;

import tools3d.utils.Utils3D;

/**
 * this class "runs" the navigation processors if active, it is a behaviour
 * 
 * @author Administrator
 * 
 */
public class NavigationTemporalBehaviour extends Behavior
{
	private ArrayList<NavigationProcessorInterface> navigationProcessors = new ArrayList<NavigationProcessorInterface>();

	// Calculations for frame duration timing,
	// used between successive calls to process
	private long previousFrameEndTime;

	private WakeupCondition FPSCriterion = new WakeupOnElapsedFrames(0, false);

	public NavigationTemporalBehaviour()
	{
		setSchedulingBounds(Utils3D.defaultBounds);
		setEnable(true);
	}

	@Override
	public void initialize()
	{
		wakeupOn(FPSCriterion);
	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> criteria)
	{
		process();
		wakeupOn(FPSCriterion);
	}

	private void process()
	{
		long timeNow = System.nanoTime();
		long frameDuration = (timeNow - previousFrameEndTime) / 1000000;
		//	System.out.println("timeNow " +timeNow);
		//	System.out.println("previousFrameEndTime " +previousFrameEndTime);
		//	System.out.println("frameDuration " +frameDuration);

		for (NavigationProcessorInterface np : navigationProcessors)
		{
			if (np.isActive())
			{
				long start = System.currentTimeMillis();
				np.process(frameDuration);
				if (System.currentTimeMillis() - start > 50)
					System.out.println("NavigationProcessorInterface " + np + " took " + (System.currentTimeMillis() - start));
			}

		}
		// record when we last thought about movement
		previousFrameEndTime = timeNow;
	}

	public void addNavigationProcessor(NavigationProcessorInterface navigationProcessor)
	{
		if (!navigationProcessors.contains(navigationProcessor))
			navigationProcessors.add(navigationProcessor);
	}

	public void removeNavigationProcessor(NavigationProcessorInterface navigationProcessor)
	{
		navigationProcessors.remove(navigationProcessor);
	}

}