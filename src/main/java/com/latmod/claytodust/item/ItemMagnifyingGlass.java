package com.latmod.claytodust.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public class ItemMagnifyingGlass extends ItemClayToDust
{
	public ItemMagnifyingGlass(String id)
	{
		super(id);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isDaytime() || worldIn.isRaining() || !worldIn.canSeeSky(pos.offset(EnumFacing.UP)))
		{
			return EnumActionResult.PASS;
		}
		else if (worldIn.isRemote)
		{
			return EnumActionResult.SUCCESS;
		}

		if (player.isSneaking())
		{
			IBlockState state = worldIn.getBlockState(pos);
			if (state == Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT))
			{
				if (worldIn.rand.nextInt(6) == 0)
				{
					worldIn.setBlockState(pos, Blocks.CLAY.getDefaultState());
				}
				else
				{
					worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
				}
			}
			else if (state.getBlock() == Blocks.GRASS)
			{
				if (worldIn.rand.nextInt(12) == 0)
				{
					worldIn.setBlockState(pos, Blocks.CLAY.getDefaultState());
				}
				else
				{
					worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
				}
			}
		}
		else
		{
			ItemStack stack = player.getHeldItem(hand);

			pos = pos.offset(facing);

			if (worldIn.isAirBlock(pos))
			{
				worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
			}

			if (player instanceof EntityPlayerMP)
			{
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
			}
		}

		return EnumActionResult.SUCCESS;
	}
}