package tools3d.camera;

import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.swing.JPanel;

import tools3d.mixed3d2d.Canvas3D2D;
import tools3d.mixed3d2d.overlay.swing.Panel3D;
import tools3d.universe.VisualPhysicalUniverse;

import com.sun.j3d.utils.universe.ViewingPlatform;

public class CameraPanel extends JPanel
{
	private VisualPhysicalUniverse universe;

	private Canvas3D2D canvas3D2D;

	private Camera camera;

	private Dolly currentDolly;

	private boolean isRendering = false;

	private Panel3D overlay;

	public CameraPanel(VisualPhysicalUniverse universe)
	{
		this.universe = universe;

		setLayout(new GridLayout(1, 1));

		// I must do this in order to enable the stencil buffer
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration[] gc = gd.getConfigurations();
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		//stencil setup stuff
		//template.setStencilSize(8);		
		// we must also set the stencil buffer to clear each frame (madness!)
		// put  -Dj3d.stencilClear=true in your vm arguments!!!  

		// antialiasing REQUIRED is good to have
		template.setSceneAntialiasing(GraphicsConfigTemplate.REQUIRED);

		GraphicsConfiguration config = template.getBestConfiguration(gc);

		canvas3D2D = new Canvas3D2D(config);	

		camera = new Camera(canvas3D2D);

		overlay = new Panel3D();//full screen
		overlay.setLocation(0, 0);
		overlay.setConfig(canvas3D2D);

	}

	public Panel3D getOverlay()
	{
		return overlay;
	}

	public void setPhysicalsVisible(boolean visible)
	{
		if (visible)
		{
			universe.setViewForPhysicalBranch(canvas3D2D.getView());
		}
		else
		{
			universe.clearViewForPhysicalBranch();
		}
	}

	public void setFreeDolly(Dolly newDolly)
	{
		if (currentDolly != null)
		{
			universe.removeViewingPlatform(currentDolly);
		}
		currentDolly = newDolly;
		// it is assumed to be added to the scene graph itself
		// universe.addViewingPlatform(currentDolly);
		camera.setAvatar(null);
		camera.setViewingPlatform(currentDolly);

		// camera.setAvatar(new SimpleAvatar("media/models/eyeAvatar.ac"));

	}

	public void setDolly(Dolly newDolly)
	{
		if (currentDolly != null)
		{
			universe.removeViewingPlatform(currentDolly);
		}
		currentDolly = newDolly;
		universe.addViewingPlatform(currentDolly);
		camera.setAvatar(null);
		camera.setViewingPlatform(currentDolly);
		//camera.setAvatar(new SimpleAvatar("media/models/eyeAvatar.ac"));
	}

	/** 
	 * NOTE instead of Dolly just a vahnilla vp
	 * @param viewingPlatform
	 */
	public void setViewingPlatform(ViewingPlatform viewingPlatform)
	{
		if (currentDolly != null)
		{
			universe.removeViewingPlatform(currentDolly);
		}
		currentDolly = null;
		universe.addViewingPlatform(viewingPlatform);
		camera.setAvatar(null);
		camera.setViewingPlatform(viewingPlatform);
	}

	public void clearDolly()
	{
		camera.setViewingPlatform(null);
		universe.removeViewingPlatform(currentDolly);
	}

	public void stopRendering()
	{
		System.out.println("NEVER CALL THIS METHOD DAMN IT! it is a major memory leak; find a solution!");
		new Exception("called by").printStackTrace();

		// maybe try Canvas3D.stopRenderer()
		// canvas3D2D.stopRenderer();

		// stop rendering by removing the canvas
		if (this.isAncestorOf(canvas3D2D))
		{
			// remove(headCamCanvas3D);
			isRendering = false;
		}
	}

	public void startRendering()
	{
		if (!isRendering)
		{
			// start rendering by adding the canvas
			add(canvas3D2D);
			isRendering = true;
			validate();
		}
	}

	public boolean isRendering()
	{
		return isRendering;
	}

	public Canvas3D2D getCanvas3D2D()
	{
		return canvas3D2D;
	}

	public Dolly getCurrentDolly()
	{
		return currentDolly;
	}

}
