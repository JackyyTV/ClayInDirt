package clayindirt.block;

import clayindirt.ClayInDirt;
import clayindirt.tile.TileFirePit;
import clayindirt.util.ITOPInfoProvider;
import clayindirt.util.IWailaInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockFirePit extends Block implements IWailaInfoProvider, ITOPInfoProvider {

    public static final PropertyBool BURNING = PropertyBool.create("burning");
    public static final PropertyBool FUELED = PropertyBool.create("fueled");
    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockFirePit() {
        super(Material.ROCK, MapColor.STONE);
        setRegistryName(ClayInDirt.MODID + ":fire_pit");
        setUnlocalizedName(ClayInDirt.MODID + ".fire_pit");
        setHardness(1.0f);
        setResistance(1.0f);
        setCreativeTab(ClayInDirt.TAB);
        setDefaultState(blockState.getBaseState().withProperty(BURNING, false).withProperty(FUELED, false));
    }

    @Override @SuppressWarnings("deprecation")
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "burning=true,fueled=true"));
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (state.getValue(BURNING)) {
            if (entity.getPosition().equals(pos.up()) || entity.getPosition().equals(pos)) {
                entity.setFire(4);
            }
        }
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING, FUELED);
    }

    @Override @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        if (meta % 2 == 1) state = state.withProperty(BURNING, true);
        if (meta >= 2) state = state.withProperty(FUELED, true);
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(BURNING)) i++;
        if (state.getValue(FUELED)) i += 2;
        return i;
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
    public boolean isNormalCube(IBlockState state) {
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

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1F, 1F, false);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity temp = world.getTileEntity(pos);
        if (!(temp instanceof TileFirePit)) return false;
        TileFirePit t = (TileFirePit) temp;

        if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
            ItemStack s;
            if ((s = t.addFuel(stack, false)) != stack) {
                player.setHeldItem(hand, s);
                return true;
            }
        }
        if (!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty()) {
            ItemStack s;
            if ((s = t.addInput(stack, false)) != stack) {
                player.setHeldItem(hand, s);
                return true;
            }
        }
        if (stack.isEmpty() && !t.getOutput().isEmpty()) {
            player.addItemStackToInventory(t.getOutput());
            t.emptyOutput();
            return true;
        }
        if (player.isSneaking()) {
            if (stack.isEmpty() && !t.getInput().isEmpty()) {
                player.addItemStackToInventory(t.getInput());
                t.emptyInput();
                return true;
            } else if (stack.isEmpty() && t.getInput().isEmpty() && !t.getFuel().isEmpty()) {
                player.addItemStackToInventory(t.getFuel());
                t.emptyFuel();
                return true;
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileFirePit t;
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileFirePit) {
            t = (TileFirePit) tile;
            for (int i = 0; i < t.getInv().getSlots(); i++) {
                Block.spawnAsEntity(world, pos, t.getInv().getStackInSlot(i));
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override @SuppressWarnings("deprecation")
    public int getLightValue(IBlockState state) {
        return state.getValue(BURNING) ? 15 : 0;
    }

    @Override @SuppressWarnings("deprecation")
    public int getLightOpacity(IBlockState state) {
        return 0;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileFirePit) {
            TileFirePit firePit = (TileFirePit) te;
            if (blockState.getValue(BURNING)) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .progress((600 - firePit.getProgress()) / 6, 100, probeInfo.defaultProgressStyle().suffix("%"));
            }
            if (player.isSneaking()) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text(TextFormatting.GREEN + "Input Stack: ")
                        .item(firePit.getInput())
                        .text(firePit.getInput().getDisplayName().equals("Air") ? "Empty" : firePit.getInput().getDisplayName());
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text(TextFormatting.GREEN + "Fuel Stack: ")
                        .item(firePit.getFuel())
                        .text(firePit.getFuel().getDisplayName().equals("Air") ? "Empty" : firePit.getFuel().getDisplayName());
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text(TextFormatting.GREEN + "Output Stack: ")
                        .item(firePit.getOutput())
                        .text(firePit.getOutput().getDisplayName().equals("Air") ? "Empty" : firePit.getOutput().getDisplayName());
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text(TextFormatting.GREEN + "Burn Time: ")
                        .progress(firePit.getBurnTime(), TileEntityFurnace.getItemBurnTime(firePit.getFuel()), probeInfo.defaultProgressStyle().suffix(" Ticks"));
            }
        }
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (!config.getConfig("vanilla.furnacedisplay") || accessor.getBlock() != this)
            return currenttip;
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileFirePit) {
            TileFirePit firePit = (TileFirePit) te;
            String renderStr = "";
            if (!firePit.getInput().isEmpty()) {
                String name = firePit.getInput().getItem().getRegistryName().toString();
                renderStr += SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(firePit.getInput().getCount()), String.valueOf(firePit.getInput().getItemDamage()));
            } else renderStr += SpecialChars.getRenderString("waila.stack", "2");
            if (!firePit.getFuel().isEmpty()) {
                String name = firePit.getFuel().getItem().getRegistryName().toString();
                renderStr += SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(firePit.getFuel().getCount()), String.valueOf(firePit.getFuel().getItemDamage()));
            } else renderStr += SpecialChars.getRenderString("waila.stack", "2");
            if (accessor.getBlockState().getValue(BURNING)) {
                renderStr += SpecialChars.getRenderString("waila.progress", String.valueOf((600 - firePit.getProgress())), String.valueOf(600));
            }
            if (!firePit.getOutput().isEmpty()) {
                String name = firePit.getOutput().getItem().getRegistryName().toString();
                renderStr += SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(firePit.getOutput().getCount()), String.valueOf(firePit.getOutput().getItemDamage()));
            } else renderStr += SpecialChars.getRenderString("waila.stack", "2");
            currenttip.add(renderStr);
        }
        return currenttip;
    }

}
