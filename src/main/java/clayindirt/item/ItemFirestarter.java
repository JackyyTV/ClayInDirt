package clayindirt.item;

import clayindirt.ClayInDirt;
import clayindirt.block.BlockFirePit;
import clayindirt.tile.TileFirePit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFirestarter extends Item {

	private float propability;

	public ItemFirestarter(String registryName, int durability, float propability) {
		setRegistryName(ClayInDirt.MODID + ":" + registryName);
		setUnlocalizedName(ClayInDirt.MODID + "." + registryName);
		setMaxDamage(durability);
		setCreativeTab(ClayInDirt.TAB);
		setMaxStackSize(1);
		this.propability = propability;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity t = world.getTileEntity(pos);

		if (t instanceof TileFirePit && !((TileFirePit) t).isBurning() && world.getBlockState(pos).getValue(BlockFirePit.FUELED)) {
			if (!world.isRemote && itemRand.nextFloat() <= propability) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockFirePit.BURNING, true));
			}
			world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			player.getHeldItem(hand).damageItem(1, player);
			return EnumActionResult.SUCCESS;
		} else {
			pos = pos.offset(facing);
			if (world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
				if (!world.isRemote && itemRand.nextFloat() <= propability) {
					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				player.getHeldItem(hand).damageItem(1, player);
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.FAIL;
	}
}