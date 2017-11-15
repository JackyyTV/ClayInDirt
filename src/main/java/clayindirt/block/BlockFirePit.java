package clayindirt.block;

import clayindirt.ClayInDirt;
import clayindirt.item.ItemFirestarter;
import clayindirt.tile.TileFirePit;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFirePit extends Block {
	
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public BlockFirePit() {
		super(Material.ROCK, MapColor.STONE);
		setRegistryName(ClayInDirt.MODID + ":fire_pit");
		setUnlocalizedName(ClayInDirt.MODID + ".fire_pit");
		setHardness(1.0f);
		setResistance(1.0f);
		setCreativeTab(ClayInDirt.TAB);
		setDefaultState(blockState.getBaseState().withProperty(BURNING, false));
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BURNING);
	}

	@Override @SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileFirePit();
	}

	@Override @SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override @SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override @SuppressWarnings("deprecation")
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		if (state.getValue(BURNING)) {
			double x = 0, z = 0;
			double y = pos.getY() + 0.4D;

			for (int i = 0; i < 3; i++) {
				x = pos.getX() + rand.nextDouble() * 0.7D + 0.15D;
				z = pos.getZ() + rand.nextDouble() * 0.7D + 0.15D;
				worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0D, 0D, 0D);
			}

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0D, 0D, 0D);
		}

		if (rand.nextDouble() < 0.1D) {
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1F, 1F, false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
		    if (playerIn.getHeldItem(hand).getItem() instanceof ItemFirestarter) {
		        return false;
            } else {
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileFirePit) {
                    playerIn.displayGUIChest((TileFirePit) tileentity);
                    playerIn.addStat(StatList.FURNACE_INTERACTION);
                }

                return true;
            }
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof TileFirePit) {
            InventoryHelper.dropInventoryItems(world, pos, (TileEntityFurnace)tileentity);
            world.updateComparatorOutputLevel(pos, this);
        }

		super.breakBlock(world, pos, state);
	}

	@Override @SuppressWarnings("deprecation")
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileFirePit) {
			return state.withProperty(BURNING, ((TileFirePit) tileEntity).isBurning());
		}

		return state;
	}

}