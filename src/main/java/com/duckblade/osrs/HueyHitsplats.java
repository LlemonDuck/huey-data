package com.duckblade.osrs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GraphicChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.NpcChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.slf4j.LoggerFactory;

@Slf4j
@Singleton
@PluginDescriptor(
	name = "Huey Hitsplats"
)
public class HueyHitsplats extends Plugin
{

	private static final File HUEY_DIR = new File(RuneLite.RUNELITE_DIR, "huey");

	@Inject
	private Client client;

	private PrintWriter writer;

	@Override
	protected void startUp() throws Exception
	{
		HUEY_DIR.mkdirs();
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied e) throws Exception
	{
		log.debug("hitsplat {} {} {}",
			client.getTickCount(),
			e.getHitsplat().getAmount(),
			client.getLocalPlayer().hasSpotAnim(2792));
		if ((!(e.getActor() instanceof NPC)) || ((NPC) e.getActor()).getId() != NpcID.HUEYCOATL_TAIL)
		{
			return;
		}

		if (writer == null)
		{
			writer = new PrintWriter(new FileWriter(new File(HUEY_DIR, client.getGameCycle() + ".csv")));
		}

		ItemContainer equipped = client.getItemContainer(InventoryID.EQUIPMENT);
		Item weapon = equipped != null ? equipped.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx()) : null;

		writer.print(client.getTickCount());
		writer.print(',');
		writer.print(e.getHitsplat().getAmount());
		writer.print(',');
		writer.print(client.getBoostedSkillLevel(Skill.ATTACK));
		writer.print(',');
		writer.print(client.getBoostedSkillLevel(Skill.STRENGTH));
		writer.print(',');
		writer.println(client.getLocalPlayer().hasSpotAnim(2792));
		writer.print(weapon == null ? -1 : weapon.getId());
		writer.print(',');
	}

	@Subscribe
	public void onNpcChanged(NpcChanged e)
	{
		if (e.getNpc().getId() == NpcID.THE_HUEYCOATL_14012)
		{
			writer.close();
			writer = null;
		}
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged e)
	{
		if ((e.getActor() == client.getLocalPlayer()))
		{
//			log.debug("player animation {}", e.getActor().getAnimation());
		}
	}

	@Subscribe
	public void onGraphicChanged(GraphicChanged e)
	{
		if ((e.getActor() == client.getLocalPlayer()))
		{
//			log.debug("spotanims");
//			e.getActor().getSpotAnims().forEach(asa -> log.debug("    {}", asa.getId()));
		}
	}

}
