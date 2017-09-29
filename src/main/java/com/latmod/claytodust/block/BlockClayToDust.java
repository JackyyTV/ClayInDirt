package com.latmod.claytodust.block;

import com.feed_the_beast.ftbl.lib.block.BlockBase;
import com.latmod.yabba.ClayToDust;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * @author LatvianModder
 */
public class BlockClayToDust extends BlockBase
{
	public BlockClayToDust(String id, Material material, MapColor color)
	{
		super(ClayToDust.MOD_ID, id, material, color);
		setCreativeTab(ClayToDust.TAB);
	}
}