package clayindirt;

import clayindirt.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ClayInDirt.MODID, name = ClayInDirt.MODNAME, version = ClayInDirt.VERSION, acceptedMinecraftVersions = ClayInDirt.MCVERSION, useMetadata = true)
public class ClayInDirt {

    public static final String VERSION = "1.0";
    public static final String MCVERSION = "[1.12,)";
    public static final String MODID = "clayindirt";
    public static final String MODNAME = "Clay in Dirt";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModRegistry.MAGNIFYING_GLASS);
        }
    };

    @SidedProxy(serverSide = "clayindirt.proxy.CommonProxy", clientSide = "clayindirt.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
