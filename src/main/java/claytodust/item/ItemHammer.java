package claytodust.item;

import claytodust.ClayToDust;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class ItemHammer extends ItemTool {
	
	public ItemHammer(String registryName, ToolMaterial materialIn, int durability) {
		super(1.0F, -2.8F, materialIn, ImmutableSet.of());
		this.setMaxDamage(durability);
		this.setRegistryName(ClayToDust.MODID + ":" + registryName);
		this.setUnlocalizedName(ClayToDust.MODID + "." + registryName);
		this.setCreativeTab(ClayToDust.TAB);
	}

	@Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("hammer");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
