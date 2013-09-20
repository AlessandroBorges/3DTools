package tools3d.navigation.rules.deprecated;

import javax.vecmath.Vector3f;

/**
 * @Deprecated use jnifbullet charactercontroller
 * @author philip
 */
@Deprecated 
public class NavigationPositionRuleNoTrans implements NavigationPositionRule
{

	private boolean active = false;

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public NavigationPositionRuleNoTrans()
	{
	}

	@Override
	public Vector3f applyRule(Vector3f desiredTranslation, Vector3f currentLocation)
	{
		desiredTranslation.set(0, 0, 0);
		return desiredTranslation;
	}
}