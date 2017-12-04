package clayindirt.item;

import clayindirt.ClayInDirt;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnifyingGlass extends Item {

    public ItemMagnifyingGlass() {
        setRegistryName(ClayInDirt.MODID + ":magnifying_glass");
        setUnlocalizedName(ClayInDirt.MODID + ".magnifying_glass");
        setMaxStackSize(1);
        setMaxDamage(250);
        setCreativeTab(ClayInDirt.TAB);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        IBlockState state = world.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);
        if (state == Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)) {
            if (world.rand.nextInt(6) == 0) {
                world.setBlockState(pos, Blocks.CLAY.getDefaultState());
                takeDamage(stack, player);
            } else {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                takeDamage(stack, player);
            }
        } else if (state.getBlock() == Blocks.GRASS) {
            if (world.rand.nextInt(12) == 0) {
                world.setBlockState(pos, Blocks.CLAY.getDefaultState());
                takeDamage(stack, player);
            } else {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                takeDamage(stack, player);
            }
        }
        else if (world.getBlockState(pos).getBlock() == Blocks.HAY_BLOCK && (world.isDaytime() && !world.isRaining() || world.canSeeSky(pos.offset(EnumFacing.UP)))) {
            pos = pos.offset(facing);
            if (world.isAirBlock(pos)) {
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
                takeDamage(stack, player);
            }
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    private void takeDamage(ItemStack stack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            if (!((EntityPlayer) entity).capabilities.isCreativeMode) {
                stack.damageItem(1, entity);
            }
        }
    }

}