package claytodust.common.items;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import claytodust.handler.CTDCreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class Hammer extends ItemTool {
	
	public Hammer(String registryName, ToolMaterial materialIn, int durability) {
		super(1.0F, -2.8F, materialIn, ImmutableSet.of());
		this.setMaxDamage(durability);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(CTDCreativeTabs.tabCTD);
	}

	@Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("hammer");
    }

}
