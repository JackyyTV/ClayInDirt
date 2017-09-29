package claytodust.common.blocks;

import java.util.Random;

import claytodust.common.tiles.TE_FirePit;
import claytodust.handler.CTDCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
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

/**
 * @author LatvianModder
 */
public class FirePit extends Block {
	
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public FirePit(String registryName){
		super(Material.ROCK, MapColor.STONE);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(CTDCreativeTabs.tabCTD);
		this.setDefaultState(blockState.getBaseState().withProperty(BURNING, false));
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, BURNING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TE_FirePit();
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return true;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer){
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand){
		if (state.getValue(BURNING)){
			double x = 0, z = 0;
			double y = pos.getY() + 0.4D;

			for (int i = 0; i < 3; i++){
				x = pos.getX() + rand.nextDouble() * 0.7D + 0.15D;
				z = pos.getZ() + rand.nextDouble() * 0.7D + 0.15D;
				worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0D, 0D, 0D);
			}

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0D, 0D, 0D);
		}

		if (rand.nextDouble() < 0.1D){
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1F, 1F, false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (worldIn.isRemote){
			return true;
		}else{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TE_FirePit){
				playerIn.displayGUIChest((TE_FirePit) tileentity);
				playerIn.addStat(StatList.FURNACE_INTERACTION);
			}

			return true;
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TE_FirePit){
			((TE_FirePit) tileentity).breakBlock();
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos){
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TE_FirePit){
			return state.withProperty(BURNING, ((TE_FirePit) tileEntity).isBurning());
		}

		return state;
	}
}