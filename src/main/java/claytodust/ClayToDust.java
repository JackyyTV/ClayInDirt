package claytodust;

import claytodust.common.CTDCommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = CTDInfo.MODID, name = CTDInfo.NAME, useMetadata = true, acceptedMinecraftVersions = "[1.12,)")
public class ClayToDust{

	@Mod.Instance(CTDInfo.MODID)
	public static ClayToDust instance;

    @SidedProxy(serverSide = "claytodust.client.CTDClientProxy", clientSide = "claytodust.common.CTDCommonProxy")
    public static CTDCommonProxy PROXY;
}
