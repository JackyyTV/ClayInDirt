package com.latmod.claytodust.item;

import com.feed_the_beast.ftbl.lib.block.ItemBlockBase;
import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.latmod.claytodust.block.BlockFirePit;
import com.latmod.claytodust.tile.TileFirePit;
import com.latmod.yabba.ClayToDust;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LatvianModder
 */
@GameRegistry.ObjectHolder(ClayToDust.MOD_ID)
@Mod.EventBusSubscriber(modid = ClayToDust.MOD_ID)
public class ClayToDustItems
{
	public static final Block FIRE_PIT = Blocks.AIR;

	public static final Item BASIC_FIRESTARTER = Items.AIR;
	public static final Item STRING_FIRESTARTER = Items.AIR;
	public static final Item MAGNIFYING_GLASS = Items.AIR;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new BlockFirePit("fire_pit")
		);

		GameRegistry.registerTileEntity(TileFirePit.class, ClayToDust.MOD_ID + ":fire_pit");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlockBase(FIRE_PIT),
				new ItemFirestarter("basic_firestarter").setMaxDamage(8),
				new ItemFirestarter("string_firestarter").setMaxDamage(32),
				new ItemMagnifyingGlass("magnifying_glass")
		);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event)
	{
		ClientUtils.registerModel(FIRE_PIT);

		ClientUtils.registerModel(BASIC_FIRESTARTER);
		ClientUtils.registerModel(STRING_FIRESTARTER);
		ClientUtils.registerModel(MAGNIFYING_GLASS);
	}
}