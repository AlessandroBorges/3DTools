package tools3d.utils.resolution;

import java.awt.DisplayMode;

import javax.swing.JOptionPane;

public class GraphicsSettings
{
	private DisplayMode originalDisplayMode = null;

	private DisplayMode desiredDisplayMode = null;

	private boolean runFullscreen = false;

	private boolean aaRequired = false;

	private boolean cancelled = false;

	public GraphicsSettings()
	{

	}

	/**
	 * Report whether or not the fullscreen option was checked.
	 * @return a boolean, true if the app should run in fullscreen
	 */
	public boolean isRunFullscreen()
	{
		return runFullscreen;
	}

	public void setRunFullscreen(boolean runFullscreen)
	{
		//quick check for -Dsun.java2d.noddraw=true being set
		String nodd = System.getProperty("sun.java2d.noddraw");
		if (runFullscreen && (nodd == null || !nodd.equals("true")))
		{
			JOptionPane.showMessageDialog(null, "Full screen without JVM VM argument -Dsun.java2d.noddraw=true is bad.");
		}

		this.runFullscreen = runFullscreen;
	}

	public void setOriginalDisplayMode(DisplayMode originalDisplayMode)
	{
		this.originalDisplayMode = originalDisplayMode;
	}

	/**
	 * Retrieve the original display mode the desktop was in before the application started
	 * @return a DisplayMode object
	 */
	public DisplayMode getOriginalDisplayMode()
	{
		return originalDisplayMode;
	}

	public DisplayMode getDesiredDisplayMode()
	{
		return desiredDisplayMode;
	}

	public void setDesiredDisplayMode(DisplayMode desiredDisplayMode)
	{
		this.desiredDisplayMode = desiredDisplayMode;
	}

	public boolean isAaRequired()
	{
		return aaRequired;
	}

	public void setAaRequired(boolean aaRequired)
	{
		this.aaRequired = aaRequired;
	}

	public boolean isCancelled()
	{
		return cancelled;
	}

	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}

	public String toPrefString()
	{
		String prefStr = originalDisplayMode.getWidth() + ":" + //
				originalDisplayMode.getHeight() + ":" + //
				originalDisplayMode.getBitDepth() + ":" + //
				originalDisplayMode.getRefreshRate() + ":" + //				
				desiredDisplayMode.getWidth() + ":" + //
				desiredDisplayMode.getHeight() + ":" + //
				desiredDisplayMode.getBitDepth() + ":" + //
				desiredDisplayMode.getRefreshRate() + ":" + //				
				runFullscreen + ":" + //
				aaRequired + ":" + //
				cancelled;

		return prefStr;
	}

	public void fromPrefString(String prefStr)
	{
		String[] prefs = prefStr.split(":");
		originalDisplayMode = new DisplayMode(Integer.parseInt(prefs[0]), Integer.parseInt(prefs[1]), Integer.parseInt(prefs[2]),
				Integer.parseInt(prefs[3]));
		desiredDisplayMode = new DisplayMode(Integer.parseInt(prefs[4]), Integer.parseInt(prefs[5]), Integer.parseInt(prefs[6]),
				Integer.parseInt(prefs[7]));
		runFullscreen = Boolean.parseBoolean(prefs[8]);
		aaRequired = Boolean.parseBoolean(prefs[9]);
		cancelled = Boolean.parseBoolean(prefs[10]);
	}

}
