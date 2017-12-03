package clayindirt;

import clayindirt.block.BlockFirePit;
import clayindirt.item.ItemFirestarter;
import clayindirt.item.ItemMagnifyingGlass;
import clayindirt.tile.TileFirePit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModRegistry {

    public static final ItemFirestarter BASIC_FIRESTARTER = new ItemFirestarter("basic_firestarter", 15, 0.3F);
    public static final ItemFirestarter STRING_FIRESTARTER = new ItemFirestarter("string_firestarter", 31, 0.7F);
    public static final ItemMagnifyingGlass MAGNIFYING_GLASS = new ItemMagnifyingGlass();

    public static final BlockFirePit FIREPIT = new BlockFirePit();

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> e) {
        e.getRegistry().register(FIREPIT);
        GameRegistry.registerTileEntity(TileFirePit.class, FIREPIT.getRegistryName().toString());
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                BASIC_FIRESTARTER,
                STRING_FIRESTARTER,
                MAGNIFYING_GLASS
        );
        e.getRegistry().register(new ItemBlock(FIREPIT).setRegistryName(FIREPIT.getRegistryName()));
    }

    @SubscribeEvent
    public void onRecipeRegistry(RegistryEvent.Register<IRecipe> e) {
        GameRegistry.addShapedRecipe(
                BASIC_FIRESTARTER.getRegistryName(),
                null,
                new ItemStack(BASIC_FIRESTARTER),
                "SS", 'S', "stickWood"
        );
        GameRegistry.addShapedRecipe(
                STRING_FIRESTARTER.getRegistryName(),
                null,
                new ItemStack(STRING_FIRESTARTER),
                "SSS", "sSs", 'S', "stickWood", 's', "string"
        );
        GameRegistry.addShapedRecipe(
                MAGNIFYING_GLASS.getRegistryName(),
                null,
                new ItemStack(MAGNIFYING_GLASS),
                " G", "S ", 'G', "blockGlass", 'S', "stickWood"
        );
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(ModelRegistryEvent e) {
        FIREPIT.initModel();
        BASIC_FIRESTARTER.initModel();
        STRING_FIRESTARTER.initModel();
        MAGNIFYING_GLASS.initModel();
    }

}
