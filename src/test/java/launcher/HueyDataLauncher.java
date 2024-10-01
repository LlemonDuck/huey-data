package launcher;

import com.duckblade.osrs.HueyHitsplats;
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