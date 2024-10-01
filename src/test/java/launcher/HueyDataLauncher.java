package launcher;

import com.duckblade.osrs.HueyHitsplats;
import com.duckblade.osrs.toa.TombsOfAmascutPlugin;
import launcher.debugplugins.ToaDebugPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class HueyDataLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(HueyHitsplats.class);
		RuneLite.main(args);
	}
}