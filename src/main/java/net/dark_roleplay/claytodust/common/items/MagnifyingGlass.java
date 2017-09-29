package net.dark_roleplay.claytodust.common.items;

import net.dark_roleplay.claytodust.handler.CTDCreativeTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
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

public class MagnifyingGlass extends Item{
	
	public MagnifyingGlass(String registryName){
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(CTDCreativeTabs.tabCTD);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		 if (world.isRemote){
			return EnumActionResult.SUCCESS;
		}

		if (player.isSneaking()){
			IBlockState state = world.getBlockState(pos);
			if (state == Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)){
				if (world.rand.nextInt(6) == 0){
					world.setBlockState(pos, Blocks.CLAY.getDefaultState());
				}else{
					world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
				}
			}else if (state.getBlock() == Blocks.GRASS){
				if (world.rand.nextInt(12) == 0){
					world.setBlockState(pos, Blocks.CLAY.getDefaultState());
				}else{
					world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
				}
			}
		}else if(world.getBlockState(pos).getBlock() == Blocks.HAY_BLOCK && (world.isDaytime() && !world.isRaining() || world.canSeeSky(pos.offset(EnumFacing.UP)))){
			ItemStack stack = player.getHeldItem(hand);

			pos = pos.offset(facing);

			if (world.isAirBlock(pos)){
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
			}

			if (player instanceof EntityPlayerMP){
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
			}
		}

		return EnumActionResult.SUCCESS;
	}
}