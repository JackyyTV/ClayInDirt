package clayindirt.proxy;

import clayindirt.ModRegistry;
import clayindirt.compat.TOPCompat;
import clayindirt.compat.WailaCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ModRegistry());
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
        if (Loader.isModLoaded("waila")) {
            WailaCompat.register();
        }
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

}
