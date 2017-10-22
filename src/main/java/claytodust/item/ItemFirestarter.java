package claytodust.item;

import claytodust.ClayToDust;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

import java.util.Random;

public class ItemFirestarter extends Item{
	
	private float propability;
	
	public ItemFirestarter(String registryName, int durability, float propability){
		setRegistryName(ClayToDust.MODID + ":" + registryName);
		setUnlocalizedName(ClayToDust.MODID + "." + registryName);
		setMaxDamage(durability);
		setCreativeTab(ClayToDust.TAB);
		setMaxStackSize(1);
		this.propability = propability;
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
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