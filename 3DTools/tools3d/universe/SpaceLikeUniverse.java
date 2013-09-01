/*
 * Created on Jan 3, 2006
 */
package tools3d.universe;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;

import tools3d.utils.Utils3D;

/**
 * @author Administrator
 * 
 */
public class SpaceLikeUniverse extends VisualPhysicalUniverse
{
	private BranchGroup environmentGroup = new BranchGroup();

	private AmbientLight ambLight;

	/**
	 * A universe with a locale created and the fog and skybox and some branches added
	 */
	public SpaceLikeUniverse()
	{
		environmentGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		environmentGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
		environmentGroup.setUserData("environment Group");

		// Create ambient light and add it
		Color3f alColor = new Color3f(1, 1, 1);
		ambLight = new AmbientLight(true, alColor);
		ambLight.setCapability(Light.ALLOW_INFLUENCING_BOUNDS_WRITE);
		ambLight.setCapability(Light.ALLOW_COLOR_WRITE);
		ambLight.setInfluencingBounds(Utils3D.defaultBounds);
		environmentGroup.addChild(ambLight);

		theLocale.addBranchGraph(environmentGroup);
	}

	public void addToEnvironmentBranch(BranchGroup newGroup)
	{
		newGroup.setCapability(BranchGroup.ALLOW_DETACH);
		environmentGroup.addChild(newGroup);
	}

	public void setAmbientLight(Color3f newColor)
	{
		ambLight.setColor(newColor);
	}
}
