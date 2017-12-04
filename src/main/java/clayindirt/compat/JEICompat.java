package clayindirt.compat;

import clayindirt.ModRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModRegistry.FIREPIT), VanillaRecipeCategoryUid.SMELTING, VanillaRecipeCategoryUid.FUEL);
    }

}
