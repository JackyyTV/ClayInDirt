package com.latmod.yabba;

import com.latmod.claytodust.ClayToDustCommon;
import com.latmod.claytodust.item.ClayToDustItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author LatvianModder
 */
@Mod(modid = ClayToDust.MOD_ID, name = ClayToDust.MOD_NAME, useMetadata = true, acceptedMinecraftVersions = "[1.12,)", dependencies = "required-after:ftbl")
public class ClayToDust
{
	public static final String MOD_ID = "claytodust";
	public static final String MOD_NAME = "Clay to Dust";

	@Mod.Instance(ClayToDust.MOD_ID)
	public static ClayToDust INST;

	@SidedProxy(serverSide = "com.latmod.claytodust.ClayToDustCommon", clientSide = "com.latmod.claytodust.client.ClayToDustClient")
	public static ClayToDustCommon PROXY;

	public static final CreativeTabs TAB = new CreativeTabs(MOD_ID)
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ClayToDustItems.BASIC_FIRESTARTER);
		}
	};

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		PROXY.preInit();
	}
}