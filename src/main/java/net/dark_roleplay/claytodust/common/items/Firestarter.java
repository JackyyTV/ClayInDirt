package net.dark_roleplay.claytodust.common.items;

import java.util.Random;

import net.dark_roleplay.claytodust.handler.CTDCreativeTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Firestarter extends Item{
	
	private float propability;
	
	public Firestarter(String registryName, int durability, float propability){
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setMaxDamage(durability);
		this.setCreativeTab(CTDCreativeTabs.tabCTD);
		this.setMaxStackSize(1);
		this.propability = propability;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote){
			return EnumActionResult.SUCCESS;
		}
		pos = pos.offset(facing);
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.canPlayerEdit(pos, facing, itemstack)){
			return EnumActionResult.FAIL;
		}else{
			if((new Random()).nextFloat() < this.propability){
				if (world.isAirBlock(pos)){
					world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
				}
	
				if (player instanceof EntityPlayerMP){
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
				}
			}

			itemstack.damageItem(1, player);
			return EnumActionResult.SUCCESS;

		}
	}
}