package claytodust;

import claytodust.block.BlockFirePit;
import claytodust.item.ItemFirestarter;
import claytodust.item.ItemHammer;
import claytodust.item.ItemMagnifyingGlass;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModRegistry {

    public static final Item.ToolMaterial MATERIAL_CLAY = EnumHelper.addToolMaterial("CLAY", 1, 64, 2F, 1.0F, 15);
    public static final Item.ToolMaterial MATERIAL_OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 4, 2559, 6F, 3.0F, 6);

    public static final ItemFirestarter BASIC_FIRESTARTER = new ItemFirestarter("basic_firestarter", 15, 0.3F);
    public static final ItemFirestarter STRING_FIRESTARTER = new ItemFirestarter("string_firestarter", 31, 0.7F);
    public static final ItemMagnifyingGlass MAGNIFYING_GLASS = new ItemMagnifyingGlass();
    public static final ItemHammer CLAY_HAMMER = new ItemHammer("clay_hammer", MATERIAL_CLAY, 59);
    public static final ItemHammer IRON_HAMMER = new ItemHammer("iron_hammer", Item.ToolMaterial.IRON, 250);
    public static final ItemHammer GOLD_HAMMER = new ItemHammer("gold_hammer", Item.ToolMaterial.GOLD, 131);
    public static final ItemHammer DIAMOND_HAMMER = new ItemHammer("diamond_hammer", Item.ToolMaterial.DIAMOND, 768);
    public static final ItemHammer OBSIDIAN_HAMMER = new ItemHammer("obsidian_hammer", MATERIAL_OBSIDIAN, 1558);

    public static final BlockFirePit FIREPIT = new BlockFirePit();

    @SubscribeEvent
    public void onBlockRegistry(RegistryEvent.Register<Block> e) {
        e.getRegistry().register(FIREPIT);
    }

    @SubscribeEvent
    public void onItemRegistry(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                BASIC_FIRESTARTER,
                STRING_FIRESTARTER,
                MAGNIFYING_GLASS,
                CLAY_HAMMER,
                IRON_HAMMER,
                GOLD_HAMMER,
                DIAMOND_HAMMER,
                OBSIDIAN_HAMMER
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
        GameRegistry.addShapedRecipe(
                CLAY_HAMMER.getRegistryName(),
                null,
                new ItemStack(CLAY_HAMMER),
                "CSC", " S ", " S ", 'C', Items.CLAY_BALL, 'S', "stickWood"
        );
        GameRegistry.addShapedRecipe(
                IRON_HAMMER.getRegistryName(),
                null,
                new ItemStack(IRON_HAMMER),
                "ISI", " S ", " S ", 'I', "ingotIron", 'S', "stickWood"
        );
        GameRegistry.addShapedRecipe(
                GOLD_HAMMER.getRegistryName(),
                null,
                new ItemStack(GOLD_HAMMER),
                "GSG", " S ", " S ", 'G', "ingotGold", 'S', "stickWood"
        );
        GameRegistry.addShapedRecipe(
                DIAMOND_HAMMER.getRegistryName(),
                null,
                new ItemStack(DIAMOND_HAMMER),
                "DSD", " S ", " S ", 'D', "gemDiamond", 'S', "stickWood"
        );
        GameRegistry.addShapedRecipe(
                OBSIDIAN_HAMMER.getRegistryName(),
                null,
                new ItemStack(OBSIDIAN_HAMMER),
                "OSO", " S ", " S ", 'O', "obsidian", 'S', "stickWood"
        );
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(ModelRegistryEvent e) {
        FIREPIT.initModel();
        BASIC_FIRESTARTER.initModel();
        STRING_FIRESTARTER.initModel();
        MAGNIFYING_GLASS.initModel();
        CLAY_HAMMER.initModel();
        IRON_HAMMER.initModel();
        GOLD_HAMMER.initModel();
        DIAMOND_HAMMER.initModel();
        OBSIDIAN_HAMMER.initModel();
    }

}
