package net.dark_roleplay.claytodust;

import net.dark_roleplay.claytodust.common.CTDCommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = CTDInfo.MODID, name = CTDInfo.NAME, useMetadata = true, acceptedMinecraftVersions = "[1.12,)")
public class ClayToDust{

	@Mod.Instance(CTDInfo.MODID)
	public static ClayToDust instance;

//	@SidedProxy(serverSide = "com.latmod.claytodust.ClayToDustCommon", clientSide = "com.latmod.claytodust.client.ClayToDustClient")
//	public static CTDCommonProxy PROXY;
}