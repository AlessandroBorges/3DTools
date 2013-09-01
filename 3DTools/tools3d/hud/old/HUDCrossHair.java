package tools3d.hud.old;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import tools.image.SimpleImageLoader;
import tools3d.hud.Canvas3D2D;
import tools3d.hud.HUDElement;

public class HUDCrossHair
{
	private HUDElement crossHairElement;

	private int width = 0;

	private int height = 0;

	private ComponentAdapter resizer = new ComponentAdapter()
	{
		public void componentResized(ComponentEvent e)
		{
			setLocation(e.getComponent());
		}
	};

	public HUDCrossHair(Canvas3D2D canvas, String imageName)
	{
		this(imageName);
		addToCanvas(canvas);
	}

	public HUDCrossHair(String imageName)
	{
		Image im = SimpleImageLoader.getImage(imageName);
		width = im.getWidth(null);
		height = im.getHeight(null);

		crossHairElement = new HUDElement(width, height);
		// setLocation(canvas);
		crossHairElement.getGraphics().setColor(new Color(0.5f, 1f, 1f, 0.2f));
		// crossHairElement.getGraphics().fillRoundRect(0, 0, 50, 50, 15, 15);
		crossHairElement.getGraphics().drawImage(im, 0, 0, null);
	}

	public void addToCanvas(Canvas3D2D canvas)
	{
		canvas.addElement(crossHairElement);
		setLocation(canvas);
		canvas.addComponentListener(resizer);
	}

	public void removeFromCanvas(Canvas3D2D canvas)
	{
		canvas.removeElement(crossHairElement);
		canvas.removeComponentListener(resizer);
	}

	private void setLocation(Component comp)
	{
		// TODO: note this does not appear to set it in the middle properly, it looks like someone is scaling my image?
		crossHairElement.setLocation((comp.getWidth() / 2) - (width / 2), (comp.getHeight() / 2) - (height / 2));
	}
}
