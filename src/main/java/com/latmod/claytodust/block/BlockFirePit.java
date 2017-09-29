package com.latmod.claytodust.block;

import com.latmod.claytodust.tile.TileFirePit;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * @author LatvianModder
 */
public class BlockFirePit extends BlockClayToDust
{
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public BlockFirePit(String id)
	{
		super(id, Material.ROCK, MapColor.STONE);
		setDefaultState(blockState.getBaseState().withProperty(BURNING, false));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BURNING);
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileFirePit();
	}

	@Override
	@Deprecated
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	@Deprecated
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (stateIn.getValue(BURNING))
		{
			double x = 0, z = 0;
			double y = pos.getY() + 0.4D;

			for (int i = 0; i < 3; i++)
			{
				x = pos.getX() + rand.nextDouble() * 0.7D + 0.15D;
				z = pos.getZ() + rand.nextDouble() * 0.7D + 0.15D;
				worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0D, 0D, 0D);
			}

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0D, 0D, 0D);
		}

		if (rand.nextDouble() < 0.1D)
		{
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1F, 1F, false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityFurnace)
			{
				playerIn.displayGUIChest((TileEntityFurnace) tileentity);
				playerIn.addStat(StatList.FURNACE_INTERACTION);
			}

			return true;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileFirePit)
		{
			((TileFirePit) tileentity).breakBlock();
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@Deprecated
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileFirePit)
		{
			return state.withProperty(BURNING, ((TileFirePit) tileEntity).isBurning());
		}

		return state;
	}
}