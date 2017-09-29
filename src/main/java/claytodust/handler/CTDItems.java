package claytodust.handler;

import claytodust.common.items.Firestarter;
import claytodust.common.items.Hammer;
import claytodust.common.items.MagnifyingGlass;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CTDItems {

	public static final Firestarter BASIC_FIRESTARTER;
	public static final Firestarter STRING_FIRESTARTER;
	public static final MagnifyingGlass MAGNIFYING_GLASS;
	
	public static final Hammer CLAY_HAMMER;
	public static final Hammer IRON_HAMMER;
	public static final Hammer GOLD_HAMMER;
	public static final Hammer DIAMOND_HAMMER;
	public static final Hammer OBSIDIAN_HAMMER;
	
	public static final ToolMaterial MATERIAL_CLAY;
	public static final ToolMaterial MATERIAL_OBSIDIAN;

	
	static{
		MATERIAL_CLAY = EnumHelper.addToolMaterial("CLAY", 1, 64, 2F, 1.0F, 15);
		MATERIAL_OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 4, 2559, 6F, 3.0F, 6);
		
		BASIC_FIRESTARTER = new Firestarter("basic_firestarter", 15, 0.3F);
		STRING_FIRESTARTER = new Firestarter("string_firestarter", 31, 0.7F);
		MAGNIFYING_GLASS = new MagnifyingGlass("magnifying_glass");
		CLAY_HAMMER = new Hammer("clay_hammer", MATERIAL_CLAY, 59);
		IRON_HAMMER = new Hammer("iron_hammer", ToolMaterial.IRON, 250);
		GOLD_HAMMER = new Hammer("gold_hammer", ToolMaterial.GOLD, 131);
		DIAMOND_HAMMER = new Hammer("diamond_hammer", ToolMaterial.DIAMOND, 768);
		OBSIDIAN_HAMMER = new Hammer("obsidian_hammer", MATERIAL_OBSIDIAN, 1558);
	}
	
	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		
		reg.registerAll(
			BASIC_FIRESTARTER,
			STRING_FIRESTARTER,
			MAGNIFYING_GLASS,
			CLAY_HAMMER,
			IRON_HAMMER,
			GOLD_HAMMER,
			DIAMOND_HAMMER,
			OBSIDIAN_HAMMER
		);
		
	}
	
}
