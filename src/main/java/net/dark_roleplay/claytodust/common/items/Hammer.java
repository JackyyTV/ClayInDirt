package net.dark_roleplay.claytodust.common.items;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import net.dark_roleplay.claytodust.handler.CTDCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;

public class Hammer extends ItemTool{
	
	public Hammer(String registryName, ToolMaterial materialIn, int durability) {
		super(1.0F, -2.8F, materialIn, ImmutableSet.of());
		this.setMaxDamage(durability);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(CTDCreativeTabs.tabCTD);
	}

	@Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("hammer");
    }
}
