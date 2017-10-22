package claytodust;

import claytodust.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ClayToDust.MODID, name = ClayToDust.MODNAME, version = ClayToDust.VERSION, acceptedMinecraftVersions = ClayToDust.MCVERSION, useMetadata = true)
public class ClayToDust {

    public static final String VERSION = "1.0";
    public static final String MCVERSION = "[1.12,)";
    public static final String MODID = "claytodust";
    public static final String MODNAME = "Clay To Dust";
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModRegistry.DIAMOND_HAMMER);
        }
    };

    @SidedProxy(serverSide = "claytodust.proxy.CommonProxy", clientSide = "claytodust.proxy.ClientProxy")
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
