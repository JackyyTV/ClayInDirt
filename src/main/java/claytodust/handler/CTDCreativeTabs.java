package claytodust.handler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CTDCreativeTabs {

	public static final CreativeTabs tabCTD = (new CreativeTabs("ctd") {
		//TODO CHANGE ITEMS
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.STICK);
		}
	});
	
}
